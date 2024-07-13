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
    private DefaultTableModel infoTableModel;

    public MainFrame() {
        setTitle("Глаза боятся, а руки делают");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        dataRepository.listenToNewData(new RenderDataRepository.OnNewDataListener() {
            @Override
            public void newMap(List<List<RenderMapPoint>> points) {
                showMap(points);
//                System.out.println(points);
            }

            @Override
            public void newPlayer(PlayerStatus playerStatus) {
                showPlayerStatus(playerStatus);
            }
        });
    }

    private void initUI() {
        // Создание главной панели для всего содержимого
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Сообщение об ошибке
        JLabel errorMessage = new JLabel("Если карта не отображается, нужно перезапустить аппку");
        errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(errorMessage, BorderLayout.NORTH);

        // Создание центральной панели для карты и информации об объекте
        JPanel centerPanel = new JPanel(new BorderLayout());

        // Заголовок для карты
        JLabel mapLabel = new JLabel("Карта");
        mapLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Инициализация таблицы для отображения информации об объекте
        String[] infoColumnNames = {"Свойство", "Значение"};
        infoTableModel = new DefaultTableModel(infoColumnNames, 0);
        JTable infoTable = new JTable(infoTableModel);
        JScrollPane infoScrollPane = new JScrollPane(infoTable);

        // Заголовок для информации об объекте
        JLabel infoLabel = new JLabel("Информация об объекте");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gridPanel = new GridPanel(1, 1, infoTableModel);

        // Панель для карты с заголовком
        JPanel mapPanel = new JPanel(new BorderLayout());
        mapPanel.add(mapLabel, BorderLayout.NORTH);
        mapPanel.add(gridPanel, BorderLayout.CENTER);

        centerPanel.add(mapPanel, BorderLayout.CENTER);

        // Панель для информации об объекте с заголовком
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.add(infoLabel, BorderLayout.NORTH);
        infoPanel.add(infoScrollPane, BorderLayout.CENTER);

        centerPanel.add(infoPanel, BorderLayout.EAST);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Инициализация таблицы для статуса игрока
        String[] columnNames = {"Имя", "Убийства врагов", "Конец игры", "Золото", "Очки", "Убийства зомби"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable playerStatusTable = new JTable(tableModel);
        playerStatusTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(playerStatusTable);
        scrollPane.setPreferredSize(new Dimension(800, 200));

        // Заголовок для статуса игрока
        JLabel statusLabel = new JLabel("Статус игрока");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Панель для статуса игрока с заголовком
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(statusLabel, BorderLayout.NORTH);
        statusPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(statusPanel, BorderLayout.SOUTH);

        // Добавление главной панели в содержимое окна
        getContentPane().add(mainPanel);
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
        tableModel.insertRow(0, new Object[]{
                playerStatus.getName(),
                playerStatus.getEnemyBlockKills(),
                playerStatus.getGameEndedAt(),
                playerStatus.getGold(),
                playerStatus.getPoints(),
                playerStatus.getZombieKills()
        });
    }
}
