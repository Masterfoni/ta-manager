# language: pt
Funcionalidade: Distribuir bolsas para planos de monitoria e homologar os planos

 Contexto:
	Dado que o usuario esta logado como coordenador e comissao
	E esteja na pagina de gerencia de planos
  
  Cenario: Distribuir um numero invalido de bolsas para um plano
  	Quando tentar diminuir o numero de bolsas de um plano para um numero invalido
  	Entao o sistema nao deve permitir a alteracao do plano
  	
  Cenario: Distribuir um numero valido de bolsas para um plano
    Quando existirem bolsas disponiveis o suficiente 
    E tentar adicionar um numero valido de bolsas para um plano
  	Entao o sistema deve realizar a distribuicao
  	
  Cenario: Homologar um plano
  	Quando finalizar a distribuicao de bolsas
  	E homologar o plano de monitoria
  	Entao o sistema deve atualizar o status do plano para homologado