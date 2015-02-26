package cells;

import cons.ECellType;
import cons.EDir;

/**
 * Budowniczy dla komórek
 * Created by VanitaZ on 2015-02-23.
 */
public class CellBuilder {

    private int x;
    private int y;
    private CellState state = new WaterState();
    private float oilLevel = 0;
    private float currentPower = 0;
    private EDir currentDir = EDir.N;

    /**
     * Zwraca zbudowaną komórkę
     * @return komórka
     */
    public Cell buildCell () {
        return new Cell(x,y,state,oilLevel,currentPower,currentDir);
    }

    public CellBuilder directories (int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public CellBuilder state (CellState state) {
        this.state = state;
        if (state instanceof SourceState) this.oilLevel = ((SourceState) state).getSourceLevel();
        return this;
    }

    public CellBuilder current (float currentPower, EDir currentDir) {
        if (state.isOilable()) {
            this.currentPower = currentPower;
            this.currentDir = currentDir;
        }
        return this;
    }

    public CellBuilder type (ECellType type) {
        switch (type) {
            case WATER:
                this.state = new WaterState();
                break;
            case OIL:
                this.state = new OilState();
                break;
            case LAND:
                this.state = new LandState();
                break;
            case COAST:
                this.state = new OilState();
                break;
            case BLOCK:
                this.state = new BlockState();
                break;
            case SOURCE:
                this.state = new SourceState();
                break;
        }
        return this;
    }

    public CellBuilder oilLevel (float level) {
        this.oilLevel = level;
        return this;
    }

}
