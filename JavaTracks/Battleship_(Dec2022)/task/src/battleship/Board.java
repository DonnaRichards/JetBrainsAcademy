package battleship;

import java.util.ArrayList;
import java.util.Scanner;

public class Board {

    Scanner sc = new Scanner(System.in);
    private int rowSize;
    private int colSize;
    private ArrayList<ArrayList<Cell>> board = new ArrayList<ArrayList<Cell>>();
    private ArrayList<Ship> ships = new ArrayList<>();

    public Board(int rowSize, int colSize) {
        this.rowSize = rowSize;
        this.colSize = colSize;
        for (int row = 0; row < this.rowSize; row++) {
            board.add(new ArrayList<>());
            for (int col = 0; col < this.colSize; col++) {
                board.get(row).add(col, new Cell(row, col));
            }
        }
    }

    public Board() {
        this(10, 10);
    }

    public void printBoard(boolean fogOfWar) {
        System.out.println();
        System.out.print("  ");
        for (int col = 0; col < this.colSize; col++) {
            System.out.printf("%d ", col + 1);
        }
        System.out.println();
        char rowLetter = 'A';
        for (int row = 0; row < this.rowSize; row++) {
            System.out.print(rowLetter + " ");
            for (int col = 0; col < this.colSize; col++) {
                if (fogOfWar) {
                    System.out.print(this.board.get(row).get(col).getFogOWarState() + " ");
                }
                else {
                    System.out.print(this.board.get(row).get(col).getState() + " ");
                }
            }
            System.out.println();
            rowLetter++;
        }
        System.out.println();
    }



