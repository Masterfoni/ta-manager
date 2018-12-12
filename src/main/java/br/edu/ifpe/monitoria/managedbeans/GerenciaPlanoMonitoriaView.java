package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
import br.edu.ifpe.monitoria.localbean.EsquemaBolsaLocalBean;
import br.edu.ifpe.monitoria.localbean.PlanoMonitoriaLocalBean;
import br.edu.ifpe.monitoria.localbean.ServidorLocalBean;
import br.edu.ifpe.monitoria.utils.CriacaoRequestResult;

@ManagedBean (name="gerenciaPlanoMonitoriaView")
@ViewScoped
public class GerenciaPlanoMonitoriaView implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{menuView}")
	private MenuView sharedMenuView;
	
	public void setSharedMenuView(MenuView sharedMenuView) {
		this.sharedMenuView = sharedMenuView;
	}

	@EJB
	private ComponenteCurricularLocalBean componentebean;
	
	@EJB 
	private PlanoMonitoriaLocalBean planobean;
	
	@EJB
	private CursoLocalBean cursobean;
	
	@EJB
	private ServidorLocalBean servidorbean;
	
	@EJB
	private EsquemaBolsaLocalBean esquemabean;
	
	private Edital editalGlobal;
	
	public List<ComponenteCurricular> componentes;
	
	public List<Curso> cursos;
	
	public List<PlanoMonitoria> planos;
	
	public List<Servidor> servidores;
	
	public PlanoMonitoria planoPersistido;
	
	public PlanoMonitoria planoAtualizado;
	
	public Servidor loggedServidor;
	
	public EsquemaBolsa esquemaAtual;
	
	public Curso cursoCoordenado;
	
	public Curso cursoSelecionado;
	
	public Curso cursoNovoPlano;
	
	public boolean comissao;
	
	public boolean periodoCerto;
	
	public boolean periodoNotas;
	
	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		loggedServidor = servidorbean.consultaServidorById((Long)session.getAttribute("id")); 
		
		comissao = FacesContext.getCurrentInstance().getExternalContext().isUserInRole("comissao");
		
		editalGlobal = sharedMenuView.getEditalGlobal();
		
		cursos = cursobean.consultaCursos();
		servidores = servidorbean.consultaServidores();
		planos = new ArrayList<PlanoMonitoria>();
		
		cursoCoordenado = cursobean.consultaCursoByCoordenador(loggedServidor.getId());
		
		List<Curso> cursosConsultados = cursobean.consultaCursos();
		if(comissao) {
			cursoSelecionado = cursosConsultados.isEmpty() ? new Curso() : cursosConsultados.get(0);
		}
		cursoNovoPlano =  cursosConsultados.isEmpty() ? new Curso() : cursosConsultados.get(0);
		
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
		if(editalGlobal != null) {
			esquemaAtual = esquemabean.consultaEsquemaByEditalCurso(editalGlobal, getCursoCoordenado()).result;
		}
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
		if(comissao) {
			if(editalGlobal != null) {
				if(cursoSelecionado != null) {
					planos = planobean.consultaPlanosByEditaleCurso(editalGlobal, cursoSelecionado, false);
				} else {
					planos = planobean.consultaPlanosByEdital(editalGlobal, false);
				}
			}
		} else {
			List<PlanoMonitoria> planosByServidor = planobean.consultaPlanosByServidor(this.loggedServidor.getId());

			if(editalGlobal != null) {
				planos = new ArrayList<PlanoMonitoria>(planosByServidor);
				planos.retainAll(planobean.consultaPlanosByEdital(editalGlobal, false));
			}
			
			ArrayList<PlanoMonitoria> planinhosCoordenados = new ArrayList<PlanoMonitoria>();
			ArrayList<PlanoMonitoria> planinhosLecionados = new ArrayList<PlanoMonitoria>();
			
			for(PlanoMonitoria planinho : planos) {
				if(planinho.getCc().getCurso().getCoordenador() != null && 
						planinho.getCc().getCurso().getCoordenador().getId() == this.loggedServidor.getId()) {
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

	public boolean isPeriodoCerto() {
		if(editalGlobal != null) {
			Date hoje = new Date();
			Calendar fim = Calendar.getInstance();
			Calendar inicio = Calendar.getInstance();
			
			fim.setTime(editalGlobal.getFimInsercaoPlano());
			fim.add(Calendar.DAY_OF_YEAR, 1);
			
			inicio.setTime(editalGlobal.getInicioInsercaoPlano());
			inicio.add(Calendar.DAY_OF_YEAR, -1);
		
			return hoje.after(inicio.getTime()) && hoje.before(fim.getTime());
		}
		return false;
	}

	public void setPeriodoCerto(boolean periodoCerto) {
		this.periodoCerto = periodoCerto;
	}

	public boolean isPeriodoNotas() {
		if(editalGlobal != null) {
			Date hoje = new Date();
			Calendar fim = Calendar.getInstance();
			Calendar inicio = Calendar.getInstance();
		
			fim.setTime(editalGlobal.getFimInsercaoNota());
			fim.add(Calendar.DAY_OF_YEAR, 1);
			
			inicio.setTime(editalGlobal.getInicioInsercaoNota());
			inicio.add(Calendar.DAY_OF_YEAR, -1);
		
			return hoje.after(inicio.getTime()) && hoje.before(fim.getTime());
		}
		return false;
	}

	public void setPeriodoNotas(boolean periodoNotas) {
		this.periodoNotas = periodoNotas;
	}

	public List<ComponenteCurricular> getComponentes() {
		
		if(comissao) {
			componentes = componentebean.consultaComponentesByCurso(cursoNovoPlano.getId());
		} else  {
			Curso curso = cursobean.consultaCursoByCoordenador(loggedServidor.getId());

			Set<ComponenteCurricular> cursosNaoRepetidos = new HashSet<ComponenteCurricular>();
			
			List<ComponenteCurricular> componentesByProfessorECurso = componentebean.consultaComponentesByProfessorECurso(this.loggedServidor, cursoNovoPlano);
			
			List<ComponenteCurricular> componentesByCurso = new ArrayList<>();
			if(curso.getId() == cursoNovoPlano.getId()) {
				componentesByCurso = componentebean.consultaComponentesByCurso(curso.getId());
			}

			if(!componentesByProfessorECurso.isEmpty())
			{
				cursosNaoRepetidos.addAll(componentesByProfessorECurso);
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
		if(editalGlobal != null) {
			planoPersistido.setEdital(editalGlobal);
			CriacaoRequestResult resultadoPlano = planobean.persistePlanoMonitoria(planoPersistido);
			FacesContext context = FacesContext.getCurrentInstance();
			
			if(!resultadoPlano.hasErrors()) {
				context.addMessage(null, new FacesMessage("Cadastro realizado com sucesso!"));
				planoPersistido = new PlanoMonitoria();
			} else {
				for(String erro : resultadoPlano.errors) {
					context.addMessage(null, new FacesMessage(erro));
				}
			}
		}
	}
	
	public Curso getCursoNovoPlano() {
		return cursoNovoPlano;
	}

	public void setCursoNovoPlano(Curso cursoNovoPlano) {
		this.cursoNovoPlano = cursoNovoPlano;
	}

	public String lancarNotas(PlanoMonitoria plano) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("plano", plano);
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute("plano", plano);
		
	    return "inserirNotas?faces-redirect=true";
	}

	public String verAlunos(PlanoMonitoria plano) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("plano", plano);
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute("plano", plano);
		
	    return "verInscritos?faces-redirect=true";
	}
	
	public boolean isProfessorDe(PlanoMonitoria plano) {
		return plano.getCc().getProfessor().getId() == this.loggedServidor.getId();
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
