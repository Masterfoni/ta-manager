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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;

@Entity
@SequenceGenerator (name = "SEQUENCIA_ESQUEMA_BOLSA",
					sequenceName = "SQ_ESQUEMA_BOLSA",
					initialValue = 1,
					allocationSize = 1)
@Table(name = "TB_ESQUEMA_BOLSA")
@Access(AccessType.FIELD)
@NamedQueries({
	@NamedQuery(name = "EsquemaBolsa.findById", query = "SELECT eb FROM EsquemaBolsa eb WHERE eb.id = :id"),
	@NamedQuery(name = "EsquemaBolsa.findByEdital", query = "SELECT eb FROM EsquemaBolsa eb WHERE eb.edital.id = :idEdital"),
	@NamedQuery(name = "EsquemaBolsa.findByCurso", query = "SELECT eb FROM EsquemaBolsa eb WHERE eb.curso.id = :idCurso"),
	@NamedQuery(name = "EsquemaBolsa.findByEditalCurso", query = "SELECT eb FROM EsquemaBolsa eb WHERE eb.edital.id = :idEdital AND eb.curso.id = :idCurso")
})
public class EsquemaBolsa implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="SEQUENCIA_ESQUEMA_BOLSA")
	private Long id;
	
	@Column (name="INT_QUANTIDADE")
	private Integer quantidade;
	
	@Valid
	@OneToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn (name = "ID_CURSO", referencedColumnName = "ID")
	private Curso curso;

	@Valid
	@OneToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn (name = "ID_EDITAL", referencedColumnName = "ID")
	private Edital edital;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Edital getEdital() {
		return edital;
	}

	public void setEdital(Edital edital) {
		this.edital = edital;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
}
