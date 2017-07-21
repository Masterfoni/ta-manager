package br.edu.ifpe.monitoria.entidades;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@SequenceGenerator (name = "SEQUENCIA_EDITAL",
					sequenceName = "SQ_EDITAL",
					initialValue = 1,
					allocationSize = 1)
@Table(name = "TB_EDITAL")
@NamedQueries({
	@NamedQuery(name = "Edital.findAll", query = "SELECT e FROM Edital e"),
	@NamedQuery(name = "Edital.findById", query = "SELECT e FROM Edital e WHERE e.id = :id"),
    @NamedQuery(name = "Edital.findByNumeroEdital", query = "SELECT e FROM Edital e WHERE e.numeroEdital = :numeroEdital")
})
@Access(AccessType.FIELD)
public class Edital {
	
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="SEQUENCIA_EDITAL")
	private Long id;
	
	@NotBlank(message = "{mensagem.notnull}{tipo.numeroedital}")
	@Column(name = "TXT_NUMERO_EDITAL")
	private String numeroEdital;
	
	@NotNull(message = "{mensagem.notnull}{tipo.inicc}")
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INI_INSCRICAO_CC")
	private Date inicioInscricaoComponenteCurricular;

	@NotNull(message = "{mensagem.notnull}{tipo.fimcc}")
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_FIM_INSCRICAO_CC")
	private Date fimInscricaoComponenteCurricular;
	
	@NotNull(message = "{mensagem.notnull}{tipo.iniestudante}")
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INI_INSCRICAO_ESTUDANTE")
	private Date inicioInscricaoEstudante;
	
	@NotNull(message = "{mensagem.notnull}{tipo.fimestudante}")
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_FIM_INSCRICAO_ESTUDANTE")
	private Date fimInscricaoEstudante;

	public String getNumeroEdital() {
		return numeroEdital;
	}

	public void setNumeroEdital(String numeroEdital) {
		this.numeroEdital = numeroEdital;
	}

	public Date getInicioInscricaoComponenteCurricular() {
		return inicioInscricaoComponenteCurricular;
	}

	public void setInicioInscricaoComponenteCurricular(Date inicioInscricaoComponenteCurricular) {
		this.inicioInscricaoComponenteCurricular = inicioInscricaoComponenteCurricular;
	}

	public Date getFimInscricaoComponenteCurricular() {
		return fimInscricaoComponenteCurricular;
	}

	public void setFimInscricaoComponenteCurricular(Date fimInscricaoComponenteCurricular) {
		this.fimInscricaoComponenteCurricular = fimInscricaoComponenteCurricular;
	}

	public Date getInicioInscricaoEstudante() {
		return inicioInscricaoEstudante;
	}

	public void setInicioInscricaoEstudante(Date inicioInscricaoEstudante) {
		this.inicioInscricaoEstudante = inicioInscricaoEstudante;
	}

	public Date getFimInscricaoEstudante() {
		return fimInscricaoEstudante;
	}

	public void setFimInscricaoEstudante(Date fimInscricaoEstudante) {
		this.fimInscricaoEstudante = fimInscricaoEstudante;
	}

	public Long getId() {
		return id;
	}
	
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        return (object instanceof Edital) && (id != null) 
             ? id.equals(((Edital) object).getId()) 
             : (object == this);
    }
    
    @Override
    public String toString() {
        return "br.edu.ifpe.monitoria.entidades.Edital[ id=" + id + ":" + numeroEdital + " ]";
    }
}


