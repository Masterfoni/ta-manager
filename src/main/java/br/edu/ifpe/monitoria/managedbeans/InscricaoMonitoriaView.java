package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.edu.ifpe.monitoria.entidades.Aluno;
import br.edu.ifpe.monitoria.entidades.Curso;
import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.entidades.Monitoria;
import br.edu.ifpe.monitoria.entidades.PlanoMonitoria;
import br.edu.ifpe.monitoria.localbean.AlunoLocalBean;
import br.edu.ifpe.monitoria.localbean.CursoLocalBean;
import br.edu.ifpe.monitoria.localbean.EditalLocalBean;
import br.edu.ifpe.monitoria.localbean.MonitoriaLocalBean;
import br.edu.ifpe.monitoria.localbean.PlanoMonitoriaLocalBean;

@ManagedBean (name="inscricaoMonitoriaView")
@ViewScoped
public class InscricaoMonitoriaView implements Serializable{
	
	private static final long serialVersionUID = -2822785320179539798L;
	
	@ManagedProperty(value="#{menuView}")
	private MenuView sharedMenuView;
	
	public void setSharedMenuView(MenuView sharedMenuView) {
		this.sharedMenuView = sharedMenuView;
	}
	
	@EJB
	private PlanoMonitoriaLocalBean planoBean;
	
	@EJB
	private MonitoriaLocalBean monitoriaBean;
	
	@EJB
	private AlunoLocalBean alunoBean;
	
	@EJB
	private CursoLocalBean cursoBean;
	
	@EJB
	private EditalLocalBean editalBean;
	
	private Edital editalGlobal;
	
	private Monitoria monitoria;
	
	private List<PlanoMonitoria> planos;
	
	private List<Curso> cursos;
	
	private Curso curso;
	
	private Aluno aluno;
	
	boolean periodoCerto;

	public InscricaoMonitoriaView() {
		planos = new ArrayList<PlanoMonitoria>();
		periodoCerto = false;
	}
	
	@PostConstruct
	public void init() {		
		editalGlobal = sharedMenuView.getEditalGlobal();
	}
	
	public void salvarMonitoria(PlanoMonitoria plano, boolean voluntario, boolean bolsista) {
		boolean alteracao = true;
		
		if(monitoria == null) {
			monitoria = new Monitoria();
			alteracao = false;
		}
		
		monitoria.setPlanoMonitoria(plano);
		monitoria.setAluno(aluno);
		monitoria.setEdital(editalGlobal);
		monitoria.setBolsista(bolsista);
		monitoria.setVoluntario(voluntario);
		
		if(alteracao) {
			monitoriaBean.atualizaMonitoria(monitoria);
		} else {
			monitoriaBean.persisteMonitoria(monitoria);
		}
	}
	
	public void alterarCurso() {	
		aluno.setCurso(curso);
		alunoBean.atualizaAluno(aluno);
	}
	
	public List<PlanoMonitoria> getPlanos() {
		planos = null;
		if(editalGlobal != null) {
			planos = planoBean.consultaPlanosByEditaleCurso(editalGlobal, getCurso(), true);
		}
		if(planos == null) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Não existe programa de monitoria disponivel para inscrição."));
		} 
		return planos;
	}

	public void setPlanos(List<PlanoMonitoria> planos) {
		this.planos = planos;
	}
	
	public Monitoria getMonitoria() {
		if(monitoria == null && editalGlobal != null) {
			monitoria = monitoriaBean.consultaMonitoriaByAlunoEdital(getAluno(), editalGlobal);
		}
		return monitoria;
	}

	public void setMonitoria(Monitoria monitoria) {
		this.monitoria = monitoria;
	}

	public Aluno getAluno() {
		if(aluno == null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext(); 
			HttpSession session = (HttpSession) ec.getSession(false);
		
			Long id = (Long) session.getAttribute("id");
			aluno = alunoBean.consultaAlunoById(id);
		}
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public List<Curso> getCursos() {
		if(cursos == null) {
			cursos = cursoBean.consultaCursos();
		}
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public Curso getCurso() {
		if(curso == null) {
			if (getCursos() != null && getCursos().size() > 0) {
				curso = getCursos().get(0);
			}
		}
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public boolean isPeriodoCerto() {
		Date hoje = new Date();
		Calendar fim = Calendar.getInstance();
		
		fim.setTime(editalGlobal.getFimInscricaoEstudante());
		fim.add(Calendar.DAY_OF_MONTH, 1);

		return hoje.after(editalGlobal.getInicioInscricaoEstudante()) && hoje.before(fim.getTime());
	}

	public void setPeriodoCerto(boolean periodoCerto) {
		this.periodoCerto = periodoCerto;
	}	
}
