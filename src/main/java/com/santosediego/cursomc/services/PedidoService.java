package com.santosediego.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santosediego.cursomc.domain.Cliente;
import com.santosediego.cursomc.domain.ItemPedido;
import com.santosediego.cursomc.domain.PagamentoComBoleto;
import com.santosediego.cursomc.domain.Pedido;
import com.santosediego.cursomc.domain.enums.EstadoPagamento;
import com.santosediego.cursomc.repositories.ItemPedidoRepository;
import com.santosediego.cursomc.repositories.PagamentoRepository;
import com.santosediego.cursomc.repositories.PedidoRepository;
import com.santosediego.cursomc.security.UserSS;
import com.santosediego.cursomc.services.exceptions.AuthorizationException;
import com.santosediego.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired // A depedência será auto instanciada pelo Spring Boot com esta anotação;
	private PedidoRepository repo;// A interface.

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;//Instanciar no TestConfig um @Bean EmailService;

	public Pedido find(Integer id) {

		Optional<Pedido> obj = repo.findById(id);

		/* Esse modelo é para o Sprint a partir da 2.0 */
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! ID: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert(Pedido obj) {

		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);

		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());

		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		//Buscamos o usuário logado e buscamos somente os pedidos deste usuário;
		UserSS user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.find(user.getId());
		return repo.findByCliente(cliente, pageRequest);
	}
}