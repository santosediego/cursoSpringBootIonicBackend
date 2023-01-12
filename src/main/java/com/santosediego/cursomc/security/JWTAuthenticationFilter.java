package com.santosediego.cursomc.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.santosediego.cursomc.dto.CredenciaisDTO;

//Código boilerplate, deverá ser feito conforme o framework.
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//Quando extendemos esse filtro, automaticamente o SpringSecurty sabe que esse filtro vai intercepitar o login
//Ele vai intercepetar a requisição de login, onde que /login já é padrão do framework, já é reservado por ele.

	private AuthenticationManager authenticationManager;
	//Códigos injetados que será utilizados nessa primira implementação JWTAuthenticationFilter;
    private JWTUtil jwtUtil;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
    	setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    //Função de tentar autenticar, segundo professor o código boilerplate total.
	@Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {

		try {
			CredenciaisDTO creds = new ObjectMapper()//Operação que tentará pegar a requisição
	                .readValue(req.getInputStream(), CredenciaisDTO.class);//E converter para CredenciaisDTO
	
	        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(), new ArrayList<>());

	        Authentication auth = authenticationManager.authenticate(authToken);//Verifica se usuário e senha são válidos e informa para o SSec se esta valido ou nao;;
	        return auth;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
			//Caso essa função apresentar problema é por que deu ruim mesmo.
		}
	}

	//Se a autenticação acima deu certo passará para essa função, aqui gera um token e acrentar na resposta da requisição.
	@Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

		String username = ((UserSS) auth.getPrincipal()).getUsername();
        String token = jwtUtil.generateToken(username);
        res.addHeader("Authorization", "Bearer " + token);
        res.addHeader("access-control-expose-headers", "Authorization");
	}

	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json"); 
            response.getWriter().append(json());
        }

        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"Não autorizado\", "
                + "\"message\": \"Email ou senha inválidos\", "
                + "\"path\": \"/login\"}";
        }
    }
}

/*
 * Implementando autenticação e geração do token JWT, checklist: Criar um filtro de 
 * autenticação.Registar esse filtro na configuração do spring security.
 */