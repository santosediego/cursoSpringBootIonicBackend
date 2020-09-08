package com.santosediego.cursomc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.santosediego.cursomc.domain.Cliente;
import com.santosediego.cursomc.domain.Pedido;

@Repository //Para identificar que a classe é um repositório anexamos essa anotação;
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

	@Transactional(readOnly=true)//Para reduzir o looking
	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest); 
}
