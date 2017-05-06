package br.edu.ifpe.monitoria.entidades;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@SequenceGenerator (name = "SEQUENCIA_EDITAL",
					sequenceName = "SQ_EDITAL",
					initialValue = 1,
					allocationSize = 1)
@Table(name = "TB_EDITAL")
@Access(AccessType.FIELD)
public class Edital {
	
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="SEQUENCIA_EDITAL")
	private Long id;
	
	@Column(name = "TXT_NUMERO_EDITAL")
	private String numeroEdital;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INI_INCSRICAO_CC")
	private Date inicioInscricaoComponenteCurricular;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_FIM_INCSRICAO_CC")
	private Date fimInscricaoComponenteCurricular;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INI_INSRICAO_ESTUDANTE")
	private Date inicioInscricaoEstudante;
	
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
}


