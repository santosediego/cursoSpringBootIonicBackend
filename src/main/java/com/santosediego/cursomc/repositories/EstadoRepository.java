package com.santosediego.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santosediego.cursomc.domain.Estado;

@Repository //Para identificar que a classe é um repositório anexamos essa anotação;
public interface EstadoRepository extends JpaRepository<Estado, Integer>{
	
	/*Modificar de class para interface e etender para a JpaRepository
	 * assim o próprio JPA disponibilizará todas as movimentações sem ter que
	 * ficar implementando todos os acesso de dados da classe Categoria
	 * (insert, update, delete e tals).
	 * */
}
