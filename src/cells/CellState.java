package cells;

import cons.ECellType;

/**
 * Interfejs do tworzenia klas stanu komórek
 * Created by VanitaZ on 2015-02-23.
 */
public interface CellState {

    public ECellType getType ();

    public float calculateNewOilLevel(Cell[] neighbours, Cell cell, float[] windDirPower, float temperature);

    public float giveActualOilLevel (float level);

    public boolean isOilable();

}
