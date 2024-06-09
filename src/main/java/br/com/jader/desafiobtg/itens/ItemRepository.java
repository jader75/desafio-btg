package br.com.jader.desafiobtg.itens;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;


public interface ItemRepository extends ListCrudRepository<Item, ItemId>{
	
	public List<Item> findByItemId_PedidoId(Long pedidoId);

}
