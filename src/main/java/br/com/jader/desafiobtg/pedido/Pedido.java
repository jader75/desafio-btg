package br.com.jader.desafiobtg.pedido;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.jader.desafiobtg.cliente.Cliente;
import br.com.jader.desafiobtg.itens.Item;
import jakarta.persistence.TemporalType;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
@Table("PEDIDOS")
public record Pedido(
	    @Id
	    @Column(value="PEDIDO_ID")
	    Long pedidoId,
	    
	    @Embedded.Nullable
	    Cliente cliente,
	    
	    @MappedCollection(idColumn = "PEDIDO_ID", keyColumn = "PEDIDO_ID")
	    List<Item> itens,
	    
	    @CreatedDate
	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(value="CRIADO_EM")
	    LocalDateTime criadoEm
) {}
