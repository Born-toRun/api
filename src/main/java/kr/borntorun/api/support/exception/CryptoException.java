package kr.borntorun.api.support.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class CryptoException extends RuntimeException {

  @Getter
  private final String message;


  public CryptoException(final String message) {
    super(message);
    this.message = message;
  }
}
