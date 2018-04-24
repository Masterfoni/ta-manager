package br.edu.ifpe.monitoria.utils;

import java.util.ArrayList;
import java.util.List;

public class RequestResult {
	public List<String> errors;
	
	public RequestResult() {
		errors = new ArrayList<String>();
	}
	
	public boolean hasErrors() {
		return errors.size() > 0;
	}
}
