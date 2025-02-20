package kr.borntorun.api.support.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidException extends RuntimeException {

  private final String message;

  public InvalidException(final String message) {
    super(message);
    this.message = message;
  }
}
