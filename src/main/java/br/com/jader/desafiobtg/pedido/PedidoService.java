package br.com.jader.desafiobtg.pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jader.desafiobtg.itens.Item;
import br.com.jader.desafiobtg.itens.ItemEntityInsert;
import br.com.jader.desafiobtg.itens.ItemEntityInsertRepository;

@Service
public class PedidoService {

	@Autowired
	private final PedidoRepository pedidoRepository;
	@Autowired
	private final PedidoEntityInsertRepository pedidoEntityInsertRepository;
	@Autowired
	private final ItemEntityInsertRepository itemEntityInsertRepository;
	@Autowired
	private ObjectMapper mapper;

	public PedidoService(PedidoRepository pedidoRepository, PedidoEntityInsertRepository pedidoEntityInsertRepository
			, ItemEntityInsertRepository itemEntityInsertRepository, ObjectMapper mapper) {
		this.pedidoRepository = pedidoRepository;
		this.pedidoEntityInsertRepository = pedidoEntityInsertRepository;
		this.itemEntityInsertRepository = itemEntityInsertRepository;
		this.mapper = mapper;
	}

	public List<Pedido> getPedidosClienteId(long clientId) {
		return this.pedidoRepository.findByClienteId(clientId);
	}

	public Long getQuantidadePedidosClienteId(long clientId) {
		return this.pedidoRepository.findByClienteId(clientId).stream().count();
	}

	@Transactional
	public PedidoEntityInsert criarPedido(String mensagem) throws Exception {
		// converter mensagem json em entity
		Pedido pedido = mapper.readValue(mensagem, Pedido.class);
		PedidoEntityInsert pedidoNovo = salvarPedido(pedido);
		return pedidoNovo;
	}

	public PedidoEntityInsert salvarPedido(Pedido pedido) {
		// salvar no banco de dados
		PedidoEntityInsert pedidoEntityNovo = this.pedidoEntityInsertRepository.save( new PedidoEntityInsert(null, pedido.cliente().clienteId(), LocalDateTime.now()) );
		pedido.itens().forEach((i) -> { salvar( pedidoEntityNovo.pedidoId(), i ); });
		return pedidoEntityNovo;
	}
	
	private void salvar(Long pedidoIdNovo, Item i) {
		itemEntityInsertRepository.executeQueryInsert( pedidoIdNovo, i.itemId().produtoId(), i.quantidade() );
	}

	public BigDecimal getPedidoTotal(Long pedidoId) {
		List<ItemEntityInsert> itens = this.itemEntityInsertRepository.findByPedidoId(pedidoId);
		return itens
				.stream()
				.map((x) -> x.valorItem().multiply(BigDecimal.valueOf(x.quantidade())))
				.reduce((x, y) -> x.add(y))
					.orElseThrow( () -> new PedidoInvalidoException("Não foi encontrado o Pedido No:" + pedidoId));
	}
	
}
