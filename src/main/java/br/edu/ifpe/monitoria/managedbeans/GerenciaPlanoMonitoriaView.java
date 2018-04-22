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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.edu.ifpe.monitoria.entidades.ComponenteCurricular;
import br.edu.ifpe.monitoria.entidades.Curso;
import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.entidades.PlanoMonitoria;
import br.edu.ifpe.monitoria.entidades.Servidor;
import br.edu.ifpe.monitoria.localbean.ComponenteCurricularLocalBean;
import br.edu.ifpe.monitoria.localbean.CursoLocalBean;
import br.edu.ifpe.monitoria.localbean.EditalLocalBean;
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
	
	public List<ComponenteCurricular> componentes;
	
	public List<Curso> cursos;
	
	public List<PlanoMonitoria> planos;
	
	public List<Servidor> servidores;
	
	public List<Edital> editais;
	
	public PlanoMonitoria planoPersistido;
	
	public PlanoMonitoria planoAtualizado;
	
	public String nomeBusca;
	
	public long editalId;
	
	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCoordenacoes(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public String getNomeBusca() {
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca) {
		this.nomeBusca = nomeBusca;
	}

	public List<Servidor> getProfessores() {
		return servidores;
	}

	public void setServidores(List<Servidor> servidores) {
		this.servidores = servidores;
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

	public void setEditais(List<Edital> editais) {
		this.editais = editais;
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
		return componentes;
	}

	public void setComponentes(List<ComponenteCurricular> componentes) {
		this.componentes = componentes;
	}
	
	public long getEditalId() {
		return editalId;
	}

	public void setEditalId(long editalId) {
		this.editalId = editalId;
	}

	@PostConstruct
	public void init() {
		nomeBusca = "";
		
		cursos = cursobean.consultaCursos();
		componentes = componentebean.consultaComponentesCurriculares();
		servidores = servidorbean.consultaServidores();
		editais = editalbean.consultaEditais();
		planos = new ArrayList<PlanoMonitoria>();
		
		planoAtualizado = new PlanoMonitoria();
		planoPersistido = new PlanoMonitoria();
		
		editalId = 0;
	}
	
	/**
	 * Método cadastrar um novo plano de monitoria no sistema.
	 *
	 * @return {@code void}, no caso de sucesso ou erro, {@code FacesMessage} será adicionada no contexto atual. 
	 */
	public void cadastrarPlano()
	{
		planoPersistido.setEdital(editalbean.consultaEditalById(editalId));
		
		if(planobean.persistePlanoMonitoria(planoPersistido))
		{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Cadastro realizado com sucesso!"));
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
