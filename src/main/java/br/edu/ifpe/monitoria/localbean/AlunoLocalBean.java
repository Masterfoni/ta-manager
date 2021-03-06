package br.edu.ifpe.monitoria.localbean;

import java.util.ArrayList;
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
import br.edu.ifpe.monitoria.entidades.Grupo;
import br.edu.ifpe.monitoria.utils.AlunoRequestResult;
import br.edu.ifpe.monitoria.utils.DelecaoRequestResult;

@Stateless
@LocalBean
public class AlunoLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	public boolean persisteAluno (@NotNull @Valid Aluno aluno, boolean criaGrupo)
	{		
		em.persist(aluno);

		if(criaGrupo) {
			Grupo gp = new Grupo();
			gp.setGrupo(Grupo.Grupos.ALUNO);
			gp.setUsuario(aluno);
			gp.setEmail(aluno.getEmail());
			em.persist(gp);
		}
		
		return true;
	}
	
	public Aluno consultaAlunoById(Long id)
	{
		Aluno alunoPorId = null;
		
		try {
			alunoPorId = em.createNamedQuery("Aluno.findById", Aluno.class).setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		
		return alunoPorId;
	}
	
	public AlunoRequestResult consultaAlunoByMatricula(String matricula) {
		AlunoRequestResult result = new AlunoRequestResult();
		
		try {
			result.result = em.createNamedQuery("Aluno.findByMatricula", Aluno.class).setParameter("matricula", matricula).getSingleResult();
		} catch (NoResultException e) {
			result.errors.add("Nenhum aluno encontrado com esta matr�cula");
		}
		
		return result;
	}
	
	public List<Aluno> consultaMonitoresByComponente(Long componenteId)
	{
		List<Aluno> alunosPorComponente = new ArrayList<Aluno>();
		
		try {
			alunosPorComponente = em.createNamedQuery("Aluno.findMonitoresByComponente", Aluno.class)
					.setParameter("componenteId", componenteId)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return alunosPorComponente;
	}
	
	public List<Aluno> consultaMonitoresByComponenteEdital(Long componenteId, Long editalId) {
		List<Aluno> alunosPorComponenteEdital = new ArrayList<Aluno>();
		
		try {
			alunosPorComponenteEdital = em.createNamedQuery("Aluno.findMonitoresByComponenteEdital", Aluno.class)
					.setParameter("editalId", editalId)
					.setParameter("componenteId", componenteId)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return alunosPorComponenteEdital;
	}
	
	public boolean atualizaAluno (Aluno aluno) {
		
		em.merge(aluno);
		return true;
	}
	
	public DelecaoRequestResult deletaAluno(Long id)
	{
		DelecaoRequestResult delecao = new DelecaoRequestResult();
		
		Aluno alunoDeletado = em.createNamedQuery("Aluno.findById", Aluno.class).setParameter("id", id).getSingleResult();

		try {
			em.remove(alunoDeletado);
		} catch (Exception e) {
			delecao.errors.add("Problemas na remo��o da entidade no banco de dados, contate o suporte.");
		}
				
		return delecao;
	}
}
