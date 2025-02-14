package kr.borntorun.api.support.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

  @Getter
  private final String message;

  public NotFoundException(final String message) {
    super(message);
    this.message = message;
  }
}
