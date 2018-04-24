package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.localbean.EditalLocalBean;
import br.edu.ifpe.monitoria.utils.DelecaoRequestResult;

@ManagedBean (name="gerenciaEditalView")
@ViewScoped
public class GerenciaEditalView implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private EditalLocalBean editalbean;

	public List<Edital> editais;
	
	public Edital editalAtualizado;
	
	public Edital editalPersistido;
	
	/** Retorna todos os editais criados no sistema
     * @return List<Edital> - Lista de editais
     */
	public List<Edital> getEditais() {
		editais = editalbean.consultaEditais();
		return editais;
	}
	
	/** Atribui uma lista de editas 
     * @param editais List<Edital>
     */
	public void setEditais(List<Edital> editais) {
		this.editais = editais;
	}
	
	/** Retorna um Edital gerado que será atualizado
     * @return Edital - Edital atualizado
     */
	public Edital getEditalAtualizado() {
		return editalAtualizado;
	}

	/** Atribui um edital para ser atualizado 
     * @param editalAtualizado Edital
     */
	public void setEditalAtualizado(Edital editalAtualizado) {
		this.editalAtualizado = editalAtualizado;
	}
	
	/** Retorna um Edital que será criado
     * @return Edital - Edital salvo
     */
	public Edital getEditalPersistido() {
		return editalPersistido;
	}

	/** Atribui um edital para ser criado 
     * @param editalPersistido Edital
     */
	public void setEditalPersistido(Edital editalPersistido) {
		this.editalPersistido = editalPersistido;
	}

	
	/** Construtor
	 *  Inicializa as propriedades: editais, editalAtualizado e editalPersistido
	 */
	public GerenciaEditalView() {
		editais = new ArrayList<Edital>();
		editalAtualizado = new Edital();
		editalPersistido = new Edital();
	}
	
	/** Persiste no banco de dados um edital criado na propriedade editalPersistido 
	 * Exibe uma mensagem ao usuário quando ocorre o sucesso na transação
     */
	public void cadastrarEdital()
	{
		if(validarDatas(editalPersistido)) {		
			editalPersistido.setNumeroEdital(editalPersistido.getNumero() + "/" + editalPersistido.getAno());
			if(editalbean.persisteEdital(editalPersistido))
			{
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Cadastro realizado com sucesso!"));
				editalPersistido = new Edital();
			}
		}
	}
	
	/** Valida as datas dos periodos de um edital 
	 * As datas são validadas se a data de inicio de periodo for antes da data de término
	 * @param editalPersistido Edital
	 * @return boolean - Se as datas são válidos
     */
	private boolean validarDatas(Edital edital) {
		FacesContext context = FacesContext.getCurrentInstance();
		boolean flag = true;
		if (edital.getInicioInscricaoComponenteCurricular().after(edital.getFimInscricaoComponenteCurricular())) {
			context.addMessage(null, new FacesMessage("A data para o fim de Inserção do Componente Curricular deve ser depois da data de início."));
			flag = false;
		}  if (edital.getInicioInscricaoEstudante().after(edital.getFimInscricaoEstudante())) {
			context.addMessage(null, new FacesMessage("A data para o fim de Inscrição dos Alunos deve ser depois da data de início."));
			flag = false;
		}  if (edital.getInicioInsercaoNota().after(edital.getFimInsercaoNota())) {
			context.addMessage(null, new FacesMessage("A data para o fim de Inserção das Notas deve ser depois da data de início."));
			flag = false;
		}  if (edital.getInicioInsercaoPlano().after(edital.getFimInsercaoPlano())) {
			context.addMessage(null, new FacesMessage("A data para o fim de Inserção dos Planos de Monitoria deve ser depois da data de início."));
			flag = false;
		}  if (edital.getInicioMonitoria().after(edital.getFimMonitoria())) {
			context.addMessage(null, new FacesMessage("A data para o fim da Monitoria deve ser depois da data de início."));
			flag = false;
		}
		return flag;
	}
	
	/** Exclui o edital informado do sistema 
	 * @param edital Edital
     */
	public String deletaEdital(Edital edital) {
		DelecaoRequestResult remocaoResultado = editalbean.deletaEdital(edital.getId());

		if(remocaoResultado.hasErrors())
		{
			FacesContext context = FacesContext.getCurrentInstance();
			
			for (String erro : remocaoResultado.errors) {
				context.addMessage(null, new FacesMessage(erro));
			}
		} 
		else 
		{
			editais.remove(edital);
		}
		
		return "";
	}
	
	/** Seleciona o edital para exibir informaçoes de alteração 
	 * @param edital Edital
     */
	public void alteraEdital(Edital edital) {
		editalAtualizado = edital;
		System.out.println(editalAtualizado);
	}
	
	/** Altera um edital existente
	 * O edital alterado esta na propriedade editalAtualizado 
	 * @param edital Edital
     */
	public void persisteAlteracao() {
		if(validarDatas(editalAtualizado)) {	
			editalAtualizado.setNumeroEdital(editalAtualizado.getNumero() + "/" + editalAtualizado.getAno());
			editalbean.atualizaEdital(editalAtualizado);
		}
	}
}
