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
import br.edu.ifpe.monitoria.utils.CriacaoRequestResult;

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
	
	public Curso cursoSelecionado;
	
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
		
		if(comissao) {
			List<Curso> cursosConsultados = cursobean.consultaCursos();
			cursoSelecionado = cursosConsultados.isEmpty() ? new Curso() : cursosConsultados.get(0);
		}
		
		planoAtualizado = new PlanoMonitoria();
		planoPersistido = new PlanoMonitoria();
	}
	
	public Curso getCursoCoordenado() {
		if(comissao) {
			cursoCoordenado = cursoSelecionado;
		}
		
		return cursoCoordenado;
	}

	public void setCursoCoordenado(Curso cursoCoordenado) {
		this.cursoCoordenado = cursoCoordenado;
	}

	public EsquemaBolsa getEsquemaAtual() {
		esquemaAtual = esquemabean.consultaEsquemaByEditalCurso(editalSelecionado, getCursoCoordenado()).result;
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
	
	public Curso getCursoSelecionado() {
		return cursoSelecionado;
	}
	
	public void setCursoSelecionado(Curso cursoSelecionado) {
		this.cursoSelecionado = cursoSelecionado;
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
			if(editalSelecionado != null) {
				if(cursoSelecionado != null) {
					planos = planobean.consultaPlanosByEditaleCurso(editalSelecionado, cursoSelecionado, false);
				} else {
					planos = planobean.consultaPlanosByEdital(editalSelecionado, false);
				}
			} else {
				planos = planobean.consultaPlanos(); 
			}
		} 
		else
		{
			List<PlanoMonitoria> planosByServidor = planobean.consultaPlanosByServidor(this.loggedServidor.getId());

			if(editalSelecionado != null)
			{
				planos = new ArrayList<PlanoMonitoria>(planosByServidor);
				planos.retainAll(planobean.consultaPlanosByEdital(editalSelecionado, false));
			}
			else
			{
				planos = new ArrayList<PlanoMonitoria>(planosByServidor);
			}
			
			ArrayList<PlanoMonitoria> planinhosCoordenados = new ArrayList<PlanoMonitoria>();
			ArrayList<PlanoMonitoria> planinhosLecionados = new ArrayList<PlanoMonitoria>();
			
			for(PlanoMonitoria planinho : planos) {
				if(planinho.getCc().getCurso().getCoordenador().getId() == this.loggedServidor.getId()) {
					planinhosCoordenados.add(planinho);
				} else if(planinho.getCc().getProfessor().getId() == this.loggedServidor.getId()) {
					planinhosLecionados.add(planinho);
				}
			}
			
			planos = new ArrayList<PlanoMonitoria>();
			planos.addAll(planinhosCoordenados);
			planos.addAll(planinhosLecionados);
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
		
		if(comissao) 
		{
			componentes = componentebean.consultaComponentesCurriculares();
		} 
		else 
		{
			Curso curso = cursobean.consultaCursoByCoordenador(loggedServidor.getId());

			Set<ComponenteCurricular> cursosNaoRepetidos = new HashSet<ComponenteCurricular>();
			
			List<ComponenteCurricular> componentesByProfessor = componentebean.consultaComponentesByProfessor(this.loggedServidor);
			List<ComponenteCurricular> componentesByCurso = componentebean.consultaComponentesByCurso(curso.getId());

			if(!componentesByProfessor.isEmpty())
			{
				cursosNaoRepetidos.addAll(componentesByProfessor);
			}
			if(!componentesByCurso.isEmpty())
			{
				cursosNaoRepetidos.addAll(componentesByCurso);
			}
			
			componentes = new ArrayList<ComponenteCurricular>(cursosNaoRepetidos);
		}
 
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
		CriacaoRequestResult resultadoPlano = planobean.persistePlanoMonitoria(planoPersistido); 
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(!resultadoPlano.hasErrors())
		{
			context.addMessage(null, new FacesMessage("Cadastro realizado com sucesso!"));
			planoPersistido = new PlanoMonitoria();
		}
		else
		{
			for(String erro : resultadoPlano.errors) {
				context.addMessage(null, new FacesMessage(erro));
			}
		}
	}
	
	public String lancarNotas(PlanoMonitoria plano) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("plano", plano);
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute("plano", plano);
		
	    return "inserirNotas?faces-redirect=true";
	}

	public boolean isProfessorDe(PlanoMonitoria plano) {
		if(plano.getCc().getProfessor().getId() == this.loggedServidor.getId())
			return true;
		else
			return false;
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
