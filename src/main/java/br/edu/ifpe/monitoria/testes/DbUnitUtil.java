package br.edu.ifpe.monitoria.testes;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.omg.CORBA.Environment;

public class DbUnitUtil {

	private static final String XML_FILE = "/dbunit/dataset.xml";
	
	 public static void inserirDados() {
		 Connection conn = null;
	     IDatabaseConnection db_conn = null;
	     try {
	    	 conn = DriverManager.getConnection(
	                    "jdbc:postgresql://localhost:5432/monitoria", "postgres", "root");
	         db_conn = new DatabaseConnection(conn);
	         
	         FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
	         builder.setColumnSensing(true);
	         
	         InputStream in = DbUnitUtil.class.getResourceAsStream(XML_FILE);
	         
	         IDataSet dataSet = builder.build(in);
	         
	         DatabaseOperation.CLEAN_INSERT.execute(db_conn, dataSet);
	     } catch (Exception ex) {
	            throw new RuntimeException(ex.getMessage(), ex);
	        } finally {
	            try {
	                if (conn != null) {
	                    conn.close();
	                }
	                if (db_conn != null) {
	                    db_conn.close();
	                }
	            } catch (SQLException ex) {
	                throw new RuntimeException(ex.getMessage(), ex);
	            }
	        }
	    }
	
}
