package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.edu.ifpe.monitoria.entidades.Aluno;
import br.edu.ifpe.monitoria.entidades.ComponenteCurricular;
import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.entidades.RelatorioFinal;
import br.edu.ifpe.monitoria.entidades.Servidor;
import br.edu.ifpe.monitoria.localbean.AlunoLocalBean;
import br.edu.ifpe.monitoria.localbean.ComponenteCurricularLocalBean;
import br.edu.ifpe.monitoria.localbean.MonitoriaLocalBean;
import br.edu.ifpe.monitoria.localbean.RelatorioFinalLocalBean;
import br.edu.ifpe.monitoria.localbean.ServidorLocalBean;
import br.edu.ifpe.monitoria.utils.RelatorioFinalRequestResult;

@ManagedBean (name="gerenciaRelatoriosFinaisView")
@ViewScoped
public class GerenciaRelatoriosFinaisView implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{menuView}")
	private MenuView sharedMenuView;
	
	public void setSharedMenuView(MenuView sharedMenuView) {
		this.sharedMenuView = sharedMenuView;
	}

	@EJB
	private RelatorioFinalLocalBean relatorioFinalBean;
	
	@EJB
	private ServidorLocalBean servidorBean;
	
	@EJB
	private MonitoriaLocalBean monitoriaBean;
	
	@EJB
	private AlunoLocalBean alunoBean;
	
	@EJB
	private ComponenteCurricularLocalBean componenteBean;
	
	public RelatorioFinal relatorioAtual;
	
	private Servidor loggedServidor;
	
	private List<ComponenteCurricular> componentes;
	
	private ComponenteCurricular componenteSelecionado;
	
	private List<Aluno> alunos;
	
	private Aluno alunoSelecionado;

	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		loggedServidor = servidorBean.consultaServidorById((Long)session.getAttribute("id"));
		
		componentes = componenteBean.consultaComponentesByProfessor(loggedServidor);
		
		Edital editalGlobal = sharedMenuView.getEditalGlobal(); 
		
		if(componentes.size() > 0 && editalGlobal != null) {
			componenteSelecionado = componentes.get(0);
			alunos = alunoBean.consultaMonitoresByComponenteEdital(componenteSelecionado.getId(), editalGlobal.getId());
			alunoSelecionado = alunos.size() > 0 ? alunos.get(0) : null;
			if(alunoSelecionado != null) {
				RelatorioFinalRequestResult consResult = relatorioFinalBean.consultaRelatorioFinalPorMonitor(alunoSelecionado.getId());
				relatorioAtual = consResult.hasErrors() ? null : consResult.result;
			}
		}
	}
	
	public void homologarRelatorio() {
		relatorioAtual.setHomologado(true);
		relatorioFinalBean.atualizaRelatorio(relatorioAtual);
	}

	public RelatorioFinal getRelatorioAtual() {
		if(alunoSelecionado != null) {
			RelatorioFinalRequestResult consResult = relatorioFinalBean.consultaRelatorioFinalPorMonitor(alunoSelecionado.getId());
			relatorioAtual = consResult.hasErrors() ? null : consResult.result;
		} else {
			relatorioAtual = null;
		}
		
		return relatorioAtual;
	}

	public void setRelatorioAtual(RelatorioFinal relatorioAtual) {
		this.relatorioAtual = relatorioAtual;
	}

	public Servidor getLoggedServidor() {
		return loggedServidor;
	}

	public void setLoggedServidor(Servidor loggedServidor) {
		this.loggedServidor = loggedServidor;
	}

	public List<ComponenteCurricular> getComponentes() {
		componentes = componenteBean.consultaComponentesByProfessor(loggedServidor);
		
		return componentes;
	}

	public void setComponentes(List<ComponenteCurricular> componentes) {
		this.componentes = componentes;
	}

	public ComponenteCurricular getComponenteSelecionado() {
		return componenteSelecionado;
	}

	public void setComponenteSelecionado(ComponenteCurricular componenteSelecionado) {
		this.componenteSelecionado = componenteSelecionado;
	}

	public List<Aluno> getAlunos() {
		Edital editalGlobal = sharedMenuView.getEditalGlobal(); 
		alunos = componenteSelecionado != null ? alunoBean.consultaMonitoresByComponenteEdital(componenteSelecionado.getId(), editalGlobal.getId()) : new ArrayList<Aluno>();

		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public Aluno getAlunoSelecionado() {
		if(alunos.size() == 0) {
			alunoSelecionado = null;
		}
		
		return alunoSelecionado;
	}

	public void setAlunoSelecionado(Aluno alunoSelecionado) {
		this.alunoSelecionado = alunoSelecionado;
	}
}
