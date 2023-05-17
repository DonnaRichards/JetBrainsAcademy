package cinema.DTO;

import java.util.ArrayList;

public class CinemaDTO {

    private int totalRows;
    private int totalColumns;
    private ArrayList<SeatDTO> availableSeats;

    public CinemaDTO() {
    }

    public CinemaDTO(int totalRows, int totalColumns, ArrayList<SeatDTO> availableSeats) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.availableSeats = availableSeats;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public void setTotalColumns(int totalColumns) {
        this.totalColumns = totalColumns;
    }

    public ArrayList<SeatDTO> getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(ArrayList<SeatDTO> availableSeats) {
        this.availableSeats = availableSeats;
    }

}
