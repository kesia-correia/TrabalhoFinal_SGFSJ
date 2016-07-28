/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sorveteriajanuaria.apresentacao;

import br.com.sorveteriajanuaria.entidade.Usuario;
import br.com.sorveteriajanuaria.excecao.CampoObrigatorioException;
import br.com.sorveteriajanuaria.excecao.SorveteriaJanuariaException;
import br.com.sorveteriajanuaria.negocio.UsuarioBO;
import java.awt.HeadlessException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Késia Correiia
 */
public class PesquisaUsuarioForm extends javax.swing.JFrame {
    private List<Usuario> listaUsuarios;
     CadastroUsuarioForm editarUsuarioForm;
     CadastroUsuarioForm novoCadastroUsuarioForm;
    /**
     * Creates new form PesquisaUsuario
     */
   
     public PesquisaUsuarioForm() {
        this.prepararTela();
    }
    
    private void carregarTabelaUsuarios() throws SQLException {
        UsuarioBO usuarioBO = new UsuarioBO();
        listaUsuarios = usuarioBO.buscarTodos();
        ModeloTabelaUsuario modelo = new ModeloTabelaUsuario();
        tblResultadoPesquisaUsuarios.setModel(modelo);
    }
    
    public void carregarTabelaPesquisaUsuarios() throws SQLException {
        ModeloTabelaUsuario modelo = new ModeloTabelaUsuario();
       tblResultadoPesquisaUsuarios.setModel(modelo);
    }
    
