package br.edu.ifpe.monitoria.entidades;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="TB_ALUNO")
@DiscriminatorValue(value="ALUNO")
@PrimaryKeyJoinColumn(name="ID_USUARIO", referencedColumnName="ID")
@Access(AccessType.FIELD)
@NamedQueries({
	@NamedQuery(name = "Aluno.findById", query = "SELECT a FROM Aluno a WHERE a.id = :id"),
	@NamedQuery(name = "Aluno.findByMatricula", query = "SELECT a FROM Aluno a WHERE a.matricula = :matricula"),
	@NamedQuery(name = "Aluno.findMonitoresByComponente", query = "SELECT a FROM Aluno a, Monitoria m WHERE m.planoMonitoria.cc.id = :componenteId AND m.planoMonitoria.cc.ativo = TRUE AND m.planoMonitoria.cc.curso.ativo = TRUE AND m.aluno.id = a.id AND m.homologado = TRUE AND m.edital.vigente = TRUE AND m.edital.fimMonitoria > CURRENT_DATE"),
	@NamedQuery(name = "Aluno.findMonitoresByComponenteEdital", query = "SELECT a FROM Aluno a, Monitoria m WHERE m.planoMonitoria.edital.id = :editalId AND m.planoMonitoria.cc.id = :componenteId AND m.planoMonitoria.cc.ativo = TRUE AND m.planoMonitoria.cc.curso.ativo = TRUE AND m.aluno.id = a.id AND m.homologado = TRUE AND m.edital.vigente = TRUE AND m.edital.fimMonitoria > CURRENT_DATE")
})
public class Aluno extends Usuario implements Serializable {

	private static final long serialVersionUID = 2L;

	@NotNull(message = "{mensagem.notnull}{tipo.matricula}")
	@Column (name="TXT_MATRICULA", unique=true)
	private String matricula;

	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_CURSO", referencedColumnName = "ID")
	private Curso curso;

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        return (object instanceof Aluno) && (id != null) 
             ? id.equals(((Aluno) object).getId()) 
             : (object == this);
    }
    
    @Override
    public String toString() {
        return "br.edu.ifpe.monitoria.entidades.Aluno[ id=" + id + ":" + nome + " ]";
    }
}
