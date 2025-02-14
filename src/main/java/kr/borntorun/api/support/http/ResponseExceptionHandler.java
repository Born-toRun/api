package kr.borntorun.api.support.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import kr.borntorun.api.support.Notification;
import kr.borntorun.api.support.exception.AuthorizationException;
import kr.borntorun.api.support.exception.ClientUnknownException;
import kr.borntorun.api.support.exception.CryptoException;
import kr.borntorun.api.support.exception.DuplicationException;
import kr.borntorun.api.support.exception.ForBiddenException;
import kr.borntorun.api.support.exception.InternalServerException;
import kr.borntorun.api.support.exception.InvalidException;
import kr.borntorun.api.support.exception.NetworkException;
import kr.borntorun.api.support.exception.NotFoundException;
import kr.borntorun.api.support.http.model.ExceptionResponse;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class ResponseExceptionHandler {

  private final Notification notification;

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ExceptionResponse> handleNotFound(NotFoundException ex) {
    ex.printStackTrace();
    return ResponseEntity.status((HttpStatus.NOT_FOUND))
        .body(ExceptionResponse.builder()
            .message(ex.getMessage())
            .build());
  }

  @ExceptionHandler({NetworkException.class, ClientUnknownException.class})
  public ResponseEntity<ExceptionResponse> handleBadGateway(Exception ex) {
    ex.printStackTrace();
    notification.send(ex.toString());
    return ResponseEntity.status((HttpStatus.BAD_GATEWAY))
        .body(ExceptionResponse.builder()
            .message(ex.getMessage())
            .build());
  }

  @ExceptionHandler({CryptoException.class, InternalServerException.class, NullPointerException.class})
  public ResponseEntity<ExceptionResponse> handleInternalServerError(Exception ex) {
    ex.printStackTrace();
    notification.send(ex.toString());
    return ResponseEntity.internalServerError()
        .body(ExceptionResponse.builder()
            .message(ex.getMessage())
            .build());
  }

  @ExceptionHandler({InvalidException.class, DuplicationException.class})
  public ResponseEntity<ExceptionResponse> handleBadRequest(Exception ex) {
    ex.printStackTrace();
    return ResponseEntity.badRequest()
        .body(ExceptionResponse.builder()
            .message(ex.getMessage())
            .build());
  }

  @ExceptionHandler(AuthorizationException.class)
  public ResponseEntity<ExceptionResponse> handleUnauthorized(AuthorizationException ex) {
    ex.printStackTrace();
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(ExceptionResponse.builder()
            .message(ex.getMessage())
            .build());
  }

  @ExceptionHandler(ForBiddenException.class)
  public ResponseEntity<ExceptionResponse> handleForBidden(ForBiddenException ex) {
    ex.printStackTrace();
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(ExceptionResponse.builder()
            .message(ex.getMessage())
            .build());
  }
}
