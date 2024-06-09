package br.com.jader.desafiobtg.itens;

import org.springframework.data.relational.core.mapping.Column;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;

@Embeddable
public record ItemId (
		@Id
		@Column(value = "PEDIDO_ID")
	    Long pedidoId
	    ,
	    @Id
		@Column(value = "PRODUTO_ID")
	    Long produtoId

) {}
