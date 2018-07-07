# language: pt
Funcionalidade: Submeter uma atividade na ata mensal como um aluno monitor

 Contexto:
 	Dado que o usuario esta logado como aluno monitor de um componente
 	E esteja na pagina minha monitoria
  
  Cenario: Registrar uma atividade valida
  	Quando clicar em registrar atividade
  	E preencher com as informacoes da atividade
  	E clicar em registrar
  	Entao o sistema deve registrar a atividade
  	
  Cenario: Registrar atividade em que a data eh fora do periodo de monitoria
  	Quando clicar em registrar atividade
  	E preencher com uma data fora do periodo de monitoria
  	E clicar em registrar
  	Entao o sistema deve exibir a mensagem de data fora do periodo
  	
  Cenario: Registrar atividade em que a hora de inicio da atividade eh antes do fim
  	Quando clicar em registrar atividade
  	E preencher com horas inconsistentes
  	E clicar em registrar
  	Entao o sistema deve exibir a mensagem de hora errada
