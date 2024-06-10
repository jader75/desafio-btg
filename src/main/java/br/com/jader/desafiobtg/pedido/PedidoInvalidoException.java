package br.com.jader.desafiobtg.pedido;

public class PedidoInvalidoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PedidoInvalidoException(String message) {
		super(message);
	}

}
