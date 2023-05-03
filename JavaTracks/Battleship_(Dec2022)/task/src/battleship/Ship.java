package battleship;

public class Ship {

    private String shipName;
    private int length;
    private Cell startCell;
    private Cell endCell;
    private boolean shot;

    Ship(String printName, int length) {
        this.shipName = printName;
        this.length = length;
        this.startCell = new Cell();
        this.endCell = new Cell();
        this.shot = false;
    }

    public String getShipName() {
        return shipName;
    }

    public int getLength() {
        return length;
    }

    public Cell getStartCell() {
        return startCell;
    }

    public void setStartCell(Cell startCell) {
        this.startCell = startCell;
    }

    public Cell getEndCell() {
        return endCell;
    }

    public void setEndCell(Cell endCell) {
        this.endCell = endCell;
    }

    public boolean isShot() {
        return shot;
    }

    public void setShot(boolean shot) {
        this.shot = shot;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "printName='" + shipName + '\'' +
                ", length=" + length +
                ", startCoordinate='" + startCell + '\'' +
                ", endCoordinate='" + endCell + '\'' +
                ", shot=" + shot +
                '}';
    }

}