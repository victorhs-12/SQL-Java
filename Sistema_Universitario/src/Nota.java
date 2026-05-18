
public class Nota {
	private int pkNota;
	private int fkRgm;
	private String disciplina;
	private String semestre;
	private double nota;
	private int qtdFaltas;
	
	
	
	
	
	public Nota() {
	}



	public Nota(int pkNota, int fkRgm, String disciplina, String semestre, double nota, int qtdFaltas) {
		super();
		this.pkNota = pkNota;
		this.fkRgm = fkRgm;
		this.disciplina = disciplina;
		this.semestre = semestre;
		this.nota = nota;
		this.qtdFaltas = qtdFaltas;
	}



	public int getPkNota() {
		return pkNota;
	}



	public void setPkNota(int pkNota) {
		this.pkNota = pkNota;
	}



	public int getFkRgm() {
		return fkRgm;
	}



	public void setFkRgm(int fkRgm) {
		this.fkRgm = fkRgm;
	}



	public String getDisciplina() {
		return disciplina;
	}



	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}



	public String getSemestre() {
		return semestre;
	}



	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}



	public double getNota() {
		return nota;
	}



	public void setNota(double nota) {
		this.nota = nota;
	}



	public int getQtdFaltas() {
		return qtdFaltas;
	}



	public void setQtdFaltas(int qtdFaltas) {
		this.qtdFaltas = qtdFaltas;
	}
	
	
	
	
	
	
	
}
