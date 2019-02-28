package ch.uzh.ifi.seal.soprafs19.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class HttpConflictException extends RuntimeException {
    public HttpConflictException() {
        super("Username already exists!");
    }
}

