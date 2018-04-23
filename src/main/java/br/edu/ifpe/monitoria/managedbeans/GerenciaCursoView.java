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

import br.edu.ifpe.monitoria.entidades.Curso;
import br.edu.ifpe.monitoria.entidades.Servidor;
import br.edu.ifpe.monitoria.localbean.CursoLocalBean;
import br.edu.ifpe.monitoria.localbean.ServidorLocalBean;

@ManagedBean (name="gerenciaCursoView")
@ViewScoped
public class GerenciaCursoView implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private CursoLocalBean cursobean;
	
	@EJB
	private ServidorLocalBean servidorbean;
	
	public List<Curso> cursos;

	public List<Servidor> servidores;
	
	public Curso cursoAtualizado;
	
	public Curso cursoPersistido;
	
	public List<Curso> getCursos() {
		this.cursos = cursobean.consultaCursos();
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public Curso getCursoAtualizado() {
		return cursoAtualizado;
	}

	public void setCursoAtualizado(Curso cursoAtualizado) {
		this.cursoAtualizado = cursoAtualizado;
	}

	public Curso getCursoPersistido() {
		return cursoPersistido;
	}

	public void setCursoPersistido(Curso cursoPersistido) {
		this.cursoPersistido = cursoPersistido;
	}
	
	public List<Servidor> getServidores() {
		servidores = servidorbean.consultaServidores();
		Servidor vazio = new Servidor();
		vazio.setNome("-- Informar depois --");
		vazio.setId(-1L);
		servidores.add(vazio);
		return servidores;
	}

	public void setServidores(List<Servidor> servidores) {
		this.servidores = servidores;
	}
	
	@PostConstruct
	public void init() {
		cursoAtualizado = new Curso();
		cursoPersistido = new Curso();
		cursos = new ArrayList<Curso>();
	}
	
	public void cadastrarCurso()
	{
		if(cursoPersistido.getCoordenador().getId() == -1L) {
			cursoPersistido.setCoordenador(null);
		}
		if(cursobean.persisteCurso(cursoPersistido))
		{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Cadastro realizado com sucesso!"));
		}
	}
		
	public String deletaCurso(Curso curso) {
		cursos.remove(curso);
		cursobean.deletaCurso(curso.getId());
		
		return "";
	}
	
	public void alteraCurso(Curso curso) {
		cursoAtualizado = curso;
	}
	
	public void persisteAlteracao() {
		cursobean.atualizaCurso(cursoAtualizado);
	}
}
