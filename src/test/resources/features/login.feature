# language: pt
Funcionalidade: Realizar login	
  Essa feature descreve tentativas de login com diversos parametros, corretos e incorretos e para diversos tipos de usuario.
  
  Contexto:
    Dado que o usuario esteja na tela de login do sistema
  	E o usuario clica para se logar com o google
  
  Cenario: Usuario logando pela primeira vez
  	Quando utilizar um email e senha corretos do google 
    E o sistema receber a autenticação
    Entao redireciona para a tela de cadastro de servidor ou de aluno
  
  Cenario: Usuario logando o sistema
  	Quando um email do google
    E o sistema verificar que o usuario possui cadastro
    Entao redireciona para a pagina de home do usuario

#	Exemplos:
#	| email							| senha		| home					|
#	| "joaovitor_179@gmail.com"		| "123456"	| "HomeAluno"			|
#	| "fal@a.recife.ifpe.edu.br"	| "1234588" | "HomeProfessor"		|
#	| "jval@a.recife.ifpe.edu.br"	| "8891879" | "HomeComissao"		|

