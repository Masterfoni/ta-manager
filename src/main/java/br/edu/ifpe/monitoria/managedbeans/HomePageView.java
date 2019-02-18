package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.edu.ifpe.monitoria.entidades.ComponenteCurricular;
import br.edu.ifpe.monitoria.entidades.Servidor;
import br.edu.ifpe.monitoria.localbean.ComponenteCurricularLocalBean;
import br.edu.ifpe.monitoria.localbean.EditalLocalBean;
import br.edu.ifpe.monitoria.localbean.MonitoriaLocalBean;
import br.edu.ifpe.monitoria.localbean.ServidorLocalBean;
import br.edu.ifpe.monitoria.utils.SessionContext;

@ManagedBean (name="homePageView")
@ViewScoped
public class HomePageView implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{menuView}")
	private MenuView sharedMenuView;
	
	public void setSharedMenuView(MenuView sharedMenuView) {
		this.sharedMenuView = sharedMenuView;
	}

	@EJB
	private MonitoriaLocalBean monitoriaBean;
	
	@EJB
	private ServidorLocalBean servidorBean;
	
	@EJB
	private ComponenteCurricularLocalBean componenteBean;
	
	@EJB
	private EditalLocalBean editalBean;
	
	private Servidor loggedServidor;
	
	private ComponenteCurricular componenteSelecionadoRFinal;
	
	private ComponenteCurricular componenteSelecionado;
	
	private GregorianCalendar mesSelecionado;
	
	boolean comissao;

	@PostConstruct
	public void init() {
		loggedServidor = servidorBean.consultaServidorById((Long)SessionContext.getInstance().getAttribute("id"));
		comissao = FacesContext.getCurrentInstance().getExternalContext().isUserInRole("professor");
	}
	
	public String gerarRelatorioMensal() {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("componenteRelatorio", this.componenteSelecionado);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("mesRelatorio", this.mesSelecionado);
		
		SessionContext sessionContext = SessionContext.getInstance();
		sessionContext.setAttribute("componenteRelatorio", this.componenteSelecionado);
		sessionContext.setAttribute("mesRelatorio", this.mesSelecionado);
		
	    return "relatorioMensal";
	}
	
	public String gerarRelatorioRFinal() {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("componenteRelatorio", this.componenteSelecionadoRFinal);
		SessionContext.getInstance().setAttribute("componenteRelatorio", this.componenteSelecionadoRFinal);
		
	    return "relatorioFinalAvaliacao";
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
		return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("comissao") ? 
				componenteBean.consultaComponentesCurriculares() :
				 componenteBean.consultaComponentesByProfessor(loggedServidor);
	}
	
	public GregorianCalendar getMesSelecionado() {
		if (mesSelecionado == null && sharedMenuView.getEditalGlobal() != null) {
			mesSelecionado = getMeses().get(0);	
		}
		
		return mesSelecionado;
	}

	public void setMesSelecionado(GregorianCalendar mesSelecionado) {
		this.mesSelecionado = mesSelecionado;
	}
	
	public List<GregorianCalendar> getMeses() {
		return sharedMenuView.getEditalGlobal() != null ? sharedMenuView.getEditalGlobal().getMesesMonitoria() : new ArrayList<GregorianCalendar>();
	}
	
	public String getNomeMes(GregorianCalendar mes) {
		Locale brazil = new Locale("pt", "BR");
		return mes.getDisplayName(GregorianCalendar.MONTH, GregorianCalendar.LONG, brazil) + "/" + 
				mes.get(GregorianCalendar.YEAR);
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