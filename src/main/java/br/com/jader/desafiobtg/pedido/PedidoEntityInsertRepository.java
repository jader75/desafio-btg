package br.com.jader.desafiobtg.pedido;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PedidoEntityInsertRepository extends ListCrudRepository<PedidoEntityInsert, Long>{
	
}
