package br.edu.ifpe.monitoria.localbean;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.edu.ifpe.monitoria.entidades.Aluno;
import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.entidades.Monitoria;
import br.edu.ifpe.monitoria.entidades.PlanoMonitoria;
import br.edu.ifpe.monitoria.utils.DelecaoRequestResult;

@Stateless
@LocalBean
public class MonitoriaLocalBean
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;

	public List<Monitoria> consultaMonitoriasAvaliadas(){
		List<Monitoria> monitorias = em.createNamedQuery("Monitoria.findAvaliadas", Monitoria.class).getResultList();

		return monitorias;
	}
	
	
	public List<Monitoria> consultaMonitorias()
	{
		List<Monitoria> monitorias = em.createNamedQuery("Monitoria.findAll", Monitoria.class).getResultList();

		return monitorias;
	}

	public List<Monitoria> consultaMonitoriaByProfessor(Long idProfessor)
	{
		List<Monitoria> monitorias = em.createNamedQuery("Monitoria.findByProfessor", Monitoria.class)
										  .setParameter("id", idProfessor).getResultList();

		return monitorias;
	}
	
	public boolean persisteMonitoria (@Valid @NotNull Monitoria monitoria)
	{
		em.persist(monitoria);

		return true;
	}
	
	public DelecaoRequestResult deletaMonitoria(Long id)
	{
		DelecaoRequestResult delecao = new DelecaoRequestResult();
		
		Monitoria monitoriaDeletada = em.createNamedQuery("Monitoria.findById", Monitoria.class).setParameter("id", id).getSingleResult();

		try {
			em.remove(monitoriaDeletada);
		} catch (Exception e) {
			delecao.errors.add("Problemas na remoção da entidade no banco de dados, contate o suporte.");
		}
				
		return delecao;
	}


	public Monitoria consultaMonitoriaByAluno(Aluno aluno, Edital edital) {
		Monitoria monitoria = null;
		try {
		 monitoria = em.createNamedQuery("Monitoria.findByAluno", Monitoria.class).
				setParameter("aluno", aluno).
				setParameter("edital", edital).
				getSingleResult();
		}catch (NoResultException e) {
			e.printStackTrace();
		}
		return monitoria;
	}


	public void atualizaMonitoria(Monitoria monitoria) {
		em.merge(monitoria);
	}


	public List<Monitoria> consultaMonitoriaByPlano(PlanoMonitoria plano) {
		List<Monitoria> monitorias = em.createNamedQuery("Monitoria.findByPlano", Monitoria.class)
				  .setParameter("plano", plano).getResultList();

		return monitorias;
	}


	public void salvarNotas(List<Monitoria> monitorias) {
		monitorias = classificar(monitorias);
		int classificacao = 1;
		for (Monitoria monitoria : monitorias) {
			if(monitoria.isClassificado()) {
				monitoria.setClassificacao(classificacao);
				classificacao++;
			}
			em.merge(monitoria);
		}
		
	}

	public List<Monitoria> classificar(List<Monitoria> monitorias) {
		Collections.sort(monitorias, new Comparator<Monitoria>() {
			@Override
			public int compare(Monitoria m1, Monitoria m2) {
				if(m1.getNotaSelecao() != null && 
						m2.getNotaSelecao() != null && 
						m1.getMediaComponente() != null &&
						m2.getMediaComponente() != null) {
					if(m1.getNotaSelecao().doubleValue() > m2.getNotaSelecao().doubleValue()) {
						return -1;
					} else if (m1.getNotaSelecao().doubleValue() < m2.getNotaSelecao().doubleValue()) {
						return 1;
					} else if (m1.getNotaSelecao().doubleValue() == m2.getNotaSelecao().doubleValue()) {
						if(m1.getMediaComponente().doubleValue() > m2.getMediaComponente().doubleValue()) {
							return -1;
						} else if (m1.getMediaComponente().doubleValue() < m2.getMediaComponente().doubleValue()) {
							return 1;
						} else if (m1.getMediaComponente().doubleValue() == m2.getMediaComponente().doubleValue()) {
							return 0;
						}
					}
				}
				return 0;
			}

		});

		return monitorias;
	}
}
