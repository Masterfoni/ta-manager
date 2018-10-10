# language: pt
Funcionalidade: Gerar o relatorio de alunos que entregaram as frequencias num determinado mes

 Contexto:
 	Dado que o usuario esta logado como um usuario professor
 	E esteja na homepage
  
  Cenario: Gerar o relatorio final de monitoria
  	Quando selecionar o componente curricular do relatorio final
  	E clicar para gerar o relatorio final
  	Entao o sistema deve mostrar quais monitores entregaram ou nao o relatorio final de monitoria
  	