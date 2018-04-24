package br.edu.ifpe.monitoria.localbean;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.edu.ifpe.monitoria.entidades.Edital;

/**
 * Responsável por salvar, listar, atualizar e remover editais no banco de dados.
 *
 * @author Felipe Araujo, João Vitor
 */
@Stateless
@LocalBean
@DeclareRoles({"comissao", "professor", "aluno"})
public class EditalLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	/** Lista todos os editais criados no sistema
     * @return List<Edital> - Lista de editais
     */
	@RolesAllowed({"comissao", "professor"})
	public List<Edital> consultaEditais()
	{
		List<Edital> editais = em.createNamedQuery("Edital.findAll", Edital.class).getResultList();
		
		return editais;
	}
	
	/** Atualiza informações de um edital especifico no sistema
     * @param edital Edital - Edital atualizado
     * @return boolean - Informa se houve sucesso na transação
     */
	@RolesAllowed({"comissao"})
	public boolean atualizaEdital(Edital edital)
	{
		Edital editalAtualizar = em.createNamedQuery("Edital.findById", Edital.class)
											   .setParameter("id", edital.getId()).getSingleResult();
		
		editalAtualizar.setNumeroEdital(edital.getNumeroEdital());
		
		em.merge(editalAtualizar);
		
		return true;
	}
	
	/** Consulta um edital no sistema pelo ID
     * @param id Long - ID a ser consultado
     * @exception NoResultException 
     * @return Edital - Edital encontrado com ID informado
     */
	@RolesAllowed({"comissao", "professor"})
	public Edital consultaEditalById(Long id)
	{
		Edital editalPorId = null;
		try {
			editalPorId = em.createNamedQuery("Edital.findById", Edital.class)
										   .setParameter("id", id).getSingleResult();
		}
		catch (NoResultException e) {
			e.printStackTrace();
		}
		
		return editalPorId;
	}
	
	/** Deleta um edital no sistema pelo ID
     * @param id Long - ID do edital a ser excluído
     * @return boolean - Informa se houve sucesso na transação
     */
	@RolesAllowed({"comissao"})
	public boolean deletaEdital(Long id)
	{
		Edital editalDeletado = em.createNamedQuery("Edital.findById", Edital.class)
										 .setParameter("id", id).getSingleResult();
		
		em.remove(editalDeletado);		
		return true;
	}
	
	/** Salva um edital válido no sistema
     * @param edital Edital - Edital a ser salvo
     * @return boolean - Informa se houve sucesso na transação
     */
	@RolesAllowed({"comissao"})
	public boolean persisteEdital(@NotNull @Valid Edital edital)
	{
		em.persist(edital);
		
		return true;
	}
}
