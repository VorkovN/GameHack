package com.example.zomnieapp.ui_app;

import com.example.zomnieapp.ui_app.common.GridPanel;
import com.example.zomnieapp.ui_app.data.RenderDataRepository;
import com.example.zomnieapp.ui_app.data.model.PlayerStatus;
import com.example.zomnieapp.ui_app.data.model.RenderMapPoint;
import com.example.zomnieapp.ui_app.data.real.RenderDataRepositoryImpl;
import com.example.zomnieapp.ui_app.mapper.MapMapper;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

@Component
public class MainFrame extends JFrame {

    public static volatile RenderDataRepository dataRepository = new RenderDataRepositoryImpl();

    private JPanel textPanel;
    private GridPanel gridPanel;
    private JTable playerStatusTable;
    private DefaultTableModel tableModel;

    public MainFrame() {
        setTitle("Spring Boot with Swing");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        dataRepository.listenToNewData(new RenderDataRepository.OnNewDataListener() {
            @Override
            public void newMap(List<List<RenderMapPoint>> map) {
                showMap(MapMapper.mapToColors(map));
            }

            @Override
            public void newPlayer(PlayerStatus playerStatus) {
                showPlayerStatus(playerStatus);
            }
        });
    }

    private void initUI() {
        textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        gridPanel = new GridPanel(1, 1);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(textPanel, BorderLayout.NORTH);
        getContentPane().add(gridPanel, BorderLayout.CENTER);

        // Инициализация таблицы
        String[] columnNames = {"Name", "Enemy Block Kills", "Game Ended At", "Gold", "Points", "Zombie Kills"};
        tableModel = new DefaultTableModel(columnNames, 0);
        playerStatusTable = new JTable(tableModel);
        playerStatusTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(playerStatusTable);
        scrollPane.setPreferredSize(new Dimension(800, 200));
        getContentPane().add(scrollPane, BorderLayout.SOUTH);
    }

    private void showMap(List<List<Color>> colors) {
        int rows = colors.size();
        int cols = colors.get(0).size();
        gridPanel.resizeGrid(rows, cols);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                java.awt.Component component = gridPanel.getComponent(row * cols + col);
                component.setBackground(colors.get(row).get(col));
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
