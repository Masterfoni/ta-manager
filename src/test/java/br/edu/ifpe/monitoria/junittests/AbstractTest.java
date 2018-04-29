package br.edu.ifpe.monitoria.junittests;

import static javax.persistence.PersistenceContextType.TRANSACTION;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;
import com.google.api.client.json.JsonFactory;
import  com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

@RunWith(Arquillian.class)
public abstract class AbstractTest {

	@PersistenceContext(unitName = "monitoria", type = TRANSACTION)
	protected EntityManager entityManager;

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "gem.war")
				.addPackages(true, "br.edu.ifpe.monitoria")
				.addAsResource("META-INF/persistence.xml")
				.addClass(JsonFactory.class)
				.addClass(JacksonFactory.class)
				.addClass(HttpTransport.class)
				.addClass(NetHttpTransport.class)
				.addAsResource("teste.pdf")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	protected <E> void testIfEquals(Collection<E> expected, Collection<E> actual) {
		assertEquals(expected.size(), actual.size());
		expected.stream().forEach(e -> {
			assertTrue(actual.contains(e));
		});
	}

	protected <T> T testIfExists(Class<T> entityClass, Object id) {
		entityManager.clear();
		T entity = entityManager.find(entityClass, id);
		assertNotNull(entity);
		return entity;
	}

	protected <T> T testIfNotExists(Class<T> entityClass, Object id) {
		entityManager.clear();
		T entity = entityManager.find(entityClass, id);
		assertNull(entity);
		return entity;
	}

}
