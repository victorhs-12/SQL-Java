import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;


public class NotaDAO {
	
	public void salvar(Nota nota) throws SQLException {
		String sql = "INSERT INTO tb_notas (fk_rgm, disciplina, semestre, nota, qtd_faltas) VALUES ( ?, ?, ?, ?, ?)";
		
		try(Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setInt(1, nota.getFkRgm());
			ps.setString(2, nota.getDisciplina());
			ps.setString(3, nota.getSemestre());
			ps.setDouble(4, nota.getNota());
			ps.setInt(5, nota.getQtdFaltas());
			
			ps.executeUpdate();
		}
	}
	
	
	public List<Nota> consultarporAluno(int rgm) throws SQLException{
		List<Nota> notas = new ArrayList<>();
		String sql = "SELECT * FROM tb_notas WHERE fk_rgm = ?";

		try(Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setInt(1, rgm);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Nota nota = new Nota();
				nota.setPkNota(rs.getInt("pk_nota"));
				nota.setFkRgm(rs.getInt("fk_rgm"));
				nota.setDisciplina(rs.getString("disciplina"));
				nota.setSemestre(rs.getString("semestre"));
				nota.setNota(rs.getDouble("nota"));
				nota.setQtdFaltas(rs.getInt("qtd_faltas"));
				
				notas.add(nota);
			}
			
		}
		return notas;
		
		
	}
	
	public void alterar(Nota nota) throws SQLException{
		String sql = "UPDATE tb_notas SET nota = ?, qtd_faltas = ? WHERE fk_rgm = ? AND disciplina = ? AND semestre = ?";
		
		try(Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setDouble(1, nota.getNota());
	        ps.setInt(2, nota.getQtdFaltas());
	        ps.setInt(3, nota.getFkRgm());
	        ps.setString(4, nota.getDisciplina());
	        ps.setString(5, nota.getSemestre());
			
			ps.executeUpdate();
		}
		
	}
	
	public void excluir(int pkNota) throws SQLException{
		String sql = "DELETE FROM tb_notas WHERE pk_nota = ?";
		
		try(Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setInt(1, pkNota);
			ps.executeUpdate();
		}
	}
	

	
}
