package br.com.jader.desafiobtg.itens;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.persistence.TemporalType;

@Table("ITENS")
public record ItemEntityInsert(

		@jakarta.persistence.Id
		@Column(value = "PEDIDO_ID")
	    Long pedidoId,
	    
		@jakarta.persistence.Id
		@Column(value = "PRODUTO_ID")
	    Long produtoId, 
		
		@Column(value = "QTDE")
	    Integer quantidade,
	    
		@Column(value = "VALOR")
	    BigDecimal valorItem,
	    
	    @CreatedDate
	    @Temporal(TemporalType.TIMESTAMP)
		@Column(value = "CRIADO_EM")
	    LocalDateTime criadoEm 

) {}
