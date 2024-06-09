package br.com.jader.desafiobtg.pedido;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PedidoRepository extends ListCrudRepository<Pedido, Long>{
	
	Pedido findByPedidoIdAndClienteClienteId(Long pedidoId, Long clientId); 
	
	@Query(value = "SELECT p.*,c.* FROM PEDIDOS p JOIN clientes c ON c.cliente_id = p.cliente_id WHERE p.cliente_Id=:clientId")
	List<Pedido> findByClienteId(@Param("clientId") long clientId);
	
	@Query(value = "SELECT p.*,c.* FROM PEDIDOS p JOIN clientes c ON c.cliente_id = p.cliente_id WHERE p.cliente_Id=:idCliente and pedido_id = :idPedido")
	Pedido findPedidoByPedidoAndClienteId(@Param("idPedido") long pedidoId, @Param("idCliente") long clientId);

}
