package com.example.zomnieapp.ui_app.ui.common;

import com.example.zomnieapp.ui_app.data.model.MapSubject;
import com.example.zomnieapp.ui_app.data.model.base.BaseSubject;
import com.example.zomnieapp.ui_app.data.model.enemy.EnemyBaseSubject;
import com.example.zomnieapp.ui_app.data.model.zombie.ZombieSubject;
import com.example.zomnieapp.ui_app.ui.model.RenderMapPoint;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GridPanel extends JPanel {
    private int rows;
    private int cols;
    private List<List<RenderMapPoint>> points;
    private final DefaultTableModel infoTableModel;
    private JPanel selectedCell;

    public GridPanel(int rows, int cols, DefaultTableModel infoTableModel) {
        this.rows = rows;
        this.cols = cols;
        this.infoTableModel = infoTableModel;
        setLayout(new GridLayout(rows, cols));
        initializeGrid();
    }

    private void initializeGrid() {
        for (int i = 0; i < rows * cols; i++) {
            add(createGridCell(i));
        }
    }

    private JPanel createGridCell(int index) {
        JPanel cell = new JPanel();
        cell.setBackground(Color.WHITE);
        cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cell.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = index / cols;
                int col = index % cols;
                if (points != null && row < points.size() && col < points.get(row).size()) {
                    RenderMapPoint point = points.get(row).get(col);
                    if (point != null) {
                        updateInfoTable(point);
                    } else {
                        clearInfoTable();
                    }
                    selectCell(cell);
                }
            }
        });
        return cell;
    }

    public void resizeGrid(int newRows, int newCols, List<List<RenderMapPoint>> points) {
        this.rows = newRows;
        this.cols = newCols;
        this.points = points;
        this.removeAll();
        this.setLayout(new GridLayout(newRows, newCols));
        initializeGrid();
        this.revalidate();
        this.repaint();
    }

    private void updateInfoTable(RenderMapPoint point) {
        infoTableModel.setRowCount(0);
        infoTableModel.addRow(new Object[]{"Type", point.getType()});
        MapSubject subject = point.getSubject();
        if (subject != null) {
            if (subject instanceof EnemyBaseSubject) {
                infoTableModel.addRow(new Object[]{"Subject Type", "Enemy"});
                infoTableModel.addRow(new Object[]{"Name", ((EnemyBaseSubject) subject).getName()});
                infoTableModel.addRow(new Object[]{"Attack", ((EnemyBaseSubject) subject).getAttack()});
                infoTableModel.addRow(new Object[]{"Last attack", ((EnemyBaseSubject) subject).getLastAttack()});
                infoTableModel.addRow(new Object[]{"X", ((EnemyBaseSubject) subject).getX()});
                infoTableModel.addRow(new Object[]{"Y", ((EnemyBaseSubject) subject).getY()});
            } else if (subject instanceof BaseSubject) {
                infoTableModel.addRow(new Object[]{"Subject Type", "Base"});
                infoTableModel.addRow(new Object[]{"Name", ((BaseSubject) subject).getName()});
                infoTableModel.addRow(new Object[]{"Attack", ((BaseSubject) subject).getAttack()});
                infoTableModel.addRow(new Object[]{"Last attack", ((BaseSubject) subject).getLastAttack()});
                infoTableModel.addRow(new Object[]{"X", ((BaseSubject) subject).getX()});
                infoTableModel.addRow(new Object[]{"Y", ((BaseSubject) subject).getY()});
            } else if (subject instanceof ZombieSubject) {
                infoTableModel.addRow(new Object[]{"Subject Type", "Zombie"});
                infoTableModel.addRow(new Object[]{"ID", ((ZombieSubject) subject).getId()});
                infoTableModel.addRow(new Object[]{"Health", ((ZombieSubject) subject).getHealth()});
                infoTableModel.addRow(new Object[]{"Attack", ((ZombieSubject) subject).getAttack()});
                infoTableModel.addRow(new Object[]{"Type", ((ZombieSubject) subject).getType()});
                infoTableModel.addRow(new Object[]{"Direction", ((ZombieSubject) subject).getDirection()});
                infoTableModel.addRow(new Object[]{"Speed", ((ZombieSubject) subject).getSpeed()});
                infoTableModel.addRow(new Object[]{"Wait turns", ((ZombieSubject) subject).getWaitTurns()});
                infoTableModel.addRow(new Object[]{"X", ((ZombieSubject) subject).getX()});
                infoTableModel.addRow(new Object[]{"Y", ((ZombieSubject) subject).getY()});
            }
        }
        infoTableModel.addRow(new Object[]{"Visible", point.isVisible()});
    }

    private void clearInfoTable() {
        infoTableModel.setRowCount(0);
    }

    private void selectCell(JPanel cell) {
        if (selectedCell != null) {
            selectedCell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        selectedCell = cell;
        selectedCell.setBorder(BorderFactory.createLineBorder(Color.RED));
    }
}
