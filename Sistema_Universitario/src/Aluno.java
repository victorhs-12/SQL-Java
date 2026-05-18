import java.time.LocalDate;
public class Aluno {
	private int rgm;
	private String nome;
	private String cpf;
	private String email;
	private String logradouro;
	private String municipio;
	private String uf;
	private String campus;
	private String periodo;
	private LocalDate dataNascimento;
	private String numeroCelular;
	private String nomeCurso;
	
	
	
	
	public Aluno() {
	}



	public Aluno(int rgm, String nome, String cpf, String email, String logradouro, String municipio, String uf,
			String campus, String periodo, LocalDate dataNascimento, String numeroCelular, String nomeCurso) {
		this.rgm = rgm;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.logradouro = logradouro;
		this.municipio = municipio;
		this.uf = uf;
		this.campus = campus;
		this.periodo = periodo;
		this.dataNascimento = dataNascimento;
		this.numeroCelular = numeroCelular;
		this.nomeCurso = nomeCurso;
	}



	public int getRgm() {
		return rgm;
	}



	public void setRgm(int rgm) {
		this.rgm = rgm;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getCpf() {
		return cpf;
	}



	public void setCpf(String cpf) {
		this.cpf = cpf;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getLogradouro() {
		return logradouro;
	}



	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}



	public String getMunicipio() {
		return municipio;
	}



	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}



	public String getUf() {
		return uf;
	}



	public void setUf(String uf) {
		this.uf = uf;
	}



	public String getCampus() {
		return campus;
	}



	public void setCampus(String campus) {
		this.campus = campus;
	}



	public String getPeriodo() {
		return periodo;
	}



	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}



	public LocalDate getDataNascimento() {
		return dataNascimento;
	}



	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}



	public String getNumeroCelular() {
		return numeroCelular;
	}



	public void setNumeroCelular(String numeroCelular) {
		this.numeroCelular = numeroCelular;
	}



	public String getNomeCurso() {
		return nomeCurso;
	}



	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}
	
	
	
}
