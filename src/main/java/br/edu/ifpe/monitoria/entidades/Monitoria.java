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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@SequenceGenerator (name = "SEQUENCIA_MONITORIA",
					sequenceName = "SQ_MONITORIA",
					initialValue = 1,
					allocationSize = 1)
@Table(name = "TB_MONITORIA")
@NamedQueries({
	@NamedQuery(name = "Monitoria.findAll", query = "SELECT m FROM Monitoria m"),
	@NamedQuery(name = "Monitoria.findAvaliadas", query = "SELECT m FROM Monitoria m WHERE m.avaliado = TRUE"),
	@NamedQuery(name = "Monitoria.findById", query = "SELECT m FROM Monitoria m WHERE m.id = :id"),
	@NamedQuery(name = "Monitoria.findByProfessor", query = "SELECT m FROM Monitoria m WHERE m.avaliado = FALSE AND m.planoMonitoria.id = "
			+ "(SELECT pm.id FROM PlanoMonitoria pm WHERE pm.cc = ("
			+ "SELECT cc FROM ComponenteCurricular cc WHERE cc.professor.id = :id))"),
	@NamedQuery(name = "Monitoria.findByAluno", query = "SELECT m FROM Monitoria m WHERE m.edital = :edital AND m.aluno = :aluno"),
	@NamedQuery(name = "Monitoria.findByEdital", query = "SELECT m FROM Monitoria m WHERE m.edital = :edital"),
	@NamedQuery(name = "Monitoria.findByPlano", query = "SELECT m FROM Monitoria m WHERE m.planoMonitoria = :plano")
})
@Access(AccessType.FIELD)
public class Monitoria implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="SEQUENCIA_MONITORIA")
	private Long id;

	@NotNull(message = "{mensagem.associacao}{tipo.aluno}")
	@OneToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_ALUNO", referencedColumnName = "ID_USUARIO")
	private Aluno aluno;

	@NotNull(message = "{mensagem.associacao}{tipo.plano}")
	@OneToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_PLANO_MONITORIA", referencedColumnName = "ID")
	private PlanoMonitoria planoMonitoria;

	@NotNull(message = "{mensagem.associacao}{tipo.edital}")
	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn (name = "ID_EDITAL", referencedColumnName = "ID")
	private Edital edital;
	
	@Column (name="BOOL_BOLSA")
	private boolean bolsa;

	@Column (name="BOOL_SELECIONADO")
	private boolean selecionado;

	@Column (name="BOOL_AVALIADO")
	private boolean avaliado;

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public PlanoMonitoria getPlanoMonitoria() {
		return planoMonitoria;
	}

	public void setPlanoMonitoria(PlanoMonitoria planoMonitoria) {
		this.planoMonitoria = planoMonitoria;
	}

	public boolean isBolsa() {
		return bolsa;
	}

	public void setBolsa(boolean bolsa) {
		this.bolsa = bolsa;
	}

	public Long getId() {
		return id;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public boolean isAvaliado() {
		return avaliado;
	}

	public void setAvaliado(boolean avaliado) {
		this.avaliado = avaliado;
	}

	public Edital getEdital() {
		return edital;
	}

	public void setEdital(Edital edital) {
		this.edital = edital;
	}
}
