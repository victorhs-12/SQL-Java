import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelaAcademico extends JFrame {

    private JTabbedPane barraAbas;
    private JPanel abaDados, abaCurso, abaNotas, abaBoletim;

    // Campos Aluno
    private JTextField txtRgm, txtNome, txtEmail, txtEndereco, txtMunicipio;
    private JFormattedTextField txtDataNasc, txtCpf, txtCelular;
    private JComboBox<String> cbUf, cbCurso, cbCampus;
    private JRadioButton rbMatutino, rbVespertino, rbNoturno;
    private ButtonGroup grupoPeriodo;

    // Campos Notas e Faltas
    private JTextField txtRgmNotas, txtNomeAlunoReadOnly, txtCursoAlunoReadOnly, txtFaltas;
    private JComboBox<String> cbDisciplina, cbSemestre, cbNota;

    // Campos Boletim (Refatorados para JTable)
    private JTextField txtRgmBoletim;
    private JLabel lblBoletimNome, lblBoletimRgm, lblBoletimCurso;
    private JTable tabelaBoletim;
    private DefaultTableModel modeloTabelaBoletim;

    // DAOs
    private AlunoDAO alunoDAO = new AlunoDAO();
    private NotaDAO notaDAO = new NotaDAO();
    private DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TelaAcademico() {
        setTitle("Sistema Acadêmico - UNICID");
        setSize(700, 520);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        criarMenuSuperior();

        barraAbas = new JTabbedPane();
        configurarAbaDadosPessoais();
        configurarAbaCurso();
        configurarAbaNotasEFaltas();
        configurarAbaBoletim();

        barraAbas.addTab("Dados Pessoais", abaDados);
        barraAbas.addTab("Curso", abaCurso);
        barraAbas.addTab("Notas e Faltas", abaNotas);
        barraAbas.addTab("Boletim", abaBoletim);

        add(barraAbas, BorderLayout.CENTER);
        criarPainelBotoes();
    }

    private void criarMenuSuperior() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuAluno = new JMenu("Aluno");
        JMenuItem itemSalvar = new JMenuItem("Salvar");
        itemSalvar.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        JMenuItem itemAlterar = new JMenuItem("Alterar");
        JMenuItem itemConsultar = new JMenuItem("Consultar");
        JMenuItem itemExcluir = new JMenuItem("Excluir");
        JMenuItem itemSair = new JMenuItem("Sair");
        
        menuAluno.add(itemSalvar);
        menuAluno.add(itemAlterar);
        menuAluno.add(itemConsultar);
        menuAluno.add(itemExcluir);
        menuAluno.addSeparator();
        menuAluno.add(itemSair);

        JMenu menuNotas = new JMenu("Notas e Faltas");
        JMenuItem itemSalvarNota = new JMenuItem("Salvar");
        JMenuItem itemAlterarNota = new JMenuItem("Alterar");
        JMenuItem itemConsultarNota = new JMenuItem("Consultar");
        JMenuItem itemExcluirNota = new JMenuItem("Excluir");
        
        menuNotas.add(itemSalvarNota);
        menuNotas.add(itemAlterarNota);
        menuNotas.add(itemConsultarNota);
        menuNotas.add(itemExcluirNota);

        JMenu menuAjuda = new JMenu("Ajuda");
        menuAjuda.add(new JMenuItem("Sobre"));

        menuBar.add(menuAluno);
        menuBar.add(menuNotas);
        menuBar.add(menuAjuda);
        setJMenuBar(menuBar);

        itemSair.addActionListener(e -> System.exit(0));
    }

    private void configurarAbaDadosPessoais() {
        abaDados = new JPanel(null);

        JLabel lblRgm = new JLabel("RGM:");
        lblRgm.setBounds(20, 20, 50, 25);
        txtRgm = new JTextField();
        txtRgm.setBounds(80, 20, 120, 25);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(220, 20, 50, 25);
        txtNome = new JTextField();
        txtNome.setBounds(270, 20, 380, 25);

        JLabel lblDataNasc = new JLabel("Data de Nascimento:");
        lblDataNasc.setBounds(20, 60, 130, 25);
        JLabel lblCpf = new JLabel("CPF:");
        lblCpf.setBounds(310, 60, 40, 25);

        try {
            txtDataNasc = new JFormattedTextField(new MaskFormatter("##/##/####"));
            txtDataNasc.setBounds(160, 60, 130, 25);

            txtCpf = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
            txtCpf.setBounds(350, 60, 140, 25);

            txtCelular = new JFormattedTextField(new MaskFormatter("(##)#####-####"));
            txtCelular.setBounds(510, 140, 140, 25);
        } catch (Exception e) { e.printStackTrace(); }

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, 100, 50, 25);
        txtEmail = new JTextField();
        txtEmail.setBounds(80, 100, 570, 25);

        JLabel lblEnd = new JLabel("End.:");
        lblEnd.setBounds(20, 140, 50, 25);
        txtEndereco = new JTextField();
        txtEndereco.setBounds(80, 140, 410, 25);

        JLabel lblCelular = new JLabel("Celular:");
        lblCelular.setBounds(510, 115, 60, 25);

        JLabel lblMunicipio = new JLabel("Município:");
        lblMunicipio.setBounds(20, 180, 70, 25);
        txtMunicipio = new JTextField();
        txtMunicipio.setBounds(90, 180, 250, 25);

        JLabel lblUf = new JLabel("UF:");
        lblUf.setBounds(360, 180, 30, 25);
        cbUf = new JComboBox<>(new String[]{"SP", "RJ", "MG", "PR", "SC", "RS", "GO", "DF"});
        cbUf.setBounds(390, 180, 70, 25);

        abaDados.add(lblRgm); abaDados.add(txtRgm);
        abaDados.add(lblNome); abaDados.add(txtNome);
        abaDados.add(lblDataNasc); abaDados.add(txtDataNasc);
        abaDados.add(lblCpf); abaDados.add(txtCpf);
        abaDados.add(lblEmail); abaDados.add(txtEmail);
        abaDados.add(lblEnd); abaDados.add(txtEndereco);
        abaDados.add(lblCelular); abaDados.add(txtCelular);
        abaDados.add(lblMunicipio); abaDados.add(txtMunicipio);
        abaDados.add(lblUf); abaDados.add(cbUf);
    }

    private void configurarAbaCurso() {
        abaCurso = new JPanel(null);

        JLabel lblCurso = new JLabel("Curso:");
        lblCurso.setBounds(20, 30, 60, 25);
        cbCurso = new JComboBox<>(new String[]{"Analise e Desenvolvimento de Sistemas", "Ciência da Computação", "Engenharia de Software"});
        cbCurso.setBounds(80, 30, 350, 25);

        JLabel lblCampus = new JLabel("Campus:");
        lblCampus.setBounds(20, 80, 60, 25);
        cbCampus = new JComboBox<>(new String[]{"Tatuapé", "Pinheiros", "Anália Franco"});
        cbCampus.setBounds(80, 80, 200, 25);

        JPanel painelPeriodo = new JPanel(new GridLayout(1, 3));
        painelPeriodo.setBorder(BorderFactory.createTitledBorder("Período"));
        painelPeriodo.setBounds(20, 130, 300, 60);

        rbMatutino = new JRadioButton("Matutino");
        rbVespertino = new JRadioButton("Vespertino");
        rbNoturno = new JRadioButton("Noturno");

        grupoPeriodo = new ButtonGroup();
        grupoPeriodo.add(rbMatutino);
        grupoPeriodo.add(rbVespertino);
        grupoPeriodo.add(rbNoturno);

        painelPeriodo.add(rbMatutino);
        painelPeriodo.add(rbVespertino);
        painelPeriodo.add(rbNoturno);

        abaCurso.add(lblCurso); abaCurso.add(cbCurso);
        abaCurso.add(lblCampus); abaCurso.add(cbCampus);
        abaCurso.add(painelPeriodo);
    }

    private void configurarAbaNotasEFaltas() {
        abaNotas = new JPanel(null);

        JLabel lblRgmNotas = new JLabel("RGM:");
        lblRgmNotas.setBounds(20, 20, 40, 25);
        txtRgmNotas = new JTextField();
        txtRgmNotas.setBounds(60, 20, 100, 25);

        txtNomeAlunoReadOnly = new JTextField("deverá mostrar o nome do aluno");
        txtNomeAlunoReadOnly.setBounds(170, 20, 280, 25);
        txtNomeAlunoReadOnly.setEditable(false);
        txtNomeAlunoReadOnly.setForeground(Color.GRAY);

        txtCursoAlunoReadOnly = new JTextField("deverá mostrar o curso do aluno");
        txtCursoAlunoReadOnly.setBounds(20, 55, 430, 25);
        txtCursoAlunoReadOnly.setEditable(false);
        txtCursoAlunoReadOnly.setForeground(Color.GRAY);

        JLabel lblDisciplina = new JLabel("Disciplina:");
        lblDisciplina.setBounds(20, 100, 80, 25);
        cbDisciplina = new JComboBox<>(new String[]{"Programação Orientada a Objetos", "Estrutura de Dados", "Banco de Dados"});
        cbDisciplina.setBounds(100, 100, 350, 25);

        JLabel lblSemestre = new JLabel("Semestre:");
        lblSemestre.setBounds(20, 140, 80, 25);
        cbSemestre = new JComboBox<>(new String[]{"2026-1", "2026-2"});
        cbSemestre.setBounds(100, 140, 100, 25);

        JLabel lblNota = new JLabel("Nota:");
        lblNota.setBounds(220, 140, 40, 25);
        cbNota = new JComboBox<>(new String[]{"0.0", "0.5", "1.0", "5.0", "10.0"}); 
        cbNota.setEditable(true); 
        cbNota.setBounds(260, 140, 60, 25);

        JLabel lblFaltas = new JLabel("Faltas:");
        lblFaltas.setBounds(340, 140, 50, 25);
        txtFaltas = new JTextField();
        txtFaltas.setBounds(390, 140, 60, 25);

        abaNotas.add(lblRgmNotas); abaNotas.add(txtRgmNotas);
        abaNotas.add(txtNomeAlunoReadOnly); abaNotas.add(txtCursoAlunoReadOnly);
        abaNotas.add(lblDisciplina); abaNotas.add(cbDisciplina);
        abaNotas.add(lblSemestre); abaNotas.add(cbSemestre);
        abaNotas.add(lblNota); abaNotas.add(cbNota);
        abaNotas.add(lblFaltas); abaNotas.add(txtFaltas);
    }

    private void configurarAbaBoletim() {
        // Implementação estruturada com JTable e cabeçalho dinâmico
        abaBoletim = new JPanel(new BorderLayout(10, 10));

        // Painel Superior de Busca
        JPanel painelBuscaBoletim = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        painelBuscaBoletim.setBorder(BorderFactory.createTitledBorder("Pesquisa de Relatório Acadêmico"));
        
        painelBuscaBoletim.add(new JLabel("Informe o RGM:"));
        txtRgmBoletim = new JTextField(15);
        painelBuscaBoletim.add(txtRgmBoletim);
        
        JButton btnGerarBoletim = new JButton("Gerar Boletim");
        painelBuscaBoletim.add(btnGerarBoletim);
        
        abaBoletim.add(painelBuscaBoletim, BorderLayout.NORTH);

        // Painel Central (Informações do Aluno + Tabela)
        JPanel painelCentroBoletim = new JPanel(new BorderLayout());
        
        // Cabeçalho com dados do Aluno
        JPanel painelInfoAluno = new JPanel(new GridLayout(3, 1, 5, 5));
        painelInfoAluno.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        lblBoletimRgm = new JLabel("RGM: ");
        lblBoletimNome = new JLabel("Nome do Aluno: ");
        lblBoletimCurso = new JLabel("Curso: ");
        
        lblBoletimRgm.setFont(new Font("Arial", Font.BOLD, 14));
        lblBoletimNome.setFont(new Font("Arial", Font.BOLD, 14));
        lblBoletimCurso.setFont(new Font("Arial", Font.BOLD, 14));
        
        painelInfoAluno.add(lblBoletimRgm);
        painelInfoAluno.add(lblBoletimNome);
        painelInfoAluno.add(lblBoletimCurso);
        painelCentroBoletim.add(painelInfoAluno, BorderLayout.NORTH);

        // Configuração do JTable (Estilo Planilha)
        String[] colunas = {"Disciplina", "Semestre", "Média Final", "Faltas", "Status"};
        modeloTabelaBoletim = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Bloqueia edição direta na tabela
            }
        };
        
        tabelaBoletim = new JTable(modeloTabelaBoletim);
        tabelaBoletim.setRowHeight(25); // Altura agradável para leitura
        tabelaBoletim.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabelaBoletim.getTableHeader().setBackground(new Color(230, 230, 230));
        
        painelCentroBoletim.add(new JScrollPane(tabelaBoletim), BorderLayout.CENTER);
        abaBoletim.add(painelCentroBoletim, BorderLayout.CENTER);

        // Ação exclusiva da busca do boletim
        btnGerarBoletim.addActionListener(e -> acaoGerarBoletim());
    }

    private void criarPainelBotoes() {
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnSalvar = criarBotaoComIcone("Salvar", "save.png");
        JButton btnAlterar = criarBotaoComIcone("Alterar", "edit.png");
        JButton btnConsultar = criarBotaoComIcone("Consultar", "search.png");
        JButton btnExcluir = criarBotaoComIcone("Excluir", "delete.png");
        JButton btnLimpar = criarBotaoComIcone("Limpar", "clear.png");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnConsultar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);

        add(painelBotoes, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> acaoSalvar());
        btnConsultar.addActionListener(e -> acaoConsultar());
        btnAlterar.addActionListener(e -> acaoAlterar());
        btnExcluir.addActionListener(e -> acaoExcluir());
        btnLimpar.addActionListener(e -> limparCamposGlobais());
    }

    private JButton criarBotaoComIcone(String tooltip, String iconName) {
        JButton btn = new JButton();
        btn.setToolTipText(tooltip);
        btn.setPreferredSize(new Dimension(60, 60));
        try {
            ImageIcon icone = new ImageIcon(getClass().getResource("/" + iconName));
            if (icone.getIconWidth() > 0) {
                btn.setIcon(icone);
            } else {
                btn.setText(tooltip);
            }
        } catch (Exception e) {
            btn.setText(tooltip); 
        }
        return btn;
    }

    // ========================================================================
    // LÓGICA DE NEGÓCIO E INTEGRAÇÃO DE BANCO DE DADOS
    // ========================================================================

    private void acaoSalvar() {
        try {
            int index = barraAbas.getSelectedIndex();
            if (index == 3) {
                JOptionPane.showMessageDialog(this, "Ações de salvamento não são aplicáveis na aba de Boletim.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if (index == 0 || index == 1) { 
                Aluno aluno = extrairAlunoDoFormulario();
                alunoDAO.salvar(aluno);
                JOptionPane.showMessageDialog(this, "Aluno salvo com sucesso!");
                limparCamposGlobais(); // Auto-limpeza após inserção
            } else if (index == 2) { 
                Nota nota = extrairNotaDoFormulario();
                notaDAO.salvar(nota);
                JOptionPane.showMessageDialog(this, "Nota salva com sucesso!");
                // Opcional: limpar apenas a nota inserida mantendo o RGM
                cbNota.setSelectedIndex(0);
                txtFaltas.setText("");
            }
        } catch (Exception ex) {
            if (ex.getMessage() != null && ex.getMessage().toLowerCase().contains("duplicate entry")) {
                JOptionPane.showMessageDialog(this, "Atenção: O RGM ou CPF informado já se encontra cadastrado no sistema.", "Dado Duplicado", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void acaoConsultar() {
        try {
            int index = barraAbas.getSelectedIndex();
            // Trava contra buscas fantasmas no Boletim
            if (index == 3) {
                JOptionPane.showMessageDialog(this, "Para consultar o boletim, utilize o botão 'Gerar Boletim' localizado no topo desta aba.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            int rgm = Integer.parseInt(index == 2 ? txtRgmNotas.getText() : txtRgm.getText());
            
            Aluno aluno = alunoDAO.consultar(rgm);
            if (aluno != null) {
                if (index == 0 || index == 1) {
                    preencherFormularioAluno(aluno);
                } else if (index == 2) {
                    txtNomeAlunoReadOnly.setText(aluno.getNome());
                    txtCursoAlunoReadOnly.setText(aluno.getNomeCurso());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Aluno não encontrado!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Informe um RGM válido (Apenas números).", "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao consultar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acaoAlterar() {
        try {
            int index = barraAbas.getSelectedIndex();
            if (index == 3) {
                JOptionPane.showMessageDialog(this, "Ações de alteração não são aplicáveis na aba de Boletim.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if (index == 0 || index == 1) {
                Aluno aluno = extrairAlunoDoFormulario();
                alunoDAO.alterar(aluno);
                JOptionPane.showMessageDialog(this, "Aluno alterado com sucesso!");
                limparCamposGlobais();
            } else if (index == 2) {
                Nota nota = extrairNotaDoFormulario();
                notaDAO.alterar(nota);
                JOptionPane.showMessageDialog(this, "Nota alterada com sucesso!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao alterar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acaoExcluir() {
        try {
            int index = barraAbas.getSelectedIndex();
            if (index == 3) {
                JOptionPane.showMessageDialog(this, "Ações de exclusão não são aplicáveis na aba de Boletim.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            int rgm = Integer.parseInt(txtRgm.getText());
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir o aluno RGM " + rgm + " e todas as suas notas?", "Confirmação", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                alunoDAO.excluir(rgm);
                JOptionPane.showMessageDialog(this, "Aluno excluído com sucesso!");
                limparCamposGlobais();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acaoGerarBoletim() {
        try {
            if (txtRgmBoletim.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, informe um RGM.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int rgm = Integer.parseInt(txtRgmBoletim.getText().trim());
            Aluno aluno = alunoDAO.consultar(rgm);
            
            if (aluno != null) {
                // Atualiza o Cabeçalho
                lblBoletimRgm.setText("RGM: " + aluno.getRgm());
                lblBoletimNome.setText("Nome do Aluno: " + aluno.getNome());
                lblBoletimCurso.setText("Curso: " + aluno.getNomeCurso());
                
                // Limpa a tabela antes de popular
                modeloTabelaBoletim.setRowCount(0);
                
                // Busca as notas e popula o JTable
                List<Nota> notas = notaDAO.consultarporAluno(aluno.getRgm());
                if (notas.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nenhuma nota cadastrada para este aluno.", "Informação", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    for (Nota n : notas) {
                        // Regra visual de Status: Aprovado se Nota >= 6.0
                        String status = (n.getNota() >= 6.0) ? "Aprovado" : "Reprovado";
                        
                        Object[] linha = {
                            n.getDisciplina(),
                            n.getSemestre(),
                            String.format("%.1f", n.getNota()),
                            n.getQtdFaltas(),
                            status
                        };
                        modeloTabelaBoletim.addRow(linha);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum aluno encontrado com este RGM.");
                limparCamposBoletim();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "O RGM deve conter apenas números.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar boletim: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- Métodos Auxiliares de Extração e Limpeza ---

    private void limparCamposGlobais() {
        // Limpando Aba 1: Dados Pessoais
        txtRgm.setText("");
        txtNome.setText("");
        txtDataNasc.setValue(null); txtDataNasc.setText("");
        txtCpf.setValue(null); txtCpf.setText("");
        txtEmail.setText("");
        txtEndereco.setText("");
        txtCelular.setValue(null); txtCelular.setText("");
        txtMunicipio.setText("");
        cbUf.setSelectedIndex(0);

        // Destravando os campos imutáveis para um novo cadastro
        txtCpf.setEditable(true);
        txtDataNasc.setEditable(true);
        txtCpf.setForeground(Color.BLACK);
        txtDataNasc.setForeground(Color.BLACK);

        // Limpando Aba 2: Curso
        cbCurso.setSelectedIndex(0);
        cbCampus.setSelectedIndex(0);
        grupoPeriodo.clearSelection();

        // Limpando Aba 3: Notas e Faltas
        txtRgmNotas.setText("");
        txtNomeAlunoReadOnly.setText("deverá mostrar o nome do aluno");
        txtCursoAlunoReadOnly.setText("deverá mostrar o curso do aluno");
        cbDisciplina.setSelectedIndex(0);
        cbSemestre.setSelectedIndex(0);
        cbNota.setSelectedIndex(0);
        txtFaltas.setText("");

        limparCamposBoletim();
    }

    private void limparCamposBoletim() {
        if (txtRgmBoletim != null) txtRgmBoletim.setText("");
        if (lblBoletimRgm != null) lblBoletimRgm.setText("RGM: ");
        if (lblBoletimNome != null) lblBoletimNome.setText("Nome do Aluno: ");
        if (lblBoletimCurso != null) lblBoletimCurso.setText("Curso: ");
        if (modeloTabelaBoletim != null) modeloTabelaBoletim.setRowCount(0); // Esvazia a tabela
    }

    private Aluno extrairAlunoDoFormulario() {
        Aluno a = new Aluno();
        a.setRgm(Integer.parseInt(txtRgm.getText().trim()));
        a.setNome(txtNome.getText());
        a.setDataNascimento(LocalDate.parse(txtDataNasc.getText(), formatadorData));
        a.setCpf(txtCpf.getText());
        a.setEmail(txtEmail.getText());
        a.setLogradouro(txtEndereco.getText());
        a.setNumeroCelular(txtCelular.getText());
        a.setMunicipio(txtMunicipio.getText());
        a.setUf(cbUf.getSelectedItem().toString());
        a.setNomeCurso(cbCurso.getSelectedItem().toString());
        a.setCampus(cbCampus.getSelectedItem().toString());
        
        if (rbMatutino.isSelected()) a.setPeriodo("Matutino");
        else if (rbVespertino.isSelected()) a.setPeriodo("Vespertino");
        else if (rbNoturno.isSelected()) a.setPeriodo("Noturno");
        else a.setPeriodo("");

        return a;
    }

    private void preencherFormularioAluno(Aluno a) {
        txtNome.setText(a.getNome());
        txtDataNasc.setText(a.getDataNascimento().format(formatadorData));
        txtCpf.setText(a.getCpf());
        txtEmail.setText(a.getEmail());
        txtEndereco.setText(a.getLogradouro());
        txtCelular.setText(a.getNumeroCelular());
        txtMunicipio.setText(a.getMunicipio());
        cbUf.setSelectedItem(a.getUf());
        cbCurso.setSelectedItem(a.getNomeCurso());
        cbCampus.setSelectedItem(a.getCampus());
        
        if ("Matutino".equals(a.getPeriodo())) rbMatutino.setSelected(true);
        else if ("Vespertino".equals(a.getPeriodo())) rbVespertino.setSelected(true);
        else if ("Noturno".equals(a.getPeriodo())) rbNoturno.setSelected(true);

        txtCpf.setEditable(false);
        txtDataNasc.setEditable(false);
        txtCpf.setForeground(Color.GRAY);
        txtDataNasc.setForeground(Color.GRAY);
    }

    private Nota extrairNotaDoFormulario() {
        Nota n = new Nota();
        n.setFkRgm(Integer.parseInt(txtRgmNotas.getText().trim()));
        n.setDisciplina(cbDisciplina.getSelectedItem().toString());
        n.setSemestre(cbSemestre.getSelectedItem().toString());
        n.setNota(Double.parseDouble(cbNota.getSelectedItem().toString().replace(",", ".")));
        n.setQtdFaltas(Integer.parseInt(txtFaltas.getText().trim()));
        return n;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaAcademico().setVisible(true));
    }
}