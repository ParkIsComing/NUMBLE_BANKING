package web.banking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotFoundRelationshipException extends RuntimeException{
    public NotFoundRelationshipException(String message) {
        super(message);
    }
}
