package cells;

import cons.ECellType;

/**
 * Created by VanitaZ on 2015-02-23.
 */
public class BlockState implements CellState {

    @Override
    public ECellType getType () {
        return ECellType.BLOCK;
    }

    @Override
    public float calculateNewOilLevel(Cell[] neighbours, Cell cell, float[] windDirPower, float temperature) {
        return 0;
    }

    @Override
    public float giveActualOilLevel(float level) {
        return 0;
    }

    @Override
    public boolean isOilable() {
        return false;
    }
}
