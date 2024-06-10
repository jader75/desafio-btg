package br.com.jader.desafiobtg.pedido;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jader.desafiobtg.mensageria.ConfiguracaoMensageria;
import br.com.jader.desafiobtg.mensageria.ProducerMq;

@RestController
@RequestMapping("pedido")
public class PedidoController {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ConfiguracaoMensageria configuracaoMensageria;

	@GetMapping("/{pedidoId}/total")
	public BigDecimal getTotal(@PathVariable Long pedidoId) {
		return this.pedidoService.getPedidoTotal(pedidoId);
	}
	
	@GetMapping("/cliente/{clienteId}")
	public List<Pedido> getPedidosCliente(@PathVariable Integer clienteId) {
		return this.pedidoService.getPedidosClienteId(clienteId);
	}

	@GetMapping("/cliente/{clienteId}/quantidade")
	public long getQuantidadePedidosCliente(@PathVariable Integer clienteId) {
		return this.pedidoService.getQuantidadePedidosClienteId(clienteId);
	}
	
	@PostMapping("/criar")
	public ResponseEntity<Object> enviarMensagem(@RequestBody Pedido pedido, Boolean isTest) {
		String body = "Pedido Recebido!";

		try {
			ProducerMq.enviarMensagem(rabbitTemplate, configuracaoMensageria.TOPICO, configuracaoMensageria.CHAVE_ROTEAMENTO, pedido);
		} catch (Exception e) {
			logger.warn("Erro ao criar Pedido!");
			return ResponseEntity.badRequest().body(new PedidoResponseEntity("Erro na Requisição!", HttpStatus.BAD_REQUEST.value()));
		}
		return ResponseEntity.ok(new PedidoResponseEntity(body, HttpStatus.ACCEPTED.value()));
	}
}
