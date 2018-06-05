# language: pt
Funcionalidade: Inserção de Notas
  Esse cenario descreve a inserção das notas da selecao para a monitoria
  O professor acessa a listagem dos alunos inscritos no componente curricular em que o mesmo eh o professor 

  Contexto:
	Dado que o usuario esta logado com perfil de professor
	E esteja na pagina de gerencia dos planos de monitoria
	E seleciona a opcao de inserir notas
	
  Cenario: notas diferentes classificacao automatica
  	Quando o professor inserir as notas de selecao e as medias e salvar
  	Entao deve mostrar a classificacao atualizada de cada aluno
  	
  Cenario: aluno possui reprovacao
  	Quando o professor indicar que aluno possui reprovacao e salvar
  	Entao deve desclassificar o aluno
