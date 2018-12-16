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
import br.edu.ifpe.monitoria.entidades.ComponenteCurricular;
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
	
	private List<GregorianCalendar> meses;
	
	private GregorianCalendar mesSelecionado;
	
	private ComponenteCurricular componenteSelecionado;
	
	private Aluno alunoSelecionado;
	
	private Frequencia frequenciaSelecionada;
	
	public Servidor loggedServidor;

	public GerenciaFrequenciasView() {}
	
	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		loggedServidor = servidorBean.consultaServidorById((Long)session.getAttribute("id")); 
		
		componentes = componenteBean.consultaComponentesByProfessor(loggedServidor);
		componenteSelecionado = componentes != null && componentes.size() > 0 ? componentes.get(0) : null;
		
		alunos = componenteSelecionado != null ? alunoBean.consultaMonitoresByComponenteEdital(componentes.get(0).getId(), sharedMenuView.getEditalGlobal().getId()) : new ArrayList<Aluno>();
		alunoSelecionado = alunos.size() > 0 ? alunos.get(0) : null;
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
		resetMesesFrequencias();
		this.componenteSelecionado = componenteSelecionado;
	}

	public List<Aluno> getAlunos() {
		return componenteSelecionado != null ? alunoBean.consultaMonitoresByComponenteEdital(componenteSelecionado.getId(), sharedMenuView.getEditalGlobal().getId()) : new ArrayList<Aluno>();
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public Aluno getAlunoSelecionado() {
		return alunoSelecionado;
	}

	public void setAlunoSelecionado(Aluno alunoSelecionado) {
		resetMesesFrequencias();
		this.alunoSelecionado = alunoSelecionado;
		getFrequenciaSelecionada();
	}

	public GregorianCalendar getMesSelecionado() {
		if (mesSelecionado == null) {
			mesSelecionado = getMeses().get(0);
		}
		return mesSelecionado;
	}

	public void setMesSelecionado(GregorianCalendar mesSelecionado) {
		for (Frequencia frequencia : frequencias) {
			if(frequencia.getMes() == mesSelecionado.get(GregorianCalendar.MONTH)) {
				frequenciaSelecionada = frequencia;
				break;
			}
		}
		this.mesSelecionado = mesSelecionado;
	}

	public List<GregorianCalendar> getMeses() {
		if(meses == null) {
			if(getFrequencias().size() > 0)
				meses = frequencias.get(0).getMonitoria().getEdital().getMesesMonitoria();
		}
		return meses;
	}

	public void setMeses(List<GregorianCalendar> meses) {
		this.meses = meses;
	}

	public List<Frequencia> getFrequencias() {
		FrequenciaRequestResult resultado = frequenciaBean.findByAluno(alunoSelecionado);
		if(resultado.hasErrors()) {
			frequencias = new ArrayList<Frequencia>();
			FacesContext context = FacesContext.getCurrentInstance();
			for (String erro : resultado.errors) {
				context.addMessage(null, new FacesMessage(erro));
			}
		} else {
			frequencias = resultado.frequencias;
		}
		return frequencias;
	}

	public void setFrequencias(List<Frequencia> frequencias) {
		this.frequencias = frequencias;
	}

	public Frequencia getFrequenciaSelecionada() {
		if(frequenciaSelecionada == null) {
			if(frequencias != null) {
				for (Frequencia frequencia : frequencias) {
					if(frequencia.getMes() == getMesSelecionado().get(GregorianCalendar.MONTH)) {
						frequenciaSelecionada = frequencia;
						break;
					}
				}
			}
		}
		return frequenciaSelecionada;
	}

	public void setFrequenciaSelecionada(Frequencia frequenciaSelecionada) {
		this.frequenciaSelecionada = frequenciaSelecionada;
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
	
	private void resetMesesFrequencias() {
		meses = null;
		mesSelecionado = null;
		frequencias = null;
		frequenciaSelecionada = null;
	}
}
