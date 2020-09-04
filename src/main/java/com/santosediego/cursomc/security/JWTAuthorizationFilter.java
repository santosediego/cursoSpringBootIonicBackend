package com.santosediego.cursomc.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private JWTUtil jwtUtil;

	private UserDetailsService userDetailsService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil,
			UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	// Método que itercepta a requisição e ve se de fato esta autorizado, ou seja, para liberar a autorização;
	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain chain) throws IOException, ServletException {

		// este metodo recebe como argumento o nome do cabeçalho;
		String header = request.getHeader("Authorization");

		if (header != null && header.startsWith("Bearer ")) {

			// É passado a chave menos os 7 primeiros digitos que equivale ao Bearer e o
			// espaço, o resto é só a chave. Quando o token for invalido retornará null;
			UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));

			if(auth != null) {
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		if(jwtUtil.tokenValido(token)) {// implementado tokenValido na JWTUtil
			String username = jwtUtil.getUsername(token); // na JWTUtil;
			UserDetails user = userDetailsService.loadUserByUsername(username);
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}
		return null;
	}
}