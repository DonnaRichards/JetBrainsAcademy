package cinema.DTO;

public class ReturnedTicketDTO {
    private SeatDTO returned_ticket;

    public ReturnedTicketDTO() {
    }

    public ReturnedTicketDTO(SeatDTO returned_ticket) {
        this.returned_ticket = returned_ticket;
    }

    public SeatDTO getReturned_ticket() {
        return returned_ticket;
    }

    public void setReturned_ticket(SeatDTO returned_ticket) {
        this.returned_ticket = returned_ticket;
    }
}
