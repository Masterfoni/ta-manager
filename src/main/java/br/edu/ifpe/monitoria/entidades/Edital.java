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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
	@NamedQuery(name = "Edital.findById", query = "SELECT e FROM Edital e WHERE e.id = :id")
})
@Access(AccessType.FIELD)
public class Edital {
	
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="SEQUENCIA_EDITAL")
	private Long id;
	
	@NotBlank
	@Column(name = "TXT_NUMERO_EDITAL")
	private String numeroEdital;
	
	@NotNull(message = "{mensagem.notnull}{tipo.numeroedital}")
	@Column(name = "TXT_NUMERO")
	private Integer numero;
	
	@NotNull(message = "{mensagem.notnull}{tipo.anoedital}")
	@Max(2100) 
	@Min(2010)
	@Column(name = "TXT_ANO")
	private Integer ano;
	
	@Column(name = "BOOL_VIGENTE")
	private boolean vigente;
	
	@NotNull(message = "{mensagem.todos}{tipo.periodo.componente}{data.inicial}")
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INI_INSCRICAO_CC")
	private Date inicioInscricaoComponenteCurricular;

	@NotNull(message = "{mensagem.notnull}{tipo.periodo.componente}{data.final}")
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_FIM_INSCRICAO_CC")
	private Date fimInscricaoComponenteCurricular;
	
	@NotNull(message = "{mensagem.notnull}{tipo.periodo.plano}{data.inicial}")
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INI_INSERCAO_PM")
	private Date inicioInsercaoPlano;

	@NotNull(message = "{mensagem.notnull}{tipo.periodo.plano}{data.final}")
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_FIM_INSCERCAO_PM")
	private Date fimInsercaoPlano;
	
	@NotNull(message = "{mensagem.notnull}{tipo.periodo.estudante}{data.inicial}")
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INI_INSCRICAO_ESTUDANTE")
	private Date inicioInscricaoEstudante;
	
	@NotNull(message = "{mensagem.notnull}{tipo.periodo.estudante}{data.final}")
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_FIM_INSCRICAO_ESTUDANTE")
	private Date fimInscricaoEstudante;

	@NotNull(message = "{mensagem.notnull}{tipo.periodo.notas}{data.inicial}")
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INI_INSERCAO_NT")
	private Date inicioInsercaoNota;

	@NotNull(message = "{mensagem.notnull}{tipo.periodo.notas}{data.final}")
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_FIM_INSCERCAO_NT")
	private Date fimInsercaoNota;
	
	@NotNull(message = "{mensagem.notnull}{tipo.periodo.monitoria}{data.inicial}")
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INI_MONITORIA")
	private Date inicioMonitoria;

	@NotNull(message = "{mensagem.notnull}{tipo.periodo.monitoria}{data.final}")
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_FIM_MONITORIA")
	private Date fimMonitoria;

    public String getNumeroEdital() {
		return numeroEdital;
	}

	public void setNumeroEdital(String numeroEdital) {
		this.numeroEdital = numeroEdital;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public boolean isVigente() {
		return vigente;
	}

	public void setVigente(boolean vigente) {
		this.vigente = vigente;
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

	public Date getInicioInsercaoPlano() {
		return inicioInsercaoPlano;
	}

	public void setInicioInsercaoPlano(Date inicioInsercaoPlano) {
		this.inicioInsercaoPlano = inicioInsercaoPlano;
	}

	public Date getFimInsercaoPlano() {
		return fimInsercaoPlano;
	}

	public void setFimInsercaoPlano(Date fimInsercaoPlano) {
		this.fimInsercaoPlano = fimInsercaoPlano;
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

	public Date getInicioInsercaoNota() {
		return inicioInsercaoNota;
	}

	public void setInicioInsercaoNota(Date inicioInsercaoNota) {
		this.inicioInsercaoNota = inicioInsercaoNota;
	}

	public Date getFimInsercaoNota() {
		return fimInsercaoNota;
	}

	public void setFimInsercaoNota(Date fimInsercaoNota) {
		this.fimInsercaoNota = fimInsercaoNota;
	}

	public Date getInicioMonitoria() {
		return inicioMonitoria;
	}

	public void setInicioMonitoria(Date inicioMonitoria) {
		this.inicioMonitoria = inicioMonitoria;
	}

	public Date getFimMonitoria() {
		return fimMonitoria;
	}

	public void setFimMonitoria(Date fimMonitoria) {
		this.fimMonitoria = fimMonitoria;
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


