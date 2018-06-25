package br.edu.ifpe.monitoria.entidades;

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
import javax.validation.constraints.NotNull;

@Entity
@SequenceGenerator (name = "SEQUENCIA_RF",
					sequenceName = "SQ_RF",
					initialValue = 1,
					allocationSize = 1)
@Table(name = "TB_RELATORIO_FINAL")
@Access(AccessType.FIELD)
@NamedQueries({
	@NamedQuery(name = "RelatorioFinal.findByMonitor", query = "SELECT rf FROM RelatorioFinal rf WHERE rf.monitoria.aluno.id = :monitorId AND rf.monitoria.edital.vigente = TRUE AND rf.monitoria.planoMonitoria.cc.ativo = TRUE AND rf.monitoria.planoMonitoria.cc.curso.ativo = TRUE AND rf.monitoria.edital.fimMonitoria > CURRENT_DATE"),
	@NamedQuery(name = "RelatorioFinal.findAll", query = "SELECT rf FROM RelatorioFinal rf"),
})
public class RelatorioFinal {
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="SEQUENCIA_RF")
	private Long id;
	
	@NotNull(message = "{mensagem.associacao}") //INSERIR TIPO CORRETO
	@OneToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn (name = "ID_MONITORIA", referencedColumnName = "ID")
	private Monitoria monitoria;
	
	@NotNull
	@Column (name="VF_HOMOLOGADO")
	private boolean homologado;
	
	@Column (name="TXT_ATIVIDADES_DESENV") //PERMISSÃO DE BLANK, PQ O ALUNO PODE DEIXAR ALGO POR ESCREVER, SERÁ UMA PÁGINA REPRESENTANDO O PDF
	private String atividadesDesenv;
	
	@Column (name="TXT_DIFICULDADES")
	private String dificuldades;
	
	@Column (name="TXT_SUGESTOES")
	private String sugestoes;
	
	@Column (name="INT_AVALIACAO")
	private int avaliacao;

	public Monitoria getMonitoria() {
		return monitoria;
	}

	public void setMonitoria(Monitoria monitoria) {
		this.monitoria = monitoria;
	}

	public boolean isHomologado() {
		return homologado;
	}

	public void setHomologado(boolean homologado) {
		this.homologado = homologado;
	}

	public String getAtividadesDesenv() {
		return atividadesDesenv;
	}

	public void setAtividadesDesenv(String atividadesDesenv) {
		this.atividadesDesenv = atividadesDesenv;
	}

	public String getDificuldades() {
		return dificuldades;
	}

	public void setDificuldades(String dificuldades) {
		this.dificuldades = dificuldades;
	}

	public String getSugestoes() {
		return sugestoes;
	}

	public void setSugestoes(String sugestoes) {
		this.sugestoes = sugestoes;
	}

	public int getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(int avaliacao) {
		this.avaliacao = avaliacao;
	}

	public Long getId() {
		return id;
	}
}
