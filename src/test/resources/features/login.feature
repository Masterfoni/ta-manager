# language: pt
Funcionalidade: Realizar login	
  Essa feature descreve tentativas de login com diversos parametros, corretos e incorretos e para diversos tipos de usuario.
  
  Contexto:
    Dado que o usuario esteja na tela de login do sistema
  	E o usuario informa o email e senha
  
  Cenario: Servidor logando pela primeira vez
  	Quando utilizar o email instituncional 
    E o sistema verificar que o usuario nao esta cadastrado
    Entao redireciona para a tela de cadastro de servidor
  
  Cenario: Servidor logando o sistema
  	Quando utilizar o email instituncional
    E o sistema verificar que o usuario possui cadastro
    Entao redireciona para a pagina de home do servidor

  Cenario: Aluno realiza login
	E o sistema autentica o usuario
	Entao redireciona para a pagina home do aluno

  Cenario: Aluno tenta realizar login com credenciais invalidas
    Quando informar um email e ou senha invalida
	E sistema nao autentica o usuario
	Entao informa mensagem de erro

#	Exemplos:
#	| email							| senha		| home					|
#	| "joaovitor_179@hotmail.com"	| "123456"	| "HomeAluno"			|
#	| "fal@a.recife.ifpe.edu.br"	| "1234588" | "HomeProfessor"		|
#	| "jval@a.recife.ifpe.edu.br"	| "8891879" | "HomeComissao"		|

