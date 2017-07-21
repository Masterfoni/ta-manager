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

import br.edu.ifpe.monitoria.entidades.Coordenacao;
import br.edu.ifpe.monitoria.entidades.Departamento;
import br.edu.ifpe.monitoria.entidades.Professor;
import br.edu.ifpe.monitoria.localbean.CoordenacaoLocalBean;
import br.edu.ifpe.monitoria.localbean.DepartamentoLocalBean;
import br.edu.ifpe.monitoria.localbean.ProfessorLocalBean;

@ManagedBean (name="gerenciaCoordenacaoView")
@ViewScoped
public class GerenciaCoordenacaoView implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private CoordenacaoLocalBean coordenacaobean;
	
	@EJB
	private DepartamentoLocalBean departamentobean;

	@EJB
	private ProfessorLocalBean professorbean;
	
	public List<Coordenacao> coordenacoes;
	
	public List<Departamento> departamentos;
	
	public List<Professor> professores;
	
	public Coordenacao coordenacaoAtualizada;
	
	public Coordenacao coordenacaoPersistida;
	
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

	public Coordenacao getCoordenacaoAtualizada() {
		return coordenacaoAtualizada;
	}

	public void setCoordenacaoAtualizada(Coordenacao coordenacaoAtualizada) {
		this.coordenacaoAtualizada = coordenacaoAtualizada;
	}

	public Coordenacao getCoordenacaoPersistida() {
		return coordenacaoPersistida;
	}

	public void setCoordenacaoPersistida(Coordenacao coordenacaoPersistida) {
		this.coordenacaoPersistida = coordenacaoPersistida;
	}

	public List<Departamento> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(List<Departamento> departamentos) {
		this.departamentos = departamentos;
	}
	
	public List<Professor> getProfessores() {
		return professores;
	}

	public void setProfessores(List<Professor> professores) {
		this.professores = professores;
	}

	@PostConstruct
	public void init() {
		nomeBusca = "";
		coordenacoes = coordenacaobean.consultaCoordenacoes();
		departamentos = departamentobean.consultaDepartamentos();
		professores = professorbean.consultaProfessores();
		coordenacaoAtualizada = new Coordenacao();
		coordenacaoPersistida = new Coordenacao();
		coordenacoes = new ArrayList<Coordenacao>();
	}
	
	public void cadastrarCoordenacao()
	{
		if(coordenacaobean.persisteCoordenacao(coordenacaoPersistida))
		{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Cadastro realizado com sucesso!"));
		}
	}
	
	public void buscaCoordenacao() {
		if(nomeBusca.isEmpty())
			this.coordenacoes = coordenacaobean.consultaCoordenacoes();
		else
			this.coordenacoes = coordenacaobean.consultaCoordenacaoByName("%"+nomeBusca+"%");
	}
	
	public String deletaCoordenacao(Coordenacao coordenacao) {
		coordenacoes.remove(coordenacao);
		
		coordenacaobean.deletaCoordenacao(coordenacao.getId());
		
		return "";
	}
	
	public void alteraCoordenacao(Coordenacao coordenacao) {
		coordenacaoAtualizada = coordenacao;
		System.out.println(coordenacaoAtualizada);
	}
	
	public void persisteAlteracao() {
		coordenacaobean.atualizaCoordenacao(coordenacaoAtualizada);
	}
}
