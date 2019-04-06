package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.edu.ifpe.monitoria.entidades.Aluno;
import br.edu.ifpe.monitoria.entidades.Atividade;
import br.edu.ifpe.monitoria.entidades.ComponenteCurricular;
import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.entidades.Frequencia;
import br.edu.ifpe.monitoria.entidades.Servidor;
import br.edu.ifpe.monitoria.localbean.AlunoLocalBean;
import br.edu.ifpe.monitoria.localbean.ComponenteCurricularLocalBean;
import br.edu.ifpe.monitoria.localbean.FrequenciaLocalBean;
import br.edu.ifpe.monitoria.localbean.ServidorLocalBean;
import br.edu.ifpe.monitoria.utils.FrequenciaRequestResult;

@ManagedBean (name="gerenciaFrequenciasView")
@ViewScoped
public class GerenciaFrequenciasView implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{menuView}")
	private MenuView sharedMenuView;
	
	public void setSharedMenuView(MenuView sharedMenuView) {
		this.sharedMenuView = sharedMenuView;
	}

	@EJB
	private ComponenteCurricularLocalBean componenteBean;

	@EJB
	private ServidorLocalBean servidorBean;

	@EJB
	private AlunoLocalBean alunoBean;
	
	@EJB
	private FrequenciaLocalBean frequenciaBean;
	
	private List<ComponenteCurricular> componentes;
	
	private List<Aluno> alunos;
	
	private List<Frequencia> frequencias;
	
	private List<Atividade> atividades;
	
	private ComponenteCurricular componenteSelecionado;
	
	private Aluno alunoSelecionado;
	
	private Frequencia frequenciaSelecionada;
	
	public Servidor loggedServidor;

	private Edital editalglobal;
	
	public GerenciaFrequenciasView() {}
	
	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		loggedServidor = servidorBean.consultaServidorById((Long)session.getAttribute("id")); 
		
		componentes = componenteBean.consultaComponentesByProfessor(loggedServidor);
		componenteSelecionado = componentes != null && componentes.size() > 0 ? componentes.get(0) : null;
		
		editalglobal = sharedMenuView.getEditalGlobal();
		
		if(editalglobal != null) {
			alunos = componenteSelecionado != null ? alunoBean.consultaMonitoresByComponenteEdital(componentes.get(0).getId(), sharedMenuView.getEditalGlobal().getId()) : new ArrayList<Aluno>();
			alunoSelecionado = alunos.size() > 0 ? alunos.get(0) : null;
		}
		
		if (alunoSelecionado != null) {
			getFrequencias();
			frequenciaSelecionada = frequencias.size() > 0 ? frequencias.get(0) : null;
		}
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
		frequenciaSelecionada = null;
		alunoSelecionado = null;
		frequencias = null;
	}

	public List<Aluno> getAlunos() {
		if(componenteSelecionado != null  && editalglobal != null) {
			alunos = alunoBean.consultaMonitoresByComponenteEdital(componenteSelecionado.getId(), sharedMenuView.getEditalGlobal().getId());
		}
		else {
			alunos = new ArrayList<Aluno>();
		}
			
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public Aluno getAlunoSelecionado() {
		return alunoSelecionado;
	}

	public void setAlunoSelecionado(Aluno alunoSelecionado) {
		if(alunoSelecionado.getId() == -1L) {
			alunoSelecionado = null;
		}else {
			this.alunoSelecionado = alunoSelecionado;
		}
		frequenciaSelecionada = null;
	}
	
	public List<Frequencia> getFrequencias() {
		if(alunoSelecionado != null) {
			FrequenciaRequestResult resultado = frequenciaBean.findByAluno(alunoSelecionado);
			
			if(resultado.hasErrors()) {
				frequencias = new ArrayList<Frequencia>();
				FacesContext context = FacesContext.getCurrentInstance();
				for (String erro : resultado.errors) {
					context.addMessage(null, new FacesMessage(erro));
				}
			} else {
				frequencias = resultado.frequencias;
				for (Frequencia f : frequencias) {
					f.getAtividades().size();
					f.getMonitoria().getPlanoMonitoria().getCc().getProfessor().getId();
				}
			}
		}
		return frequencias;
	}

	public void setFrequencias(List<Frequencia> frequencias) {
		this.frequencias = frequencias;
	}

	public Frequencia getFrequenciaSelecionada() {
		return frequenciaSelecionada;
	}

	public void setFrequenciaSelecionada(Frequencia frequenciaSelecionada) {
		if (frequenciaSelecionada == null || frequenciaSelecionada.getMes() == -1) {
			this.frequenciaSelecionada = null;
		} else {
			this.frequenciaSelecionada = frequenciaSelecionada;
			this.atividades = frequenciaSelecionada.getAtividades();
		}
	}

	public Servidor getLoggedServidor() {
		if(loggedServidor == null) {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			loggedServidor = servidorBean.consultaServidorById((Long)session.getAttribute("id"));
		}
		return loggedServidor;
	}

	public void setLoggedServidor(Servidor loggedServidor) {
		this.loggedServidor = loggedServidor;
	}
	
	public List<Atividade> getAtividades() {
		if(atividades == null) {
			atividades = frequenciaSelecionada.getAtividades();
		}
		return atividades;
	}

	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}

	public void aprovacaoFrequencia(boolean aprovado) {
		frequenciaBean.aprovarFrequencia(frequenciaSelecionada, aprovado);
	}
	
	public void receberFrequencia() {
		frequenciaBean.receberFrequencia(frequenciaSelecionada);
	}
	
	public String getNomeMes(GregorianCalendar mes) {
		Locale brazil = new Locale("pt", "BR");
		return mes.getDisplayName(GregorianCalendar.MONTH, GregorianCalendar.LONG, brazil) + "/" + 
				mes.get(GregorianCalendar.YEAR);
	}
	
	public String getHora(Date hora) {
		if(hora != null) {
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			return format.format(hora);
		}
		return "";
	}
	
	public String getData(Date data) {
		if(data != null) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	    	return format.format(data);
		}
		return "";
	}
}
