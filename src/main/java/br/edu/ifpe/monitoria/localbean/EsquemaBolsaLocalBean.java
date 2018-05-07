package br.edu.ifpe.monitoria.localbean;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.edu.ifpe.monitoria.entidades.EsquemaBolsa;
import br.edu.ifpe.monitoria.entidades.PlanoMonitoria;
import br.edu.ifpe.monitoria.utils.AtualizacaoRequestResult;
import br.edu.ifpe.monitoria.utils.CriacaoRequestResult;
import br.edu.ifpe.monitoria.entidades.Curso;
import br.edu.ifpe.monitoria.entidades.Edital;

@Stateless
@LocalBean
public class EsquemaBolsaLocalBean 
{
	
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;

	/**
	 * Método responsável por atualizar um esquema de bolsa.
	 *
	 * @param esquemaBolsa Um objeto {@code EsquemaBolsa} que representa o estado atualizado.
	 * @return AtualizacaoRequestResult Um objeto contendo eventuais mensagens de erros e um {@code boolean} representando o resultado;
	 */
	public AtualizacaoRequestResult atualizaEsquemaBolsa(EsquemaBolsa esquemaBolsa)
	{
		AtualizacaoRequestResult resultado = new AtualizacaoRequestResult();
		
		List<PlanoMonitoria> planos = em.createNamedQuery("PlanoMonitoria.findByEditaleCurso", PlanoMonitoria.class)
									  .setParameter("edital", esquemaBolsa.getEdital())
									  .setParameter("curso", esquemaBolsa.getCurso().getId()).getResultList();
		
		int numeroBolsas = 0;
		
		for(PlanoMonitoria plano : planos)
		{
			numeroBolsas += plano.getBolsas();
		}
		
		if(numeroBolsas > esquemaBolsa.getQuantidade())
		{
			resultado.errors.add("Esta quantidade de bolsas é menor do que o número já distribuído pelo coordenador nos planos!");
			resultado.result = false;
		}
		else
		{
			em.merge(esquemaBolsa);
			resultado.result = true;
		}
		

		return resultado;
	}

	/**
	 * Método responsável por salvar um novo esquema de bolsa no banco de dados
	 *
	 * @param {@code Bolsa} bolsa Entidade de bolsa à ser persistidaid Identificador único do professor
	 * @return {@code CriacaoRequestResult} Objeto que terá seu atributo {@code result} setado como true ou false dependendo do resultado da operação
	 */
	public CriacaoRequestResult persisteEsquemaBolsa(@NotNull @Valid EsquemaBolsa esquemaBolsa)
	{
		CriacaoRequestResult resultado = new CriacaoRequestResult();
		
		List<EsquemaBolsa> esquemas = em.createNamedQuery("EsquemaBolsa.findByEditalCurso", EsquemaBolsa.class)
									  .setParameter("idEdital", esquemaBolsa.getEdital().getId())
									  .setParameter("idCurso", esquemaBolsa.getCurso().getId()).getResultList();
		
		if(!esquemas.isEmpty()) {
			resultado.errors.add("Já existe um esquema de bolsas deste curso para este edital!");
			resultado.result = false;
		} else {
			em.persist(esquemaBolsa);
			resultado.result = true;
		}
		
		return resultado;
	}

	/**
	 * Método responsável por procurar os esquemas de bolsas de um determinado edital
	 *
	 * @param edital Edital possuidor de esquemas
	 * @return {@code List<EsquemaBolsa>} Lista de esquemas de um determinado edital
	 */
	public List<EsquemaBolsa> consultaEsquemaByEdital(Edital edital) {

		List<EsquemaBolsa> esquemas = em.createNamedQuery("EsquemaBolsa.findByEdital", EsquemaBolsa.class)
				.setParameter("idEdital", edital.getId()).getResultList();

		return esquemas;
	}
	
	/**
	 * Método responsável por procurar os esquemas de bolsas de um determinado curso
	 *
	 * @param edital Curso curso possuidor de esquemas
	 * @return {@code List<EsquemaBolsa>} Lista de esquemas de um determinado curso
	 */
	public List<EsquemaBolsa> consultaEsquemaByCurso(Curso curso) {

		List<EsquemaBolsa> esquemas = em.createNamedQuery("EsquemaBolsa.findByCurso", EsquemaBolsa.class)
				.setParameter("idCurso", curso.getId()).getResultList();

		return esquemas;
	}
	
	/**
	 * Método responsável por procurar os esquemas de bolsas para um curso num determinado edital
	 *
	 * @param edital Edital possuidor de curso
	 * @param curso Curso possuidor de esquemas
	 * @return {@code List<EsquemaBolsa>} Lista de esquemas de um determinado edital para um curso
	 */
	public List<EsquemaBolsa> consultaEsquemaByEditalCurso(Edital edital, Curso curso) {

		List<EsquemaBolsa> esquemas = em.createNamedQuery("EsquemaBolsa.findByEditalCurso", EsquemaBolsa.class)
				.setParameter("idEdital", curso.getId()).setParameter("idCurso", curso.getId()).getResultList();

		return esquemas;
	}

}


