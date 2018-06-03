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
	@NamedQuery(name = "Monitoria.findById", query = "SELECT m FROM Monitoria m WHERE m.id = :id"),
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
	
	@Column (name="DOUBLE_NOTA_SELECAO")
	private Double notaSelecao;
	
	@Column (name="DOUBLE_MEDIA_COMPONENTE")
	private Double mediaComponente;
	
	@Column (name="INT_DESEMPATE")
	private Integer desempate;
	
	@Column (name="INT_CLASSIFICACAO")
	private Integer classificacao;
	
	@Column (name="BOOL_EMPATADO")
	private boolean empatado;
	
	@Column (name="BOOL_REPROVACAO")
	private boolean reprovacao;
	
	@Column (name="BOOL_CLASSIFICADO")
	private boolean classificado;
	
	@Column (name="BOOL_SELECIONADO")
	private boolean selecionado;
	
	@Column (name="BOOL_BOLSISTA")
	private boolean bolsista;
	
	public Long getId() {
		return id;
	}
	
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

	public Edital getEdital() {
		return edital;
	}
	
	public void setEdital(Edital edital) {
		this.edital = edital;
	}

	public Double getNotaSelecao() {
		return notaSelecao;
	}
	
	public void setNotaSelecao(Double notaSelecao) {
		if(notaSelecao == null || notaSelecao < 7) {
			setClassificado(false);
			setClassificacao(null);
		}
		this.notaSelecao = notaSelecao;
	}
	
	public Double getMediaComponente() {
		return mediaComponente;
	}

	public void setMediaComponente(Double mediaComponente) {
		if(notaSelecao == null || notaSelecao < 7) {
			setClassificado(false);
			setClassificacao(null);
		} 
		this.mediaComponente = mediaComponente;
	}
		
	public Integer getDesempate() {
		return desempate;
	}

	public void setDesempate(Integer desempate) {
		this.desempate = desempate;
	}

	public Integer getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(Integer classificacao) {
		this.classificacao = classificacao;
	}
	
	public boolean isEmpatado() {
		return empatado;
	}

	public void setEmpatado(boolean empatado) {
		this.empatado = empatado;
	}

	public boolean isReprovacao() {
		return reprovacao;
	}

	public void setReprovacao(boolean reprovacao) {
		if(reprovacao) {
			setClassificado(false);
			setClassificacao(null);
		}
		this.reprovacao = reprovacao;
	}

	public boolean isClassificado() {
		if(!reprovacao &&
				mediaComponente != null && notaSelecao != null &&
				mediaComponente >= edital.getMediaMinimaCC() && notaSelecao >= edital.getNotaMinimaSelecao() && 
				(!empatado || (empatado && desempate != null)))
			setClassificado(true);
		else
			setClassificado(false);
		return classificado;
	}

	public void setClassificado(boolean classificado) {
		this.classificado = classificado;
	}
	
	public boolean isBolsista() {
		return bolsista;
	}

	public void setBolsista(boolean bolsista) {
		this.bolsista = bolsista;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}
}
