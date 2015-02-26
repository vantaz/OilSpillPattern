package visual;

import cells.Cell;

import javax.swing.*;
import java.awt.*;

/**
 * Created by VanitaZ on 2015-02-24.
 */
public class OilView extends JFrame {

    private JSplitPane mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private JScrollPane areaPanel;
    private JScrollPane buttonPanel;

    private AreaTable areaTable;
    private OilControl control;
    private ButtonPanel buttons;

    public OilView(Cell[][] area, OilControl c) {
        setTitle("Spill some oil");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.control = c;
        areaTable = new AreaTable(area,control);
        buttons = new ButtonPanel(c);

        setOptimalSize();
        setLocationRelativeTo(null);

        buttonPanel = new JScrollPane(buttons);
        areaPanel = new JScrollPane(areaTable);

        initGUI();
    }

    private void setOptimalSize() {
        int dim = areaTable.getColumnCount() * 1 + 10;
        setSize(dim+250,dim+50);
    }

    private void initGUI() {

        Container contentPane = getContentPane();

        mainPanel.setDividerLocation(areaTable.getColumnCount() * 1 + 10);
        mainPanel.setRightComponent(buttonPanel);
        mainPanel.setLeftComponent(areaPanel);

        contentPane.add(mainPanel);

    }

    public void changeArea (Cell[][] area) {
        this.areaTable = new AreaTable(area,control);
        this.areaPanel = new JScrollPane(areaTable);
        mainPanel.setDividerLocation(areaTable.getColumnCount() * 1 + 10);
        mainPanel.setLeftComponent(areaPanel);
    }

    public void areaUpdated () {
        areaPanel.repaint();
    }

    public int getSelectedRow () {
        return areaTable.getSelectedRow();
    }

    public int getSelectedColumn () {
        return areaTable.getSelectedColumn();
    }

    public void setInfo (String s) {
        buttons.setInfo(s);
    }
}
