package visual;

import cells.Cell;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Renderer dla komórek na mapie
 * Created by VanitaZ on 2015-02-24.
 */
public class MapCellRenderer extends DefaultTableCellRenderer {

    private Color landColor = Color.green.darker();
    private Color waterColor = Color.blue.darker();
    private Color coastColor = Color.yellow;
    private Color oilColor = Color.black;
    private Color oilColor1 = new Color(22);
    private Color oilColor2 = new Color(44);
    private Color oilColor3 = new Color(77);
    private Color blockColor = Color.red;
    private Color sourceColor = Color.red;

    private Cell[][] area;

    public MapCellRenderer (Cell[][] mapGrid) {
            this.area = mapGrid;
        }

    public Component getTableCellRendererComponent (JTable table, Object tableCell, boolean isSelected, boolean hasFocus, int row, int col) {

            Component renderer = super.getTableCellRendererComponent(table,tableCell,isSelected,hasFocus,row,col);



            switch (area[row][col].getType()) {
                case LAND:
                    renderer.setBackground(landColor);
                    break;
                case WATER:
                    renderer.setBackground(waterColor);
                    break;
                case COAST:
                    renderer.setBackground(coastColor);
                    break;
                case OIL:
                    renderer.setBackground(oilColor);
                    if (area[row][col].getOilLevel() < 0.00001) renderer.setBackground(oilColor3);
                    else if (area[row][col].getOilLevel() < 1) renderer.setBackground(oilColor2);
                    else if (area[row][col].getOilLevel() < 10) renderer.setBackground(oilColor1);
                    break;
                case BLOCK:
                    renderer.setBackground(blockColor);
                    break;
                case SOURCE:
                    renderer.setBackground(sourceColor);
                    break;
            }

            this.setBorder (BorderFactory.createEmptyBorder());

            if (isSelected) renderer.setBackground(Color.CYAN);

            return this;
        }
}
