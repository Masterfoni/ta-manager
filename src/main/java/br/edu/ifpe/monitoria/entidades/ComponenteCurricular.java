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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@SequenceGenerator (name = "SEQUENCIA_CC",
					sequenceName = "SQ_CC",
					initialValue = 1,
					allocationSize = 1)
@Table(name = "TB_COMP_CURRICULAR")
@Access(AccessType.FIELD)
public class ComponenteCurricular implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="SEQUENCIA_CC")
	private Long id;
	
	@NotNull
	@Column (name="TXT_NOME")
	private String nome;
	
	@NotNull
	@Column (name="TXT_CODIGO")
	private String codigo;
	
	@NotNull
	@Column (name="INT_CARGA_HORARIA")
	private Integer cargaHoraria;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column (name="TXT_TURNO")
	private Turno turno;
	
	public enum Turno {
		MATUTINO,
		VESPERTINO,
		NOTURNO,
	}
		
	@NotBlank
	@ValidaPeriodo
	@Size(max = 6, min = 6)
	@Column (name="TXT_PERIODO")
	private String periodo;

	@NotNull
	@OneToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn (name = "ID_COORDENACAO", referencedColumnName = "ID")
	private Coordenacao coordenacao;

	@NotNull
	@OneToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn (name = "ID_PROFESSOR", referencedColumnName = "ID_USUARIO")
	private Professor professor;

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

	public Coordenacao getCoordenacao() {
		return coordenacao;
	}

	public void setCoordenacao(Coordenacao coordenacao) {
		this.coordenacao = coordenacao;
	}
	
	public int getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(int cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
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
}
