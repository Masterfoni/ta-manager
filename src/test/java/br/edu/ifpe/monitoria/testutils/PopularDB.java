package br.edu.ifpe.monitoria.testutils;

public class PopularDB {

	public static void main(String[] args) {
		DbUnitUtil.selecionaDataset(Dataset.InsercaoDeNotasCucumber);
		DbUnitUtil.inserirDados();
	}
}
