package br.edu.ifpe.monitoria.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
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
	@NamedQuery(name = "Edital.findById", query = "SELECT e FROM Edital e WHERE e.id = :id"),
	@NamedQuery(name = "Edital.findByNumero", query = "SELECT e FROM Edital e WHERE e.numeroEdital = :numero"),
	@NamedQuery(name = "Edital.findVigentes", query = "SELECT e FROM Edital e WHERE e.vigente = true")
})
@Access(AccessType.FIELD)
public class Edital implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
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
	
	@NotNull(message = "{mensagem.notnull}{tipo.periodo.provas}{data.inicial}")
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INI_PROVAS")
	private Date inicioRealizacaoProvas;
	
	@NotNull(message = "{mensagem.notnull}{tipo.periodo.provas}{data.final}")
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_FIM_PROVAS")
	private Date fimRealizacaoProvas;
	
	@NotNull(message = "{mensagem.notnull}{tipo.periodo.componente}{data.inicial}")
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
	
	@NotNull(message = "{mensagem.notnull}{tipo.data.publicacao.classificados}")
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_PUB_ALUN_CLASS")
	private Date publicacaoAlunosClassificados;
	
	@NotNull(message = "{mensagem.notnull}{tipo.data.publicacao.selecionados}")
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_PUB_ALUN_SELEC")
	private Date publicacaoAlunosSelecionados;
	
	@NotNull(message = "{mensagem.notnull}{tipo.periodo.monitoria}{data.inicial}")
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INI_MONITORIA")
	private Date inicioMonitoria;

	@NotNull(message = "{mensagem.notnull}{tipo.periodo.monitoria}{data.final}")
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_FIM_MONITORIA")
	private Date fimMonitoria;
	
	@NotNull(message = "{mensagem.notnull}{tipo.notaselecao}")
	@Column(name = "DOUBLE_NOTA_MINIMA")
	private Double notaMinimaSelecao;
	
	@NotNull(message = "{mensagem.notnull}{tipo.mediacomponente}")
	@Column(name = "DOUBLE_MEDIA_MINIMA")
	private Double mediaMinimaCC;
	
	@Valid
	@OneToMany(mappedBy="edital", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private List<EsquemaBolsa> esquemas;

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
	
	public Double getNotaMinimaSelecao() {
		return notaMinimaSelecao;
	}

	public void setNotaMinimaSelecao(Double notaMinimaSelecao) {
		this.notaMinimaSelecao = notaMinimaSelecao;
	}

	public Double getMediaMinimaCC() {
		return mediaMinimaCC;
	}

	public void setMediaMinimaCC(Double mediaMinimaCC) {
		this.mediaMinimaCC = mediaMinimaCC;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public List<EsquemaBolsa> getEsquemas() {
		return esquemas;
	}

	public void setEsquemas(List<EsquemaBolsa> esquemas) {
		this.esquemas = esquemas;
	}

	public Date getInicioRealizacaoProvas() {
		return inicioRealizacaoProvas;
	}

	public void setInicioRealizacaoProvas(Date inicioRealizacaoProvas) {
		this.inicioRealizacaoProvas = inicioRealizacaoProvas;
	}

	public Date getFimRealizacaoProvas() {
		return fimRealizacaoProvas;
	}

	public void setFimRealizacaoProvas(Date fimRealizacaoProvas) {
		this.fimRealizacaoProvas = fimRealizacaoProvas;
	}
	
	public Date getPublicacaoAlunosClassificados() {
		return publicacaoAlunosClassificados;
	}

	public void setPublicacaoAlunosClassificados(Date publicacaoAlunosClassificados) {
		this.publicacaoAlunosClassificados = publicacaoAlunosClassificados;
	}

	public Date getPublicacaoAlunosSelecionados() {
		return publicacaoAlunosSelecionados;
	}

	public void setPublicacaoAlunosSelecionados(Date publicacaoAlunosSelecionados) {
		this.publicacaoAlunosSelecionados = publicacaoAlunosSelecionados;
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

	public List<GregorianCalendar> getMesesMonitoria() {
		GregorianCalendar dataInicio = new GregorianCalendar();
		dataInicio.setTime(inicioMonitoria);

		GregorianCalendar data = new GregorianCalendar();
		data.setTime(inicioMonitoria);
		data.set(GregorianCalendar.DAY_OF_MONTH, 1);
		
		GregorianCalendar dataFim = new GregorianCalendar();
		dataFim.setTime(fimMonitoria);
				
		List<GregorianCalendar> meses = new ArrayList<GregorianCalendar>();
		
		do{
			GregorianCalendar d = new GregorianCalendar();
			d.setTime(data.getTime());
			meses.add(d);
			data.set(GregorianCalendar.MONTH, data.get(GregorianCalendar.MONTH) +1);
		} while(data.before(dataFim) || data.equals(dataFim));
		
		return meses;
	}
}


