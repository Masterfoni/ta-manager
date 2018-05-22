# language: pt
Funcionalidade: Criacao de edital
  Esse cenario descreve a criacao de um edital, com o preenchimento incorreto ou correto das informacoes, denotando validacoes pertinentes.

  Contexto:
	Dado que o usuario esta logado com perfil da comissao
	E esteja na pagina de gerencia de editais
	E tenta criar um novo edital

  Cenario: Criar um edital com informacoes validas
	Quando preencher o formulario com informacoes validas
	Entao o sistema deve criar um novo edital

  Cenario: Criar um edital com datas invalidas
	Quando informar periodos de datas inconsistentes
	Entao o sistema informa que a data final de um periodo nao pode ser antes do inicio 
	