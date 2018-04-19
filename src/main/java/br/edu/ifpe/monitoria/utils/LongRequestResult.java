package br.edu.ifpe.monitoria.utils;

public class LongRequestResult extends RequestResult 
{
	public Long data;
	
	public LongRequestResult()
	{
		super();
	}
	
	public boolean hasErrors() 
	{
		return !errors.isEmpty();
	}
}
