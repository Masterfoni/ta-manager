package br.edu.ifpe.monitoria.testutils;

import java.util.Date;
import java.util.GregorianCalendar;

import br.edu.ifpe.monitoria.entidades.Edital;

public class TesteAvulso {
	
	public static void main(String[] args) {
		Edital ed = new Edital();
		ed.setInicioMonitoria(new Date(new GregorianCalendar(2018, 10, 1).getTime().getTime()));
		ed.setFimMonitoria(new Date(new GregorianCalendar(2019, 0, 31).getTime().getTime()));
		
		for (GregorianCalendar mes : ed.getMesesMonitoria()) {
			System.out.println(mes.get(GregorianCalendar.MONTH));
		}
	}
}
