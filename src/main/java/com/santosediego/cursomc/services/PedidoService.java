package com.santosediego.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santosediego.cursomc.domain.Pedido;
import com.santosediego.cursomc.repositories.PedidoRepository;
import com.santosediego.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class PedidoService {

	@Autowired //A depedência será auto instanciada pelo Spring Boot com esta anotação;
	private PedidoRepository repo;// A interface.
	
	public Pedido find(Integer id) {
		
		Optional<Pedido> obj = repo.findById(id);
		
		/*Esse modelo é para o Sprint a partir da 2.0*/
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! ID: " + id + ", Tipo: " + Pedido.class.getName()));
	}
}