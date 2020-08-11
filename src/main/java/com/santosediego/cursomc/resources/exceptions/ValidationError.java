package com.santosediego.cursomc.resources.exceptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError implements Serializable{

	private static final long serialVersionUID = 1L;

	public List<FildMessage> errors = new ArrayList<>();

	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
	}

	public List<FildMessage> getErrors() {
		return errors;
	}

	public void addError(String fildName, String message) {
		errors.add(new FildMessage(fildName, message));
	}
}