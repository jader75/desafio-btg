package br.com.jader.desafiobtg.mensageria;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.jader.desafiobtg.pedido.PedidoEntityInsert;
import br.com.jader.desafiobtg.pedido.PedidoService;

@Component
public class ConsumerMq {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	private CountDownLatch latch = new CountDownLatch(1);
	@Autowired
	private PedidoService pedidoService;
	

	@Transactional
	public void consumirMensagem(String mensagem) {
		try {
			logger.info("Mensagem Recebida");
			PedidoEntityInsert pedidoNovo = pedidoService.criarPedido(mensagem);
			logger.info("Pedido Criado : " + pedidoNovo.pedidoId());
			latch.countDown();
		} catch (Exception e) {
			latch.countDown();
			logger.error("Erro ao consumir/criar Pedido!");
		}
	}

	public CountDownLatch getLatch() {
		return latch;
	}
	
}
