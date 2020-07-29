package com.santosediego.cursomc.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController //Anotações necessárias para Rest;
@RequestMapping(value = "/categorias")//Necessário para direcionar a url para esse recurso;
public class CategoriaResource {
	
	@RequestMapping(method = RequestMethod.GET)//Para a função poder funcionar deve-se anota-la com o devido metodo;
	public String listar() {
		return "Rest está funcionando!";
	}

}
