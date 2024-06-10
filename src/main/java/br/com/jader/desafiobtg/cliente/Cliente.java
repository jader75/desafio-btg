package br.com.jader.desafiobtg.cliente;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("CLIENTES")
public record Cliente (
	
    @Id
    @Column(value="CLIENTE_ID")
	Long clienteId,
	
	@Column(value="NOME_COMPLETO")
	String nomeCompleto, 
	
	@CreatedDate
	@Column(value="CRIADO_EM")
	LocalDateTime criadoEm
	
) {
	
}
