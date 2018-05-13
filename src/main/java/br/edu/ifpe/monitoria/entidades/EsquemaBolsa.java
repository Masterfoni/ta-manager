package br.edu.ifpe.monitoria.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
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
	
	@Version
	private Integer version;
	
	@Column (name="INT_QUANTIDADE")
	private Integer quantidade;
	
	@Column (name="INT_QUANTIDADE_REMANESCENTE")
	private Integer quantidadeRemanescente;
	
	@Valid
	@OneToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn (name = "ID_CURSO", referencedColumnName = "ID")
	private Curso curso;

	@Valid
	@OneToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn (name = "ID_EDITAL", referencedColumnName = "ID")
	private Edital edital;
	
	@Valid
	@OneToMany(mappedBy="esquemaAssociado", cascade=CascadeType.ALL)
	private List<PlanoMonitoria> planos;
	
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
		int bolsasRemanescentes = getQuantidadeRemanescente();
		
		if(this.quantidade != null) {
			if(quantidade < this.quantidade) {
				if(bolsasRemanescentes > (this.quantidade - quantidade)) {
					this.quantidade = quantidade;
				}
			} else {
				this.quantidade = quantidade;
			}
		}
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
	
	public List<PlanoMonitoria> getPlanos() {
		return planos;
	}

	public void setPlanos(List<PlanoMonitoria> planos) {
		this.planos = planos;
	}
	
	public Integer getQuantidadeRemanescente() {
		int totalUsado = 0;
		
		for(PlanoMonitoria plano : this.getPlanos()) {
			totalUsado += plano.getBolsas();
		}
		
		return quantidade - totalUsado;
	}

	public void setQuantidadeRemanescente(Integer quantidadeRemanescente) {
		this.quantidadeRemanescente = quantidadeRemanescente;
	}
	
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public void addPlano(PlanoMonitoria plano) {
		plano.setEsquemaAssociado(this);
		planos.add(plano);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
}
