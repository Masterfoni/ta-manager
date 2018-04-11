# language: pt
Funcionalidade: Criacao de edital
  Esse cenario descreve a criacao de um edital, com o preenchimento incorreto ou correto das informacoes, denotando validacoes pertinentes.

  Contexto:
	Dado que o usuario esta logado com perfil da comissao
	E esteja na pagina de gerencia de editais
	E tenta criar um novo edital

  Cenário: Criar um edital com informacoes validas
	Quando preencher o formulário com informacoes validas
	Entao o sistema deve criar um novo edital

  Cenário: Criar um edital com datas invalidas
	Quando informar uma data final de inscricao menor que a atual
	Entao o sistema exibe uma mensagem de erro
	