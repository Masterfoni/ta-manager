package br.edu.ifpe.monitoria.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@SequenceGenerator (name = "SEQUENCIA_ATIVIDADE",
					sequenceName = "SQ_ATIVIDADE",
					initialValue = 1,
					allocationSize = 1)
@Table(name = "TB_ATIVIDADE")
public class Atividade implements Serializable{

	private static final long serialVersionUID = -6400104740132348600L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="SEQUENCIA_ATIVIDADE")
	private Long id;
	
	@Valid
	@ManyToOne
	@JoinColumn(name="ID_FREQUENCIA")
	private Frequencia frequencia;
	
	@NotNull(message = "{mensagem.notnull}{data.atividade}")
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_DATA")
	private Date data;
	
	@NotNull(message = "{mensagem.notnull}{horainicio.atividade}")
	@Temporal(TemporalType.TIME)
	@Column(name="TM_HORAINICIO", columnDefinition="TIME")
	private Date horaInicio;
	
	@NotNull(message = "{mensagem.notnull}{horafim.atividade}")
	@Temporal(TemporalType.TIME)
	@Column(name="TM_HORAFIM", columnDefinition ="TIME")
	private Date horaFim;
	
	@NotNull(message = "{mensagem.notnull}{tipo.atividade}")
	@Column (name="TXT_ATIVIDADE")
	private String atividade;

	@Column (name="TXT_OBSERVACAO")
	private String observacao;

	public Frequencia getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(Frequencia frequencia) {
		this.frequencia = frequencia;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(Date horaFim) {
		this.horaFim = horaFim;
	}

	public String getAtividade() {
		return atividade;
	}

	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Long getId() {
		return id;
	}
}
