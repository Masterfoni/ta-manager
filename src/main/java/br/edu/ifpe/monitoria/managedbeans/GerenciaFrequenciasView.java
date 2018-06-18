package br.edu.ifpe.monitoria.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.edu.ifpe.monitoria.entidades.Aluno;
import br.edu.ifpe.monitoria.entidades.ComponenteCurricular;
import br.edu.ifpe.monitoria.entidades.Servidor;
import br.edu.ifpe.monitoria.localbean.AlunoLocalBean;
import br.edu.ifpe.monitoria.localbean.ComponenteCurricularLocalBean;
import br.edu.ifpe.monitoria.localbean.ServidorLocalBean;


@ManagedBean (name="gerenciaFrequenciasView")
@ViewScoped
public class GerenciaFrequenciasView {

	@EJB
	private ComponenteCurricularLocalBean componenteBean;

	@EJB
	private ServidorLocalBean servidorBean;

	@EJB
	private AlunoLocalBean alunoBean;
	
	private List<ComponenteCurricular> componentes;
	
	private List<Aluno> alunos;
	
	private ComponenteCurricular componenteSelecionado;
	
	private Aluno alunoSelecionado;
	
	public Servidor loggedServidor;

	public GerenciaFrequenciasView() {}
	
	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		loggedServidor = servidorBean.consultaServidorById((Long)session.getAttribute("id")); 
		
		componentes = componenteBean.consultaComponentesByProfessor(loggedServidor);
		alunos = componentes != null && componentes.size() > 0 ? alunoBean.consultaMonitoresByComponente(componentes.get(0).getId()) : new ArrayList<Aluno>();
	}

	public List<ComponenteCurricular> getComponentes() {
		return componenteBean.consultaComponentesByProfessor(loggedServidor);
	}

	public void setComponentes(List<ComponenteCurricular> componentes) {
		this.componentes = componentes;
	}

	public ComponenteCurricular getComponenteSelecionado() {
		return componenteSelecionado;
	}

	public void setComponenteSelecionado(ComponenteCurricular componenteSelecionado) {
		this.componenteSelecionado = componenteSelecionado;
		alunos = this.componenteSelecionado != null ? alunoBean.consultaMonitoresByComponente(this.componenteSelecionado.getId()) : new ArrayList<Aluno>(); 
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public Aluno getAlunoSelecionado() {
		return alunoSelecionado;
	}

	public void setAlunoSelecionado(Aluno alunoSelecionado) {
		this.alunoSelecionado = alunoSelecionado;
	}
}
