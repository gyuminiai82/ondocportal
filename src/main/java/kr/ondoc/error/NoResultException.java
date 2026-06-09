package kr.ondoc.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class NoResultException extends RuntimeException {
	public NoResultException(String message) {
        super(message);
    }
}
