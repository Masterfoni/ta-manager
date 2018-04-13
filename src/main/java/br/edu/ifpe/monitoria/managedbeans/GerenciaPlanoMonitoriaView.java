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
import br.edu.ifpe.monitoria.entidades.ComponenteCurricular.Turno;
import br.edu.ifpe.monitoria.entidades.Coordenacao;
import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.entidades.PlanoMonitoria;
import br.edu.ifpe.monitoria.entidades.Servidor;
import br.edu.ifpe.monitoria.localbean.ComponenteCurricularLocalBean;
import br.edu.ifpe.monitoria.localbean.CoordenacaoLocalBean;
import br.edu.ifpe.monitoria.localbean.EditalLocalBean;
import br.edu.ifpe.monitoria.localbean.PlanoMonitoriaLocalBean;
import br.edu.ifpe.monitoria.localbean.ServidorLocalBean;

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
	private ServidorLocalBean servidorbean;
	
	public List<ComponenteCurricular> componentes;
	
	public List<Coordenacao> coordenacoes;
	
	public List<PlanoMonitoria> planos;
	
	public List<Servidor> servidores;
	
	public List<Edital> editais;
	
	public ComponenteCurricular componentePersistido;
	
	public PlanoMonitoria planoPersistido;
	
	public PlanoMonitoria planoAtualizado;
	
	public String nomeBusca;
	
	public long editalId;
	
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

	public List<Servidor> getProfessores() {
		return servidores;
	}

	public void setServidores(List<Servidor> servidores) {
		this.servidores = servidores;
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

	public List<ComponenteCurricular> getComponentes() {
		return componentes;
	}

	public void setComponentes(List<ComponenteCurricular> componentes) {
		this.componentes = componentes;
	}
	
	public long getEditalId() {
		return editalId;
	}

	public void setEditalId(long editalId) {
		this.editalId = editalId;
	}

	@PostConstruct
	public void init() {
		nomeBusca = "";
		
		coordenacoes = coordenacaobean.consultaCoordenacoes();
		componentes = componentebean.consultaComponentesCurriculares();
		servidores = servidorbean.consultaServidores();
		planos = planobean.consultaPlanos();
		editais = editalbean.consultaEditais();
		
		planoAtualizado = new PlanoMonitoria();
		planoPersistido = new PlanoMonitoria();
		componentePersistido = new ComponenteCurricular();
		
		editalId = 0;
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
		planoPersistido.setEdital(editalbean.consultaEditalById(editalId));
		
		if(planobean.persistePlanoMonitoria(planoPersistido))
		{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Cadastro realizado com sucesso!"));
		}
	}
	
	public void buscaPlanoMonitoria() {
			this.planos = planobean.consultaPlanos();
	}
	
	public void alteraPlano(PlanoMonitoria plano) {
		planoAtualizado = plano;
		planoAtualizado.setEdital(editalbean.consultaEditalById(editalId));
	}
	
	public void persisteAlteracao() {
		planobean.atualizaPlanoMonitoria(planoAtualizado);
	}
	
	public Turno[] getTurns() {
		return Turno.values();
	}
}
