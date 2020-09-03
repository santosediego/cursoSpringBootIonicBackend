package com.santosediego.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.santosediego.cursomc.domain.Cliente;
import com.santosediego.cursomc.repositories.ClienteRepository;
import com.santosediego.cursomc.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	/*
	 * UserDetailsService interface do Spring Security que permite a pelo nome do
	 * usuário. Impl de implementação;
	 */

	@Autowired
	private ClienteRepository repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Cliente cli = repo.findByEmail(email);
		if (cli == null) {
			throw new UsernameNotFoundException(email);
			// Caso o usuário não exista, aplicar a exceção da UserDetailsService;
		}

		return new UserSS(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());
	}

}
/*
 * Implementando autenticação e geração do token JWT, checklist: Criar classe de
 * serviço conforme contrato do Spring Security (implements UserDetailsService))
 */