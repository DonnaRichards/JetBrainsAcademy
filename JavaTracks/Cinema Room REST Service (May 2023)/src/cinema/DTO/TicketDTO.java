package cinema.DTO;

import java.util.UUID;

public class TicketDTO {
    private UUID token;
    private SeatDTO ticket;

    public TicketDTO() {
    }

    public TicketDTO(UUID token, SeatDTO ticket) {
        this.token = token;
        this.ticket = ticket;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public SeatDTO getTicket() {
        return ticket;
    }

    public void setTicket(SeatDTO ticket) {
        this.ticket = ticket;
    }
}
