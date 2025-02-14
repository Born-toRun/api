package kr.borntorun.api.support.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class ForBiddenException extends RuntimeException {

  @Getter
  private final String message;

  public ForBiddenException(final String message) {
    super(message);
    this.message = message;
  }
}
