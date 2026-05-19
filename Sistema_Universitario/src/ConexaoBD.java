import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

//conexão feita com os dados ocultados (config.properties)
public class ConexaoBD {
    private static Connection conexao = null;

    public static Connection getConnection() throws SQLException {
        if (conexao == null || conexao.isClosed()) {
            try {
                Properties props = new Properties();
                props.load(new FileInputStream("config.properties"));

                String url = props.getProperty("db.url");
                String usuario = props.getProperty("db.usuario");
                String senha = props.getProperty("db.senha");

                Class.forName("com.mysql.cj.jdbc.Driver");
                conexao = DriverManager.getConnection(url, usuario, senha);

            } catch (ClassNotFoundException | IOException e) {
                throw new SQLException("Erro ao conectar: " + e.getMessage());
            }
        }
        return conexao;
    }
}