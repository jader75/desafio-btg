package br.com.jader.desafiobtg.itens;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;



public interface ItemEntityInsertRepository extends ListCrudRepository<ItemEntityInsert, Long>{
	
    @Modifying
    @Query(
    		value = "INSERT INTO ITENS VALUES (:idPedido, :idProduto, (SELECT VALOR_PADRAO FROM PRODUTOS WHERE PRODUTO_ID=:idProduto), :qtde, CURRENT_TIMESTAMP());"
    )
	public int executeQueryInsert(@Param(value = "idPedido") Long idPedido, @Param(value = "idProduto") Long idProduto, @Param(value = "qtde") Integer qtde);
    
    List<ItemEntityInsert> findByPedidoId(Long pedidoId);
    
}
