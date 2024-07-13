package com.example.zomnieapp.ui_app.ui;

import com.example.zomnieapp.ui_app.data.RenderDataRepository;
import com.example.zomnieapp.ui_app.data.model.PlayerStatus;
import com.example.zomnieapp.ui_app.data.real.RenderDataRepositoryImpl;
import com.example.zomnieapp.ui_app.ui.common.GridPanel;
import com.example.zomnieapp.ui_app.ui.model.RenderMapPoint;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

@Component
public class MainFrame extends JFrame {

    public static volatile RenderDataRepository dataRepository = new RenderDataRepositoryImpl();

    private GridPanel gridPanel;
    private DefaultTableModel tableModel;

    public MainFrame() {
        setTitle("Spring Boot with Swing");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        dataRepository.listenToNewData(new RenderDataRepository.OnNewDataListener() {
            @Override
            public void newMap(List<List<RenderMapPoint>> points) {
                showMap(points);
            }

            @Override
            public void newPlayer(PlayerStatus playerStatus) {
                showPlayerStatus(playerStatus);
            }
        });
    }

    private void initUI() {
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        // Инициализация таблицы для отображения информации об объекте
        String[] infoColumnNames = {"Property", "Value"};
        DefaultTableModel infoTableModel = new DefaultTableModel(infoColumnNames, 0);
        JTable infoTable = new JTable(infoTableModel);
        JScrollPane infoScrollPane = new JScrollPane(infoTable);

        gridPanel = new GridPanel(1, 1, infoTableModel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(textPanel, BorderLayout.NORTH);
        getContentPane().add(gridPanel, BorderLayout.CENTER);
        getContentPane().add(infoScrollPane, BorderLayout.EAST);

        // Инициализация таблицы
        String[] columnNames = {"Name", "Enemy Block Kills", "Game Ended At", "Gold", "Points", "Zombie Kills"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable playerStatusTable = new JTable(tableModel);
        playerStatusTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(playerStatusTable);
        scrollPane.setPreferredSize(new Dimension(800, 200));
        getContentPane().add(scrollPane, BorderLayout.SOUTH);
    }

    private void showMap(List<List<RenderMapPoint>> points) {
        int rows = points.size();
        int cols = points.get(0).size();
        gridPanel.resizeGrid(rows, cols, points);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                java.awt.Component component = gridPanel.getComponent(row * cols + col);
                RenderMapPoint point = points.get(row).get(col);
                if (point != null) {
                    component.setBackground(point.getColor());
                } else {
                    component.setBackground(Color.WHITE);
                }
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void showPlayerStatus(PlayerStatus playerStatus) {
        tableModel.addRow(new Object[]{
                playerStatus.getName(),
                playerStatus.getEnemyBlockKills(),
                playerStatus.getGameEndedAt(),
                playerStatus.getGold(),
                playerStatus.getPoints(),
                playerStatus.getZombieKills()
        });
    }
}
