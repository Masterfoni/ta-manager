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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;

@Entity
@SequenceGenerator (name = "SEQUENCIA_FREQUENCIA",
	sequenceName = "SQ_FREQUENCIA",
	initialValue = 1,
	allocationSize = 1)
@Table(name = "TB_FREQUENCIA")
@Access(AccessType.FIELD)
@NamedQueries({
	@NamedQuery(name = "Frequencia.findByMonitoria", query = "SELECT f FROM Frequencia f WHERE f.monitoria = :monitoria")
})
public class Frequencia implements Serializable{

	private static final long serialVersionUID = 3671440051695351979L;

	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="SEQUENCIA_FREQUENCIA")
	private Long id;
	
	@Valid
	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn (name = "ID_MONITORIA", referencedColumnName = "ID")
	private Monitoria monitoria;
	
	@Valid
	@OneToMany(mappedBy="frequencia", cascade=CascadeType.ALL)
	private List<Atividade> atividades;
	
	@Column (name="INT_MES")
	private Integer mes;
	
	@Column (name="BOOL_APROVADO")
	private boolean aprovado;
	
	@Column (name="BOOL_RECEBIDO")
	private boolean recebido;

	public Monitoria getMonitoria() {
		return monitoria;
	}

	public void setMonitoria(Monitoria monitoria) {
		this.monitoria = monitoria;
	}

	public List<Atividade> getAtividades() {
		return atividades;
	}

	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public boolean isAprovado() {
		return aprovado;
	}

	public void setAprovado(boolean aprovado) {
		this.aprovado = aprovado;
	}

	public boolean isRecebido() {
		return recebido;
	}

	public void setRecebido(boolean recebido) {
		this.recebido = recebido;
	}

	public Long getId() {
		return id;
	}
}
