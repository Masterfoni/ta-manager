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

import br.edu.ifpe.monitoria.entidades.Curso;

@Stateless
@LocalBean
@DeclareRoles({"comissao","professor"})
public class CursoLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	@RolesAllowed({"comissao", "professor"})
	public List<Curso> consultaCursos()
	{
		List<Curso> cursos = em.createNamedQuery("Curso.findAll", Curso.class).getResultList();
		
		return cursos;
	}
	
	@RolesAllowed({"comissao"})
	public boolean atualizaCurso(Curso curso)
	{
		Curso cursoAtualizar = em.createNamedQuery("Curso.findById", Curso.class).setParameter("id", curso.getId()).getSingleResult();
		
		cursoAtualizar.setCoordenador(curso.getCoordenador());
		cursoAtualizar.setSigla(curso.getSigla());
		cursoAtualizar.setNome(curso.getNome());
		
		em.merge(cursoAtualizar);
		
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
	public boolean deletaCurso(Long id)
	{
		Curso cursoDeletado = em.createNamedQuery("Curso.findById", Curso.class).setParameter("id", id).getSingleResult();
		
		em.remove(cursoDeletado);
		
		return true;
	}
	
	@RolesAllowed({"comissao"})
	public boolean persisteCurso(@NotNull @Valid Curso curso)
	{
		em.persist(curso);
		
		return true;
	}
}
