package areas;

/**
 * Klasa obrazująca planszę, składająca się z poszczególnych komórek
 * Zawiera informację o wymiarach, komórkach, temperaturze oraz sile i kierunku wiatru
 * Created by VanitaZ on 2015-02-24.
 */
import cells.CellBuilder;
import cells.SourceState;
import cons.*;
import cells.Cell;
import java.util.Random;

public class Area {

    private int dimension = 500;

    private Cell[][] mapGrid;

    private float temperature = 0;

    private float windPower = 0;
    private EDir windDir = EDir.N;

    private float[] windDirPower = new float[8];

    private int sourceX;
    private int sourceY;

    /**
     * Konstruktor tworzący pustą lub losową planszę
     * @param random true jeśli plansza ma być losowa
     */
    public Area (boolean random) {
        if (random)
            this.generateRandomMapGrid();
        else
            this.makeMapGrid();

        this.calculateWindDirPower();
    }

    /**
     * Konstruktor tworzący planszę z określonymi parametrami
     * @param dimension wymiar
     * @param temperature temperatura
     * @param windPower siła wiatru
     * @param windDir kierunek wiatru
     */
    public Area (int dimension, float temperature, float windPower, EDir windDir) {
        this.dimension = dimension;
        this.temperature = temperature;
        this.windPower = windPower;
        this.windDir = windDir;
        this.calculateWindDirPower();

        this.makeMapGrid();
    }

    /**
     * Pobranie wymiaru planszy
     * @return wymiar
     */
    public int getDimension () {
        return this.dimension;
    }

    /**
     * Tworzy tablicę zawierającą komórki i ustawia ich parametry
     */
    private void makeMapGrid () {
        this.mapGrid = new Cell[dimension][dimension];
        for (int x = 0; x < dimension; x++)
            for (int y = 0; y < dimension; y++) {
                mapGrid[y][x] = new CellBuilder().directories(y,x).buildCell();
            }
        this.introduceCellsThemNeighbours();
    }

    /**
     * Tworzy źródło w podanej lokalizacji
     * @param x współrzędna X
     * @param y współrzędna Y
     */
    public void generateSource (int x, int y) {
        if (x > 0 && x < dimension)
            this.sourceX = x-1;
        else
            this.sourceX = dimension / 2;
        if (y > 0 && y < dimension)
            this.sourceY = y-1;
        else
            this.sourceY = dimension / 2;

        mapGrid[this.sourceX][this.sourceY].setState(new SourceState());
    }

    /**
     * Ustawia tablicę kierunkową siły wiatru
     */
    private void calculateWindDirPower() {
        if (windPower != 0) {
            int dir = this.windDir.ordinal();
            this.windDirPower[dir] = this.windPower;
            this.windDirPower[(dir + 4) % 8] = -this.windPower;
            this.windDirPower[(dir + 1) % 8] = this.windPower / 2;
            this.windDirPower[(dir + 7) % 8] = this.windPower / 2;
            this.windDirPower[(dir + 3) % 8] = -this.windPower / 2;
            this.windDirPower[(dir + 5) % 8] = -this.windPower / 2;
            this.windDirPower[(dir + 2) % 8] = 0;
            this.windDirPower[(dir + 6) % 8] = 0;
        }
        else
            for (int i = 0; i<8; i++)
                this.windDirPower[i] = 0;
    }

    /**
     * Tworzy i przekazuje komórkom informacje o ich sąsiadach
     */
    private void introduceCellsThemNeighbours () {
        for (int x = 0; x < dimension; x++)
            for (int y = 0; y < dimension; y++) {
                Cell[] neighbours = new Cell[8];

                if (y > 0)
                    neighbours[0] = mapGrid[y-1][x];
                if (y > 0 && x < this.dimension-1)
                    neighbours[1] = mapGrid[y-1][x+1];
                if (x < this.dimension-1)
                    neighbours[2] = mapGrid[y][x+1];
                if (y <  this.dimension-1 && x < this.dimension-1)
                    neighbours[3] = mapGrid[y+1][x+1];
                if (y <  this.dimension-1)
                    neighbours[4] = mapGrid[y+1][x];
                if (y <  this.dimension-1 && x > 0)
                    neighbours[5] = mapGrid[y+1][x-1];
                if (x > 0)
                    neighbours[6] = mapGrid[y][x-1];
                if (y > 0 && x > 0)
                    neighbours[7] = mapGrid[y-1][x-1];

                this.mapGrid[y][x].setNeighbours(neighbours);
            }

    }

    public float getWindPower() {
        return windPower;
    }

    public EDir getWindDir() {
        return windDir;
    }

    /**
     * Oblicza nowy poziom ropy dla komórek
     */

    public void calculateNewOilLevelForAll () {
        for (int x = 0; x < dimension; x++)
            for (int y = 0; y < dimension; y++) {
                mapGrid[y][x].calculateNewOilLevel(windDirPower,temperature);
            }
    }

