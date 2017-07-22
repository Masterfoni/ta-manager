package br.edu.ifpe.monitoria.localbean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import br.edu.ifpe.monitoria.entidades.Monitoria;

@Stateless
@LocalBean
public class MonitoriaLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	public List<Monitoria> consultaMonitorias()
	{
		List<Monitoria> monitorias = em.createNamedQuery("Monitoria.findAll", Monitoria.class).getResultList();
		
		return monitorias;
	}
	
	public boolean aprovaMonitoria(Monitoria monitoria)
	{
		Monitoria monitoriaAprovada = em.createNamedQuery("monitorias.findById", Monitoria.class)
											   .setParameter("id", monitoria.getId()).getSingleResult();
		
		monitoriaAprovada.setAluno(monitoria.getAluno());
		monitoriaAprovada.setBolsa(monitoria.isBolsa());
		monitoriaAprovada.setPlanoMonitoria(monitoria.getPlanoMonitoria());
		monitoriaAprovada.setSelecionado(true);
		
		em.merge(monitoriaAprovada);
		
		return true;
	}
	
	public List<Monitoria> consultaMonitoriaByProfessor(Long idProfessor)
	{
		List<Monitoria> monitorias = em.createNamedQuery("Monitoria.findByProfessor", Monitoria.class)
										  .setParameter("id", idProfessor).getResultList();
		
		return monitorias;
	}
	
	public boolean defereMonitoria(Monitoria monitoria)
	{
		Monitoria monitoriaDeferida = em.createNamedQuery("Monitoria.findById", Monitoria.class)
										 .setParameter("id", monitoria.getId()).getSingleResult();

		monitoriaDeferida.setAluno(monitoria.getAluno());
		monitoriaDeferida.setBolsa(monitoria.isBolsa());
		monitoriaDeferida.setPlanoMonitoria(monitoria.getPlanoMonitoria());
		monitoriaDeferida.setSelecionado(false);
		
		em.merge(monitoriaDeferida);
		
		return true;
	}
}
