package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.edu.ifpe.monitoria.entidades.Curso;
import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.entidades.EsquemaBolsa;
import br.edu.ifpe.monitoria.localbean.CursoLocalBean;
import br.edu.ifpe.monitoria.localbean.EditalLocalBean;
import br.edu.ifpe.monitoria.localbean.EsquemaBolsaLocalBean;
import br.edu.ifpe.monitoria.utils.AtualizacaoRequestResult;
import br.edu.ifpe.monitoria.utils.CriacaoRequestResult;
import br.edu.ifpe.monitoria.utils.DelecaoRequestResult;

@ManagedBean (name="gerenciaEditalView")
@ViewScoped
public class GerenciaEditalView implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{menuView}")
	private MenuView sharedMenuView;
	
	public void setSharedMenuView(MenuView sharedMenuView) {
		this.sharedMenuView = sharedMenuView;
	}
	
	private Edital editalGlobal;
	
	@EJB
	private EditalLocalBean editalbean;
	
	@EJB
	private EsquemaBolsaLocalBean esquemabean;
	
	@EJB
	private CursoLocalBean cursobean;
	
	public List<Edital> editais;
	
	public List<EsquemaBolsa> esquemasEdital;
	
	public List<Curso> cursos;

	public Edital editalAtualizado;
	
	public Edital editalPersistido;
	
	public Edital editalExpandido;
	
	public Curso cursoSelecionado;
	
	@PostConstruct
	public void init() {
		editalGlobal = sharedMenuView.getEditalGlobal();
	}
	
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
	
	/** Retorna os esquemas de bolsas de um determinado edital
     * @return List<EsquemaBolsa> - Lista de esquemas de bolsa
     */
	public List<EsquemaBolsa> getEsquemasEdital() {
		return esquemasEdital;
	}
	
	/** Atribui uma lista de esquemas de um determinado edital 
     * @param List<EsquemaBolsa> esquemas
     */
	public void setEsquemasEditals(List<EsquemaBolsa> esquemas) {
		this.esquemasEdital = esquemas;
	}
	
	/** Retorna todos os cursos cadastrados no sistema
     * @return List<Curso> - Lista de cursos
     */
	public List<Curso> getCursos() {
		cursos = cursobean.consultaCursos();
		return cursos;
	}
	
	/** Atribui uma lista cursos para serem utilizados na view 
     * @param List<Curso> cursos
     */
	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}
	
	/** Retorna o curso selecionado em rela��o ao edital
     * @return {@code Curso} Curso selecionado
     */
	public Curso getCursoSelecionado() {
		return cursoSelecionado;
	}
	
	/** Seleciona determinado curso como o curso relativo � cria��o de esquemas de um edital
     * @param Curso
     */
	public void setCursoSelecionado(Curso cursoSelecionado) {
		this.cursoSelecionado = cursoSelecionado;
	}
	
	/** Retorna o edital que est� expandido no contexto atual
     * @return {@code Edital} Edital com seus esquemas de bolsa detalhados
     */
	public Edital getEditalExpandido() {
		return editalExpandido;
	}
	
	/** Define o atual edital que encontra-se expandido, com seus esquemas de bolsa detalhados
     * @param Edital
     */
	public void setEditalExpandido(Edital editalExpandido) {
		this.editalExpandido = editalExpandido;
	}
	
	/** Retorna um Edital gerado que ser� atualizado
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
	
	/** Retorna um Edital que ser� criado
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
		esquemasEdital = new ArrayList<EsquemaBolsa>();
		editalAtualizado = new Edital();
		editalPersistido = new Edital();
	}
	
	/**
	 * Expande um edital para detalhar seus esquemas de monitoria ou cadastrar eventuais esquemas
	 * @param edital
	 */
	public void expandeEdital(Edital edital) { 
		this.editalExpandido = edital;
		this.esquemasEdital = esquemabean.consultaEsquemaByEdital(edital);
	}
	
	/**
	 * Utilizando o atual edital expandido e o curso selecionado, cria um novo esquema de bolsas para planos de monitoria do curso
	 * no determinado edital. Caso existam erros na opera��o, eles ser�o inseridos no {@code FacesContext} como mensagens.
	 */
	public void cadastraEsquema() 
	{
		FacesContext context = FacesContext.getCurrentInstance();
		
		EsquemaBolsa novoEsquema = new EsquemaBolsa();
		novoEsquema.setEdital(this.editalExpandido);
		novoEsquema.setCurso(this.cursoSelecionado);
		novoEsquema.setQuantidade(0);
		novoEsquema.setDistribuido(false);
		
		CriacaoRequestResult resultadoCriacao = esquemabean.persisteEsquemaBolsa(novoEsquema);
		
		if(resultadoCriacao.hasErrors()) 
		{
			for(String erro : resultadoCriacao.errors) 
			{
				context.addMessage(null, new FacesMessage(erro));
			}
		} else {
			esquemasEdital = esquemabean.consultaEsquemaByEdital(editalExpandido);
		}
	}
	
	/**
	 * Atualiza o n�mero de bolsas de um determinado esquema de monitoria, leva em considera��o as bolsas j� distribuidas
	 * e n�o permite que o novo n�mero seja menor do que as bolsas j� distribuidas pelos planos de monitoria de um determinado curso num determinado edital.
	 * Caso n�o seja poss�vel realizar a atualiza��o, mensagens do tipo {@code FacesMessage} ser�o adicionadas ao contexto {@code FacesContext} 
	 */
	public void atualizaBolsasEsquema(EsquemaBolsa esquema) {
		AtualizacaoRequestResult resultado = esquemabean.atualizaEsquemaBolsa(esquema);
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(resultado.hasErrors())
		{
			for(String erro : resultado.errors)
			{
				context.addMessage(null, new FacesMessage(erro));
			}
		} else {
			esquema.setVersion(esquema.getVersion() + 1);
		}
	}
	
	/** Persiste no banco de dados um edital criado na propriedade editalPersistido 
	 * Exibe uma mensagem ao usu�rio quando ocorre o sucesso na transa��o
     */
	public void cadastrarEdital()
	{
		FacesContext context = FacesContext.getCurrentInstance();

		editalPersistido.setNumeroEdital(editalPersistido.getNumero() + "/" + editalPersistido.getAno());
		carregarDatasCadastro();
		
		CriacaoRequestResult resultado = editalbean.persisteEdital(editalPersistido);
		
		if(resultado.hasErrors())
		{
			for(String error : resultado.errors)
			{
				context.addMessage(null, new FacesMessage(error));
			}
		}
		else 
		{
			context.addMessage(null, new FacesMessage("Cadastro realizado com sucesso!"));
			editalPersistido = new Edital();
		}
	}
	
	/** Carrega as Datas inseridas no Cadastro do Edital
	 * Busca as informa��es inseridas nos input html type="date"
     */
	private void carregarDatasCadastro() {
		String dataString = "";
		dataString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("iniCompE");
		editalPersistido.setInicioInscricaoComponenteCurricular(convertData(dataString));
		dataString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("fimCompE");
		editalPersistido.setFimInscricaoComponenteCurricular(convertData(dataString));
		dataString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("iniPME");
		editalPersistido.setInicioInsercaoPlano(convertData(dataString));
		dataString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("fimPME");
		editalPersistido.setFimInsercaoPlano(convertData(dataString));
		dataString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("iniProvaE");
		editalPersistido.setInicioRealizacaoProvas(convertData(dataString));
		dataString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("fimProvaE");
		editalPersistido.setFimRealizacaoProvas(convertData(dataString));
		dataString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("iniAlunoE");
		editalPersistido.setInicioInscricaoEstudante(convertData(dataString));
		dataString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("fimAlunoE");
		editalPersistido.setFimInscricaoEstudante(convertData(dataString));
		dataString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("iniNotaE");
		editalPersistido.setInicioInsercaoNota(convertData(dataString));
		dataString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("fimNotaE");
		editalPersistido.setFimInsercaoNota(convertData(dataString));
		dataString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("publiClassE");
		editalPersistido.setPublicacaoAlunosClassificados(convertData(dataString));
		dataString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("publiSelecE");
		editalPersistido.setPublicacaoAlunosSelecionados(convertData(dataString));
		dataString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("iniMonE");
		editalPersistido.setInicioMonitoria(convertData(dataString));
		dataString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("fimMonE");
		editalPersistido.setFimMonitoria(convertData(dataString));
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
	
	/** Seleciona o edital para exibir informa�oes de altera��o 
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
		editalAtualizado.setNumeroEdital(editalAtualizado.getNumero() + "/" + editalAtualizado.getAno());
		AtualizacaoRequestResult resultado = editalbean.atualizaEdital(editalAtualizado);
		if(resultado.hasErrors())
		{
			FacesContext context = FacesContext.getCurrentInstance();
			
			for (String erro : resultado.errors) {
				context.addMessage(null, new FacesMessage(erro));
			}
		} else {
			if(editalGlobal != null) {
				if(editalAtualizado.getId() == editalGlobal.getId() && !editalAtualizado.isVigente()) {
					if(!editalAtualizado.isVigente())
						sharedMenuView.setEditalGlobal(null);
					else 
						sharedMenuView.setEditalGlobal(editalAtualizado);
				}
			}
		}
	}

	/** Converte String em Date
	 * Converte a string retornada de um input html type="date" em um objeto java.util.Date
	 * @param data String
     */
	private Date convertData(String data) {
		Calendar dataCalendar = new GregorianCalendar();
		dataCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(data.substring(8, 10)));
		dataCalendar.set(Calendar.MONTH, (Integer.parseInt(data.substring(5, 7))-1 ));
		dataCalendar.set(Calendar.YEAR, Integer.parseInt(data.substring(0, 4)));
		return dataCalendar.getTime();
	}
}
