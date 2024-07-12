package com.example.zomnieapp.ui_app.common;

import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel {
    private int rows;
    private int cols;

    public GridPanel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        setLayout(new GridLayout(rows, cols));
        initializeGrid();
    }

    private void initializeGrid() {
        for (int i = 0; i < rows * cols; i++) {
            add(createGridCell());
        }
    }

    private JPanel createGridCell() {
        JPanel cell = new JPanel();
        cell.setBackground(Color.WHITE);
        cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return cell;
    }

    public void resizeGrid(int newRows, int newCols) {
        this.rows = newRows;
        this.cols = newCols;
        this.removeAll();
        this.setLayout(new GridLayout(newRows, newCols));
        initializeGrid();
        this.revalidate();
        this.repaint();
    }
}