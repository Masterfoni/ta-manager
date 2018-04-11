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
            case Departamento:
                XML_FILE = "dbunit/dataset.xml";
                ultimo_executado = Dataset.Departamento;
                break;
            case Usuario:
                XML_FILE = "dbunit/dataset.xml";
                ultimo_executado = Dataset.Usuario;
                break;
        }
    }

    public static void limpaBase(Connection connection)
    {
        try
        {
            Statement stmt = connection.createStatement();

            String sql;
           
            sql = "DELETE FROM tb_usuario";
            stmt.executeUpdate(sql);
            sql = "DELETE FROM tb_departamento";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
        	
        }
    }

    @SuppressWarnings("UseSpecificCatch")
    public static void inserirDados()
    {
        Connection conn = null;
        IDatabaseConnection db_conn = null;
        
        try
        {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/monitoria", "postgres", "root");
            db_conn = new DatabaseConnection(conn);
            limpaBase(conn);
            String schema = db_conn.getSchema();

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
