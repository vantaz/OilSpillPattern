package areas;

import cells.Cell;
import cons.ECellType;
import cons.EDir;

/**
 * Created by VanitaZ on 2015-02-26.
 */
public class AreaMemo {

    private int dimension;

    private float[][] cellsoilLevel;
    private ECellType[][] cellsTypes;

    private float windPower;
    private EDir windDir;

    public AreaMemo (Area area) {
        this.setState(area);
    }

    public int getDimension() {
        return dimension;
    }

    public void setState (Area area) {
        Cell[][] mapGrid = area.getMapGrid();
        this.dimension = area.getDimension();
        this.cellsoilLevel = new float[dimension][dimension];
        this.cellsTypes = new ECellType[dimension][dimension];
        for (int x = 0; x < dimension; x++)
            for (int y = 0; y < dimension; y++) {
                this.cellsoilLevel[y][x] = mapGrid[y][x].getOilLevel();
                this.cellsTypes[y][x] = mapGrid[y][x].getType();
            }
        this.windPower = area.getWindPower();
        this.windDir = area.getWindDir();
    }

    public float cellOilLevel (int y, int x) {
        return this.cellsoilLevel[y][x];
    }

    public ECellType cellType (int y, int x) {
        return this.cellsTypes[y][x];
    }

    public float getWindPower() {
        return windPower;
    }

    public EDir getWindDir() {
        return windDir;
    }
}
