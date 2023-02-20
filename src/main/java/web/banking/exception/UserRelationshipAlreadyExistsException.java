package web.banking.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class UserRelationshipAlreadyExistsException extends DataIntegrityViolationException {
    public UserRelationshipAlreadyExistsException(String msg) {
        super(msg);
    }
}
