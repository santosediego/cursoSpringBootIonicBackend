package com.santosediego.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.santosediego.cursomc.domain.Categoria;
import com.santosediego.cursomc.repositories.CategoriaRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	/* Implemenar a CommandLineRunner para puxar a função run abaixo */
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*A função run ira ao iniciar instanciar no projeto que a gente colocar aqui;*/
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		/*saveAll a partir do Spring 2.(...)*/
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
	}

}
