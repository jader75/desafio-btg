package br.com.jader.desafiobtg.pedido;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PedidoExceptionHandler {

  @ExceptionHandler(PedidoInvalidoException.class)
  public ResponseEntity<Object> handle(PedidoInvalidoException exception) {
    return ResponseEntity.badRequest().body(exception.getMessage());
  }
}
