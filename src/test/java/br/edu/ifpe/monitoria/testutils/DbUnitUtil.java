package br.edu.ifpe.monitoria.testutils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;

public class DbUnitUtil
{

    private static String XML_FILE = "dbunit/dataset.xml";
    public static Dataset ultimo_executado;

    public static void selecionaDataset(Dataset dataset)
    {
        switch (dataset)
        {
	        case Vazio:
	        	XML_FILE = "dbunit/dataset.xml";
	            ultimo_executado = Dataset.Vazio;
	            break;
	        case EditalCucumber:
	        	XML_FILE = "dbunit/editalCucumber.xml";
	            ultimo_executado = Dataset.EditalCucumber;
	            break;
	        case PlanoMonitoriaCucumber:
	          	XML_FILE = "dbunit/planoMonitoriaCucumber.xml";
	            ultimo_executado = Dataset.PlanoMonitoriaCucumber;
	            break;
	        case PlanoMonitoriaBolsasCucumber:
	        	XML_FILE = "dbunit/planoMonitoriaBolsasCucumber.xml";
	        	ultimo_executado = Dataset.PlanoMonitoriaBolsasCucumber;
	        	break;
	        case CandidaturaCucumber:
	          	XML_FILE = "dbunit/candidaturaCucumber.xml";
	            ultimo_executado = Dataset.CandidaturaCucumber;
	            break;
	        case PlanoMonitoriaDistHomolCucumber:
	        	XML_FILE = "dbunit/planoMonitoriaDistHomolCucumber.xml";
	        	ultimo_executado = Dataset.PlanoMonitoriaDistHomolCucumber;
	        	break;
	        case InsercaoDeNotasCucumber:
	        	XML_FILE = "dbunit/insercaoNotas.xml";
	        	ultimo_executado = Dataset.InsercaoDeNotasCucumber;
	        	break;
	        case SubmissaoRelatorioFinalCucumber:
	        	XML_FILE = "dbunit/submissaoRelatorioFinalCucumber.xml";
	        	ultimo_executado = Dataset.SubmissaoRelatorioFinalCucumber;
	        	break;
        }
    }

    public static void limpaBase(Connection connection)
    {
        try
        {
            Statement stmt = connection.createStatement();

            String sql;
            
            sql = "DELETE FROM TB_RELATORIO_FINAL";
            stmt.executeUpdate(sql);
            sql = "DELETE FROM TB_MONITORIA";
            stmt.executeUpdate(sql);
            sql = "DELETE FROM TB_PLANO_MONITORIA";
            stmt.executeUpdate(sql);
            sql = "DELETE FROM TB_ESQUEMA_BOLSA";
            stmt.executeUpdate(sql);
            sql = "DELETE FROM TB_COMP_CURRICULAR";
            stmt.executeUpdate(sql);
            sql = "DELETE FROM TB_ALUNO";
            stmt.executeUpdate(sql);
            sql = "DELETE FROM TB_CURSO";
            stmt.executeUpdate(sql);
            sql = "DELETE FROM TB_EDITAL";
            stmt.executeUpdate(sql);
            sql = "DELETE FROM TB_USUARIO_GRUPO";
            stmt.executeUpdate(sql);
            sql = "DELETE FROM TB_SERVIDOR";
            stmt.executeUpdate(sql);
            sql = "DELETE FROM TB_PERFILGOOGLE";
            stmt.executeUpdate(sql);
            sql = "DELETE FROM TB_USUARIO";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
        	e.printStackTrace();
        }
    }

    public static void inserirDados()
    {
        Connection conn = null;
        IDatabaseConnection db_conn = null;
        
        try
        {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/monitoria", "postgres", "root");
            db_conn = new DatabaseConnection(conn);
            limpaBase(conn);

            DatabaseConfig dbConfig = db_conn.getConfig();
            dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());

            FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
            builder.setColumnSensing(true);
            IDataSet dataSet = builder.build(new File(XML_FILE));      
            DatabaseOperation.CLEAN_INSERT.execute(db_conn, dataSet);

        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } finally {
            try
            {
                if (conn != null)
                {
                    conn.close();
                }

                if (db_conn != null)
                {
                    db_conn.close();
                }
                
            } catch (SQLException ex) {
                throw new RuntimeException(ex.getMessage(), ex);
            }
        }
    }
}
