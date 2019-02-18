package br.edu.ifpe.monitoria.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.edu.ifpe.monitoria.entidades.Servidor;
import br.edu.ifpe.monitoria.localbean.ServidorLocalBean;
import br.edu.ifpe.monitoria.localbean.UsuarioLocalBean;
import br.edu.ifpe.monitoria.utils.SessionContext;

@ManagedBean (name="gerenciaComissaoView")
@ViewScoped
public class GerenciaComissaoView implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UsuarioLocalBean usuariobean;
	
	@EJB
	private ServidorLocalBean servidorBean;

	public List<Servidor> servidores;
	
	public Servidor loggedServidor;
	
	public String nomeBusca;
	
	public GerenciaComissaoView() {
		nomeBusca = "";
	}
	
	@PostConstruct
	public void init() {
		loggedServidor = servidorBean.consultaServidorById((Long)SessionContext.getInstance().getAttribute("id"));
		
		buscaServidores();
	}
	
	public void buscaServidores() {
		if(nomeBusca.isEmpty()) {
			this.servidores = servidorBean.consultaServidores();
		} else {
			//this.servidores = servidorBean.consultaServidoresByName("%"+nomeBusca+"%");			
		}
		
		this.servidores.remove(loggedServidor);
	}
	
	public void toggleGrupo(Servidor servidor) {
		if(checkComissao(servidor)) {
			usuariobean.revokeComissao(servidor);
		} else {
			usuariobean.grantComissao(servidor);
		}
	}
	
	public boolean checkComissao(Servidor servidor) {
		return usuariobean.checaComissao(servidor);
	}

	public List<Servidor> getServidores() {
		return servidores;
	}

	public void setServidores(List<Servidor> servidores) {
		this.servidores = servidores;
	}

	public String getNomeBusca() {
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca) {
		this.nomeBusca = nomeBusca;
	}

}
