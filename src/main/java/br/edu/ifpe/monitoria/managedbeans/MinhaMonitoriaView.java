package br.edu.ifpe.monitoria.managedbeans;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.derby.tools.sysinfo;

import br.edu.ifpe.monitoria.entidades.Aluno;
import br.edu.ifpe.monitoria.entidades.Atividade;
import br.edu.ifpe.monitoria.entidades.Frequencia;
import br.edu.ifpe.monitoria.entidades.Monitoria;
import br.edu.ifpe.monitoria.localbean.AlunoLocalBean;
import br.edu.ifpe.monitoria.localbean.AtividadeLocalBean;
import br.edu.ifpe.monitoria.localbean.FrequenciaLocalBean;
import br.edu.ifpe.monitoria.localbean.MonitoriaLocalBean;
import br.edu.ifpe.monitoria.utils.CriacaoRequestResult;
import br.edu.ifpe.monitoria.utils.FrequenciaRequestResult;

@ManagedBean (name="minhaMonitoriaView")
@ViewScoped
public class MinhaMonitoriaView {
	
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
	
	public MinhaMonitoriaView() {
		novaAtividade = new Atividade();
	}
	
	public String getNomeMes(GregorianCalendar mes) {
		Locale brazil = new Locale("pt", "BR");
		return mes.getDisplayName(GregorianCalendar.MONTH, GregorianCalendar.LONG, brazil) + "/" + 
				mes.get(GregorianCalendar.YEAR);
	}
	
	public void registrarAtividade() {
		novaAtividade.setFrequencia(getFrequenciaSelecionada());
		
		CriacaoRequestResult resultado = atividadeBean.registrarAvidade(novaAtividade);
		if(resultado.result) {
			novaAtividade = new Atividade();
		}
	}
	
	public String getHora(Date hora) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	    return format.format(hora);
	}
	
	public String getData(Date data) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	    return format.format(data);
	}
	
	public List<Frequencia> getFrequencias() {
		if(frequencias == null) {
			FrequenciaRequestResult result = frequenciaBean.findByMonitoria(getMonitoria());
			if(result.hasErrors()) {
				
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
			monitoria =  monitoriaBean.consultaMonitoriaAtivaByAluno(getAluno());
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
		for (Atividade atividade : frequenciaSelecionada.getAtividades()) {
			System.out.println("-------------------------------------------------------------");
			System.out.println(atividade.getAtividade());
		}
		
		
		return frequenciaSelecionada;
	}

	public void setFrequenciaSelecionada(Frequencia frequenciaSelecionada) {
		this.frequenciaSelecionada = frequenciaSelecionada;
		for (Atividade atividade : frequenciaSelecionada.getAtividades()) {
			System.out.println("-------------------------------------------------------------");
			System.out.println(atividade.getAtividade());
		}
	}

	public Atividade getNovaAtividade() {
		return novaAtividade;
	}

	public void setNovaAtividade(Atividade novaAtividade) {
		this.novaAtividade = novaAtividade;
	}
}
