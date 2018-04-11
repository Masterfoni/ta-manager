# language: pt
Funcionalidade: Aluno se candidata a um plano de monitoria

 Contexto:
	Dado que o usuario esta logado como aluno
	E esteja na pagina de inscricao
	E se inscrever para monitoria em um componente curricular

  Cenário: primeira inscricao em um plano
	Entao o aluno se candidata a monitoria

  Cenário: aluno tenta selecionar mais de um plano
	Entao impede a inscricao 
	E exibe uma mensagem de erro
	