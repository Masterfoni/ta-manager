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
import br.edu.ifpe.monitoria.utils.RelatorioFinalRequestResult;

@Stateless
@LocalBean
public class RelatorioFinalLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	public boolean persisteRelatorio(@NotNull @Valid RelatorioFinal relatorioFinal)
	{
		try {
			em.persist(relatorioFinal);			
		} catch (Exception e) {
			return false;
		}
		
		return true;
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
		
		try {
			em.merge(relatorioFinal);
			resultado.result = true;
		} catch (Exception e) {
			resultado.errors.add(e.getMessage());
			resultado.result = false;
		}
		
		return resultado;
	}
}
