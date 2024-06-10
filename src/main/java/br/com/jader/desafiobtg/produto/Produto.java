package br.com.jader.desafiobtg.produto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("PRODUTOS")
public record Produto(
		
    @Column(value="PRODUTO_ID")
    Long produtoId,
    
    @Column(value="DESCRICAO")
    String descricao,
    
    @Column(value="VALOR_PADRAO")
    BigDecimal valorPadrao,

    @Column(value="CRIADO_EM")
    LocalDateTime criadoEm 
) {}
