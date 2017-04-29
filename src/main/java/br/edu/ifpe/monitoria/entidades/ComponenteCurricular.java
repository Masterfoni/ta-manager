package br.edu.ifpe.monitoria.entidades;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
	
	@Column (name="TXT_NOME")
	private String nome;
	
	@Column (name="TXT_CODIGO")
	private String codigo;
	
	@Column (name="INT_CARGA_HORARIA")
	private String cargaHoraria;
	
	@Column (name="TXT_TURNO")
	private String turno;
	
	@Column (name="TXT_PERIODO")
	private String periodo;

	@OneToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn (name = "ID_COORDENACAO", referencedColumnName = "ID")
	private Coordenacao coordenacao;

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

	public String getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(String cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
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

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Long getId() {
		return id;
	}
}
