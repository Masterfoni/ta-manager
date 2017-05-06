package br.edu.ifpe.monitoria.entidades;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidadorPeriodo implements ConstraintValidator<ValidaPeriodo, String>{

	@Override
	public void initialize(ValidaPeriodo constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		try {
			int ano = Integer.parseInt(value.substring(0,4));
			String barra = value.substring(4,5);
			int periodo = Integer.parseInt(value.substring(5, 6));
			if(ano > 2000 && ano < 3000){
				if(barra.equals("/")){
					if(periodo > 0 && periodo < 3){
						return true;
					}
				}
			}
		}catch (Exception e) {
			
		}
		return false;
	}
	
}
