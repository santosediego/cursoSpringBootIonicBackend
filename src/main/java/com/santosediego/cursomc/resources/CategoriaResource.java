package com.santosediego.cursomc.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.santosediego.cursomc.domain.Categoria;
import com.santosediego.cursomc.services.CategoriaService;

/* -- Controlador REST -- */

@RestController //Anotações necessárias para Rest;
@RequestMapping(value = "/categorias")//Necessário para direcionar a url para esse recurso;
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)//Para a função poder funcionar deve-se anota-la com o devido metodo;
	public ResponseEntity<?> find(@PathVariable Integer id) {//Para identificar o id que o usuário digitou a anotação PathVarable direciona;
		/*O ResponseEntity automaticamente já encapsula varias info http para o serviço rest*/
		
		Categoria obj = service.buscar(id);
		
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){
		
		obj = service.insert(obj);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}

}