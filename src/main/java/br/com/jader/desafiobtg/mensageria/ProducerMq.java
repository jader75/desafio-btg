package br.com.jader.desafiobtg.mensageria;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import br.com.jader.desafiobtg.pedido.Pedido;

@Component
public class ProducerMq {

	public static void enviarMensagem(RabbitTemplate template, String topico, String chaveRoteamento, Object ojbMensagem) throws Exception {
	
		switch (ojbMensagem) {
			case null -> {
				// handle null case
			}
			case Pedido p -> {
		    	ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		    	String json = ow.writeValueAsString(p);
				template.convertAndSend(topico, chaveRoteamento, json);
			}
			case String s -> {
				template.convertAndSend(topico, chaveRoteamento, s);
			}
			default -> {
				// handle everything else
			}
		}
	}

}
