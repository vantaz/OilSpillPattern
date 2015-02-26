package visual;

import cells.Cell;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

/**
 * Klasa przedstawiająca mapę w postaci JTable z odpowiednim Rendererem
 * Created by VanitaZ on 2015-02-24.
 */
public class AreaTable extends JTable {

    private OilControl control;

    public AreaTable(Cell[][] mapGrid, OilControl c) {
        super(mapGrid.length,mapGrid.length);
        control = c;

        setDefaultRenderer(Object.class, new MapCellRenderer(mapGrid));

        setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel = getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                control.setInfo(getSelectedColumn(), getSelectedRow());
            }
        });

        setTableHeader(null);
        setShowGrid(false);
        setIntercellSpacing(new Dimension(0, 0));
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        setRowHeight(1);

        for (int i = 0; i < mapGrid.length; i++) {
            getColumnModel().getColumn(i).setMaxWidth(1);
            getColumnModel().getColumn(i).setMinWidth(1);
            getColumnModel().getColumn(i).setPreferredWidth(1);
        }

    }
}
