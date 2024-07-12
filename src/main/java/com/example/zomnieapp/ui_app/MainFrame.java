package com.example.zomnieapp.ui_app;

import com.example.zomnieapp.ui_app.data.RenderData;
import com.example.zomnieapp.ui_app.data.RenderDataRepository;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

@Component
public class MainFrame extends JFrame {

    public static volatile RenderDataRepository dataRepository = new RenderDataRepository();
    JLabel label = new JLabel("Hello, Spring with Swing!", SwingConstants.LEFT);

    private JPanel textPanel;
    private JPanel gridPanel;
    private int gridSize = 10; // Example grid size

    public MainFrame() {
        setTitle("Spring Boot with Swing");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        dataRepository.listenToNewData(data -> {
            showData(data);
        });
    }


    private void initUI() {
        textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        gridPanel = new JPanel(new GridLayout(gridSize, gridSize));

        // Initialize grid with white squares
        for (int i = 0; i < gridSize * gridSize; i++) {
            JPanel panel = new JPanel();
            panel.setBackground(getRandomColor());
            gridPanel.add(panel);
        }

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(textPanel, BorderLayout.NORTH);
        getContentPane().add(gridPanel, BorderLayout.CENTER);
    }

    private static Color getRandomColor() {
        int randInt = new Random().nextInt(4);
        if (randInt == 0) {
            return Color.WHITE;
        } else if (randInt == 1) {
            return Color.GREEN;
        } else if (randInt == 2) {
            return Color.BLUE;
        } else {
            return Color.YELLOW;
        }
    }

    private void showData(RenderData data) {
        textPanel.removeAll();
        for (String text : data.getTexts()) {
            textPanel.add(new JLabel(text));
        }
        textPanel.revalidate();
        textPanel.repaint();

        // Update grid panel with new colors
        List<Color> colors = data.getColors();
        java.awt.Component[] components = gridPanel.getComponents();
        for (int i = 0; i < components.length; i++) {
            components[i].setBackground(colors.get(i % colors.size()));
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }
}