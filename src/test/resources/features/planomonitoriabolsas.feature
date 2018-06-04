# language: pt
Funcionalidade: Distribuir bolsas para cursos

 Contexto:
	Dado que o usuario esta logado como comissao
	E esteja na pagina de edital
	E crie um edital valido
  
  Cenario: Definir quantidade de bolsas para um curso
  	Quando gerenciar um edital especifico
  	E definir quantidade de bolsas para um curso
	Entao o sistema deve especificar aquela quantidade para o esquema
  
  Cenario: Criar um lancamento quando ja houver outro criado
    Quando gerenciar um edital especifico
  	E ja existir um lancamento criado para determinado curso
  	E escolher lancar bolsas para aquele curso
  	Entao o sistema informa que ja existe um esquema criado
  	
  Cenario: Definir um edital como vigente sem distribuir bolsas
  	Quando existirem cursos sem bolsas explicitamente lancadas
  	E definir um edital como vigente
  	Entao o sistema deve informar que se deve definir uma quantidade de bolsas
	
