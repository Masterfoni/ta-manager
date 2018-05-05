package br.edu.ifpe.monitoria.testutils;

public class LimparDB {
	public static void main(String [] args) {
		if(DbUnitUtil.ultimo_executado != Dataset.Vazio) {
			 DbUnitUtil.selecionaDataset(Dataset.Vazio);
	         DbUnitUtil.inserirDados();
		}
	}
}
