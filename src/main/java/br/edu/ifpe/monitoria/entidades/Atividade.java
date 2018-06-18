package br.edu.ifpe.monitoria.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
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

import org.eclipse.persistence.annotations.TimeOfDay;

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
	
	@ManyToOne
	@JoinColumn(name="ID_FREQUENCIA")
	private Frequencia frequencia;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_DATA")
	private Date data;
	
	@Temporal(TemporalType.TIME)
	@Column(name="TM_HORAINICIO", columnDefinition="TIME")
	private Date horaInicio;
	
	@Temporal(TemporalType.TIME)
	@Column(name="TM_HORAFIM", columnDefinition ="TIME")
	private Date horaFim;
	
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
