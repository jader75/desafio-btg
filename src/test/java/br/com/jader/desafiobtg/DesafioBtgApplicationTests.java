package br.com.jader.desafiobtg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import br.com.jader.desafiobtg.cliente.Cliente;
import br.com.jader.desafiobtg.itens.ItemEntityInsert;
import br.com.jader.desafiobtg.itens.ItemEntityInsertRepository;
import br.com.jader.desafiobtg.pedido.Pedido;
import br.com.jader.desafiobtg.pedido.PedidoRepository;
import br.com.jader.desafiobtg.pedido.PedidoService;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DesafioBtgApplicationTests {
	
    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoService pedidoServiceMock;

    @MockBean
    private static PedidoRepository pedidoRepositoryMock;
	
    @MockBean
    private static ItemEntityInsertRepository itemEntityInsertRepositoryock;
    
    private static LocalDateTime hoje = LocalDateTime.now();
    private static Cliente clienteTesteMock = new Cliente(1l, "Cliente Teste 1", hoje);;
    
    @BeforeEach
    public void setUp() {
        Mockito.when( itemEntityInsertRepositoryock.findByPedidoId(1L)).thenReturn( getListaItensMock() );
        Mockito.when( pedidoRepositoryMock.findByClienteId( clienteTesteMock.clienteId() ) ).thenReturn( getPedidosMock() );
    }
    
    /**************************************
     * Teste de calculo utilizando metodos mais atuais de programação (Stream, Map, Reduce)
     **************************************/
	@Test
	void testPedidoTotalCorreto() {
		BigDecimal valorEsperado = getListaItensMock().stream()
													.map( (x) -> x.valorItem().multiply( BigDecimal.valueOf( x.quantidade() ) ) )
													.reduce( (x, y) -> x.add( y ) ).get();
		assertEquals( valorEsperado, pedidoServiceMock.getPedidoTotal( clienteTesteMock.clienteId () ) );
	}
	
    /**************************************
     * Teste de calculo com resultado incorreto
     **************************************/
	@Test
	void testPedidoTotalIncorreto() {
		BigDecimal valorEsperado = new BigDecimal(999999999);		
		assertNotEquals( valorEsperado, pedidoServiceMock.getPedidoTotal( clienteTesteMock.clienteId() ) );
	}
	
    /**************************************
     * Teste utilizando mock em acessos externos (Bando de dados)
     **************************************/
	@Test
	void testQuantidadePedidosPorClienteIncorreto() {
		Long valorPedido =  pedidoServiceMock.getQuantidadePedidosClienteId( clienteTesteMock.clienteId() );
		assertNotEquals( 999999999999999999L, valorPedido );
	}

    /**************************************
     * Teste utilizando mock em acessos externos (Bando de dados)
     **************************************/
	@Test
	void testQuantidadePedidosPorClienteCorreto() {
		Long valorPedido =  pedidoServiceMock.getQuantidadePedidosClienteId( clienteTesteMock.clienteId() );
		assertEquals( 4L, valorPedido );
	}
	
    /**************************************
     * Teste fazendo acesso ao Banco de dados conforme os registros inseridos pelo arquivo src/test/resources/data.sql 
     **************************************/
	@Test
	void testPedidosPorClienteNaoEncontrado() {
		var pedidos = pedidoService.getPedidosClienteId( 999999999L );
		assertTrue( pedidos.isEmpty() );
	}
	
    /**************************************
     * Teste fazendo acesso ao Banco de dados conforme os registros inseridos pelo arquivo src/test/resources/data.sql 
     **************************************/
	@Test
	void testPedidosPorClienteEncontrado() {
		var pedidos = pedidoService.getPedidosClienteId( clienteTesteMock.clienteId() );
		assertFalse(pedidos.isEmpty());
	}
	
	private List<ItemEntityInsert> getListaItensMock() {
		var itemLapis 		= new ItemEntityInsert(1L, 1L, 10, new BigDecimal(1), LocalDateTime.now());
		var itemBorracha 	= new ItemEntityInsert(1L, 2L, 20, new BigDecimal(0.7), LocalDateTime.now());
		var itemCaderno 	= new ItemEntityInsert(1L, 3L, 15, new BigDecimal(0.8), LocalDateTime.now());
		var itens = new LinkedList<ItemEntityInsert>();
		itens.add(itemLapis);
		itens.add(itemBorracha);
		itens.add(itemCaderno);
		
		return itens;
	}
	
	private List<Pedido> getPedidosMock() {
		var listaPedidos = new LinkedList<Pedido>();
		
		listaPedidos.add( new Pedido(1L, clienteTesteMock, Collections.emptyList(), hoje) );
		listaPedidos.add( new Pedido(2L, clienteTesteMock, Collections.emptyList(), hoje) );
		listaPedidos.add( new Pedido(3L, clienteTesteMock, Collections.emptyList(), hoje) );
		listaPedidos.add( new Pedido(4L, clienteTesteMock, Collections.emptyList(), hoje) );
		
		return listaPedidos;
	}

}
