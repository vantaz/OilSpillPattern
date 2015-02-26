package cells;

import cons.ECellType;
import cons.EDir;

/**
 * Klasa przedstawiająca pojedyńczą komórkę na mapie
 * Ma określony stan i położenie, w zależności od tego może posiadać dodatkowe parametry
 * Created by VanitaZ on 2015-02-23.
 */
public class Cell {

    private CellState state;
    private int x;
    private int y;

    private float oilLevel;
    private float newOilLevel = 0;

    private float currentPower;
    private EDir currentDir;
    private float[] currentDirPower = new float[8];

    private Cell[] neighbours;

    /**
     * Konstruktor ze wszystkimi parametrami
     * @param x współrzędna X
     * @param y współrzędna Y
     * @param state stan komórki
     * @param oilLevel poziom oleju w komórce
     * @param currentPower siła prądu
     * @param currentDir kierunek prądu
     */
    public Cell(int x, int y, CellState state, float oilLevel, float currentPower, EDir currentDir) {
        this.setXY(x,y);
        this.setState(state);
        this.setOilLevel(oilLevel >= 0 ? oilLevel : 0);
        this.setCurrent(currentPower,currentDir);
    }

    /**
     * Ustawia współrzędne komórki
     * @param x współrzędna X
     * @param y współrzędan Y
     */
    private void setXY (int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Ustawia poziom oleju w komórce
     * @param oilLevel nowy poziom oleju
     */
    public void setOilLevel (float oilLevel) {
        if (state.isOilable()) {
            this.oilLevel = oilLevel;
        }
        else this.oilLevel = 0;
    }

    /**
     * Ustawia stan komórki
     * @param state nowy stan komórki
     */
    public void setState (CellState state) {
        this.state = state;
        if (state instanceof SourceState) this.oilLevel = ((SourceState) state).getSourceLevel();
    }

    /**
     * Ustawia parametry prądu i oblicza tablice kierunkową
     * @param currentPower siła prądu
     * @param currentDir kierunek prądu
     */
    public void setCurrent (float currentPower, EDir currentDir) {
        this.currentPower = currentPower;
        this.currentDir = currentDir;
        this.calculatecurrentDirPower();
    }

    /**
     * Ustawia tablice kierunkową siły prądu
     */
    private void calculatecurrentDirPower() {
        if (currentPower != 0) {
            int dir = this.currentDir.ordinal();
            this.currentDirPower[dir] = this.currentPower;
            this.currentDirPower[(dir + 4) % 8] = -this.currentPower;
            this.currentDirPower[(dir + 1) % 8] = this.currentPower / 2;
            this.currentDirPower[(dir + 7) % 8] = this.currentPower / 2;
            this.currentDirPower[(dir + 3) % 8] = -this.currentPower / 2;
            this.currentDirPower[(dir + 5) % 8] = -this.currentPower / 2;
            this.currentDirPower[(dir + 2) % 8] = 0;
            this.currentDirPower[(dir + 6) % 8] = 0;
        }
        else
            for (int i = 0; i<8; i++)
                this.currentDirPower[i] = 0;
    }

    /**
     * Oblicza nowy stan ropy w komórce zależnie od swojego stanu
     * @param windDirPower Tablica kierunkowa siły wiatru
     * @param temperature Temperatura
     */
    public void calculateNewOilLevel (float[] windDirPower, float temperature) {
        this.newOilLevel = state.calculateNewOilLevel(neighbours, this, windDirPower, temperature);
    }

    /**
     * Uaktualnia stan ropy w komórce
     */
    public void updateOilLevel () {
        this.oilLevel = this.newOilLevel > 0 ? this.newOilLevel : 0;
    }

    /**
     * Ustawia tablicę sąsiadów komórki
     * @param neighbours tablica sąsiadów komórki
     */
    public void setNeighbours (Cell[] neighbours) {
        this.neighbours = neighbours;
    }

    /**
     * Zwraca poziom ropy w komórce
     * @return poziom ropy
     */
    public float getOilLevel () {
        return this.state.giveActualOilLevel(oilLevel);
    }

    /**
     * Zwraca siłę prądu w konkretnym kierunku
     * @param dir kierunek, w którym szukamy prądu
     * @return siął prądu w danym kierunku
     */
    public float getCurrentPowerAtDir (EDir dir) {
        return this.currentDirPower[dir.ordinal()];
    }

    /**
     * Zwraca typ komórki (zależny od jej stanu)
     * @return typ komórki
     */
    public ECellType getType () {
        return state.getType();
    }

    /**
     * Sprawdza czy komórke może przyjąć ropę
     * @return true jeśli komórka może przyjąć ropę, false w przeciwnym wypadku
     */
    public boolean isOilable () {
        return state.isOilable();
    }

    /**
     * Sprawdza ile sąsiadów danego typu ma komórka
     * @param type typ poszukiwanych sąsiadów
     * @return ilość sąsiadów
     */
    private int howManyNeighoursType (ECellType type) {
        int count = 0;
        for (Cell cell : neighbours) {
            if (cell != null)
                if (cell.getType() == type)
                    count++;
        }
        return count;
    }

    /**
     * Sprawdza, czy komórka powinna być wybrzeżem.
     */
    public void checkCoast () {
        if (this.getType() == ECellType.LAND && this.howManyNeighoursType(ECellType.WATER) > 0)
            this.setState(new CoastState());
    }

    /**
     * Sprawdza, czy komórka ma być lądem czy wodą w trakcie generacji świata
     */
    public void generate () {
        if (this.getType() == ECellType.LAND && this.howManyNeighoursType(ECellType.WATER) > 4)
            this.setState(new WaterState());
        else if (this.getType() == ECellType.WATER && this.howManyNeighoursType(ECellType.LAND) > 4)
            this.setState(new LandState());
    }

    public void setType (ECellType type) {
        if (this.getType() != type) {
            switch (type) {
                case LAND:
                    setState(new LandState());
                    break;
                case WATER:
                    setState(new WaterState());
                    break;
                case COAST:
                    setState(new CoastState());
                    break;
                case OIL:
                    setState(new OilState());
                    break;
                case BLOCK:
                    setState(new BlockState());
                    break;
                case SOURCE:
                    setState(new SourceState());
                    break;

            }
        }
    }
}
