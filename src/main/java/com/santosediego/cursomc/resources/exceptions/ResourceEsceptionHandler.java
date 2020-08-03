package com.santosediego.cursomc.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.santosediego.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceEsceptionHandler {

	/*Essa implementação é padrão do @ControllerAdvice*/
	
	/*Classe auxiliar que irá interceptar as excesões, ela terá a assinatura abaixo*/
	
	@ExceptionHandler(ObjectNotFoundException.class)/*Para indicar que é um tratador de exceções desse tipo;*/
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
}
