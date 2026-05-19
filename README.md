## 🚀 Tecnologias Utilizadas

* **Linguagem:** Java (JDK 21+)
* **Interface Gráfica:** Java Swing (GUI)
* **Banco de Dados:** MySQL
* **Conectividade:** JDBC (Java Database Connectivity)
* **Padrão de Projeto:** DAO (Data Access Object) e MVC (Model-View-Controller) simplificado.


## 🧠 Decisões de Engenharia e Arquitetura

### 1. Imutabilidade de Dados Sensíveis (Chaves Naturais)
* **O que fizemos:** O CPF e a Data de Nascimento são tratados como dados imutáveis após o cadastro inicial.
* **Por que fizemos:** O CPF é uma chave natural única (`UNIQUE` no banco). Permitir alterações casuais em chaves naturais compromete a rastreabilidade e a auditoria do sistema. Na interface gráfica, após a consulta, esses campos são bloqueados (`setEditable(false)`), prevenindo erros de operação na secretaria.

### 2. Atualização Baseada em Chaves Lógicas (Composite Keys)
* **O que fizemos:** No `UPDATE` de notas (`NotaDAO`), removemos a possibilidade de alterar a *Disciplina* e o *Semestre*. O método utiliza a composição `RGM + Disciplina + Semestre` para localizar e atualizar estritamente os fatos mutáveis: **Nota** e **Faltas**.
* **Por que fizemos:** Alterar o contexto de uma nota (a disciplina ou o semestre) corrompe o histórico acadêmico do aluno. Se o aluno foi matriculado na disciplina errada, o registro deve ser excluído e refeito, garantindo a integridade do histórico (Data Quality).

### 3. Exclusão em Cascata (Integridade Referencial)
* **O que fizemos:** Implementamos a regra `ON DELETE CASCADE` diretamente na *Foreign Key* da tabela `tb_notas` no MySQL.
* **Por que fizemos:** Garante que, ao acionar a exclusão de um Aluno via sistema, não fiquem registros "órfãos" (notas sem aluno) no banco de dados, transferindo a responsabilidade da integridade referencial para o SGBD, onde ela deve estar.

### 4. Tratamento Elegante de Exceções (UX e Segurança)
* **O que fizemos:** Filtros específicos nos blocos `try-catch` da camada View.
* **Por que fizemos:** Em vez de expor o log de erro do MySQL (`SQLIntegrityConstraintViolationException`) para o usuário final quando um RGM ou CPF duplicado é inserido, o sistema captura a exceção e exibe uma mensagem amigável (User Experience), protegendo detalhes da infraestrutura.

* ## ⚙️ Execução do programa
* Executar a classe principal 'TelaAcademico.java'
