package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.edu.ifpe.monitoria.entidades.ComponenteCurricular;
import br.edu.ifpe.monitoria.entidades.Curso;
import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.entidades.EsquemaBolsa;
import br.edu.ifpe.monitoria.entidades.PlanoMonitoria;
import br.edu.ifpe.monitoria.entidades.Servidor;
import br.edu.ifpe.monitoria.localbean.ComponenteCurricularLocalBean;
import br.edu.ifpe.monitoria.localbean.CursoLocalBean;
import br.edu.ifpe.monitoria.localbean.EditalLocalBean;
import br.edu.ifpe.monitoria.localbean.EsquemaBolsaLocalBean;
import br.edu.ifpe.monitoria.localbean.PlanoMonitoriaLocalBean;
import br.edu.ifpe.monitoria.localbean.ServidorLocalBean;

@ManagedBean (name="gerenciaPlanoMonitoriaView")
@ViewScoped
public class GerenciaPlanoMonitoriaView implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ComponenteCurricularLocalBean componentebean;
	
	@EJB 
	private PlanoMonitoriaLocalBean planobean;

	@EJB
	private EditalLocalBean editalbean;
	
	@EJB
	private CursoLocalBean cursobean;
	
	@EJB
	private ServidorLocalBean servidorbean;
	
	@EJB
	private EsquemaBolsaLocalBean esquemabean;
	
	public List<ComponenteCurricular> componentes;
	
	public List<Curso> cursos;
	
	public List<PlanoMonitoria> planos;
	
	public List<Servidor> servidores;
	
	public List<Edital> editais;
	
	public PlanoMonitoria planoPersistido;
	
	public PlanoMonitoria planoAtualizado;
	
	public Edital editalSelecionado;
	
	public boolean showButton;
	
	public boolean isShowButton() {
		return showButton;
	}

	public void setShowButton(boolean showButton) {
		this.showButton = showButton;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCoordenacoes(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public List<Servidor> getProfessores() {
		return servidores;
	}

	public void setServidores(List<Servidor> servidores) {
		this.servidores = servidores;
	}
	
	public Edital getEditalSelecionado() {
		return editalSelecionado;
	}

	public void setEditalSelecionado(Edital editalSelecionado) {
		this.editalSelecionado = editalSelecionado;
	}

	/**
	 * Método responsável por atualizar um componente curricular.
	 * Este método verifica o papel atual do usuário logado, caso o papel seja de comissão, ele tem liberdade para filtrar todos os planos
	 * caso não, ele filtra baseado nos privilégios de coordenador e, em última instância, professor (não coordenador).
	 *
	 * @return {@code List<PlanoMonitoria>} uma lista de planos de monitoria 
	 */
	public List<PlanoMonitoria> getPlanos() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		Servidor loggedServidor = servidorbean.consultaServidorById((Long)session.getAttribute("id")); 
		
		if(request.isUserInRole("comissao"))
		{
			planos = planobean.consultaPlanos();
		} 
		else
		{
			Set<PlanoMonitoria> planosNaoRepetidos = new HashSet<PlanoMonitoria>();
			List<PlanoMonitoria> planosByServidor = planobean.consultaPlanosByServidor(loggedServidor.getId());;
			List<PlanoMonitoria> planosByCoordenador = planobean.consultaPlanosByCoordenador(loggedServidor.getId());

			if(!planosByServidor.isEmpty())
			{
				planosNaoRepetidos.addAll(planosByServidor);
			}
			if(!planosByCoordenador.isEmpty())
			{
				planosNaoRepetidos.addAll(planosByCoordenador);
			}
			
			planos = new ArrayList<PlanoMonitoria>(planosNaoRepetidos);
		}
		
		return planos;
	}

	public void setPlanos(List<PlanoMonitoria> planos) {
		this.planos = planos;
	}

	public List<Edital> getEditais() {
		return editais;
	}

	public PlanoMonitoria getPlanoPersistido() {
		return planoPersistido;
	}

	public void setPlanoPersistido(PlanoMonitoria planoPersistido) {
		this.planoPersistido = planoPersistido;
	}

	public PlanoMonitoria getPlanoAtualizado() {
		return planoAtualizado;
	}

	public void setPlanoAtualizado(PlanoMonitoria planoAtualizado) {
		this.planoAtualizado = planoAtualizado;
	}

	public List<ComponenteCurricular> getComponentes() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Servidor loggedServidor = servidorbean.consultaServidorById((Long)session.getAttribute("id")); 
		componentes = componentebean.consultaComponentesByProfessor(loggedServidor);
		return componentes;
	}

	public void setComponentes(List<ComponenteCurricular> componentes) {
		this.componentes = componentes;
	}
	
	@PostConstruct
	public void init() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		showButton = request.isUserInRole("comissao");
		
		cursos = cursobean.consultaCursos();
		servidores = servidorbean.consultaServidores();
		editais = editalbean.consultaEditais();
		planos = new ArrayList<PlanoMonitoria>();
		
		editalSelecionado = editais.size() > 0 ? editais.get(0) : new Edital();
		
		planoAtualizado = new PlanoMonitoria();
		planoPersistido = new PlanoMonitoria();
	}
	
	public void aplicaFiltroEdital(AjaxBehaviorEvent abe)
	{
		planos = planobean.consultaPlanosByEdital(editalSelecionado);
	}
	
	/**
	 * Método cadastrar um novo plano de monitoria no sistema.
	 *
	 * @return {@code void}, no caso de sucesso ou erro, {@code FacesMessage} será adicionada no contexto atual. 
	 */
	public void cadastrarPlano()
	{
		if(planobean.persistePlanoMonitoria(planoPersistido))
		{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Cadastro realizado com sucesso!"));
		}
	}
	
	public String calcularMaximoDisponivel(PlanoMonitoria plano)
	{
		String disponiveis = "";
		int bolsasUtilizadas = 0;
		
		List<EsquemaBolsa> esquemas = esquemabean.consultaEsquemaByEditalCurso(plano.getEdital(), plano.getCc().getCurso());
		List<PlanoMonitoria> planos = planobean.consultaPlanosByEditaleCurso(plano.getEdital(), plano.getCc().getCurso());
		
		for(PlanoMonitoria item : planos)
		{
			bolsasUtilizadas += item.getBolsas();
		}
		
		if(esquemas.size() > 0)
		{
			disponiveis = "" + (esquemas.get(0).getQuantidade() - bolsasUtilizadas);
		}
		
		return disponiveis;
	}
	
	public void modificarBolsas(boolean adiciona, PlanoMonitoria plano)
	{
		int novaQuantidade = adiciona ? plano.getBolsas() +1 : Math.max(0, plano.getBolsas() -1);
		int bolsasDisponibilizadasTotal = 0;
		
		List<EsquemaBolsa> esquemas = esquemabean.consultaEsquemaByEditalCurso(plano.getEdital(), plano.getCc().getCurso());
		List<PlanoMonitoria> planos = planobean.consultaPlanosByEditaleCurso(plano.getEdital(), plano.getCc().getCurso());
		
		for(PlanoMonitoria item : planos)
		{
			if(item.getId() != plano.getId())
				bolsasDisponibilizadasTotal += item.getBolsas();
		}
		
		if(esquemas.size() > 0) 
		{
			if(esquemas.get(0).getQuantidade() < (novaQuantidade + bolsasDisponibilizadasTotal))
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Quantidade não permitida pois excede o máximo disponibilizado pela comissão."));
			}
			else
			{
				plano.setBolsas(novaQuantidade);
				planobean.atualizaPlanoMonitoria(plano);
			}
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ainda não foi definida uma quantidade de bolsas para este curso."));
		}
		
	}
	
	public void buscaPlanoMonitoria() {
		this.planos = planobean.consultaPlanos();
	}
	
	public void alteraPlano(PlanoMonitoria plano) {
		planoAtualizado = plano;
	}
	
	public void persisteAlteracao() {
		planobean.atualizaPlanoMonitoria(planoAtualizado);
	}
}
