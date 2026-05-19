import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {

	public static void main(String[] args) {
		System.out.println("Testando conexão...");
        
        try {
            Connection conn = ConexaoBD.getConnection();
            
            if (conn != null) {
                System.out.println("✅ Conexão bem-sucedida!");
                System.out.println("Banco: " + conn.getCatalog());
                conn.close();
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Erro na conexão: " + e.getMessage());
        }
    }



}
