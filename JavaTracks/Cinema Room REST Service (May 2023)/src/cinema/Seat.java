package cinema;

import cinema.DTO.SeatDTO;

public class Seat {

    private int row;
    private int column;
    private int price;
    private boolean available;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        if (row <= 4){
            this.price = 10;
        }
        else {
            this.price = 8;
        }
        this.available = true;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public SeatDTO convertSeatToDTO() {
        return new SeatDTO(this.getRow(),this.getColumn(), this.getPrice());
    }
}
