import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class AlunoDAO {
	public void salvar(Aluno aluno) throws SQLException{
		String sql = "INSERT INTO tb_alunos (pk_rgm, nome_aluno, cpf, email, logradouro, municipio, uf, campus, periodo, data_nascimento, numero_celular, nome_curso) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		
		try(Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setInt(1, aluno.getRgm());
			ps.setString(2, aluno.getNome());
			ps.setString(3, aluno.getCpf());
			ps.setString(4, aluno.getEmail());
			ps.setString(5, aluno.getLogradouro());
			ps.setString(6, aluno.getMunicipio());
			ps.setString(7, aluno.getUf());
			ps.setString(8, aluno.getCampus());
			ps.setString(9, aluno.getPeriodo());
			ps.setDate(10, java.sql.Date.valueOf(aluno.getDataNascimento()));
			ps.setString(11, aluno.getNumeroCelular());
			ps.setString(12, aluno.getNomeCurso());
			
			ps.executeUpdate();
		}
	}
	
	
	public Aluno consultar(int rgm) throws SQLException{
		String sql = "SELECT * FROM tb_alunos WHERE pk_rgm = ?";
		
		try(Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setInt(1, rgm);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				Aluno aluno = new Aluno();
				aluno.setRgm(rs.getInt("pk_rgm"));
				aluno.setNome(rs.getString("nome_aluno"));
				aluno.setCpf(rs.getString("cpf"));
				aluno.setEmail(rs.getString("email"));
				aluno.setLogradouro(rs.getString("logradouro"));
				aluno.setMunicipio(rs.getString("municipio"));
				aluno.setUf(rs.getString("uf"));
				aluno.setCampus(rs.getString("campus"));
				aluno.setPeriodo(rs.getString("periodo"));
				aluno.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
				aluno.setNumeroCelular(rs.getString("numero_celular"));
				aluno.setNomeCurso(rs.getString("nome_curso"));
				
				return aluno;
				
				
			}
		}
		return null;
	}
	
	public void alterar(Aluno aluno) throws SQLException{
		String sql = "UPDATE tb_alunos SET nome_aluno = ?, email = ?, logradouro = ?, municipio = ?, uf = ?, campus = ?, periodo = ?, numero_celular = ?, nome_curso = ? WHERE pk_rgm = ?";
		
		try(Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setString(1, aluno.getNome());
	        ps.setString(2, aluno.getEmail());
	        ps.setString(3, aluno.getLogradouro());
	        ps.setString(4, aluno.getMunicipio());
	        ps.setString(5, aluno.getUf());
	        ps.setString(6, aluno.getCampus());
	        ps.setString(7, aluno.getPeriodo());
	        ps.setString(8, aluno.getNumeroCelular());
	        ps.setString(9, aluno.getNomeCurso());
	        ps.setInt(10, aluno.getRgm());
	        
			ps.executeUpdate();
		}
	}
	
	public void excluir(int rgm) throws SQLException{
		String sql = "DELETE FROM tb_alunos WHERE pk_rgm = ?";
		
		try(Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setInt(1, rgm);
			ps.executeUpdate();
		}
		
	}
}
