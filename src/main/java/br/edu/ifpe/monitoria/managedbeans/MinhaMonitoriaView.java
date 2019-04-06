package br.edu.ifpe.monitoria.managedbeans;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.entidades.Frequencia;
import br.edu.ifpe.monitoria.entidades.Monitoria;
import br.edu.ifpe.monitoria.localbean.AlunoLocalBean;
import br.edu.ifpe.monitoria.localbean.AtividadeLocalBean;
import br.edu.ifpe.monitoria.localbean.FrequenciaLocalBean;
import br.edu.ifpe.monitoria.localbean.MonitoriaLocalBean;
import br.edu.ifpe.monitoria.utils.AtualizacaoRequestResult;
import br.edu.ifpe.monitoria.utils.CriacaoRequestResult;
import br.edu.ifpe.monitoria.utils.FrequenciaRequestResult;

@ManagedBean (name="minhaMonitoriaView")
@ViewScoped
public class MinhaMonitoriaView {
	
	@ManagedProperty(value="#{menuView}")
	private MenuView sharedMenuView;
	
	public void setSharedMenuView(MenuView sharedMenuView) {
		this.sharedMenuView = sharedMenuView;
	}
	
	@EJB
	private FrequenciaLocalBean frequenciaBean;
	
	@EJB
	private MonitoriaLocalBean monitoriaBean;
	
	@EJB
	private AlunoLocalBean alunoBean;
	
	@EJB
	private AtividadeLocalBean atividadeBean;

	private List<Frequencia> frequencias;
	
	private Monitoria monitoria;
	
	private Aluno aluno;

	private GregorianCalendar mesSelecionado;
	
	private Frequencia frequenciaSelecionada;
	
	private List<GregorianCalendar> meses;
	
	private Atividade novaAtividade;
	
	private Atividade atividadeSelecionada;
	
	private String horaInicioAlt;
	
	private String horaFimAlt;

	private Edital editalGlobal;
	
	@PostConstruct
	public void init() {
		editalGlobal = sharedMenuView.getEditalGlobal();
	}
	
	
	public MinhaMonitoriaView() {
		novaAtividade = new Atividade();
	}
	
	public String getNomeMes(GregorianCalendar mes) {
		Locale brazil = new Locale("pt", "BR");
		return mes.getDisplayName(GregorianCalendar.MONTH, GregorianCalendar.LONG, brazil) + "/" + 
				mes.get(GregorianCalendar.YEAR);
	}
	
	public void registrarAtividade() {
		String dataRA = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("dataRA");
		novaAtividade.setData(convertData(dataRA));
		
		String entradaRA = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("entradaRA");
		novaAtividade.setHoraInicio(converteHora(entradaRA));
		
		String saidaRA = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("saidaRA");
		novaAtividade.setHoraFim(converteHora(saidaRA));
		
		CriacaoRequestResult resultado = atividadeBean.registrarAvidade(novaAtividade, frequencias);
		if(resultado.result) {
			novaAtividade = new Atividade();
			
			frequencias = null;
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			for (String erro : resultado.errors) {
				context.addMessage(null, new FacesMessage(erro));
			}
		}
	}
	
	public void alterarAtividade() {
		atividadeSelecionada.setHoraInicio(converteHora(horaInicioAlt));
		atividadeSelecionada.setHoraFim(converteHora(horaFimAlt));
		
		AtualizacaoRequestResult resultado = atividadeBean.atualizarAtividade(atividadeSelecionada);
		if(!resultado.result) { 
			FacesContext context = FacesContext.getCurrentInstance();
			for (String erro : resultado.errors) {
				context.addMessage(null, new FacesMessage(erro));
			}
		}
	}
	
	public void removerAtividade(Atividade atividade) {
		atividadeBean.removeAtividade(atividade, atividade.getFrequencia());
	}
	
	public Date converteHora(String hora) {
		Calendar saida = new GregorianCalendar();
		saida.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hora.substring(0, 2)));
		saida.set(Calendar.MINUTE, Integer.parseInt(hora.substring(3,5)));
		saida.set(Calendar.SECOND, 00);
		return saida.getTime();
	}
	
	public Date convertData(String data) {
		Calendar dataCalendar = new GregorianCalendar();
		dataCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(data.substring(8, 10)));
		dataCalendar.set(Calendar.MONTH, (Integer.parseInt(data.substring(5, 7))-1 ));
		dataCalendar.set(Calendar.YEAR, Integer.parseInt(data.substring(0, 4)));
		return dataCalendar.getTime();
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
	
	public String getDataForm(Date data) {
		if(data != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    	return format.format(data);
		}
		return "";
	}
	
	public List<Frequencia> getFrequencias() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(frequencias == null) {
			FrequenciaRequestResult result = frequenciaBean.findByMonitoria(getMonitoria());
			if(result.hasErrors()) {
				for (String erro : result.errors) {
					context.addMessage(null, new FacesMessage(erro));
				}
			} else {
				frequencias = result.frequencias;
			}
		}
		return frequencias;
	}

	public void setFrequencias(List<Frequencia> frequencias) {
		this.frequencias = frequencias;
	}

	public Monitoria getMonitoria() {
		if(monitoria == null) {
			monitoria =  monitoriaBean.consultaMonitoriaByAlunoEdital(getAluno(), editalGlobal);
		}
		return monitoria;
	}

	public void setMonitoria(Monitoria monitoria) {
		this.monitoria = monitoria;
	}

	public Aluno getAluno() {
		if(aluno == null) {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			aluno = alunoBean.consultaAlunoById((Long)session.getAttribute("id"));
		}
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public GregorianCalendar getMesSelecionado() {
		if(mesSelecionado == null) {
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
			meses = getMonitoria().getEdital().getMesesMonitoria();
		}
		return meses;
	}

	public void setMeses(List<GregorianCalendar> meses) {
		this.meses = meses;
	}

	public Frequencia getFrequenciaSelecionada() {
		if(frequenciaSelecionada == null) {
			for (Frequencia frequencia : frequencias) {
				if(frequencia.getMes() == getMesSelecionado().get(GregorianCalendar.MONTH)) {
					frequenciaSelecionada = frequencia;
					break;
				}
			}
		}
		return frequenciaSelecionada;
	}

	public void setFrequenciaSelecionada(Frequencia frequenciaSelecionada) {
		this.frequenciaSelecionada = frequenciaSelecionada;
	}

	public Atividade getNovaAtividade() {
		return novaAtividade;
	}

	public void setNovaAtividade(Atividade novaAtividade) {
		this.novaAtividade = novaAtividade;
	}

	public Atividade getAtividadeSelecionada() {
		return atividadeSelecionada;
	}

	public void setAtividadeSelecionada(Atividade atividadeSelecionada) {
		this.atividadeSelecionada = atividadeSelecionada;
	}

	public String getHoraInicioAlt() {
		if(atividadeSelecionada != null) {
			horaInicioAlt = getHora(atividadeSelecionada.getHoraInicio());
		} else {
			horaInicioAlt = "";
		}
		return horaInicioAlt;
	}

	public void setHoraInicioAlt(String horaInicioAlt) {
		this.horaInicioAlt = horaInicioAlt;
	}

	public String getHoraFimAlt() {
		if(atividadeSelecionada != null) {
			horaFimAlt = getHora(atividadeSelecionada.getHoraFim());
		} else {
			horaFimAlt = "";
		}
		return horaFimAlt;
	}

	public void setHoraFimAlt(String horaFimAlt) {
		this.horaFimAlt = horaFimAlt;
	}
}
