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

import br.edu.ifpe.monitoria.entidades.Coordenacao;

@Stateless
@LocalBean
@DeclareRoles({"administrativo","professor"})
public class CoordenacaoLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	@RolesAllowed({"administrativo", "professor"})
	public List<Coordenacao> consultaCoordenacoes()
	{
		List<Coordenacao> coordenacoes = em.createNamedQuery("Coordenacao.findAll", Coordenacao.class).getResultList();
		
		return coordenacoes;
	}
	
	@RolesAllowed({"administrativo"})
	public boolean atualizaCoordenacao(Coordenacao coordenacao)
	{
		Coordenacao coordAtualizar = em.createNamedQuery("Coordenacao.findById", Coordenacao.class).setParameter("id", coordenacao.getId()).getSingleResult();
		
		coordAtualizar.setDepartamento(coordenacao.getDepartamento());
		coordAtualizar.setCoordenador(coordenacao.getCoordenador());
		coordAtualizar.setSigla(coordenacao.getSigla());
		coordAtualizar.setNome(coordenacao.getNome());
		
		em.merge(coordAtualizar);
		
		return true;
	}
	
	@RolesAllowed({"administrativo"})
	public Coordenacao consultaCoordenacaoById(Long id)
	{
		Coordenacao coordPorId = em.createNamedQuery("Coordenacao.findById", Coordenacao.class).setParameter("id", id).getSingleResult();
		
		return coordPorId;
	}
	
	@RolesAllowed({"administrativo"})
	public List<Coordenacao> consultaCoordenacaoByName(String nome)
	{
		List<Coordenacao> coords = em.createNamedQuery("Coordenacao.findByNome", Coordenacao.class).setParameter("nome", nome).getResultList();
		
		return coords;
	}
	
	@RolesAllowed({"administrativo"})
	public boolean deletaCoordenacao(Long id)
	{
		Coordenacao coordDeletado = em.createNamedQuery("Coordenacao.findById", Coordenacao.class).setParameter("id", id).getSingleResult();
		
		em.remove(coordDeletado);
		
		return true;
	}
	
	@RolesAllowed({"administrativo"})
	public boolean persisteCoordenacao(@NotNull @Valid Coordenacao coordenacao)
	{
		em.persist(coordenacao);
		
		return true;
	}
}