      private void prepararTela() {
        try {
            initComponents();
            setLocationRelativeTo( null );
            carregarTabelaUsuarios();
        } catch (SorveteriaJanuariaException e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Consulta de usuário",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            System.out.println("Erro inesperado! Informe a mensagem de erro ao administrador do sistema.");
            e.printStackTrace(System.out);
            JOptionPane.showMessageDialog(
                    this,
                    "Erro inesperado! Informe o erro ao administrador do sistema",
                    "Consulta de usuário",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
      
    private void pesquisarUsuario() {
        try {
            this.validarCamposPesquisa();
            String filtro = this.recuperarFiltro();
            String palavraPesquisada = this.recuperarPalavraPesquisada();
            UsuarioBO usuarioBO = new UsuarioBO();
            this.listaUsuarios = usuarioBO.pesquisar(filtro, palavraPesquisada);
            this.carregarTabelaPesquisaUsuarios();
        } catch (SorveteriaJanuariaException e) {
            String mensagem = "Pesquisa não realizada:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Pesquisa de usuário", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            String mensagem = "Sua pesquisa não retornou nenhum resultado.";
            mensagem += "\nMensagem de erro:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Pesquisa de usuário", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            String mensagem = "Erro inesperado! Informe a mensagem de erro ao administrador do sistema.";
            mensagem += "\nMensagem de erro:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Pesquisa de usuário", JOptionPane.ERROR_MESSAGE);
        }
    }
     
    private void validarCamposPesquisa() {
        if ((!rdoNome.isSelected() && !rdoLogin.isSelected())
                || txtPalavraPesquisada.getText().trim().isEmpty()) {
            throw new CampoObrigatorioException();
        }
    }
    
     private String recuperarFiltro() {

        String filtro;
        if (rdoNome.isSelected()) {
            filtro = "NOME";
        } else{
            filtro = "LOGIN";
        }
        return filtro;
    }
     
    private String recuperarPalavraPesquisada() {
        String palavraPesquisada =txtPalavraPesquisada.getText().trim();
        return palavraPesquisada;
    }
     
    private void editarUsuario() throws NoSuchAlgorithmException, UnsupportedEncodingException{
        int linhaSelecionada = tblResultadoPesquisaUsuarios.getSelectedRow();
        if (linhaSelecionada != -1) {
            Usuario usuarioSelecionado = listaUsuarios.get(linhaSelecionada);

            if (this.editarUsuarioForm != null) {
                this.editarUsuarioForm.dispose();
            }
            this.editarUsuarioForm = new CadastroUsuarioForm(this, usuarioSelecionado);
            this.editarUsuarioForm.setVisible(true);
            this.editarUsuarioForm.setLocationRelativeTo( null );
            PesquisaUsuarioForm.this.dispose();
        } else {
            String mensagem = "Gentileza selecionar um usuário para poder realizar a edição!!!";
            JOptionPane.showMessageDialog(this, mensagem, "Edição de Usuario", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void excluirUsuario(){
               try {
            int linhaSelecionada = tblResultadoPesquisaUsuarios.getSelectedRow();
            if (linhaSelecionada != -1) {
               Usuario usuarioSelecionado = listaUsuarios.get(linhaSelecionada);

                int resposta;
                String mensagem = "Deseja excluir o usuário: "
                        + usuarioSelecionado.getNome() +"?"; 
                String titulo = "Exclusão de Usuário";
                resposta = JOptionPane.showConfirmDialog(this,
                        mensagem, titulo, JOptionPane.YES_NO_OPTION);

                if (resposta == JOptionPane.YES_OPTION) {
                    UsuarioBO usuarioBO = new UsuarioBO();
                    usuarioBO.excluir(usuarioSelecionado.getIdUsuario());

                    String mensagemSuceso = "Usuário: "
                            + usuarioSelecionado.getNome() + ", "
                            + "excluído com sucesso!";
                    JOptionPane.showMessageDialog(this,
                            mensagemSuceso, titulo,
                            JOptionPane.INFORMATION_MESSAGE);

                    this.carregarTabelaUsuarios();
                }
            } else {
                String mensagem = "Gentileza selecionar um usuário para poder realizar a exclusão!!";
                JOptionPane.showMessageDialog(this,
                        mensagem,
                        "Exclusão de usuário",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException | SQLException e) {
            String mensagem = "Erro inesperado! Informe a mensagem de erro ao administrador do sistema.";
            mensagem += "\nMensagem de erro:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Exclusão de usuário", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }
    
     private void desabilitarCampos(){
        if (rdoNome.isSelected()) {
            rdoLogin.setSelected(false);
        } else if(rdoLogin.isSelected()){
            rdoNome.setSelected(false);
        }
    }

    
    private void limparPesquisa() throws SQLException{
        this.limparCamposPesquisa();
        this.carregarTabelaUsuarios();        
    }
    
    private void limparCamposPesquisa(){
        txtPalavraPesquisada.setText("");
        rdoNome.setSelected(false);
        rdoLogin.setSelected(false);
    }
     
     
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpPesquisaUsuarios = new javax.swing.JPanel();
        lblPesquisa = new javax.swing.JLabel();
        rdoLogin = new javax.swing.JRadioButton();
        rdoNome = new javax.swing.JRadioButton();
        btnPesquisar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblPalavra = new javax.swing.JLabel();
        txtPalavraPesquisada = new javax.swing.JTextField();
        jpResultadoUsuarios = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResultadoPesquisaUsuarios = new javax.swing.JTable();
        btnNovo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pesquisa Usuario - Sorveteria Januária");

        jpPesquisaUsuarios.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pesquisar", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jpPesquisaUsuarios.setToolTipText("");

        lblPesquisa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblPesquisa.setText("Pesquisar por:");

        rdoLogin.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoLogin.setText("Login");
        rdoLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoLoginActionPerformed(evt);
            }
        });

        rdoNome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoNome.setText("Nome");
        rdoNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNomeActionPerformed(evt);
            }
        });

        btnPesquisar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sorveteriajanuaria/apresentacao/icones/buscar.png"))); // NOI18N
        btnPesquisar.setText("Pesquisar");
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sorveteriajanuaria/apresentacao/icones/fechar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        lblPalavra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblPalavra.setText("Palavra:");

        javax.swing.GroupLayout jpPesquisaUsuariosLayout = new javax.swing.GroupLayout(jpPesquisaUsuarios);
        jpPesquisaUsuarios.setLayout(jpPesquisaUsuariosLayout);
        jpPesquisaUsuariosLayout.setHorizontalGroup(
            jpPesquisaUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPesquisaUsuariosLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jpPesquisaUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpPesquisaUsuariosLayout.createSequentialGroup()
                        .addComponent(lblPesquisa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdoLogin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdoNome)
                        .addGap(0, 406, Short.MAX_VALUE))
                    .addGroup(jpPesquisaUsuariosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnPesquisar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPesquisaUsuariosLayout.createSequentialGroup()
                        .addComponent(lblPalavra)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPalavraPesquisada)))
                .addContainerGap())
        );
        jpPesquisaUsuariosLayout.setVerticalGroup(
            jpPesquisaUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPesquisaUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpPesquisaUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpPesquisaUsuariosLayout.createSequentialGroup()
                        .addGap(0, 44, Short.MAX_VALUE)
                        .addGroup(jpPesquisaUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPalavra)
                            .addComponent(txtPalavraPesquisada, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jpPesquisaUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jpPesquisaUsuariosLayout.createSequentialGroup()
                        .addGroup(jpPesquisaUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoLogin)
                            .addComponent(rdoNome)
                            .addComponent(lblPesquisa))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(5, 5, 5))
        );

        jpResultadoUsuarios.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Resultado da Pesquisa:", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        tblResultadoPesquisaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblResultadoPesquisaUsuarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblResultadoPesquisaUsuarios);

        btnNovo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sorveteriajanuaria/apresentacao/icones/add.png"))); // NOI18N
        btnNovo.setText("Novo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnEditar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sorveteriajanuaria/apresentacao/icones/editar.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sorveteriajanuaria/apresentacao/icones/deletar.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnFechar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sorveteriajanuaria/apresentacao/icones/fechar.png"))); // NOI18N
        btnFechar.setText("Fechar");
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpResultadoUsuariosLayout = new javax.swing.GroupLayout(jpResultadoUsuarios);
        jpResultadoUsuarios.setLayout(jpResultadoUsuariosLayout);
        jpResultadoUsuariosLayout.setHorizontalGroup(
            jpResultadoUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpResultadoUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpResultadoUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpResultadoUsuariosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnNovo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFechar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpResultadoUsuariosLayout.setVerticalGroup(
            jpResultadoUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpResultadoUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpResultadoUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEditar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpResultadoUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpResultadoUsuarios, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpPesquisaUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpPesquisaUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jpResultadoUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        try {
            // TODO add your handling code here:
            this.editarUsuario();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(PesquisaUsuarioForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
    try {
            // TODO add your handling code here:
            this.limparPesquisa();
        } catch (SQLException ex) {
            Logger.getLogger(PesquisaFornecedoresForm.class.getName()).log(Level.SEVERE, null, ex);
        }        // TODO add your handling code here:
        
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        // TODO add your handling code here:
       this.pesquisar();
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:
        this.excluirUsuario();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        // TODO add your handling code here:
        this.exibirTelaCadastroUsuarios();
       
    }//GEN-LAST:event_btnNovoActionPerformed

    private void exibirTelaCadastroUsuarios() {
        if (this.novoCadastroUsuarioForm== null) {
            this.novoCadastroUsuarioForm = new CadastroUsuarioForm();
        }
        novoCadastroUsuarioForm.setVisible(true);
        novoCadastroUsuarioForm.toFront();
        this.dispose();
    }
    
    
    private void rdoLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoLoginActionPerformed
        // TODO add your handling code here:
        this.desabilitarCampos();
    }//GEN-LAST:event_rdoLoginActionPerformed

    private void rdoNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNomeActionPerformed
        // TODO add your handling code here:
        this.desabilitarCampos();
    }//GEN-LAST:event_rdoNomeActionPerformed
    
    private void pesquisar(){
        if(rdoNome.isSelected() || rdoLogin.isSelected()){
            this.pesquisarUsuario();
        }
   }
    
    
 

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
            java.util.logging.Logger.getLogger(PesquisaUsuarioForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PesquisaUsuarioForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PesquisaUsuarioForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PesquisaUsuarioForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PesquisaUsuarioForm().setVisible(true);
            }
        });
    }
    
    private class ModeloTabelaUsuario extends AbstractTableModel {

        @Override
        public int getRowCount() {
            return listaUsuarios.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
           Usuario usuarioAtual = listaUsuarios.get(rowIndex);
            if (columnIndex == 0) {
                return usuarioAtual.getNome();
            } else{ 
                return usuarioAtual.getLogin();
            } 
        }

        @Override
        public String getColumnName(int coluna) {
            if (coluna == 0) {
                return "NOME:";
            } else{
                return "LOGIN:";
            } 
        }
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel jpPesquisaUsuarios;
    private javax.swing.JPanel jpResultadoUsuarios;
    private javax.swing.JLabel lblPalavra;
    private javax.swing.JLabel lblPesquisa;
    private javax.swing.JRadioButton rdoLogin;
    private javax.swing.JRadioButton rdoNome;
    private javax.swing.JTable tblResultadoPesquisaUsuarios;
    private javax.swing.JTextField txtPalavraPesquisada;
    // End of variables declaration//GEN-END:variables
}
