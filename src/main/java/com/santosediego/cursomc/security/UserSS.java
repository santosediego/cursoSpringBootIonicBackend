package com.santosediego.cursomc.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.santosediego.cursomc.domain.enums.Perfil;

public class UserSS implements UserDetails {
	/*
	 * UserDetails é um contrato do Spring Security para poder trabalhar com
	 * usuários. UserSS de User Spring Security;
	 */

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String email;
	private String senha;

	// Aqui se refere a lista de perfis, então já colocamos ela da forma que o
	// UserDetails exige.
	private Collection<? extends GrantedAuthority> authorities;

	public UserSS() {
	}

	public UserSS(Integer id, String email, String senha, Set<Perfil> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao()))
				.collect(Collectors.toList());
		// Aqui convertemos o perfil para Collection, onde, para cada x instanciamos um
		// SimpleGrantedAuthority(informando a descrição)coletando para a lista;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// Perfis do usuário, aqui é o get;
		return authorities;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		/*
		 * Verifica se a conta não está expirada, neste caso não iremos utilizar então
		 * por padrã enviaremos true, na necessidade de utilização deverá implementar
		 * uma lógica.
		 */
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		/*
		 * Aqui veririficamos se a conta não está bloqueada, igualmente a anterior, na
		 * necessidade de utilização, implementar uma lógica. Por padrão vamos retornar
		 * true.
		 */
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// Segue a mesma ideia dos comnentários anteriores;
		return true;
	}

	@Override
	public boolean isEnabled() {
		/* Aqui pergunta se o usuário esta ativo, segue padrão informando true. */
		return true;
	}

	//daqui pra cima:
	/*
	 * Implementando autenticação e geração do token JWT, checklist: Criar classe de
	 * usuário conforme contrato do Spring Security (implements UserDetails)
	 */
	
	public boolean hasRole(Perfil perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
	}
}