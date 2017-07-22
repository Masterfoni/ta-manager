package br.edu.ifpe.monitoria.localbean;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.edu.ifpe.monitoria.entidades.Monitoria;

@Stateless
@LocalBean
@DeclareRoles({"ADMINISTRATIVO", "PROFESSOR"})
public class MonitoriaLocalBean
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;

	@RolesAllowed({"PROFESSOR", "ADMINISTRATIVO"})
	public List<Monitoria> consultaMonitorias()
	{
		List<Monitoria> monitorias = em.createNamedQuery("Monitoria.findAll", Monitoria.class).getResultList();

		return monitorias;
	}

	@RolesAllowed({"PROFESSOR"})
	public boolean aprovaMonitoria(Monitoria monitoria)
	{
		Monitoria monitoriaAprovada = em.createNamedQuery("Monitoria.findById", Monitoria.class)
											   .setParameter("id", monitoria.getId()).getSingleResult();

		monitoriaAprovada.setAluno(monitoria.getAluno());
		monitoriaAprovada.setBolsa(monitoria.isBolsa());
		monitoriaAprovada.setPlanoMonitoria(monitoria.getPlanoMonitoria());
		monitoriaAprovada.setAvaliado(true);
		monitoriaAprovada.setSelecionado(true);
		
		System.out.println(monitoriaAprovada.isAvaliado());

		em.merge(monitoriaAprovada);

		return true;
	}

	@RolesAllowed({"PROFESSOR", "ADMINISTRATIVO"})
	public List<Monitoria> consultaMonitoriaByProfessor(Long idProfessor)
	{
		List<Monitoria> monitorias = em.createNamedQuery("Monitoria.findByProfessor", Monitoria.class)
										  .setParameter("id", idProfessor).getResultList();

		return monitorias;
	}
	
	@RolesAllowed({"PROFESSOR"})
	public boolean defereMonitoria(Monitoria monitoria)
	{
		Monitoria monitoriaDeferida = em.createNamedQuery("Monitoria.findById", Monitoria.class)
										 .setParameter("id", monitoria.getId()).getSingleResult();

		monitoriaDeferida.setAluno(monitoria.getAluno());
		monitoriaDeferida.setBolsa(monitoria.isBolsa());
		monitoriaDeferida.setPlanoMonitoria(monitoria.getPlanoMonitoria());
		monitoriaDeferida.setAvaliado(true);
		monitoriaDeferida.setSelecionado(false);
		
		System.out.println(monitoriaDeferida.isAvaliado());

		em.merge(monitoriaDeferida);

		return true;
	}

	public boolean persisteMonitoria (@Valid @NotNull Monitoria monitoria)
	{
		em.persist(monitoria);

		return true;
	}
}
