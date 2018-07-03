package br.edu.ifpe.monitoria.entidades;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@SequenceGenerator (name = "SEQUENCIA_ATIVIDADE",
					sequenceName = "SQ_ATIVIDADE",
					initialValue = 1,
					allocationSize = 1)
@Table(name = "TB_ATIVIDADE")
@NamedQueries({
	@NamedQuery(name = "Atividade.findById", query = "SELECT a FROM Atividade a WHERE a.id = :id")
})
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
	@NotBlank(message = "{mensagem.notnull}{tipo.atividade}")
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

	public void setFrequencia(List<Frequencia> frequencias) {
		GregorianCalendar dataAtividade = new GregorianCalendar();
		dataAtividade.setTime(getData());
		
		for (Frequencia frequencia : frequencias) {
			if(dataAtividade.get(Calendar.MONTH) == frequencia.getMes()) {
				setFrequencia(frequencia);
				break;
			}
		}
	}
	
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
	
    @Override
    public boolean equals(Object object) {
        return (object instanceof Atividade) && (id != null) 
             ? id.equals(((Atividade) object).getId()) 
             : (object == this);
    }
    
    @Override
    public String toString() {
        return "br.edu.ifpe.monitoria.entidades.Atividade[ id=" + id  + " ]";
    }
}
