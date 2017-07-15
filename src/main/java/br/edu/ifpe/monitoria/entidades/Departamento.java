package br.edu.ifpe.monitoria.entidades;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@SequenceGenerator (name = "SEQUENCIA_DEPARTAMENTO",
		sequenceName = "SQ_DEPARTAMENTO",
		initialValue = 1,
		allocationSize = 1)
@Table(name="TB_DEPARTAMENTO")
@NamedQueries({
	@NamedQuery(name = "Departamento.findAll", query = "SELECT d FROM Departamento d"),
	@NamedQuery(name = "Departamento.findById", query = "SELECT d FROM Departamento d WHERE d.id = :id"),
    @NamedQuery(name = "Departamento.findBySigla", query = "SELECT d FROM Departamento d WHERE d.sigla LIKE :sigla"),
    @NamedQuery(name = "Departamento.findByNome", query = "SELECT d FROM Departamento d WHERE d.nome LIKE :nome")
})
@NamedNativeQueries({
     @NamedNativeQuery(name = "Departamento.PorNomeSQL", query = "SELECT ID, TXT_NOME, TXT_SIGLA FROM TB_DEPARTAMENTO WHERE TXT_NOME LIKE ? ORDER BY ID", resultClass = Departamento.class)
})
@Access(AccessType.FIELD)
public class Departamento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="SEQUENCIA_DEPARTAMENTO")
	private Long id;
	
	@NotBlank(message = "{mensagem.notnull}{tipo.nome}")
	@Column (name="TXT_NOME")
	private String nome;
	
	@NotBlank(message = "{mensagem.notnull}{tipo.sigla}")
	@Column (name="TXT_SIGLA", unique = true)
	private String sigla;

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

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
}
