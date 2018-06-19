package br.edu.ifpe.monitoria.entidades;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import br.edu.ifpe.monitoria.validacao.ValidaPeriodo;

@Entity
@SequenceGenerator (name = "SEQUENCIA_CC",
					sequenceName = "SQ_CC",
					initialValue = 1,
					allocationSize = 1)
@Table(name = "TB_COMP_CURRICULAR")
@Access(AccessType.FIELD)
@NamedQueries({
	@NamedQuery(name = "ComponenteCurricular.findAll", query = "SELECT c FROM ComponenteCurricular c WHERE c.curso.ativo = TRUE AND c.ativo = TRUE"),
	@NamedQuery(name = "ComponenteCurricular.findById", query = "SELECT c FROM ComponenteCurricular c WHERE c.id = :id AND c.curso.ativo = TRUE AND c.ativo = TRUE"),
	@NamedQuery(name = "ComponenteCurricular.findByProfessor", query = "SELECT c FROM ComponenteCurricular c WHERE c.professor.id = :professorId AND c.curso.ativo = TRUE AND c.ativo = true"),
	@NamedQuery(name = "ComponenteCurricular.findByCurso", query = "SELECT c FROM ComponenteCurricular c WHERE c.curso.id = :id AND c.curso.ativo = TRUE AND c.ativo = TRUE"),
	@NamedQuery(name = "ComponenteCurricular.findByNome", query = "SELECT c FROM ComponenteCurricular c WHERE c.nome = :nome AND c.curso.ativo = TRUE AND c.ativo = TRUE")
})
public class ComponenteCurricular implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="SEQUENCIA_CC")
	private Long id;
	
	@NotNull(message = "{mensagem.notnull}{tipo.nome}")
	@Column (name="TXT_NOME")
	private String nome;
	
	@NotNull(message = "{mensagem.notnull}{tipo.codigo}")
	@Column (name="TXT_CODIGO")
	private String codigo;
	
	@NotNull(message = "{mensagem.notnull}{tipo.cargahoraria}")
	@Column (name="INT_CARGA_HORARIA")
	private Integer cargaHoraria;
	
	@NotNull(message = "{mensagem.notnull}{tipo.turno}")
	@Enumerated(EnumType.STRING)
	@Column (name="TXT_TURNO")
	private Turno turno;
	
	@Column (name="BOOL_ATIVO")
	private boolean ativo;
	
	public enum Turno {
		MATUTINO("Matutino"),
		VESPERTINO("Vespertino"),
		NOTURNO("Noturno");
		
		private String label;
		
		private Turno(String label) {
			this.label = label;
		}
		
		public String getLabel() {
			return label;
		}
	}
		
	@NotBlank
	@ValidaPeriodo
	@Size(max = 6, min = 6)
	@Column (name="TXT_PERIODO")
	private String periodo;

	@Valid
	@OneToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn (name = "ID_CURSO", referencedColumnName = "ID")
	private Curso curso;

	@Valid
	@OneToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn (name = "ID_SERVIDOR", referencedColumnName = "ID_USUARIO")
	private Servidor professor;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	public Integer getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(Integer cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public Servidor getProfessor() {
		return professor;
	}

	public void setProfessor(Servidor professor) {
		this.professor = professor;
	}

	public Long getId() {
		return id;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}
	
    public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        return (object instanceof ComponenteCurricular) && (id != null) 
             ? id.equals(((ComponenteCurricular) object).getId()) 
             : (object == this);
    }
    
    @Override
    public String toString() {
        return "br.edu.ifpe.monitoria.entidades.ComponenteCurricular[ id=" + id + ":" + nome + " ]";
    }
}
