# language: pt
Funcionalidade: Distribuir bolsas para planos de monitoria e homologar os planos

 Contexto:
	Dado que o usuario esta logado como coordenador
	E esteja na pagina de gerencia de planos
  
  Cenario: Distribuir um numero invalido de bolsas para um plano
  	Quando tentar diminuir o numero de bolsas de um plano para um numero invalido
  	Entao o sistema nao deve permitir a alteracao do plano
  
  Cenario: Distribuir um numero valido de bolsas para um plano
    Quando distribuir um numero valido de bolsas para um plano
  	E existirem bolsas disponiveis o suficiente
  	Entao o sistema deve realizar a distribuicao
  	
  Cenario: Distribuir bolsas para planos nao coordenados
  	Quando existir um componente curricular de um curso nao coordenado
  	Entao o sistema nao deve permitir a distribuicao de bolsas
	
