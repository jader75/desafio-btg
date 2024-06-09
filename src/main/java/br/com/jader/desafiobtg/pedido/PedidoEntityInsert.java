package br.com.jader.desafiobtg.pedido;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.persistence.TemporalType;


@Table("PEDIDOS")
public record PedidoEntityInsert(
	    @Id
	    @Column(value="PEDIDO_ID")
	    Long pedidoId,
	    
	    @Column(value="CLIENTE_ID")
	    Long clienteId,
	    
	    @CreatedDate
	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(value="CRIADO_EM")
	    LocalDateTime criadoEm
) {}
