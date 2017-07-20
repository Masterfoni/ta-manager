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
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator (name = "SEQUENCIA_COORDENACAO",
		sequenceName = "SQ_COORDENACAO",
		initialValue = 1,
		allocationSize = 1)
@Table(name="TB_COORDENACAO")
@NamedQueries({
	@NamedQuery(name = "Coordenacao.findAll", query = "SELECT c FROM Coordenacao c"),
	@NamedQuery(name = "Coordenacao.findById", query = "SELECT c FROM Coordenacao c WHERE c.id = :id"),
    @NamedQuery(name = "Coordenacao.findBySigla", query = "SELECT c FROM Coordenacao c WHERE c.sigla LIKE :sigla"),
    @NamedQuery(name = "Coordenacao.findByNome", query = "SELECT c FROM Coordenacao c WHERE c.nome LIKE :nome")
})
@NamedNativeQueries({
     @NamedNativeQuery(name = "Coordenacao.PorNomeSQL", query = "SELECT ID, TXT_NOME, TXT_SIGLA FROM TB_COORDENACAO WHERE TXT_NOME LIKE ? ORDER BY ID", resultClass = Departamento.class)
})
@Access(AccessType.FIELD)
public class Coordenacao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="SEQUENCIA_COORDENACAO")
	private Long id;
	
	@Column (name="TXT_NOME")
	private String nome;
	
	@Column (name="TXT_SIGLA")
	private String sigla;
	
	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_DEPARTAMENTO", referencedColumnName = "ID")
	private Departamento departamento;
	
	@OneToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_COORDENADOR", referencedColumnName = "ID")
	private Professor coordenador;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Professor getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(Professor coordenador) {
		this.coordenador = coordenador;
	}

	public Long getId() {
		return id;
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
        return (object instanceof Coordenacao) && (id != null) 
             ? id.equals(((Coordenacao) object).getId()) 
             : (object == this);
    }
    
    @Override
    public String toString() {
        return "br.edu.ifpe.monitoria.entidades.Coordenacao[ id=" + id + ":" + nome + " ]";
    }
}
