package cells;

import cons.Consts;
import cons.ECellType;
import cons.EDir;

/**
 * Created by VanitaZ on 2015-02-23.
 */
public class SourceState implements CellState {

    private float sourceOverallLevel = 0;
    private float sourceLevel = 0;

    public SourceState () {
        this.sourceOverallLevel = 10000;
        this.sourceLevel = 100;
    }

    public SourceState (float level, float maxLevel) {
        this.sourceOverallLevel = maxLevel;
        this.sourceLevel = level;
    }

    @Override
    public ECellType getType () {
        return ECellType.SOURCE;
    }

    @Override
    public float calculateNewOilLevel(Cell[] neighbours, Cell cell, float[] windDirPower, float temperature) {
        float oilLevel = cell.getOilLevel();
        float newOilLevel = oilLevel;

        if (cell.isOilable()) {

            float tmp = 0;

            if (neighbours[0] != null && neighbours[0].isOilable())
                tmp += (1 + cell.getCurrentPowerAtDir(EDir.S) + windDirPower[4]) * neighbours[0].getOilLevel() - oilLevel;
            if (neighbours[2] != null && neighbours[2].isOilable())
                tmp += (1 + cell.getCurrentPowerAtDir(EDir.W) + windDirPower[6]) * neighbours[2].getOilLevel() - oilLevel;
            if (neighbours[4] != null && neighbours[4].isOilable())
                tmp += (1 + cell.getCurrentPowerAtDir(EDir.N) + windDirPower[0]) * neighbours[4].getOilLevel() - oilLevel;
            if (neighbours[6] != null && neighbours[6].isOilable())
                tmp += (1 + cell.getCurrentPowerAtDir(EDir.E) + windDirPower[2]) * neighbours[6].getOilLevel() - oilLevel;

            newOilLevel += tmp * cons.Consts.OIL_B_ADJ;
            tmp = 0;

            if (neighbours[1] != null && neighbours[1].isOilable())
                tmp += (1 + cell.getCurrentPowerAtDir(EDir.SW) + windDirPower[4]) * neighbours[1].getOilLevel() - oilLevel;
            if (neighbours[3] != null && neighbours[3].isOilable())
                tmp += (1 + cell.getCurrentPowerAtDir(EDir.NW) + windDirPower[6]) * neighbours[3].getOilLevel() - oilLevel;
            if (neighbours[5] != null && neighbours[5].isOilable())
                tmp += (1 + cell.getCurrentPowerAtDir(EDir.NE) + windDirPower[0]) * neighbours[5].getOilLevel() - oilLevel;
            if (neighbours[7] != null && neighbours[7].isOilable())
                tmp += (1 + cell.getCurrentPowerAtDir(EDir.SE) + windDirPower[2]) * neighbours[7].getOilLevel() - oilLevel;

            newOilLevel += tmp * Consts.OIL_B_DIA;

            newOilLevel -= Consts.EVAPORATION * temperature;

        }

        newOilLevel = refill(newOilLevel);

        if (sourceOverallLevel <= 0) cell.setState(new OilState());
        if (newOilLevel == 0) cell.setState(new WaterState());

        return newOilLevel;
    }

    @Override
    public float giveActualOilLevel(float level) {
        return level;
    }

    public float getSourceLevel () {
        return this.sourceLevel;
    }

    private float refill(float oilLevel) {
        float newOilLevel = 0;

        if (sourceOverallLevel <= 0) return oilLevel;
        sourceOverallLevel -= this.sourceLevel - oilLevel;
        newOilLevel = sourceLevel + (sourceOverallLevel >= 0 ? 0 : sourceOverallLevel);
        return newOilLevel;
    }

    @Override
    public boolean isOilable() {
        return true;
    }
}
