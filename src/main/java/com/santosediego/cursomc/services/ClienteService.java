package com.santosediego.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santosediego.cursomc.domain.Cliente;
import com.santosediego.cursomc.repositories.ClienteRepository;
import com.santosediego.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class ClienteService {

	@Autowired //A depedência será auto instanciada pelo Spring Boot com esta anotação;
	private ClienteRepository repo;// A interface.
	
	public Cliente buscar(Integer id) {
		
		Optional<Cliente> obj = repo.findById(id);
		
		/*Esse modelo é para o Sprint a partir da 2.0*/
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! ID: " + id + ", Tipo: " + Cliente.class.getName()));
	}
}
