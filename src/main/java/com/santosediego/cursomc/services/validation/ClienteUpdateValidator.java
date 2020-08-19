package com.santosediego.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.santosediego.cursomc.domain.Cliente;
import com.santosediego.cursomc.dto.ClienteDTO;
import com.santosediego.cursomc.repositories.ClienteRepository;
import com.santosediego.cursomc.resources.exceptions.FieldMessage;

/*Validador da anotação ClienteUpdate a anotação customizada*/
public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	/*
	 * Como se refere a uma atualização vamos utilizar o DTO especifico para
	 * atualizar
	 */

	@Autowired
	private ClienteRepository repo;

	/* Para conseguirmos o id de atualização vamos utilizar o objeto abaixo que pegará o id pela uri*/
	@Autowired
	private HttpServletRequest request;

	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {

		@SuppressWarnings("unchecked")//Anotação para retirar o Warning, não é necessário;
		Map<String, String> map = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		// O cara acima pega um Map das variaveis de uri que estão na requisição;
		Integer uriId = Integer.parseInt(map.get("id"));

		// Lista criada para carregar o nome do campo e o tipo de erro identificado;
		List<FieldMessage> list = new ArrayList<>();

		Cliente aux = repo.findByEmail(objDto.getEmail());

		if (aux != null && !aux.getId().equals(uriId)) {
			list.add(new FieldMessage("email", "Email já existente"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}