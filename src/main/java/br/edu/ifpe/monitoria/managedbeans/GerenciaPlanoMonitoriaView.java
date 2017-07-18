package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.edu.ifpe.monitoria.entidades.ComponenteCurricular;
import br.edu.ifpe.monitoria.entidades.Coordenacao;
import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.entidades.PlanoMonitoria;
import br.edu.ifpe.monitoria.entidades.Professor;
import br.edu.ifpe.monitoria.localbean.ComponenteCurricularLocalBean;
import br.edu.ifpe.monitoria.localbean.CoordenacaoLocalBean;
import br.edu.ifpe.monitoria.localbean.EditalLocalBean;
import br.edu.ifpe.monitoria.localbean.PlanoMonitoriaLocalBean;
import br.edu.ifpe.monitoria.localbean.ProfessorLocalBean;

@ManagedBean (name="gerenciaPlanoMonitoriaView")
@ViewScoped
public class GerenciaPlanoMonitoriaView implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ComponenteCurricularLocalBean componentebean;
	
	@EJB 
	private PlanoMonitoriaLocalBean planobean;

	@EJB
	private EditalLocalBean editalbean;
	
	@EJB
	private CoordenacaoLocalBean coordenacaobean;
	
	@EJB
	private ProfessorLocalBean professorbean;
	
	public List<PlanoMonitoria> planos;
	
	public List<Coordenacao> coordenacoes;
	
	public List<Professor> professores;
	
	public List<Edital> editais;
	
	public ComponenteCurricular componentePersistido;
	
	public PlanoMonitoria planoPersistido;
	
	public PlanoMonitoria planoAtualizado;
	
	public String nomeBusca;
	
	public List<Coordenacao> getCoordenacoes() {
		return coordenacoes;
	}

	public void setCoordenacoes(List<Coordenacao> coordenacoes) {
		this.coordenacoes = coordenacoes;
	}

	public String getNomeBusca() {
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca) {
		this.nomeBusca = nomeBusca;
	}

	public ComponenteCurricular getComponentePersistido() {
		return componentePersistido;
	}

	public void setComponentePersistido(ComponenteCurricular componentePersistido) {
		this.componentePersistido = componentePersistido;
	}

	public List<Professor> getProfessores() {
		return professores;
	}

	public void setProfessores(List<Professor> professores) {
		this.professores = professores;
	}

	public List<PlanoMonitoria> getPlanos() {
		return planos;
	}

	public void setPlanos(List<PlanoMonitoria> planos) {
		this.planos = planos;
	}

	public List<Edital> getEditais() {
		return editais;
	}

	public void setEditais(List<Edital> editais) {
		this.editais = editais;
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

	@PostConstruct
	public void init() {
		nomeBusca = "";
		
		coordenacoes = coordenacaobean.consultaCoordenacoes();
		professores = professorbean.consultaProfessores();
		planos = planobean.consultaPlanos();
		editais = editalbean.consultaEditais();
		
		planoAtualizado = new PlanoMonitoria();
		planoPersistido = new PlanoMonitoria();
		componentePersistido = new ComponenteCurricular();
	}
	
	public void cadastrarComponente()
	{
		if(componentebean.persisteComponenteCurricular(componentePersistido))
		{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Cadastro realizado com sucesso!"));
		}
	}
	
	public void cadastrarPlano()
	{
		if(planobean.persistePlanoMonitoria(planoPersistido))
		{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Cadastro realizado com sucesso!"));
		}
	}
	
	public void buscaPlanoMonitoria() {
//		if(nomeBusca.isEmpty())
//			System.out.println(this.coordenacoes);
//		else
//			this.planos = planobean.consultaPlanosByName("%"+nomeBusca+"%");
	}
	
	public void alteraPlano(PlanoMonitoria plano) {
		planoAtualizado = plano;
		System.out.println(planoAtualizado);
	}
	
	public void persisteAlteracao() {
		planobean.atualizaPlanoMonitoria(planoAtualizado);
	}
}
