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
	 * @param componenteCurricular Um objeto ComponenteCurricular que representa o estado atualizado.
	 * @return true no caso de sucesso 
	 */
	public boolean atualizaEsquemaBolsa(EsquemaBolsa esquemaBolsa)
	{
		em.merge(esquemaBolsa);

		return true;
	}

	/**
	 * Método responsável por salvar um novo esquema de bolsa no banco de dados
	 *
	 * @param {@code Bolsa} bolsa Entidade de bolsa à ser persistidaid Identificador único do professor
	 * @return {@code boolean} True ou False dependendo do resultado da operação
	 */
	public boolean persisteEsquemaBolsa(@NotNull @Valid EsquemaBolsa esquemaBolsa)
	{
		em.persist(esquemaBolsa);

		return true;
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


