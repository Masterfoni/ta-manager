package br.edu.ifpe.monitoria.junittests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.edu.ifpe.monitoria.testutils.Dataset;
import br.edu.ifpe.monitoria.testutils.DbUnitUtil;

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
