package kr.borntorun.api.support.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends RuntimeException {

  @Getter
  private final String message;

  public InternalServerException(final String message) {
    super(message);
    this.message = message;
  }
}
