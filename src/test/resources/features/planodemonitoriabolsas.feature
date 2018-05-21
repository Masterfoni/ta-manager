# language: pt
Funcionalidade: Distribuir bolsas para cursos e planos de monitoria

 Contexto:
	Dado que o usuario esta logado como comissao
	E esteja na pagina de edital
	E gerencie bolsas

  Cenario: Criar um lançamento quando ainda não existir outro criado
	Quando escolher lançar bolsas para um determinado curso
	E nao existir lançamento criado para aquele curso
	Entao o sistema deve criar um novo lançamento de bolsas
	
  Cenario: Criar um lançamento quando já houver outro criado
  	Quando escolher lançar bolsas para um determinado curso
  	E ja existir um lançamento criado para aquele curso
  	Entao o sistema informa que ja existe um esquema criado
	
  Cenario: Distribuir bolsas para cursos num determinado edital
  	Quando houver um lançamento criado para algum curso
  	E o usuario informar uma quantidade de bolsas valida
  	Entao o sistema deve disponibilizar aquela quantidade de bolsas para o curso

	