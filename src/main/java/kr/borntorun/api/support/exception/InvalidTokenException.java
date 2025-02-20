package kr.borntorun.api.support.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidTokenException extends RuntimeException {

  @Getter
  private final String message;

  public InvalidTokenException(final String message) {
    super(message);
    this.message = message;
  }
}
