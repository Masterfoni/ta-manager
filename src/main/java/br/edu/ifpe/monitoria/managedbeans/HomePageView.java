package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.edu.ifpe.monitoria.entidades.ComponenteCurricular;
import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.entidades.Servidor;
import br.edu.ifpe.monitoria.localbean.ComponenteCurricularLocalBean;
import br.edu.ifpe.monitoria.localbean.EditalLocalBean;
import br.edu.ifpe.monitoria.localbean.MonitoriaLocalBean;
import br.edu.ifpe.monitoria.localbean.ServidorLocalBean;

@ManagedBean (name="homePageView")
public class HomePageView implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private MonitoriaLocalBean monitoriaBean;
	
	@EJB
	private ServidorLocalBean servidorBean;
	
	@EJB
	private ComponenteCurricularLocalBean componenteBean;
	
	@EJB
	private EditalLocalBean editalBean;
	
	private Edital editalAtual;
	
	private Servidor loggedServidor;
	
	private ComponenteCurricular componenteSelecionadoRFinal;
	
	private ComponenteCurricular componenteSelecionado;
	
	private GregorianCalendar mesSelecionado;
	
	boolean comissao;

	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		loggedServidor = servidorBean.consultaServidorById((Long)session.getAttribute("id"));
		
		comissao = FacesContext.getCurrentInstance().getExternalContext().isUserInRole("professor");
		
		List<Edital> consultaResult = editalBean.consultaEditaisVigentes();
		
		editalAtual = consultaResult.size() > 0 ? consultaResult.get(0) : null;		
	}
	
	public String gerarRelatorioMensal() {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("componenteRelatorio", this.componenteSelecionado);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("mesRelatorio", this.mesSelecionado);
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute("componenteRelatorio", this.componenteSelecionado);
		session.setAttribute("mesRelatorio", this.mesSelecionado);
		
	    return "relatorioMensal";
	}
	
	public String gerarRelatorioRFinal() {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("componenteRelatorio", this.componenteSelecionadoRFinal);
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute("componenteRelatorio", this.componenteSelecionadoRFinal);
		
	    return "relatorioFinalAvaliacao";
	}

	public String back() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext(); 
		HttpSession session = (HttpSession) ec.getSession(false);
		
		if(session != null)
			return "homepage";
		else
			return "";
	}
	
	public Servidor getLoggedServidor() {
		return loggedServidor;
	}

	public void setLoggedServidor(Servidor loggedServidor) {
		this.loggedServidor = loggedServidor;
	}

	public ComponenteCurricular getComponenteSelecionado() {
		return componenteSelecionado;
	}

	public void setComponenteSelecionado(ComponenteCurricular componenteSelecionado) {
		this.componenteSelecionado = componenteSelecionado;
	}

	public List<ComponenteCurricular> getComponentes() {
		return componenteBean.consultaComponentesByProfessor(loggedServidor);
	}
	
	public GregorianCalendar getMesSelecionado() {
		if (mesSelecionado == null && editalAtual != null) {
			mesSelecionado = getMeses().get(0);	
		}
		
		return mesSelecionado;
	}

	public void setMesSelecionado(GregorianCalendar mesSelecionado) {
		this.mesSelecionado = mesSelecionado;
	}
	
	public List<GregorianCalendar> getMeses() {
		return editalAtual != null ? editalAtual.getMesesMonitoria() : new ArrayList<GregorianCalendar>();
	}
	
	public String getNomeMes(GregorianCalendar mes) {
		Locale brazil = new Locale("pt", "BR");
		return mes.getDisplayName(GregorianCalendar.MONTH, GregorianCalendar.LONG, brazil) + "/" + 
				mes.get(GregorianCalendar.YEAR);
	}

	public Edital getEditalAtual() {
		return editalAtual;
	}

	public void setEditalAtual(Edital editalAtual) {
		this.editalAtual = editalAtual;
	}

	public ComponenteCurricular getComponenteSelecionadoRFinal() {
		return componenteSelecionadoRFinal;
	}

	public void setComponenteSelecionadoRFinal(ComponenteCurricular componenteSelecionadoRFinal) {
		this.componenteSelecionadoRFinal = componenteSelecionadoRFinal;
	}
	

	public boolean isComissao() {
		return comissao;
	}

	public void setComissao(boolean comissao) {
		this.comissao = comissao;
	}
}