package com.santosediego.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.santosediego.cursomc.security.JWTAuthenticationFilter;
import com.santosediego.cursomc.security.JWTAuthorizationFilter;
import com.santosediego.cursomc.security.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)// Permite inserr anotações de pré autorizações nos endpoints;
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;

	@Autowired
	private UserDetailsService userDetailsService;
	//Automaticamente será identificado a implementação da classe UserDetailsServiceImpl;

	@Autowired
	private JWTUtil jwtUtil;
	// Caminhos que por padrão estaram liberados;
	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**" // o endpoint e tudo que vier depois;
	};

	// Caminhos que será permitido apenas leitura;
	private static final String[] PUBLIC_MATCHERS_GET = {
			"/produtos/**",
			"/categorias/**",
			"/estados/**"
	};

	private static final String[] PUBLIC_MATCHERS_POST = {
			"/clientes",
			"/auth/forgot/**"
	};

	// Sobreescrever o método configure;
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable(); // Se tiver em modo teste habilita o h2 console;
		}

		http.cors().and().csrf().disable();
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll() // permitir autorização de post no vetor de post;
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll() // permitir autorização de get no vetor de get;
			.antMatchers(PUBLIC_MATCHERS).permitAll()
			.anyRequest().authenticated();
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));/*Em SecurityConfig, registrar o filtro de autenticação*/
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override //Código padrão
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		//passwordEncoder recebe a função mais abaixo. Injetar a classe userDetailsService
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
		/*
		 * Implementando autenticação e geração do token JWT, checklist: Em
		 * SecurityConfig, sobrescrever o método: public void
		 * configure(AuthenticationManagerBuilder auth).
		 */
	}

	// Definindo um ban dando acesso basico a todos os caminhos por multiplas
	// fontes.
	// https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/cors/CorsConfiguration.html
	@Bean
	CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));

		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}

	// Função para encriptar a senha do cliente;
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}