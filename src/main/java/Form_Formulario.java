
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableModel;

public class Form_Formulario extends javax.swing.JFrame {

    Statement st;
    DefaultTableModel modelo;
    private int CodigoEditar = 0;
    private String CodigoPaciente = "";
    private Date Data;

    private String Codigo;
    private String Nome;
    private Date DataNasc;
    private int Resultado1;
    private int Resultado2;
    private int Resultado3;
    private int Resultado4;
    private int Resultado5;
    private int Resultado6;
    private int ResultadoFinal;
    private String ResultadoFinalDescricao;

    public Form_Formulario() {
        initComponents();
        this.setLocationRelativeTo(null);
        try {
            st = new DBConexao().getConnection();
        } catch (Exception e) {
            System.out.println("Error: " + e.toString() + e.getMessage());
        }

        jtfData.setText(Utilidades.DateToString(new Date()));

        ListarPacientes();
        jtTabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CodigoPaciente = jtTabela.getValueAt(jtTabela.getSelectedRow(), 0).toString();
                System.out.println("CodigoPaciente = " + CodigoPaciente);
            }
        });

    }

    public Form_Formulario(int codigo) {
        initComponents();
        this.setLocationRelativeTo(null);
        CodigoEditar = codigo;

        try {
            st = new DBConexao().getConnection();
        } catch (Exception e) {
            System.out.println("Error: " + e.toString() + e.getMessage());
        }
        BuscaValorEditar();
        ListarPacientes();
        jtTabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CodigoPaciente = jtTabela.getValueAt(jtTabela.getSelectedRow(), 0).toString();
                System.out.println("CodigoPaciente = " + CodigoPaciente);
            }
        });
    }

    public void BuscaValorEditar() {
        try {
            String sql = "SELECT codigo_paciente, DATE_FORMAT(data, '%d/%m/%Y') as data, resultado1, resultado2, resultado3, resultado4, resultado5, resultado6 FROM Formulario "
                    + " WHERE codigo=" + CodigoEditar;
            System.out.println(sql);
            ResultSet rec = st.executeQuery(sql);
            while (rec.next()) {
                CodigoPaciente = rec.getString("codigo_paciente");
                jtfData.setText(rec.getString("data"));
                SetarValorRadioButton(buttonGroup1, rec.getString("resultado1"));
                SetarValorRadioButton(buttonGroup2, rec.getString("resultado2"));
                SetarValorRadioButton(buttonGroup3, rec.getString("resultado3"));
                SetarValorRadioButton(buttonGroup4, rec.getString("resultado4"));
                SetarValorRadioButton(buttonGroup5, rec.getString("resultado5"));
                SetarValorRadioButton(buttonGroup6, rec.getString("resultado6"));
            }
        } catch (SQLException s) {
            JOptionPane.showMessageDialog(this, "Erro ao listar!! " + s.toString());
        }
    }

    private void ListarPacientes() {
        String colunas[] = {"Cod.", "Paciente"};
        modelo = new DefaultTableModel(colunas, 0);
        int codigoPacienteTabela = 0;

        try {
            String sql = "SELECT codigo, nome FROM Paciente ";

            ResultSet rec = st.executeQuery(sql);
            while (rec.next()) {
                String codigo = rec.getString("codigo");
                String nome = rec.getString("nome");

                if (rec.getString("codigo").equals(CodigoPaciente)) {
                    codigoPacienteTabela = modelo.getRowCount();
                }

                modelo.addRow(new Object[]{codigo, nome});
            }
        } catch (SQLException s) {
            JOptionPane.showMessageDialog(this, "Erro ao listar!! " + s.toString());
        }
        jtTabela.setModel(modelo);
        jtTabela.setRowSelectionInterval(codigoPacienteTabela, codigoPacienteTabela);
    }

    private void BuscaValorCampos() {
        Data = Utilidades.StringToDate(jtfData.getText());
        Data = new java.sql.Date(Data.getTime());
        Resultado1 = Integer.valueOf(buttonGroup1.getSelection().getActionCommand());
        Resultado2 = Integer.valueOf(buttonGroup2.getSelection().getActionCommand());
        Resultado3 = Integer.valueOf(buttonGroup3.getSelection().getActionCommand());
        Resultado4 = Integer.valueOf(buttonGroup4.getSelection().getActionCommand());
        Resultado5 = Integer.valueOf(buttonGroup5.getSelection().getActionCommand());
        Resultado6 = Integer.valueOf(buttonGroup6.getSelection().getActionCommand());
        ResultadoFinal = Resultado1 + Resultado2 + Resultado3 + Resultado4 + Resultado5 + Resultado6;
        ResultadoFinalDescricao = RetornaMensagemExame(Integer.valueOf(ResultadoFinal));
    }

    public String RetornaMensagemExame(int resultado) {
        if (resultado <= 24) {
            return "Baixo Risco";
        } else if (resultado <= 44) {
            return "Risco Moderado";
        } else {
            return "Risco Elevado";
        }
    }

    public void Alterar() {
        BuscaValorCampos();

        String sql = "UPDATE Formulario SET data='" + Data + "', resultado1=" + Resultado1 + ", resultado2=" + Resultado2 + ", resultado3=" + Resultado3 + ", resultado4=" + Resultado4 + ", resultado5=" + Resultado5 + ", resultado6=" + Resultado6 + ", resultadofinal=" + ResultadoFinal + ", resultadofinaldescricao='" + ResultadoFinalDescricao + "', codigo_paciente=" + CodigoPaciente + "";
        String where = " WHERE codigo=" + CodigoEditar;
        sql += where;
        System.out.println(sql);
        try {
            int resp = st.executeUpdate(sql);
            if (resp == 1) {
                new List_Formulario().setVisible(true);
                this.dispose();
                JOptionPane.showMessageDialog(this, "Formulário alterado com sucesso!!");
            }
        } catch (SQLException s) {
            JOptionPane.showMessageDialog(this, "Informações não alteradas!! " + s.toString());
        }
    }

    public void Incluir() {
        BuscaValorCampos();

        String sql = "INSERT INTO Formulario (data, resultado1, resultado2, resultado3, resultado4, resultado5, resultado6, resultadofinal, resultadofinaldescricao, codigo_paciente)";
        String values = " VALUES ('" + Data + "', " + Resultado1 + ", " + Resultado2 + ", " + Resultado3 + ", " + Resultado4 + ", " + Resultado5 + ", " + Resultado6 + ", " + ResultadoFinal + ", '" + ResultadoFinalDescricao + "', " + CodigoPaciente + ")";
        sql += values;
        System.out.println(sql);
        try {
            int resp = st.executeUpdate(sql);
            if (resp == 1) {
                new List_Formulario().setVisible(true);
                this.dispose();
                JOptionPane.showMessageDialog(this, "Formulário incluido com sucesso!!");
            }
        } catch (SQLException s) {
            JOptionPane.showMessageDialog(this, "Informações não incluida!! " + s.toString());
        }
    }

    public void SetarValorRadioButton(ButtonGroup buttonGroup, String procuraValor) {
        //Convert Enumeration to a List
        List<AbstractButton> listRadioButton = Collections.list(buttonGroup.getElements());
        System.out.println("tamanho = " + listRadioButton.size());
        //show the list of JRadioButton
        for (AbstractButton button : listRadioButton) {
            if (button.getActionCommand().equals(procuraValor)) {
                System.out.println("------------------------------------");
                System.out.println("Texto = " + ((JRadioButton) button).getText());
                System.out.println("Is selectd = " + button.isSelected());
                System.out.println("Valor = " + button.getActionCommand());
                System.out.println("------------------------------------");
                button.setSelected(true);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        jrbQ1O9 = new javax.swing.JRadioButton();
        jrbQ1O10 = new javax.swing.JRadioButton();
        jbFinalizar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jrbQ1O6 = new javax.swing.JRadioButton();
        jrbQ1O7 = new javax.swing.JRadioButton();
        jrbQ1O8 = new javax.swing.JRadioButton();
        jrbQ1O11 = new javax.swing.JRadioButton();
        jrbQ1O12 = new javax.swing.JRadioButton();
        jrbQ1O13 = new javax.swing.JRadioButton();
        jrbQ1O1 = new javax.swing.JRadioButton();
        jrbQ1O14 = new javax.swing.JRadioButton();
        jrbQ1O15 = new javax.swing.JRadioButton();
        jrbQ1O16 = new javax.swing.JRadioButton();
        jrbQ1O2 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jrbQ1O3 = new javax.swing.JRadioButton();
        jrbQ1O4 = new javax.swing.JRadioButton();
        jrbQ1O5 = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jtfData = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtTabela = new javax.swing.JTable();
        jbMenu = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        jLabel3.setText("Data:");

        jLabel2.setText("Nome:");

        buttonGroup1.add(jrbQ1O9);
        jrbQ1O9.setText("Sim");

        buttonGroup1.add(jrbQ1O10);
        jrbQ1O10.setText("Não");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastrar/Alterar Formulario");

        jbFinalizar.setText("Salvar");
        jbFinalizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbFinalizarMouseClicked(evt);
            }
        });

        buttonGroup4.add(jrbQ1O6);
        jrbQ1O6.setSelected(true);
        jrbQ1O6.setText("Não");
        jrbQ1O6.setActionCommand("0");

        buttonGroup3.add(jrbQ1O7);
        jrbQ1O7.setSelected(true);
        jrbQ1O7.setText("Nenhum/Acamado/Repouso leito");
        jrbQ1O7.setActionCommand("0");

        buttonGroup3.add(jrbQ1O8);
        jrbQ1O8.setText("Bengala/Muleta");
        jrbQ1O8.setActionCommand("15");

        buttonGroup6.add(jrbQ1O11);
        jrbQ1O11.setSelected(true);
        jrbQ1O11.setText("Orientado");
        jrbQ1O11.setActionCommand("0");

        buttonGroup6.add(jrbQ1O12);
        jrbQ1O12.setText("Desorientado/Confuso");
        jrbQ1O12.setActionCommand("15");

        buttonGroup5.add(jrbQ1O13);
        jrbQ1O13.setSelected(true);
        jrbQ1O13.setText("Normal/Acamado/Cadeira de rodas");
        jrbQ1O13.setActionCommand("0");

        buttonGroup1.add(jrbQ1O1);
        jrbQ1O1.setText("Sim");
        jrbQ1O1.setActionCommand("25");

        buttonGroup5.add(jrbQ1O14);
        jrbQ1O14.setText("Lenta/Fraca");
        jrbQ1O14.setActionCommand("10");

        buttonGroup5.add(jrbQ1O15);
        jrbQ1O15.setText("Alterada/Cambaleante");
        jrbQ1O15.setActionCommand("20");

        buttonGroup3.add(jrbQ1O16);
        jrbQ1O16.setText("Mobiliário ou parede");
        jrbQ1O16.setActionCommand("30");

        buttonGroup1.add(jrbQ1O2);
        jrbQ1O2.setSelected(true);
        jrbQ1O2.setText("Não");
        jrbQ1O2.setActionCommand("0");

        jLabel1.setText("1- História de quedas nos últimos três meses");

        jLabel6.setText("2- Diagnóstico Secundário");

        jLabel7.setText("3- Auxilio na mobilidade");

        jLabel8.setText("4-Terapia Endovenosa");

        jLabel9.setText("5- Marcha");

        jLabel10.setText("6- Estado Mental");

        buttonGroup2.add(jrbQ1O3);
        jrbQ1O3.setText("Sim");
        jrbQ1O3.setActionCommand("15");

        buttonGroup2.add(jrbQ1O4);
        jrbQ1O4.setSelected(true);
        jrbQ1O4.setText("Não");
        jrbQ1O4.setActionCommand("0");

        buttonGroup4.add(jrbQ1O5);
        jrbQ1O5.setText("Sim");
        jrbQ1O5.setActionCommand("20");

        jLabel4.setText("Data do Exame:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator3)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator5)
                    .addComponent(jSeparator6)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jrbQ1O15)
                                    .addComponent(jrbQ1O14)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jrbQ1O5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jrbQ1O6))
                                    .addComponent(jrbQ1O13)
                                    .addComponent(jLabel10)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jrbQ1O11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jrbQ1O12))
                                    .addComponent(jrbQ1O8)
                                    .addComponent(jrbQ1O16)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel7)
                                    .addComponent(jrbQ1O7)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jrbQ1O1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jrbQ1O2))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jrbQ1O3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jrbQ1O4))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtfData, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 59, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrbQ1O1)
                    .addComponent(jrbQ1O2))
                .addGap(1, 1, 1)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrbQ1O3)
                    .addComponent(jrbQ1O4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jrbQ1O7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jrbQ1O8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jrbQ1O16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrbQ1O5)
                    .addComponent(jrbQ1O6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jrbQ1O13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jrbQ1O14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jrbQ1O15)
                .addGap(3, 3, 3)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrbQ1O11)
                    .addComponent(jrbQ1O12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jtTabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Cod", "Paciente"
            }
        ));
        jScrollPane2.setViewportView(jtTabela);

        jbMenu.setText("<<< Voltar");
        jbMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbMenuMouseClicked(evt);
            }
        });

        jLabel5.setText("Selecione um paciente abaixo, para realizar a escala:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jbMenu)
                        .addGap(72, 72, 72)
                        .addComponent(jbFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbFinalizar, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jbMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbFinalizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbFinalizarMouseClicked
        String dataRecebida = jtfData.getText();
        if (Utilidades.DataValida(dataRecebida)) {
            if (CodigoEditar > 0) {
                Alterar();
            } else {
                Incluir();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Data no Formato Inválido!!\nA data deve estar no formato dd/MM/yyyy");
        }
    }//GEN-LAST:event_jbFinalizarMouseClicked

    private void jbMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbMenuMouseClicked
        new List_Formulario().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jbMenuMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Form_Formulario.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Formulario.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Formulario.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Formulario.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Formulario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JButton jbFinalizar;
    private javax.swing.JButton jbMenu;
    private javax.swing.JRadioButton jrbQ1O1;
    private javax.swing.JRadioButton jrbQ1O10;
    private javax.swing.JRadioButton jrbQ1O11;
    private javax.swing.JRadioButton jrbQ1O12;
    private javax.swing.JRadioButton jrbQ1O13;
    private javax.swing.JRadioButton jrbQ1O14;
    private javax.swing.JRadioButton jrbQ1O15;
    private javax.swing.JRadioButton jrbQ1O16;
    private javax.swing.JRadioButton jrbQ1O2;
    private javax.swing.JRadioButton jrbQ1O3;
    private javax.swing.JRadioButton jrbQ1O4;
    private javax.swing.JRadioButton jrbQ1O5;
    private javax.swing.JRadioButton jrbQ1O6;
    private javax.swing.JRadioButton jrbQ1O7;
    private javax.swing.JRadioButton jrbQ1O8;
    private javax.swing.JRadioButton jrbQ1O9;
    private javax.swing.JTable jtTabela;
    private javax.swing.JTextField jtfData;
    // End of variables declaration//GEN-END:variables
}
