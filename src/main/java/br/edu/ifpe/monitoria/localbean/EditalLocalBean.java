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

import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.entidades.Monitoria;
import br.edu.ifpe.monitoria.entidades.PlanoMonitoria;
import br.edu.ifpe.monitoria.utils.DelecaoRequestResult;

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
	@PermitAll
	public List<Edital> consultaEditais()
	{
		List<Edital> editais = em.createNamedQuery("Edital.findAll", Edital.class).getResultList();
		
		return editais;
	}
	
	/** Atualiza informações de um edital especifico no sistema
     * @param edital Edital - Edital atualizado
     * @return boolean - Informa se houve sucesso na transação
     */
//	@RolesAllowed({"comissao"})
	public boolean atualizaEdital(Edital edital)
	{
		em.merge(edital);
		
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
	public DelecaoRequestResult deletaEdital(Long id)
	{
		DelecaoRequestResult delecao = new DelecaoRequestResult();
		boolean podeExcluir = true;
		
		Edital editalDeletado = em.createNamedQuery("Edital.findById", Edital.class)
				.setParameter("id", id).getSingleResult();
		
		List<Monitoria> monitorias = em.createNamedQuery("Monitoria.findByEdital", Monitoria.class).setParameter("edital", editalDeletado).getResultList();
		List<PlanoMonitoria> planos = em.createNamedQuery("PlanoMonitoria.findByEdital", PlanoMonitoria.class).setParameter("edital", editalDeletado).getResultList();
		
		if(monitorias != null && !monitorias.isEmpty()) {
			delecao.errors.add("Existem monitorias vinculados à este edital, não é possivel excluir este edital. "
					+ "Mas você pode dizer que não está vigente na opção alterar.");
			podeExcluir = false;
		}
		if(planos != null && !planos.isEmpty()) {
			delecao.errors.add("Existem planos de monitorias vinculados à este edital, não é possivel excluir este edital. "
					+ "Remova-os primeiro!");
			podeExcluir = false;
		}
		
		if(podeExcluir) {
			try {
				em.remove(editalDeletado);
			} catch (Exception e) {
				delecao.errors.add("Problemas na remoção da entidade no banco de dados, contate o suporte.");
			}
		}
				
		return delecao;
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

	public List<Edital> consultaEditaisVigentes() {
		List<Edital> editais = em.createNamedQuery("Edital.findVigentes", Edital.class).getResultList();
		
		return editais;
	}
}
