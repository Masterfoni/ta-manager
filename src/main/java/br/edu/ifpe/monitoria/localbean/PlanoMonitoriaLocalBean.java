package br.edu.ifpe.monitoria.localbean;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.edu.ifpe.monitoria.entidades.Curso;
import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.entidades.EsquemaBolsa;
import br.edu.ifpe.monitoria.entidades.Monitoria;
import br.edu.ifpe.monitoria.entidades.PlanoMonitoria;
import br.edu.ifpe.monitoria.utils.CriacaoRequestResult;
import br.edu.ifpe.monitoria.utils.DelecaoRequestResult;

/**
 * Responsável por salvar, listar, atualizar e remover planos de monitoria no banco de dados.
 *
 * @author Felipe Araujo, João Vitor
 */
@Stateless
@LocalBean
public class PlanoMonitoriaLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	/**
	 * Método responsável por inserir no banco um novo plano de monitoria
	 *
	 * @param plano uma instância de {@code PlanoMonitoria} que representa o plano à ser persistido
	 * @return true no caso de sucesso 
	 */
	public CriacaoRequestResult persistePlanoMonitoria (@Valid @NotNull PlanoMonitoria plano)
	{
		CriacaoRequestResult resultado = new CriacaoRequestResult();
		
		List<PlanoMonitoria> planoSingleResult = em.createNamedQuery("PlanoMonitoria.findByEditalComponente", PlanoMonitoria.class)
				.setParameter("editalId", plano.getEdital().getId())
				.setParameter("ccId", plano.getCc().getId()).getResultList();

		if(!planoSingleResult.isEmpty()) {
			resultado.errors.add("Já existe um plano cadastrado para este edital e componente!");
			resultado.result = false;
		}
		
		
		if(!resultado.hasErrors()) {
			plano.setBolsas(0);
			plano.setHomologado(false);
			
			List<EsquemaBolsa> esquemaSingleResult = em.createNamedQuery("EsquemaBolsa.findByEditalCurso", EsquemaBolsa.class)
					.setParameter("idEdital", plano.getEdital().getId())
					.setParameter("idCurso", plano.getCc().getCurso().getId()).getResultList();
			
			if(esquemaSingleResult.size() > 0) {
				EsquemaBolsa esquemaAssociado = esquemaSingleResult.get(0);
				plano.setEsquemaAssociado(esquemaSingleResult.get(0));
				em.persist(plano);
				
				esquemaAssociado.addPlano(plano);
				em.merge(esquemaAssociado);
			} else {
				em.persist(plano);
			}
			
			resultado.result = true;
		}
		
		return resultado;
	}
	
	/**
	 * Método responsável por resgatar planos de monitoria com base no ID do servidor
	 *
	 * @param id Identificador único do servidor no banco de dados
	 * @return {@code List<PlanoMonitoria>} uma lista de planos de monitoria vinculados à um componente curricular do qual o servidor é professor. 
	 */
	public List<PlanoMonitoria> consultaPlanosByServidor(Long id)
	{
		List<PlanoMonitoria> planos = em.createNamedQuery("PlanoMonitoria.findByProfessor", PlanoMonitoria.class).setParameter("id", id).getResultList();
		
		return planos;
	}
	
	public List<PlanoMonitoria> consultaPlanosByCoordenador(Long id)
	{
		List<PlanoMonitoria> planos = em.createNamedQuery("PlanoMonitoria.findByCoordenador", PlanoMonitoria.class).setParameter("id", id).getResultList();
		
		return planos;
	}
	
	/**
	 * Método responsável por listar todos os planos cadastrados no sistema
	 *
	 * @return {@code List<PlanoMonitoria>} Uma lista de planos de monitoria, que pode estar vazia ou não. 
	 */
	public List<PlanoMonitoria> consultaPlanos()
	{
		List<PlanoMonitoria> planos = em.createNamedQuery("PlanoMonitoria.findAll", PlanoMonitoria.class).getResultList();
		
		return planos;
	}
	
	/**
	 * Método responsável por resgatar um plano de monitoria específico do banco de dados
	 *
	 * @param id Identificador único do plano de monitoria no banco de dados.
	 * @return {@code PlanoMonitoria} Plano de monitoria do banco de dados 
	 */
	public PlanoMonitoria consultaPlanosById(Long id)
	{
		PlanoMonitoria planoPorId = em.createNamedQuery("PlanoMonitoria.findById", PlanoMonitoria.class).setParameter("id", id).getSingleResult();
		
		return planoPorId;
	}
	
	/**
	 * Método responsável por persistir as atualizações de um plano de monitoria já cadastrado.
	 *
	 * @return {@code true} ou {@code false} dependendo do sucesso, ou não da operação.
	 */
	public boolean atualizaPlanoMonitoria(PlanoMonitoria plano)
	{
		em.merge(plano);
		
		return true;
	}
	
	public DelecaoRequestResult deletaPlanoMonitoria(Long id)
	{
		DelecaoRequestResult delecao = new DelecaoRequestResult();
		boolean podeExcluir = true;
		
		PlanoMonitoria planoDeletado = em.createNamedQuery("PlanoMonitoria.findById", PlanoMonitoria.class).setParameter("id", id).getSingleResult();
		
		List<Monitoria> monitorias = em.createNamedQuery("Monitoria.findByPlano", Monitoria.class).setParameter("plano", planoDeletado).getResultList();
		
		if(monitorias != null && !monitorias.isEmpty()) {
			delecao.errors.add("Existem monitorias vinculados à este edital, não é possivel excluir este edital. "
					+ "Mas você pode dizer que não está vigente na opção alterar.");
			podeExcluir = false;
		}
		
		if(podeExcluir) 
		{
			try {
				em.remove(planoDeletado);
			} catch (Exception e) {
				delecao.errors.add("Problemas na remoção da entidade no banco de dados, contate o suporte.");
			}
		}
				
		return delecao;
	}
	
	public List<PlanoMonitoria> consultaPlanosByEditaleCurso(Edital edital, Curso curso) {
		List<PlanoMonitoria> planos = em.createNamedQuery("PlanoMonitoria.findByEditaleCurso", PlanoMonitoria.class).
				setParameter("edital", edital).
				setParameter("curso", curso.getId()).
				getResultList();
		
		return planos;
	}
	
	public List<PlanoMonitoria> consultaPlanosByEdital(Edital edital, boolean apenasHomologados) {
		String namedQuery = apenasHomologados ? "PlanoMonitoria.findHomologadosByEdital" : "PlanoMonitoria.findByEdital";
		
		List<PlanoMonitoria> planos = em.createNamedQuery(namedQuery, PlanoMonitoria.class).setParameter("edital", edital).getResultList();
		
		return planos;
	}
}
