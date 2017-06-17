package br.edu.ifpe.monitoria.entidades;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

@Entity
@SequenceGenerator (name = "SEQUENCIA_USUARIO",
					sequenceName = "SQ_USUARIO",
					initialValue = 1,
					allocationSize = 1)
@Table(name="TB_USUARIO")
@Inheritance (strategy = InheritanceType.JOINED)
@DiscriminatorColumn (name="DISC_USUARIO", discriminatorType = DiscriminatorType.STRING, length=20)
@Access(AccessType.FIELD)
@NamedQueries({
	@NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
	@NamedQuery(name = "Usuario.findById", query = "SELECT u FROM Usuario u WHERE u.id = :id"),
	@NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email"),
	@NamedQuery(name = "Usuario.findByNome", query = "SELECT u FROM Usuario u WHERE u.nome = :nome"),
	@NamedQuery(name = "Usuario.findByEmailSenha", query = "SELECT u FROM Usuario u WHERE u.email = :email AND u.senha = :senha")
})
public class Usuario implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="SEQUENCIA_USUARIO")
	private Long id;
	
	@NotNull(message = "{mensagem.notnull}{tipo.nome}")
	@Column (name="TXT_NOME")
	private String nome;
	
	@NotNull(message = "{mensagem.notnull}{tipo.email}")
	@Email(message = "E-mail inválido.")
	@Column(name="TXT_EMAIL", unique = true)
	private String email;
	
	@NotNull(message = "{mensagem.notnull}{tipo.senha}")
	@Column (name="TXT_SENHA")
	private String senha;
	
	@ElementCollection
	@CollectionTable(name = "TB_TELEFONE",
					 joinColumns = @JoinColumn(name="ID_USUARIO", nullable = true))
	private Collection<String> telefones;
	
	public Collection<String> getTelefones() 
	{
		return telefones;
	}
	
	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}

	public String getNome() 
	{
		return nome;
	}

	public void setNome(String nome) 
	{
		this.nome = nome;
	}

	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
