package br.edu.ifpe.monitoria.localbean;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import br.edu.ifpe.monitoria.entidades.Aluno;
import br.edu.ifpe.monitoria.entidades.Frequencia;
import br.edu.ifpe.monitoria.entidades.Monitoria;
import br.edu.ifpe.monitoria.utils.FrequenciaRequestResult;

@Stateless
@LocalBean
public class FrequenciaLocalBean {
	
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	public FrequenciaRequestResult findByAluno(Aluno aluno) {
		FrequenciaRequestResult resultado = new FrequenciaRequestResult();
		
		List<Frequencia> frequencias = em.createNamedQuery("Frequencia.findByAluno", Frequencia.class)
				.setParameter("aluno", aluno)
				.getResultList();
		
		if(frequencias.size() > 0) {
			resultado.frequencias = frequencias;
		} else {
			resultado.errors.add("Não existem frequencias registradas para esse aluno");
		}
		
		return resultado;
	}
	
	public FrequenciaRequestResult findByMonitoria(Monitoria monitoria) {
		
		FrequenciaRequestResult resultado = new FrequenciaRequestResult();
		
		List<Frequencia> frequencias = em.createNamedQuery("Frequencia.findByMonitoria", Frequencia.class)
				.setParameter("monitoria", monitoria)
				.getResultList();
		
		if(frequencias.size() < 1) {
			if(monitoria.isSelecionado()) {
				Date hoje = new Date();
				if(hoje.after(monitoria.getEdital().getInicioMonitoria()) && 
						hoje.before(monitoria.getEdital().getFimMonitoria())) {
					
					criarFrequencias(monitoria);
					frequencias = em.createNamedQuery("Frequencia.findByMonitoria", Frequencia.class)
							.setParameter("monitoria", monitoria)
							.getResultList();
					
				} else {
					resultado.errors.add("Ainda não esta no periodo de Monitoria.");
				}
			}
			else {
				resultado.errors.add("Estudante não foi selecionado para monitoria neste Edital.");
			}
		}
		resultado.frequencias = frequencias;
		return resultado;
	}

	private void criarFrequencias(Monitoria monitoria) {
		List<GregorianCalendar> meses = monitoria.getEdital().getMesesMonitoria();
		
		for (GregorianCalendar mes : meses) {
			Frequencia f = new Frequencia();
			f.setMonitoria(monitoria);
			f.setMes(mes.get(GregorianCalendar.MONTH));
			em.persist(f);
		}
	}


}
