package br.edu.ifpe.monitoria.localbean;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.edu.ifpe.monitoria.entidades.RelatorioFinal;
import br.edu.ifpe.monitoria.utils.AtualizacaoRequestResult;
import br.edu.ifpe.monitoria.utils.CriacaoRequestResult;
import br.edu.ifpe.monitoria.utils.RelatorioFinalRequestResult;

@Stateless
@LocalBean
public class RelatorioFinalLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	public CriacaoRequestResult persisteRelatorio(@NotNull @Valid RelatorioFinal relatorioFinal)
	{
		CriacaoRequestResult resultado = new CriacaoRequestResult();
		
		if(relatorioFinal.getAvaliacao() > 10) {
			resultado.errors.add("Avaliação deve ser igual ou inferior à 10!");
		}
		
		if(!resultado.hasErrors()) {
			try {
				em.persist(relatorioFinal);
				resultado.result = true;
			} catch (Exception e) {
				resultado.errors.add(e.getMessage());
				resultado.result = false;
			}			
		}
		
		return resultado;
	}
	
	public RelatorioFinalRequestResult consultaRelatorioFinalPorMonitor(Long monitorId) {
		RelatorioFinalRequestResult resultado = new RelatorioFinalRequestResult();
		
		try {
			resultado.result = em.createNamedQuery("RelatorioFinal.findByMonitor", RelatorioFinal.class).setParameter("monitorId", monitorId)
												.getSingleResult();
		} catch (Exception e) {
			resultado.errors.add(e.getMessage());
		}
		
		return resultado;
	}
	
	public AtualizacaoRequestResult atualizaRelatorio(@NotNull @Valid RelatorioFinal relatorioFinal)
	{
		AtualizacaoRequestResult resultado = new AtualizacaoRequestResult();
		
		if(relatorioFinal.getAvaliacao() > 10) {
			resultado.errors.add("Avaliação deve ser igual ou inferior à 10!");
		}
		
		if(!resultado.hasErrors()) {
			try {
				em.merge(relatorioFinal);
				resultado.result = true;
			} catch (Exception e) {
				resultado.errors.add(e.getMessage());
				resultado.result = false;
			}
		}
		
		return resultado;
	}
}
