package kr.borntorun.api.support.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class AuthorizationException extends RuntimeException {

  @Getter
  private final String message;

  public AuthorizationException(final String message) {
    super(message);
    this.message = message;
  }
}
