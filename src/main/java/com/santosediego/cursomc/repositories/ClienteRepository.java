package com.santosediego.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.santosediego.cursomc.domain.Cliente;

@Repository // Para identificar que a classe é um repositório anexamos essa anotação;
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	/*
	 * No Spring Data conseguimos criar uma pesquisa inserindo logo a frente da
	 * palavra findBy o nome do campo que queremos buscar, o spring data
	 * identificará isso automaticamente.
	 */

	/*
	 * A anotação transactional faz com que a pesquisa não precisa ser envolvida com
	 * uma trasaçao de banco de dados, fica mais rápido e evita lokin no banco de
	 * dados
	 */

	@Transactional(readOnly = true)
	Cliente findByEmail(String email);
}