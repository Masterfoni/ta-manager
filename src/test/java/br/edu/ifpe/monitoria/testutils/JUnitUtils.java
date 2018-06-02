package br.edu.ifpe.monitoria.testutils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

public class JUnitUtils {

	public static EJBContainer buildContainer() throws Exception 
	{
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(EJBContainer.MODULES, new File("target/classes"));
//		properties.put("org.glassfish.ejb.embedded.glassfish.installation.root", "C:/Program Files/glassfish4/glassfish");
//		properties.put("org.glassfish.ejb.embedded.glassfish.configuration.file", "C:/Program Files/glassfish4/glassfish/domains/domain1/config/domain.xml");
		properties.put("org.glassfish.ejb.embedded.glassfish.installation.root", "C:/glassfish4/glassfish");
		properties.put("org.glassfish.ejb.embedded.glassfish.configuration.file", "C:/glassfish4/glassfish/domains/domain1/config/domain.xml");
		
		return EJBContainer.createEJBContainer(properties);
	}
	
	public static Object getLocalBean(EJBContainer container, String beanClassName) throws NamingException
	{
		return container.getContext().lookup("java:global/classes/"+beanClassName);
	}
}
