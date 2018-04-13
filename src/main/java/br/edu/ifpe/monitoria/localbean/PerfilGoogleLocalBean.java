package br.edu.ifpe.monitoria.localbean;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.edu.ifpe.monitoria.entidades.Grupo;
import br.edu.ifpe.monitoria.entidades.PerfilGoogle;

@Stateless
@LocalBean
public class PerfilGoogleLocalBean 
{
	
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	public boolean persistePerfilGoogle(@NotNull @Valid PerfilGoogle perfilGoogle)
	{
		em.persist(perfilGoogle);
		
		Grupo gp = new Grupo();
		gp.setGrupo(Grupo.Grupos.COMISSAO);
		gp.setUsuario(perfilGoogle.getUsuario());
		gp.setEmail(perfilGoogle.getUsuario().getEmail());
		em.persist(gp);
		
		Grupo gp1 = new Grupo();
		gp1.setGrupo(Grupo.Grupos.COORDENADOR);
		gp1.setUsuario(perfilGoogle.getUsuario());
		gp1.setEmail(perfilGoogle.getUsuario().getEmail());
		em.persist(gp1);
		
		Grupo gp2 = new Grupo();
		gp2.setGrupo(Grupo.Grupos.PROFESSOR);
		gp2.setUsuario(perfilGoogle.getUsuario());
		gp2.setEmail(perfilGoogle.getUsuario().getEmail());
		em.persist(gp2);
		
		
		return true;
	}
}
