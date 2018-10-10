# language: pt
Funcionalidade: Gerar o relatorio de alunos que entregaram as frequencias num determinado mes

 Contexto:
 	Dado que o usuario esta logado como um professor
 	E esteja na pagina inicial
  
  Cenario: Gerar o relatorio de frequencia mensal para um componente curricular
  	Quando selecionar o mes para a geracao do relatorio
  	E clicar em gerar relatorio
  	Entao o sistema deve mostrar a situacao de entrega de frequencia dos alunos
  	