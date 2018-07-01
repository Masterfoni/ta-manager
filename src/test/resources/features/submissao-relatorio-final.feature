# language: pt
Funcionalidade: Preencher um relatório final de monitoria como um aluno monitor

 Contexto:
 	Dado que o usuario esta logado como aluno monitor
 	E esteja na pagina de relatorio final
  
  Cenario: Preencher informacoes sobre sua monitoria
  	Quando preencher informacoes sobre atividades desempenhadas
  	E preencher informacoes sobre suas dificuldades
  	E preencher informacoes sobre sugestoes de melhoria
  	E avaliar o orientador salvando o relatorio
  	Entao o sistema deve atualizar o unico relatorio final
  	
  Cenario: Dar uma avaliacao invalida sobre o orientador
  	Quando preencher a avaliacao do orientador
  	E a avaliacao for invalida
  	Entao o sistema deve exibir uma mensagem informando os limites de nota