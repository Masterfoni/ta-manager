package br.edu.ifpe.monitoria.localbean;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import br.edu.ifpe.monitoria.entidades.Atividade;
import br.edu.ifpe.monitoria.entidades.Frequencia;
import br.edu.ifpe.monitoria.utils.AtualizacaoRequestResult;
import br.edu.ifpe.monitoria.utils.CriacaoRequestResult;

@Stateless
@LocalBean
public class AtividadeLocalBean {
	
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	public CriacaoRequestResult registrarAvidade(Atividade atividade, List<Frequencia> frequencias) {
		CriacaoRequestResult resultado = new CriacaoRequestResult();
		
		atividade.setFrequencia(frequencias);
		
		validarData(atividade, resultado.errors);
		validarHorario(atividade, resultado.errors);
		
		if(!resultado.hasErrors()) {
			em.persist(atividade);
			Frequencia frequencia = atividade.getFrequencia();
			frequencia.addAtividade(atividade);
			em.merge(atividade.getFrequencia());
			em.flush();
			resultado.result = true;
		}
		
		return resultado;
	}
	
	public void removeAtividade(Atividade atividade, Frequencia frequencia) {
		
		Atividade atv = em.createNamedQuery("Atividade.findById", Atividade.class)
			.setParameter("id", atividade.getId()).getSingleResult();
		
		em.remove(atv);
		frequencia.removeAtividade(atv);
		em.merge(frequencia);
		em.flush();
	}
	
	private void validarHorario(Atividade atividade, List<String> errors) {
		if(atividade.getHoraInicio().after(atividade.getHoraFim())) {
			errors.add("A hora de início da atividade deve ser antes da hora de encerramento.");
		}
	}
	
	private void validarData(Atividade atividade, List<String> errors) {
		
		if(atividade.getFrequencia() == null) {
			errors.add("A data da atividade precisa ser no período da monitoria.");
			return;
		}
		
		if(atividade.getData().before(atividade.getFrequencia().getMonitoria().getEdital().getInicioMonitoria()) || 
				atividade.getData().after(atividade.getFrequencia().getMonitoria().getEdital().getFimMonitoria())) {
			
			
			errors.add("A data da atividade precisa ser no periodo da monitoria. Entre " + 
						atividade.getFrequencia().getMonitoria().getEdital().getInicioMonitoria() +
						" e " + 
						atividade.getFrequencia().getMonitoria().getEdital().getFimMonitoria());
		}
		
		GregorianCalendar amanha = new GregorianCalendar();
		amanha.setTime(new Date());
		amanha.set(Calendar.DAY_OF_MONTH, amanha.get(Calendar.DAY_OF_MONTH) + 1);
		if(atividade.getData().after(amanha.getTime())) {
			errors.add("Só é possível adicionar atividades que já ocorreram.");
		}
		
	}

	public AtualizacaoRequestResult atualizarAtividade(Atividade atividade) {
		AtualizacaoRequestResult resultado = new AtualizacaoRequestResult();
		
		validarHorario(atividade, resultado.errors);
		
		if(!resultado.hasErrors()) {
			em.merge(atividade);
			em.merge(atividade.getFrequencia());
			resultado.result = true;
		}
		return resultado;
	}
}
