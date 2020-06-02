
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConexao {
    private static final String URL = "jdbc:mysql://200.132.195.111/MorseCristian";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USUARIO = "aluno";
    private static final String SENHA = "aluno";
    
    public Statement getConnection() throws SQLException {
        try {
            Class.forName( DRIVER );
            
            Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
            return conn.createStatement();
        }
        catch (ClassNotFoundException e) {
            throw new SQLException(e.getMessage());
        }
    }
}
