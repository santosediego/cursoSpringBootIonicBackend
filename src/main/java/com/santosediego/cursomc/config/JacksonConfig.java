package com.santosediego.cursomc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.santosediego.cursomc.domain.PagamentoComBoleto;
import com.santosediego.cursomc.domain.PagamentoComCartao;

@Configuration
public class JacksonConfig {
// https://stackoverflow.com/questions/41452598/overcome-can-not-construct-instance-ofinterfaceclass-without-hinting-the-pare

	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			public void configure(ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(PagamentoComCartao.class);// Classes a registrar;
				objectMapper.registerSubtypes(PagamentoComBoleto.class);// Classes a registrar;
				super.configure(objectMapper);
			}
		};
		return builder;
	}
}

/*
 * Essa classe de configuração para o Jackson é padrão, o que muda aqui é as
 * classes que temos que registrar;
 */