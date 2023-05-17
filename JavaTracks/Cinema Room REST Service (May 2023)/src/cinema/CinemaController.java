package cinema;

import cinema.DTO.ReturnedTicketTokenDTO;
import cinema.DTO.SeatPositionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CinemaController {

    private final Cinema cinema;

    public CinemaController() {
        this.cinema = new Cinema(9, 9);
    }

    @GetMapping("/seats")
    public ResponseEntity<?> returnCinema() {
        ResponseEntity<?> responseEntity = null;
        try {
            responseEntity = new ResponseEntity<>(cinema.showCinema(), HttpStatus.OK);
        }
        catch (Exception e) {
            responseEntity = new ResponseEntity<>(
                    "Error retrieving cinema details",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSeat(@RequestBody SeatPositionDTO seatPositionDTO) {
        return cinema.sellSeat(seatPositionDTO.getRow(), seatPositionDTO.getColumn());
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnSeat(@RequestBody ReturnedTicketTokenDTO returnedTicketTokenDTO) {
        return cinema.returnTicket(returnedTicketTokenDTO.getToken());
    }

    @PostMapping("/stats")
    public ResponseEntity<?> showStats(@RequestParam(required = false) String password) {
        return cinema.showStats(password);
    }

}
