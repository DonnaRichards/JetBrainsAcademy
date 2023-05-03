package battleship;

public class Cell {

    private int row;
    private int col;
    private String coordinate;
    private char state;
    private char fogOWarState;
    final static int CHAR_A_VALUE = 65;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.coordinate = String.format("%s%d", Character.valueOf((char) (row + CHAR_A_VALUE)), this.col + 1);
        this.state = '~';
        this.fogOWarState = '~';
    }

    public Cell() {
        this.row = -1;
        this.col = -1;
        this.coordinate = "";
        this.state = '~';
        this.fogOWarState = '~';
    }

    @Override
    public String toString() {
        return String.format("Cell: row %d, col %d, coord %s, state %s, fow_state %s ",
                this.row,
                this.col,
                this.coordinate,
                Character.toString(this.state),
                Character.toString(this.fogOWarState));
    }

    public int getRow() {
        return row;
    }

    public void setRow(char row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public char getState() {
        return state;
    }

    public void setState(char state) {
        this.state = state;
    }

    public char getFogOWarState() {
        return fogOWarState;
    }

    public void setFogOWarState(char fogOWarState) {
        this.fogOWarState = fogOWarState;
    }

}
