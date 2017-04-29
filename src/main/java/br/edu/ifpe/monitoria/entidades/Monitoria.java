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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator (name = "SEQUENCIA_MONITORIA",
					sequenceName = "SQ_MONITORIA",
					initialValue = 1,
					allocationSize = 1)
@Table(name = "TB_MONITORIA")
@Access(AccessType.FIELD)
public class Monitoria implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="SEQUENCIA_MONITORIA")
	private Long id;
	
	@OneToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_ALUNO", referencedColumnName = "ID")
	private Aluno aluno;
	
	@OneToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_COMP_CURRICULAR", referencedColumnName = "ID")
	private ComponenteCurricular componenteCurricular;
	
	@Column (name="BOOL_BOLSA")
	private boolean bolsa;

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public ComponenteCurricular getComponenteCurricular() {
		return componenteCurricular;
	}

	public void setComponenteCurricular(ComponenteCurricular componenteCurricular) {
		this.componenteCurricular = componenteCurricular;
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
}
