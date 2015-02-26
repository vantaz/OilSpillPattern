package cells;

import cons.Consts;
import cons.ECellType;
import cons.EDir;

/**
 * Created by VanitaZ on 2015-02-23.
 */
public class CoastState implements CellState {


    @Override
    public ECellType getType () {
        return ECellType.COAST;
    }

    @Override
    public float calculateNewOilLevel(Cell[] neighbours, Cell cell, float[] windDirPower, float temperature) {
        float oilLevel = cell.getOilLevel();
        float newOilLevel = oilLevel;

        if (Consts.COAST_MAX_LVL > cell.getOilLevel()) {

            float tmp = 0;

            if (neighbours[0] != null && neighbours[0].isOilable())
                tmp += (1 + cell.getCurrentPowerAtDir(EDir.S) + windDirPower[4]) * neighbours[0].getOilLevel();
            if (neighbours[2] != null && neighbours[2].isOilable())
                tmp += (1 + cell.getCurrentPowerAtDir(EDir.W) + windDirPower[6]) * neighbours[2].getOilLevel();
            if (neighbours[4] != null && neighbours[4].isOilable())
                tmp += (1 + cell.getCurrentPowerAtDir(EDir.N) + windDirPower[0]) * neighbours[4].getOilLevel();
            if (neighbours[6] != null && neighbours[6].isOilable())
                tmp += (1 + cell.getCurrentPowerAtDir(EDir.E) + windDirPower[2]) * neighbours[6].getOilLevel();

            newOilLevel += tmp * cons.Consts.OIL_B_ADJ;
            tmp = 0;

            if (neighbours[1] != null && neighbours[1].isOilable())
                tmp += (1 + cell.getCurrentPowerAtDir(EDir.SW) + windDirPower[4]) * neighbours[1].getOilLevel();
            if (neighbours[3] != null && neighbours[3].isOilable())
                tmp += (1 + cell.getCurrentPowerAtDir(EDir.NW) + windDirPower[6]) * neighbours[3].getOilLevel();
            if (neighbours[5] != null && neighbours[5].isOilable())
                tmp += (1 + cell.getCurrentPowerAtDir(EDir.NE) + windDirPower[0]) * neighbours[5].getOilLevel();
            if (neighbours[7] != null && neighbours[7].isOilable())
                tmp += (1 + cell.getCurrentPowerAtDir(EDir.SE) + windDirPower[2]) * neighbours[7].getOilLevel();

            newOilLevel += tmp * Consts.OIL_B_DIA;

            newOilLevel -= Consts.EVAPORATION * temperature;

        }

        if(newOilLevel >= Consts.COAST_MAX_LVL) return Consts.COAST_MAX_LVL;
        return newOilLevel;
    }

    @Override
    public float giveActualOilLevel(float level) {
        return level;
    }

    @Override
    public boolean isOilable() {
        return false;
    }
}
