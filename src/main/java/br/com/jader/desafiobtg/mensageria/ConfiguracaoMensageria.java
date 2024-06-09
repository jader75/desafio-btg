package br.com.jader.desafiobtg.mensageria;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguracaoMensageria {

	@Value( "${mq_chave_roteamento}" )
	public String CHAVE_ROTEAMENTO;

	@Value( "${mq_topico}" )
	public String TOPICO;

	@Value( "${mq_fila}" )
	public String FILA;

	@Bean
	Queue queue() {
		return new Queue(FILA, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(TOPICO);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(CHAVE_ROTEAMENTO);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(FILA);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	// Registra o Listener no conteiner
	MessageListenerAdapter listenerAdapter(ConsumerMq consumerMq) {
		return new MessageListenerAdapter(consumerMq, "consumirMensagem");
	}

}