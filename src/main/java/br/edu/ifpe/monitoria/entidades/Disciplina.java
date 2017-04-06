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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator (name = "SEQUENCIA_DISCIPLINA",
					sequenceName = "SQ_DISCIPLINA",
					initialValue = 1,
					allocationSize = 1)
@Table(name = "TB_DISCIPLINA")
@Access(AccessType.FIELD)
public class Disciplina implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="SEQUENCIA_DEPARTAMENTO")
	private Long id;
	
	@Column (name="TXT_NOME")
	private String nome;
	
	@Column (name="TXT_CODIGO")
	private String codigo;
	
	@Column (name="INT_BOLSAS")
	private Integer numBolsas;
	
	@Column (name="INT_CODIGO")
	private Integer numMonitores;
	
	@Column (name="TXT_TURNO")
	private String turno;
	
	@Column (name="TXT_PERIODO")
	private String periodo;

	@ManyToOne (fetch = FetchType.LAZY, optional = false)
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

	public Integer getNumBolsas() {
		return numBolsas;
	}

	public void setNumBolsas(Integer numBolsas) {
		this.numBolsas = numBolsas;
	}

	public Integer getNumMonitores() {
		return numMonitores;
	}

	public void setNumMonitores(Integer numMonitores) {
		this.numMonitores = numMonitores;
	}
	
}
