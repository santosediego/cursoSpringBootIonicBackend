package com.santosediego.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.santosediego.cursomc.domain.Pedido;
import com.santosediego.cursomc.services.PedidoService;

/* -- Controlador REST -- */

@RestController //Anotações necessárias para Rest;
@RequestMapping(value = "/pedidos")//Necessário para direcionar a url para esse recurso;
public class PedidoResource {
	
	@Autowired
	private PedidoService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)//Para a função poder funcionar deve-se anota-la com o devido metodo;
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {//Para identificar o id que o usuário digitou a anotação PathVarable direciona;
		/*O ResponseEntity automaticamente já encapsula varias info http para o serviço rest*/
		
		Pedido obj = service.find(id);
		
		return ResponseEntity.ok().body(obj);
	}

}