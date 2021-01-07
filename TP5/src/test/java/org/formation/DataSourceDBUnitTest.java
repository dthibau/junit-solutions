package org.formation;

import static org.dbunit.Assertion.assertEqualsIgnoreCols;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DataSourceDBUnitTest extends DataSourceBasedDBTestCase {

	 
	@Override
	protected DataSource getDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(
          "jdbc:h2:mem:default;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:schema.sql'");
//        dataSource.setURL(
//                "jdbc:h2:~/tmp/test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:schema.sql'");
        dataSource.setUser("sa");
        dataSource.setPassword("sa");
        return dataSource;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		 return new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
		          .getResourceAsStream("data.xml"));
	}
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }
    
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
    @Override
    protected DatabaseOperation getSetUpOperation() {
        return DatabaseOperation.REFRESH;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() {
        return DatabaseOperation.DELETE_ALL;
    }
	@Test
	public void givenDataSetEmptySchema_whenDataSetCreated_thenTablesAreEqual() throws Exception {
	    IDataSet expectedDataSet = getDataSet();
	    ITable expectedTable = expectedDataSet.getTable("fournisseur");
	    IDataSet databaseDataSet = getConnection().createDataSet();
	    ITable actualTable = databaseDataSet.getTable("fournisseur");
	    assertEquals(expectedTable, actualTable);
	}
	
	@Test
	public void testInsert() throws Exception {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("expected-fournisseur.xml")) {
            // given
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
            ITable expectedTable = expectedDataSet.getTable("fournisseur");

            FournisseurDao dao = new FournisseurDao();
            dao.createFournisseur("Test", "TEST");
 
            ITable actualData = getConnection()
                .createQueryTable("result_name", "SELECT * FROM fournisseur WHERE nom='Test'");

            // then
            assertEqualsIgnoreCols(expectedTable, actualData, new String[] { "id" });
        }
	}
	
	@Test
	public void select() throws SQLException {
		JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:default;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("sa");
		Connection conn = dataSource.getConnection();
		ResultSet rs = conn.createStatement()
	            .executeQuery(
	                    "select * from fournisseur");
		while ( rs.next() ) {
			System.out.println(rs.getString("nom"));
		}
		rs.close();
		conn.close();
	}

}
