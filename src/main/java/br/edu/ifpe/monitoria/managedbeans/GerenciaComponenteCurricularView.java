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
import br.edu.ifpe.monitoria.entidades.Curso;
import br.edu.ifpe.monitoria.entidades.Servidor;
import br.edu.ifpe.monitoria.localbean.ComponenteCurricularLocalBean;
import br.edu.ifpe.monitoria.localbean.CursoLocalBean;
import br.edu.ifpe.monitoria.localbean.ServidorLocalBean;
import br.edu.ifpe.monitoria.utils.AtualizacaoRequestResult;
import br.edu.ifpe.monitoria.utils.DelecaoRequestResult;

@ManagedBean (name="gerenciaComponenteCurricularView")
@ViewScoped
public class GerenciaComponenteCurricularView implements Serializable {

	private static final long serialVersionUID = -7661212971462467078L;

	@EJB
	private ComponenteCurricularLocalBean componentebean;

	@EJB
	private CursoLocalBean cursobean;

	@EJB
	private ServidorLocalBean servidorbean;
	
	public List<Servidor> professores;
	
	public List<ComponenteCurricular> componentes;

	public List<Curso> cursos;
	
	public ComponenteCurricular componentePersistido;

	public ComponenteCurricular componenteAtualizado;
	
	public List<Servidor> getProfessores() {
		return professores;
	}

	public void setProfessores(List<Servidor> professores) {
		this.professores = professores;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public ComponenteCurricular getComponentePersistido() {
		return componentePersistido;
	}
	
	public void setComponentePersistido(ComponenteCurricular componentePersistido) {
		this.componentePersistido = componentePersistido;
	}
	
	public ComponenteCurricular getComponenteAtualizado() {
		return componenteAtualizado;
	}
	
	public void setComponenteAtualizado(ComponenteCurricular componenteAtualizado) {
		this.componenteAtualizado = componenteAtualizado;
	}

	public List<ComponenteCurricular> getComponentes() {
		componentes = componentebean.consultaComponentesCurriculares(true);
		return componentes;
	}
	
	public void setComponentes(List<ComponenteCurricular> componentes) {
		this.componentes = componentes;
	}

	@PostConstruct
	public void init() {
		cursos = cursobean.consultaCursos();
		componentes = componentebean.consultaComponentesCurriculares(true);
		professores = servidorbean.consultaServidores();
		componentePersistido = new ComponenteCurricular();
		componenteAtualizado = new ComponenteCurricular();
	}
	
	public void alteraComponente(ComponenteCurricular componente) {
		componenteAtualizado = componente;
	}
	
	public void toggleComponente() {
		AtualizacaoRequestResult inativacaoResult = componentebean.toggleAtivacaoComponenteCurricular(componenteAtualizado);
		
		if(inativacaoResult.hasErrors()) {
			FacesContext context = FacesContext.getCurrentInstance();

			for(String error : inativacaoResult.errors) {	
				context.addMessage(null, new FacesMessage(error));				
			}
		}
	}
	
	public void persisteAlteracao() {
		componentebean.atualizaComponenteCurricular(componenteAtualizado);
	}

	public void cadastrarComponente()
	{
		if(componentebean.persisteComponenteCurricular(componentePersistido))
		{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Cadastro realizado com sucesso!"));
			componentePersistido = new ComponenteCurricular();
		}
	}
	
	public void deletarComponente(ComponenteCurricular componente)
	{
		DelecaoRequestResult deleteOutput = componentebean.deletaComponenteCurricular(componente.getId());
		
		if(deleteOutput.hasErrors())
		{
			FacesContext context = FacesContext.getCurrentInstance();

			for(String error : deleteOutput.errors)
			{ 
				context.addMessage(null, new FacesMessage(error));
			}
		}
	}

	public Turno[] getTurns() {
		return Turno.values();
	}
}