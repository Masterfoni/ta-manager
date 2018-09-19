package br.edu.ifpe.monitoria.utils;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

public class Dominios {
	 
	private static List<String> dominiosInstituncionais;
	private static List<String> dominiosAlunos;
	
   	public static List<String> getDominiosInstituncionais() {
		if(dominiosInstituncionais == null) {
			carregarDominiosInstituncionais();
		} 
		return dominiosInstituncionais;
	}


	public static List<String> getDominiosAlunos() {
		if(dominiosAlunos == null) {
			carregarDominiosAlunos();
		}
		return dominiosAlunos;
	}
	
	private static void carregarDominiosInstituncionais() {
		String dominiosI = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("dominiosInstituncionais");
		int fim, inicio=0;
		dominiosInstituncionais = new ArrayList<String>();
		for (int i=0; i<dominiosI.length(); i++) {
			char c = dominiosI.charAt(i);
			if(c == ';') {
				fim = i;
				dominiosInstituncionais.add(dominiosI.substring(inicio, fim));
				inicio = i+1;
			}
		}
	}

	private static void carregarDominiosAlunos() {
		String dominiosA = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("dominiosAlunos");
		int fim, inicio=0;
		dominiosAlunos = new ArrayList<String>();
		for (int i=0; i<dominiosA.length(); i++) {
			char c = dominiosA.charAt(i);
			if(c == ';') {
				fim = i;
				dominiosAlunos.add(dominiosA.substring(inicio, fim));
				inicio = i+1;
			}
		}
	}
}
