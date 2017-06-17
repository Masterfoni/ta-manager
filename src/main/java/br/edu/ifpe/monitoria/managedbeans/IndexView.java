package br.edu.ifpe.monitoria.managedbeans;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import br.edu.ifpe.monitoria.entidades.Aluno;
import br.edu.ifpe.monitoria.entidades.Usuario;
import br.edu.ifpe.monitoria.localBean.AlunoLocalBean;
import br.edu.ifpe.monitoria.localBean.UsuarioLocalBean;

@ManagedBean (name="indexView")
public class IndexView implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UsuarioLocalBean usuarioBean;

	@EJB
	private AlunoLocalBean alunoBean;

	private Usuario usuario;

	private Aluno aluno;

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public IndexView() {
		usuario = new Usuario();
		aluno = new Aluno();
	}

	public void cadastrarAluno()
	{
		if(alunoBean.persisteAluno(aluno))
		{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Cadastro realizado com sucesso!"));
		}
	}

	public void loginUsuario()
	{
		Usuario usuarioLogado = usuarioBean.consultaUsuarioPorEmailSenha(usuario.getEmail(), usuario.getSenha());

		if(usuarioLogado.getId() == null)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
																				"Usuario ou senha incorretos.", null));
		}
		else
		{
			try {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/homepage.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
