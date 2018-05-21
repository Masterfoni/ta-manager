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
	
	public Servidor loggedServidor;
	
	public EsquemaBolsa esquemaAtual;
	
	public Curso cursoCoordenado;
	
	public boolean comissao;
	
	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		loggedServidor = servidorbean.consultaServidorById((Long)session.getAttribute("id")); 
		
		comissao = FacesContext.getCurrentInstance().getExternalContext().isUserInRole("comissao");
		
		cursos = cursobean.consultaCursos();
		servidores = servidorbean.consultaServidores();
		editais = editalbean.consultaEditais();
		planos = new ArrayList<PlanoMonitoria>();
		
		editalSelecionado = editais.size() > 0 ? editais.get(0) : new Edital();
		
		cursoCoordenado = cursobean.consultaCursoByCoordenador(loggedServidor.getId());
		
		planoAtualizado = new PlanoMonitoria();
		planoPersistido = new PlanoMonitoria();
	}
	
	
	
	public Curso getCursoCoordenado() {
		return cursoCoordenado;
	}

	public void setCursoCoordenado(Curso cursoCoordenado) {
		this.cursoCoordenado = cursoCoordenado;
	}

	public EsquemaBolsa getEsquemaAtual() {
		esquemaAtual = esquemabean.consultaEsquemaByEditalCurso(editalSelecionado, cursoCoordenado).result;
		return esquemaAtual;
	}

	public void setEsquemaAtual(EsquemaBolsa esquemaAtual) {
		this.esquemaAtual = esquemaAtual;
	}

	public Servidor getLoggedServidor() {
		return loggedServidor;
	}

	public void setLoggedServidor(Servidor loggedServidor) {
		this.loggedServidor = loggedServidor;
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
		if(comissao)
		{
			planos = editalSelecionado != null ? planobean.consultaPlanosByEdital(editalSelecionado, false) : planobean.consultaPlanos(); 
		} 
		else
		{
			Set<PlanoMonitoria> planosNaoRepetidos = new HashSet<PlanoMonitoria>();
			List<PlanoMonitoria> planosByServidor = planobean.consultaPlanosByServidor(this.loggedServidor.getId());;
			List<PlanoMonitoria> planosByCoordenador = planobean.consultaPlanosByCoordenador(this.loggedServidor.getId());

			if(!planosByServidor.isEmpty())
			{
				planosNaoRepetidos.addAll(planosByServidor);
			}
			if(!planosByCoordenador.isEmpty())
			{
				planosNaoRepetidos.addAll(planosByCoordenador);
			}
			
			if(editalSelecionado != null)
			{
				planos = new ArrayList<PlanoMonitoria>(planosNaoRepetidos);
				planos.retainAll(planobean.consultaPlanosByEdital(editalSelecionado, false));
			}
			else
			{
				planos = new ArrayList<PlanoMonitoria>(planosNaoRepetidos);
			}
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
	
	public boolean isComissao() {
		return comissao;
	}

	public void setComissao(boolean comissao) {
		this.comissao = comissao;
	}

	public List<ComponenteCurricular> getComponentes() {
		componentes = comissao ? componentebean.consultaComponentesCurriculares() : componentebean.consultaComponentesByProfessor(this.loggedServidor); 
		
		return componentes;
	}

	public void setComponentes(List<ComponenteCurricular> componentes) {
		this.componentes = componentes;
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
			planoPersistido = new PlanoMonitoria();
		}
	}
	
	public void modificarBolsas(boolean isIncremento, PlanoMonitoria plano)
	{
		if(plano.distribuirBolsa(isIncremento)) {
			planobean.atualizaPlanoMonitoria(plano);
		}
		
		planoPersistido = new PlanoMonitoria();
	}
	
	public void buscaPlanoMonitoria() {
		this.planos = planobean.consultaPlanos();
	}
	
	public void alteraPlano(PlanoMonitoria plano) {
		planoAtualizado = plano;
	}
	
	public void homologarPlano() {
		planoAtualizado.setHomologado(true);
		planobean.atualizaPlanoMonitoria(planoAtualizado);
	}
	
	public void persisteAlteracao() {
		planobean.atualizaPlanoMonitoria(planoAtualizado);
	}
}
