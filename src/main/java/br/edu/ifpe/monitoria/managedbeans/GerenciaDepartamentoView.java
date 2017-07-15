package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.edu.ifpe.monitoria.entidades.Departamento;
import br.edu.ifpe.monitoria.localbean.DepartamentoLocalBean;

@ManagedBean (name="gerenciaDepartamentoView")
@ViewScoped
public class GerenciaDepartamentoView implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private DepartamentoLocalBean departamentobean;

	public List<Departamento> departamentos;
	
	public Departamento departamentoAtualizado;
	
	public Departamento departamentoPersistido;
	
	public String nomeBusca;
	
	public List<Departamento> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(List<Departamento> departamentos) {
		this.departamentos = departamentos;
	}

	public String getNomeBusca() {
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca) {
		this.nomeBusca = nomeBusca;
	}

	public Departamento getDepartamentoAtualizado() {
		return departamentoAtualizado;
	}

	public void setDepartamentoAtualizado(Departamento departamentoAtualizado) {
		this.departamentoAtualizado = departamentoAtualizado;
	}

	public Departamento getDepartamentoPersistido() {
		return departamentoPersistido;
	}

	public void setDepartamentoPersistido(Departamento departamentoPersistido) {
		this.departamentoPersistido = departamentoPersistido;
	}

	public GerenciaDepartamentoView() {
		nomeBusca = "";
		departamentos = new ArrayList<Departamento>();
		departamentoAtualizado = new Departamento();
		departamentoPersistido = new Departamento();
	}
	
	@PostConstruct
	public void init() {
		departamentos = departamentobean.consultaDepartamentos();
	}
	
	public void cadastrarDepartamento()
	{
		if(departamentobean.persisteDepartamento(departamentoPersistido))
		{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Cadastro realizado com sucesso!"));
		}
	}
	
	public void buscaDepartamento() {
		if(nomeBusca.isEmpty())
			System.out.println(this.departamentos);
		else
			this.departamentos = departamentobean.consultaDepartamentoByName("%"+nomeBusca+"%");
	}
	
	public String deletaDepartamento(Departamento departamento) {
		departamentos.remove(departamento);
		
		departamentobean.deletaDepartamento(departamento.getId());
		
		return "";
	}
	
	public void alteraDepartamento(Departamento departamento) {
		departamentoAtualizado = departamento;
		System.out.println(departamentoAtualizado);
	}
	
	public void persisteAlteracao() {
		departamentobean.atualizaDepartamento(departamentoAtualizado);
	}
}
