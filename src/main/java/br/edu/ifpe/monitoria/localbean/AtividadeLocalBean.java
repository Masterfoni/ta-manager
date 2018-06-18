package br.edu.ifpe.monitoria.localbean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import br.edu.ifpe.monitoria.entidades.Atividade;
import br.edu.ifpe.monitoria.utils.CriacaoRequestResult;

@Stateless
@LocalBean
public class AtividadeLocalBean {
	
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	public CriacaoRequestResult registrarAvidade(Atividade atividade) {
		CriacaoRequestResult resultado = new CriacaoRequestResult();
		
		resultado.errors = validarData(atividade);
		
		if(!resultado.hasErrors()) {
			em.persist(atividade);
			resultado.result = true;
		}
		
		return resultado;
	}
	
	private List<String> validarData(Atividade atividade) {
		List<String> validationFailures = new ArrayList<String>();
		
		if(atividade.getData().before(atividade.getFrequencia().getMonitoria().getEdital().getInicioMonitoria()) || 
				atividade.getData().after(atividade.getFrequencia().getMonitoria().getEdital().getFimMonitoria())) {
			
			
			validationFailures.add("A data da atividade precisa ser no periodo da monitoria. Entre " + 
						atividade.getFrequencia().getMonitoria().getEdital().getInicioMonitoria() +
						" e " + 
						atividade.getFrequencia().getMonitoria().getEdital().getFimMonitoria());
		}
		
		return validationFailures;
	}
}
