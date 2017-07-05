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
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

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
	@NamedQuery(name = "Usuario.findByNome", query = "SELECT u FROM Usuario u WHERE u.nome LIKE :nome"),
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

	@NotBlank(message = "{mensagem.notnull}{tipo.cpf}")
	@CPF(message = "{mensagem.cpf}")
	@Column (name="TXT_CPF", unique=true)
	private String cpf;

	@NotBlank(message = "{mensagem.notnull}{tipo.rg}")
	@Column (name="TXT_RG", unique=true)
	private String rg;

	@NotBlank(message = "{mensagem.notnull}{tipo.emissor}")
	@Column (name="TXT_RG_EMISSOR")
	private String rgEmissor;

	@NotBlank(message = "{mensagem.notnull}{tipo.sexo}")
	@Column (name="TXT_SEXO")
	private String sexo;

	@ElementCollection
	@CollectionTable(name = "TB_TELEFONE",
					 joinColumns = @JoinColumn(name="ID_USUARIO", nullable = true))
	private Collection<String> telefones;

	public Collection<String> getTelefones()
	{
		return telefones;
	}

	public void setTelefones(Collection<String> telefones) {
		this.telefones = telefones;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getRgEmissor() {
		return rgEmissor;
	}

	public void setRgEmissor(String rgEmissor) {
		this.rgEmissor = rgEmissor;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Long getId() {
		return id;
	}
}
