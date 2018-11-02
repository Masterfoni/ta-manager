package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean (name="menuView")
@SessionScoped
public class MenuView implements Serializable {

	private static final long serialVersionUID = 1L;

	boolean comissao;
	
	boolean aluno;
	
	boolean professor;

	@PostConstruct
	public void init() {
		comissao = FacesContext.getCurrentInstance().getExternalContext().isUserInRole("comissao");
		aluno = FacesContext.getCurrentInstance().getExternalContext().isUserInRole("aluno");
		professor = FacesContext.getCurrentInstance().getExternalContext().isUserInRole("professor");
	}

	public boolean isComissao() {
		return comissao;
	}

	public void setComissao(boolean comissao) {
		this.comissao = comissao;
	}

	public boolean isAluno() {
		return aluno;
	}

	public void setAluno(boolean aluno) {
		this.aluno = aluno;
	}

	public boolean isProfessor() {
		return professor;
	}

	public void setProfessor(boolean professor) {
		this.professor = professor;
	}
}