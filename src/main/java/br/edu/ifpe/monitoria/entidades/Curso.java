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
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@SequenceGenerator (name = "SEQUENCIA_CURSO",
		sequenceName = "SQ_CURSO",
		initialValue = 1,
		allocationSize = 1)
@Table(name="TB_CURSO")
@NamedQueries({
	@NamedQuery(name = "Curso.findAll", query = "SELECT c FROM Curso c ORDER BY c.nome, c.ativo"),
	@NamedQuery(name = "Curso.findById", query = "SELECT c FROM Curso c WHERE c.id = :id AND c.ativo = TRUE"),
	@NamedQuery(name = "Curso.findByIdAll", query = "SELECT c FROM Curso c WHERE c.id = :id"),
    @NamedQuery(name = "Curso.findBySigla", query = "SELECT c FROM Curso c WHERE c.sigla LIKE :sigla AND c.ativo = TRUE ORDER BY c.nome"),
    @NamedQuery(name = "Curso.findByNome", query = "SELECT c FROM Curso c WHERE c.nome LIKE :nome AND c.ativo = TRUE ORDER BY c.nome"),
    @NamedQuery(name = "Curso.findByCoordenador", query = "SELECT c FROM Curso c WHERE c.coordenador.id = :coordenadorId AND c.ativo = TRUE ORDER BY c.nome")
})
@NamedNativeQueries({
     @NamedNativeQuery(name = "Curso.PorNomeSQL", query = "SELECT ID, TXT_NOME, TXT_SIGLA FROM TB_COORDENACAO WHERE TXT_NOME LIKE ? ORDER BY ID", resultClass = Curso.class)
})
@Access(AccessType.FIELD)
public class Curso implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="SEQUENCIA_CURSO")
	private Long id;
	
	@Column (name="BOOL_ATIVO")
	private boolean ativo;
	
	@NotBlank(message = "{mensagem.notnull}{tipo.nome}")
	@Column (name="TXT_NOME")
	private String nome;
	
	@NotBlank(message = "{mensagem.notnull}{tipo.sigla}")
	@Column (name="TXT_SIGLA")
	private String sigla;
	
	@NotBlank(message = "{mensagem.notnull}{tipo.coordenacao}")
	@Column (name="TXT_COORDENACAO")
	private String coordenacao;
	
	@Column (name="TXT_DEPARTAMENTO")
	private String departamento;
	
	@OneToOne (fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "ID_COORDENADOR", referencedColumnName = "ID_USUARIO")
	private Servidor coordenador;

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

	public Servidor getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(Servidor coordenador) {
		this.coordenador = coordenador;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCoordenacao() {
		return coordenacao;
	}

	public void setCoordenacao(String coordenacao) {
		this.coordenacao = coordenacao;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        return (object instanceof Curso) && (id != null) 
             ? id.equals(((Curso) object).getId()) 
             : (object == this);
    }
    
    @Override
    public String toString() {
        return "br.edu.ifpe.monitoria.entidades.Curso[ id=" + id + ":" + nome + " ]";
    }
}
