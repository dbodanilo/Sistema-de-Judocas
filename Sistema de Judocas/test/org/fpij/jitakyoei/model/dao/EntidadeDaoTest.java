package org.fpij.jitakyoei.model.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.fpij.jitakyoei.model.beans.Entidade;
import org.fpij.jitakyoei.util.DatabaseManager;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class EntidadeDaoTest {
	
	private static DAO<Entidade> entidadeDao;
	
	public static void clearDatabase() {
		List<Entidade> entidades = entidadeDao.list();
		
		for (Entidade e : entidades) {
			entidadeDao.delete(e);
		}
		
		assertEquals(0, entidadeDao.list().size());
	}

	@Before
	public void setUp() throws Exception {
		DatabaseManager.setEnviroment(DatabaseManager.TEST);
		
		entidadeDao = new DAOImpl<Entidade>(Entidade.class);
	}
	
	@Test
	public void testSaveEntidade() throws Exception {
		int prevSize = entidadeDao.list().size();
		
		Entidade e = new Entidade();
		e.setNome("saveTest");
		
		entidadeDao.save(e);
		assertEquals(prevSize + 1, entidadeDao.list().size());
		
		Entidade e1 = entidadeDao.get(e);
		assertEquals(true, e1.equals(e));		
	}
	
	@Test
	public void testDeleteEntidade() throws Exception {
		int prevSize = entidadeDao.list().size();
		
		Entidade e = new Entidade();
		e.setNome("deleteTest");
		
		entidadeDao.save(e);
		entidadeDao.delete(e);
		
		assertEquals(prevSize, entidadeDao.list().size());
	}
	
	@Test
	public void testListEntidade() throws Exception {
		clearDatabase();
		
		Entidade es[] = new Entidade[] { new Entidade(), new Entidade(), new Entidade()};

		es[0].setNome("listTest00");
		es[1].setNome("listTest01");
		es[2].setNome("listTest02");
		
		for (Entidade e : es) {
			entidadeDao.save(e);
		}
		
		List<Entidade> e1s = entidadeDao.list();
		assertEquals(es.length, e1s.size());		
		
		for (Entidade e1 : e1s) {
			boolean matchAny = false;
			for (Entidade e : es) {
				if (e.equals(e1)) {
					matchAny = true;
					break;
				}
			}
			assertEquals(true, matchAny);
		}
	}

	@Test
	public void testGetEntidade() {		
		Entidade e = new Entidade();
		e.setNome("getTest");
		
		entidadeDao.save(e);
		Entidade e1 = entidadeDao.get(e);
		
		assertEquals(true, e1.equals(e));
	}

	@Test
	public void testSearchEntidade() {		
		Entidade es[] = new Entidade[] { new Entidade(), new Entidade(), new Entidade()};

		es[0].setNome("searchTest");
		es[1].setNome("searchTest");
		es[2].setNome("searchTest");
		
		for (Entidade e : es) {
			entidadeDao.save(e);
		}
		
		List<Entidade> e1s = entidadeDao.list();
		assertEquals(es.length, e1s.size());
		
		for (Entidade e1 : e1s) {
			assertEquals(true, e1.equals(es[0]));
		}
	}
	
	@AfterClass
	public static void closeDatabase() {
		clearDatabase();
		DatabaseManager.close();
	}
}
