package cinema.DTO;

public class SeatPositionDTO {

    private int row;
    private int column;

    public SeatPositionDTO() {}

    public SeatPositionDTO(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null || getClass() != obj.getClass())
//            return false;
//        SeatPosition that = (SeatPosition) obj;
//        return row == that.row && column == that.column;
//    }
}