    private boolean validCellFromCoordinate(String coordinate) {
        for (int row = 0; row < this.rowSize; row++) {
            for (int col = 0; col < this.colSize; col++) {
                if (this.board.get(row).get(col).getCoordinate().equalsIgnoreCase(coordinate)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Cell getCellFromCoordinate(String coordinate) {
        int row;
        int col;
        for (row = 0; row < this.rowSize; row++) {
            for (col = 0; col < this.colSize; col++) {
                if (this.board.get(row).get(col).getCoordinate().equalsIgnoreCase(coordinate)) {
                    return this.board.get(row).get(col);
                }
            }
        }
         throw new IllegalArgumentException("Error! Cell not found for coordinate " + coordinate);
    }

    private void placeShip(Ship ship) {
        boolean shipAdded = false;
        while (!shipAdded) {
            shipAdded = this.addShip(ship);
        }
        this.printBoard(false);
    }

    public void placeShips(int playerNum) {
        System.out.printf("Player %d, place your ships on the game field\n", playerNum);
        this.printBoard(false);
        this.placeShip(new Ship("Aircraft Carrier", 5));
        this.placeShip(new Ship("Battleship", 4));
        this.placeShip(new Ship("Submarine", 3));
        this.placeShip(new Ship("Cruiser", 3));
        this.placeShip(new Ship("Destroyer", 2));
    }

    public boolean addShip(Ship ship) {
        System.out.printf("Enter the coordinates of the %s (%d cells):%n",
                ship.getShipName(), ship.getLength());
        String beginningCoordinate = sc.next();
        if (!validCellFromCoordinate(beginningCoordinate)) {
            System.out.println("Error! Invalid beginning coordinate.  Try again.");
            return false;
        }
        Cell beginningCell = getCellFromCoordinate(beginningCoordinate);

        String endCoordinate = sc.next();
        sc.nextLine();
        if (!validCellFromCoordinate(endCoordinate)) {
            System.out.println("Error! Invalid end coordinate.  Try again.");
            return false;
        }
        Cell endingCell = getCellFromCoordinate(endCoordinate);

        if (beginningCell.getRow() == endingCell.getRow()) {
            if (Math.abs(endingCell.getCol() - beginningCell.getCol()) != ship.getLength() - 1) {
                System.out.printf("Error! Wrong length for the %s! Try again:%n", ship.getShipName());
                return false;
            }
        }
        else if (beginningCell.getCol() == endingCell.getCol()) {
            if (Math.abs(endingCell.getRow() - beginningCell.getRow()) != ship.getLength() - 1) {
                System.out.printf("Error! Wrong length for the %s! Try again:%n", ship.getShipName());
                return false;
            }
        } else {
            System.out.println("Error! Ship position not horizontal or vertical");
            return false;
        }

        if (beginningCell.getRow() > endingCell.getRow()
                || beginningCell.getCol() > endingCell.getCol()) {
            Cell temp = beginningCell;
            beginningCell = endingCell;
            endingCell = temp;
        }

        /*
        - distance from other ships
        -- all cells for ship itself are ~
        -- + cells surrounding ship are ~
        -- how ?
        -- start row = beg row - 1 if beg row > 0 else beg row
        -- end row = end row + 1 if end row < board row count else end row
        -- start col = beg col - 1 if beg col > 0 else beg col
        -- end col = end col + 1 if end col < board col count else end col
        -- nested for - if any are not ~ , error too close
         */
        int startRow = beginningCell.getRow() == 0 ? 0 : beginningCell.getRow() - 1;
        int startCol = beginningCell.getCol() == 0 ? 0 : beginningCell.getCol() - 1;
        int stopRow = endingCell.getRow() == this.rowSize - 1 ? endingCell.getRow() : endingCell.getRow() + 1;
        int stopCol = endingCell.getCol() == this.colSize - 1 ? endingCell.getCol() : endingCell.getCol() + 1;
        for (int row = startRow; row <= stopRow; row++) {
            for (int col = startCol; col <= stopCol; col++) {
                if (this.board.get(row).get(col).getState() != '~') {
                    System.out.println("Error! You placed ship too close to another one");
                    return false;
                }
            }
        }
        ship.setStartCell(beginningCell);
        ship.setEndCell(endingCell);
        for (int row = beginningCell.getRow(); row <= endingCell.getRow(); row++) {
            for (int col = beginningCell.getCol(); col <= endingCell.getCol(); col++) {
                this.board.get(row).get(col).setState('O');
            }
        }
        this.ships.add(ship);
        return true;
    }

    public boolean takeAShot() {
        String shotCoordinate = sc.nextLine();
        if (!validCellFromCoordinate(shotCoordinate)) {
            while (!validCellFromCoordinate(shotCoordinate)) {
                System.out.println("Error! You entered invalid coordinates! Try again: ");
                shotCoordinate = sc.nextLine();
            }
        }
        Cell shotCell = getCellFromCoordinate(shotCoordinate);
        if (getCellFromCoordinate(shotCoordinate).getState() == 'O' ||
                getCellFromCoordinate(shotCoordinate).getState() == 'X' ) {
            shotCell.setState('X');
            shotCell.setFogOWarState('X');
            return true;
        }
        else {
            shotCell.setState('M');
            shotCell.setFogOWarState('M');
            return false;
        }
    }

    public boolean shipSunk(Ship ship) {
        if (ship.isShot()) {
            return false;
        }
        int begRow = ship.getStartCell().getRow();
        int begCol = ship.getStartCell().getCol();
        int endRow = ship.getEndCell().getRow();
        int endCol = ship.getEndCell().getCol();
        if (begRow == endRow) {
            for (int i = begCol; i <= endCol; i++) {
                if (this.board.get(begRow).get(i).getState() != 'X') {
                    return false;
                }
            }
        }
        else if (begCol == endCol) {
            for (int i = begRow; i <= endRow; i++) {
                if (this.board.get(i).get(begCol).getState() != 'X') {
                    return false;
                }
            }
        }
        else {
            return false;
        }
        ship.setShot(true);
        return true;
    }

    public boolean allSunk() {
        for (int row = 0; row < this.rowSize; row++) {
            for (int col = 0; col < this.colSize; col++) {
                if (this.board.get(row).get(col).getState() == 'O') {
                    return false;
                }
            }
        }
        return true;
    }

    public ArrayList<Ship> getShips() {
        return this.ships;
    }

    public void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }
}
