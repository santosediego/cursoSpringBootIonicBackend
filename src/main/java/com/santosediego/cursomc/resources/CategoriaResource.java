package com.santosediego.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.santosediego.cursomc.domain.Categoria;

@RestController //Anotações necessárias para Rest;
@RequestMapping(value = "/categorias")//Necessário para direcionar a url para esse recurso;
public class CategoriaResource {
	
	@RequestMapping(method = RequestMethod.GET)//Para a função poder funcionar deve-se anota-la com o devido metodo;
	public List<Categoria> listar() {//mudar o método para lista;
		
		Categoria cat1 = new Categoria(1, "Informática");
		Categoria cat2 = new Categoria(2, "Escritório");
		
		List<Categoria> list = new ArrayList<>();
		list.add(cat1);
		list.add(cat2);
		
		return list;
	}

}
