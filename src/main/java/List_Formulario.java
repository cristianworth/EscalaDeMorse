
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class List_Formulario extends javax.swing.JFrame {

    Statement st;
    DefaultTableModel modelo;

    public List_Formulario() {
        initComponents();
        this.setLocationRelativeTo(null);

        // Cria o objeto DBConexao para conectar ao banco de dados
        try {
            st = new DBConexao().getConnection();
        } catch (Exception e) {
            System.out.println("Error: " + e.toString() + e.getMessage());
        }

        Listar("");
        jtTabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //faz alguma ação quando clickar na tabela
            }
        });
    }

    public boolean LinhaValida() {
        int row = jtTabela.getSelectedRow();
        if (row >= 0) { //zero é a primeira coluna
            return true;
        } else {
            return false;
        }
    }

    public int BuscaCodigoSelecionado() {
        int row = jtTabela.getSelectedRow();
        int column = 0; //coluna do codigo
        int codigoSelecionado = Integer.valueOf(jtTabela.getValueAt(row, column).toString());
        return codigoSelecionado;
    }

    private void Listar(String orderBy) {
        String colunas[] = {"Cod", "Paciente", "Data", "Resultado"};
        modelo = new DefaultTableModel(colunas, 0);

        try {
            String sql = "SELECT Formulario.codigo, Paciente.nome, DATE_FORMAT(data, '%d/%m/%Y') as data, resultado1, resultado2, resultado3, resultado4, resultado5, resultadofinal, resultadofinaldescricao FROM Formulario \n"
                    + "left join Paciente on (Paciente.codigo = Formulario.codigo_paciente) ";
            if (orderBy != "") {
                sql += orderBy;
            }
            ResultSet rec = st.executeQuery(sql);
            while (rec.next()) {
                String codigo = rec.getString("codigo");
                String nome = rec.getString("nome");
                String data = rec.getString("data");
                String resultadofinaldescricao = rec.getString("resultadofinaldescricao");

                modelo.addRow(new Object[]{codigo, nome, data, resultadofinaldescricao});
            }
        } catch (SQLException s) {
            JOptionPane.showMessageDialog(this, "Erro ao listar!! " + s.toString());
        }
        jtTabela.setModel(modelo);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jbListar = new javax.swing.JButton();
        jbOrdenar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtTabela = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jbIncluir = new javax.swing.JButton();
        jbAlterar = new javax.swing.JButton();
        jbExcluir = new javax.swing.JButton();
        jbMenu = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Formulário");
        setResizable(false);

        jbListar.setText("Listar");
        jbListar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbListarMouseClicked(evt);
            }
        });

        jbOrdenar.setText("Ordenar por Nome");
        jbOrdenar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbOrdenarMouseClicked(evt);
            }
        });

        jtTabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Cod", "Paciente", "Data", "Resultado"
            }
        ));
        jScrollPane2.setViewportView(jtTabela);

        jbIncluir.setText("Incluir");
        jbIncluir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbIncluirMouseClicked(evt);
            }
        });

        jbAlterar.setText("Alterar");
        jbAlterar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbAlterarMouseClicked(evt);
            }
        });

        jbExcluir.setText("Excluir");
        jbExcluir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbExcluirMouseClicked(evt);
            }
        });

        jbMenu.setText("<<< Voltar");
        jbMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbMenuMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbMenu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbIncluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbAlterar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbAlterar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbMenu)))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jbOrdenar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbListar, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbOrdenar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbListar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbListarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbListarMouseClicked
        Listar("");
    }//GEN-LAST:event_jbListarMouseClicked

    private void jbIncluirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbIncluirMouseClicked
        Form_Formulario form = new Form_Formulario();
        form.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jbIncluirMouseClicked

    private void jbAlterarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbAlterarMouseClicked
        if (LinhaValida()) {
            Form_Formulario form = new Form_Formulario(BuscaCodigoSelecionado());
            form.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um registro para Alterar");
        }
    }//GEN-LAST:event_jbAlterarMouseClicked

    private void jbExcluirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbExcluirMouseClicked
        if (LinhaValida()) {
            String sql = "DELETE FROM Formulario WHERE codigo=" + BuscaCodigoSelecionado();

            try {
                int resp = st.executeUpdate(sql);
                if (resp == 1) {
                    Listar("");
                }
            } catch (SQLException s) {
                JOptionPane.showMessageDialog(this, "Informações não excluidas!! " + s.toString());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um registro para Excluir");
        }
    }//GEN-LAST:event_jbExcluirMouseClicked

    private void jbOrdenarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbOrdenarMouseClicked
        Listar("order by nome");
    }//GEN-LAST:event_jbOrdenarMouseClicked

    private void jbMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbMenuMouseClicked
        GUI_Main form = new GUI_Main();
        form.setVisible(true);
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
            java.util.logging.Logger.getLogger(List_Formulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(List_Formulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(List_Formulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(List_Formulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new List_Formulario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbAlterar;
    private javax.swing.JButton jbExcluir;
    private javax.swing.JButton jbIncluir;
    private javax.swing.JButton jbListar;
    private javax.swing.JButton jbMenu;
    private javax.swing.JButton jbOrdenar;
    private javax.swing.JTable jtTabela;
    // End of variables declaration//GEN-END:variables
}
