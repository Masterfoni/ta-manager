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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@SequenceGenerator (name = "SEQUENCIA_PM",
					sequenceName = "SQ_PM",
					initialValue = 1,
					allocationSize = 1)
@Table(name = "TB_PLANO_MONITORIA")
@Access(AccessType.FIELD)
@NamedQueries({
	@NamedQuery(name = "PlanoMonitoria.findAll", query = "SELECT p FROM PlanoMonitoria p"),
	@NamedQuery(name = "PlanoMonitoria.findById", query = "SELECT p FROM PlanoMonitoria p WHERE p.id = :id"),
	@NamedQuery(name = "PlanoMonitoria.findByProfessor", query = "SELECT p FROM PlanoMonitoria p WHERE p.cc.professor.id = :id"),
	@NamedQuery(name = "PlanoMonitoria.findByCoordenador", query = "SELECT p FROM PlanoMonitoria p WHERE p.cc.curso.coordenador.id = :id"),
	@NamedQuery(name = "PlanoMonitoria.findByComponente", query = "SELECT p FROM PlanoMonitoria p WHERE p.cc.id = :id")
})
public class PlanoMonitoria {

	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="SEQUENCIA_PM")
	private Long id;
	
	@NotNull(message = "{mensagem.associacao}{tipo.edital}")
	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn (name = "ID_EDITAL", referencedColumnName = "ID")
	private Edital edital;
	
	@NotNull(message = "{mensagem.associacao}{tipo.cc}")
	@OneToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn (name = "ID_COMP_CURRICULAR", referencedColumnName = "ID")
	private ComponenteCurricular cc;
	
	@NotNull(message = "{mensagem.notnull}{tipo.bolsas}")
	@Column (name="INT_BOLSAS")
	private Integer bolsas;
	
	@NotNull(message = "{mensagem.notnull}{tipo.voluntarios}")
	@Column (name="INT_VOLUNTARIOS")
	private Integer voluntarios;
	
	@Column (name="TXT_JUSTIFICATIVA")
	private String justificativa;
	
	@Column (name="TXT_OBJETIVO")
	private String objetivo;
	
	@Column (name="TXT_LISTA_ATIVIDADES")
	private String listaAtividades;

	public Edital getEdital() {
		return edital;
	}

	public void setEdital(Edital edital) {
		this.edital = edital;
	}

	public ComponenteCurricular getCc() {
		return cc;
	}

	public void setCc(ComponenteCurricular cc) {
		this.cc = cc;
	}

	public Integer getBolsas() {
		return bolsas;
	}

	public void setBolsas(Integer bolsas) {
		this.bolsas = bolsas;
	}

	public Integer getVoluntarios() {
		return voluntarios;
	}

	public void setVoluntarios(Integer voluntarios) {
		this.voluntarios = voluntarios;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getPeriodo() {
		return listaAtividades;
	}

	public void setPeriodo(String listaAtividades) {
		this.listaAtividades = listaAtividades;
	}

	public Long getId() {
		return id;
	}

	public String getListaAtividades() {
		return listaAtividades;
	}

	public void setListaAtividades(String listaAtividades) {
		this.listaAtividades = listaAtividades;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        return (object instanceof PlanoMonitoria) && (id != null) 
             ? id.equals(((PlanoMonitoria) object).getId()) 
             : (object == this);
    }
}