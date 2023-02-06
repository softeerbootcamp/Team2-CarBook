package softeer.carbook.domain.user.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Message {
    private String message;

    public Message(String message) {
        this.message = message;
    }

    public static ResponseEntity<Message> make200Response(String msg){
        return new ResponseEntity<>(
                new Message(msg),
                HttpStatus.OK
        );
    }

    public static ResponseEntity<Message> make400Response(String msg){
        return new ResponseEntity<>(
                new Message(msg),
                HttpStatus.BAD_REQUEST
        );
    }

    public String getMessage() {
        return message;
    }
}
