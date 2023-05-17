package cinema;

import cinema.DTO.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class Cinema {

    private int totalRows;
    private int totalColumns;
    private Seat[][] availableSeats;
    private HashMap<UUID, int[]> tickets;

    public Cinema(int totalRows, int totalColumns) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.availableSeats = new Seat[totalRows][totalColumns];
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalColumns; j++) {
                availableSeats[i][j] = new Seat(i+1, j+1);
            }
        }
        this.tickets = new HashMap<>();
    }


    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public CinemaDTO showCinema() {

        CinemaDTO cinemaDTO = new CinemaDTO(this.getTotalRows(), this.getTotalColumns(), new ArrayList<SeatDTO>());
        for (int i = 0; i < this.totalRows; i++) {
            for (int j = 0; j < this.totalColumns; j++) {
                Seat seat = this.availableSeats[i][j];
                if (seat.isAvailable()) {
                    cinemaDTO.getAvailableSeats().add(seat.convertSeatToDTO());
                }
            }
        }
        return cinemaDTO;
    }

    protected boolean validSeatNumber(int row, int column) {
        return row >= 1 &&
                row <= this.getTotalRows() &&
                column >= 1 &&
                column <= this.getTotalColumns();
    }

    public boolean availableSeat(int row, int column) {
        return this.availableSeats[row-1][column-1].isAvailable();
    }

    private void setSeatSold(int i, int j, UUID token) {
        this.availableSeats[i][j].setAvailable(false);
        this.tickets.put(token, new int[] {i, j});
    }

    private void returnSeat(int i, int j, UUID token) {
        this.availableSeats[i][j].setAvailable(true);
        this.tickets.remove(token);
    }

    public ResponseEntity<?> sellSeat(int row, int column) {
        if (this.validSeatNumber(row, column)) {
            if (this.availableSeats[row-1][column-1].isAvailable()) {
                TicketDTO ticketDTO = new TicketDTO();
                UUID token = UUID.randomUUID();
                ticketDTO.setToken(token);
                ticketDTO.setTicket(this.availableSeats[row-1][column-1].convertSeatToDTO());
                this.setSeatSold(row-1, column-1, token);
                return new ResponseEntity<>(ticketDTO, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(Map.of("error","The ticket has been already purchased!"),
                        HttpStatus.BAD_REQUEST);
            }
        }
        else {
            return new ResponseEntity<>(Map.of("error","The number of a row or a column is out of bounds!"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> returnTicket(UUID token) {
        if (this.tickets.get(token) == null) {
            return new ResponseEntity<>(Map.of("error", "Wrong token!"),
                    HttpStatus.BAD_REQUEST);
        }
        else {
            int[] seatLocation = this.tickets.get(token);
            int i = seatLocation[0];
            int j = seatLocation[1];
            ReturnedTicketDTO returnedTicketDTO = new ReturnedTicketDTO();
            this.returnSeat(i, j, token);
            returnedTicketDTO.setReturned_ticket(this.availableSeats[i][j].convertSeatToDTO());
            return new ResponseEntity<>(returnedTicketDTO, HttpStatus.OK);
        }
    }

    private StatsDTO calculateStats() {
        StatsDTO statsDTO = new StatsDTO();
        for (int i = 0; i < this.totalRows; i++) {
            for (int j = 0; j < this.totalColumns; j++) {
                Seat seat = this.availableSeats[i][j];
                if (seat.isAvailable()) {
                    statsDTO.setNumberOfAvailableSeats(statsDTO.getNumberOfAvailableSeats() + 1);
                }
                else {
                    statsDTO.setCurrentIncome(statsDTO.getCurrentIncome() + seat.getPrice());
                    statsDTO.setNumberOfPurchasedTickets(statsDTO.getNumberOfPurchasedTickets() + 1);
                }
            }
        }
        return statsDTO;
    }

    public ResponseEntity<?> showStats(String password) {
        if (this.validPassword(password)) {
            return new ResponseEntity<>(this.calculateStats(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(Map.of("error","The password is wrong!"),
                    HttpStatus.UNAUTHORIZED);
        }
    }

    public boolean validPassword(String password) {
        return "super_secret".equals(password);
    }


//    public String formatStats() {
//        int income = this.calculateIncome();
//        int available = this.countAvailable();
//        int purchased = this.countPurchased();
//        return
//    }



}
