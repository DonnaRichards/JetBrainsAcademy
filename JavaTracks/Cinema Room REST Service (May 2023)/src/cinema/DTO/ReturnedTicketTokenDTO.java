package cinema.DTO;

import java.util.UUID;

public class ReturnedTicketTokenDTO {

    private UUID token;

    public ReturnedTicketTokenDTO() {
    }

    public ReturnedTicketTokenDTO(UUID token) {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}