    /**
     * uaktualnia poziom ropy w komórkach
     */
    public void updateOilLevelForAll () {
        for (int x = 0; x < dimension; x++)
            for (int y = 0; y < dimension; y++) {
                mapGrid[y][x].updateOilLevel();
            }
    }

    /**
     * Testowe wyśietlenie planszy w konsoli
     */
    public void print () {
        for (int x = 0; x < dimension; x++) {
            for (int y = 0; y < dimension; y++) {
                ECellType type = mapGrid[y][x].getType();
                switch (type) {
                    case WATER:
                        System.out.print("W");
                        break;
                    case LAND:
                        System.out.print("L");
                        break;
                    case OIL:
                        System.out.print("O");
                        break;
                    case COAST:
                        System.out.print("C");
                        break;
                    case SOURCE:
                        System.out.print("S");
                        break;
                    case BLOCK:
                        System.out.print("B");
                        break;
                }

            }
            System.out.println();
        }
    }

    /**
     * Tworzy losową planszę
     */
    public void generateRandomMapGrid () {
        Random generator = new Random();

        this.dimension = 500;

        this.mapGrid = new Cell[dimension][dimension];
        for (int x = 0; x < dimension; x++)
            for (int y = 0; y < dimension; y++) {
                mapGrid[y][x] =
                        new CellBuilder()
                                .directories(y, x)
                                .type(generator.nextInt(100) > 45 ? ECellType.WATER : ECellType.LAND)
                                .buildCell();
            }

        this.introduceCellsThemNeighbours();

        for (int i = 0; i < Consts.WORLD_GENERATION_I; i++)
            for (int x = 0; x < dimension; x++)
                for (int y = 0; y < dimension; y++)
                    mapGrid[y][x].generate();


        this.generateCoast();

    }

    /**
     * Generuje testową mapę o okreslonych parametrach
     */
    public void generateTestMapGrid () {
        this.dimension = 500;
        mapGrid = new Cell[dimension][dimension];
        for (int x = 0; x<dimension; x++)
            for (int y = 0; y<dimension; y++) {
                mapGrid[y][x] =
                    new CellBuilder()
                        .directories(y, x)
                        .type(ECellType.WATER)
                        .current(y > 225 && y < 275 ? (float)0.9 : 0,EDir.E)
                        .buildCell();
            }
        this.introduceCellsThemNeighbours();

        generateSource(250,250);
    }

    /**
     * Tworzy źródło w losowym miejscu
     */
    public void generateRandomSource () {
        Random generator = new Random();

        while (true) {
            int x = generator.nextInt(dimension);
            int y = generator.nextInt(dimension);
            if(this.mapGrid[y][x].getType() == ECellType.WATER) {
                this.generateSource(y, x);
                break;
            }
        }
    }

    /**
     * Generuje wybrzeże dla całej planszy
     */
    private void generateCoast () {
        for (int x = 0; x < dimension; x++)
            for (int y = 0; y < dimension; y++) {
                mapGrid[y][x].checkCoast();
            }
    }

    /**
     * Zwraca tablicę komórek
     * @return tablica 2D komórek
     */
    public Cell[][] getMapGrid () {
        return this.mapGrid;
    }

    /**
     * Ustawia siłe wiatru
     * @param pow siła wiatru
     */
    public void setWindPower (float pow) {
        this.windPower = pow;
        calculateWindDirPower();
    }

    /**
     * Ustawia kierunek wiatru
     * @param dir kierunek wiatru
     */
    public void setWindDirection (EDir dir) {
        this.windDir = dir;
        calculateWindDirPower();
    }

    /**
     * Tworzy łańcuch opisujący komórkę pod podanymi współrzędnymi
     * @param y współrzędna Y
     * @param x wspołrzędna X
     * @return opis komórki
     */
    public String getCellInfo (int y, int x) {
        StringBuilder sb = new StringBuilder();
        sb.append(y);
        sb.append(" x ");
        sb.append(x);
        sb.append(" \n ");
        sb.append(mapGrid[x][y].getOilLevel());
        return sb.toString();
    }

    /**
     * Utworzenie pamiątki z mapy
     * @return nowa pamiątka
     */
    public AreaMemo createMemo () {
        return new AreaMemo(this);
    }

    /**
     * Odtworzenie mapy z pamiatki
     * @param memo pamiątka
     */
    public void setMemo (AreaMemo memo) {
        this.dimension = memo.getDimension();
        for (int x = 0; x < dimension; x++)
            for (int y = 0; y < dimension; y++) {
                this.mapGrid[y][x].setOilLevel(memo.cellOilLevel(y,x));
                this.mapGrid[y][x].setType(memo.cellType(y,x));
            }
        this.windPower = memo.getWindPower();
        this.windDir = memo.getWindDir();
    }

}

