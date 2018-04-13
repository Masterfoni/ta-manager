package br.edu.ifpe.monitoria.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="TB_SERVIDOR")
@DiscriminatorValue(value="SERVIDOR")
@PrimaryKeyJoinColumn(name="ID_USUARIO", referencedColumnName="ID")
@NamedQueries({
	@NamedQuery(name = "Servidor.findAll", query = "SELECT s FROM Servidor s")
})
public class Servidor extends Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "{mensagem.notnull}{tipo.siape}")
	@Max(9999999)
	@Column (name="INT_SIAPE")
	private Integer siape;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TXT_TITULACAO")
	private Titulacao titulacao;
	
	public enum Titulacao {
		GRADUAÇÃO("Graduado"),
		ESPECIALIZAÇÃO("Especialista"),
		MESTRADO("Mestre"),
		DOUTORADO("Doutor");
		
		private String label;
		
		private Titulacao(String label) {
			this.label = label;
		}
		
		public String getLabel() {
			return label;
		}
	}

	public Integer getSiape() {
		return siape;
	}

	public void setSiape(Integer siape) {
		this.siape = siape;
	}

	public Titulacao getTitulacao() {
		return titulacao;
	}

	public void setTitulacao(Titulacao titulacao) {
		this.titulacao = titulacao;
	}
	
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        return (object instanceof Servidor) && (id != null) 
             ? id.equals(((Servidor) object).getId()) 
             : (object == this);
    }
    
    @Override
    public String toString() {
        return "br.edu.ifpe.monitoria.entidades.Servidor[ id=" + id + ":" + nome + " ]";
    }
}
