package kr.borntorun.api.support.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class DuplicationException extends RuntimeException {

	private final String message;

	public DuplicationException(final String message) {
		super(message);
		this.message = message;
	}
}
