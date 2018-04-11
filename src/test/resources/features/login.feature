# language: pt
Funcionalidade: Realizar login	
  Essa feature descreve tentativas de login com diversos parametros, corretos e incorretos e para diversos tipos de usuario.
  
  Contexto:
    Dado que o usuario esteja na tela de login do sistema
  	E o usuario informa o email e senha
  
  Cenário: Servidor logando pela primeira vez
  	Quando utilizar o email instituncional 
    E o sistema verificar que o usuario nao esta cadastrado
    Então redireciona para a tela de cadastro de servidor
  
  Cenário: Servidor logando o sistema
  	Quando utilizar o email instituncional
    E o sistema verificar que o usuario possui cadastro
    Então redireciona para a pagina de home do servidor

  Cenário: Aluno realiza login
	E o sistema autentica o usuario
	Então redireciona para a pagina home do aluno

  Cenário: Aluno tenta realizar login com credenciais invalidas
    Quando informar um email e ou senha invalida
	E sistema nao autentica o usuario
	Então informa mensagem de erro

#	Exemplos:
#	| email							| senha		| home					|
#	| "joaovitor_179@hotmail.com"	| "123456"	| "HomeAluno"			|
#	| "fal@a.recife.ifpe.edu.br"	| "1234588" | "HomeProfessor"		|
#	| "jval@a.recife.ifpe.edu.br"	| "8891879" | "HomeComissao"		|

