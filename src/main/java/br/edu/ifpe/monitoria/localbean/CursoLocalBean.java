package br.edu.ifpe.monitoria.localbean;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.edu.ifpe.monitoria.entidades.ComponenteCurricular;
import br.edu.ifpe.monitoria.entidades.Curso;
import br.edu.ifpe.monitoria.utils.DelecaoRequestResult;

@Stateless
@LocalBean
@DeclareRoles({"comissao","professor"})
public class CursoLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	@PermitAll
	public List<Curso> consultaCursos()
	{
		List<Curso> cursos = em.createNamedQuery("Curso.findAll", Curso.class).getResultList();
		
		return cursos;
	}
	
	@RolesAllowed({"comissao"})
	public boolean atualizaCurso(Curso curso)
	{
		em.merge(curso);

		return true;
	}
	
	@RolesAllowed({"comissao"})
	public Curso consultaCursoById(Long id)
	{
		Curso cursoPorId = em.createNamedQuery("Curso.findById", Curso.class).setParameter("id", id).getSingleResult();
		
		return cursoPorId;
	}
	
	@RolesAllowed({"comissao"})
	public List<Curso> consultaCursoByName(String nome)
	{
		List<Curso> cursos = em.createNamedQuery("Curso.findByNome", Curso.class).setParameter("nome", nome).getResultList();
		
		return cursos;
	}
	
	@RolesAllowed({"comissao"})
	public DelecaoRequestResult deletaCurso(Long id)
	{
		DelecaoRequestResult delecao = new DelecaoRequestResult();
		
		List<ComponenteCurricular> componentesDoCurso = em.createNamedQuery("ComponenteCurricular.findByCurso", ComponenteCurricular.class).setParameter("id", id).getResultList();
		
		if(componentesDoCurso != null && !componentesDoCurso.isEmpty())
		{
			delecao.errors.add("Existem componentes vinculados à este curso, remova-os primeiro!");
		}
		else
		{
			try {
				Curso cursoDeletado = em.createNamedQuery("Curso.findById", Curso.class).setParameter("id", id).getSingleResult();
				try {
					em.remove(cursoDeletado);
				} catch (Exception e) {
					delecao.errors.add("Problemas na remoção da entidade no banco de dados, contate o suporte.");
				}
			} catch (NoResultException e) {
				delecao.errors.add("Entidade não encontrada no banco de dados, contate o suporte.");
			}
		}
		
		return delecao;
	}
	
	@RolesAllowed({"comissao"})
	public boolean persisteCurso(@NotNull @Valid Curso curso)
	{
		em.persist(curso);
		
		return true;
	}
}
