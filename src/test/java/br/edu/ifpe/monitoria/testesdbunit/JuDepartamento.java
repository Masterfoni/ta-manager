package br.edu.ifpe.monitoria.testesdbunit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JuDepartamento {
	
	public JuDepartamento()
	{
		if(DbUnitUtil.ultimo_executado != Dataset.Departamento)
		{
			DbUnitUtil.selecionaDataset(Dataset.Departamento);
			DbUnitUtil.inserirDados();
		}
	}
	
	@Test
	public void testJackson()
	{
		assertEquals(1, 1);
	}

}
