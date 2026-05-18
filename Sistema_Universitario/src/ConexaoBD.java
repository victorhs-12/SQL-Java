import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

//conexão do banco de dados e do driver do SQL

public class ConexaoBD {
	private static final String usuario = "root";
    private static final String senha = "Arrow1212$";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/sistema_universitario";

    private static final String Driver = "com.mysql.cj.jdbc.Driver";
    private static Connection conexao = null;

    public static Connection getConnection() throws SQLException{
    	if(conexao == null || conexao.isClosed()) {
    		try {
    			Class.forName(Driver);
    			conexao = DriverManager.getConnection(URL, usuario, senha);
    		}catch(ClassNotFoundException e) {
    			throw new SQLException("Driver não encontrado: " + e.getMessage());
    		}
 
        
    	}
    	
    	return conexao;
    }
}
