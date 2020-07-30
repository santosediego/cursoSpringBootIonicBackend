package com.santosediego.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santosediego.cursomc.domain.Categoria;
import com.santosediego.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired //A depedência será auto instanciada pelo Spring Boot com esta anotação;
	private CategoriaRepository repo;// A interface.
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElse(null);
	}
}
