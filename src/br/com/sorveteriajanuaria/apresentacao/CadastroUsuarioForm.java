/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sorveteriajanuaria.apresentacao;

import br.com.sorveteriajanuaria.entidade.Usuario;
import br.com.sorveteriajanuaria.excecao.CampoObrigatorioException;
import br.com.sorveteriajanuaria.excecao.SorveteriaJanuariaException;
import br.com.sorveteriajanuaria.excecao.ValidarSenhaUsuarioConfirmarSenhaException;
import br.com.sorveteriajanuaria.negocio.UsuarioBO;
import static br.com.sorveteriajanuaria.negocio.UsuarioBO.MD5;
import java.awt.HeadlessException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Késia Correiia
 */
public final class CadastroUsuarioForm extends javax.swing.JFrame {
       private Usuario usuario;
       private PesquisaUsuarioForm pesquisaUsuarioForm;
       private List<Usuario> listaUsuarios;
    
    /**
     * Creates new form CadastroUsuario
     */
    public CadastroUsuarioForm() {
        usuario = new Usuario();
        initComponents();
        setLocationRelativeTo( null );
    }
    
    
    public CadastroUsuarioForm(PesquisaUsuarioForm pesquisaUsuarioForm, Usuario usuarioEdicao) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        this.pesquisaUsuarioForm = pesquisaUsuarioForm;
        this.usuario= usuarioEdicao;
        initComponents();
        this.inicializarCamposTela();
    }
    
    public void inicializarCamposTela() throws NoSuchAlgorithmException, UnsupportedEncodingException{
        this.txtNome.setText(this.usuario.getNome());
        String senha = txtSenha.getText();
        String senha2 = txtConfirmaSenha.getText();
        this.txtLogin.setText(this.usuario.getLogin());
        usuario.setSenha(MD5(senha));
        
    }
      
     private void limparCamposTela() {
       this.txtNome.setText("");
       this.txtLogin.setText("");
       this.txtSenha.setText("");
       this.txtConfirmaSenha.setText("");
     }

     private void recuperarCamposTela() throws NoSuchAlgorithmException, UnsupportedEncodingException { 
         if(!txtNome.getText().trim().equals("")){
            usuario.setNome(txtNome.getText().trim());
         }else{
             throw new CampoObrigatorioException();
         }
         
         if(!txtLogin.getText().trim().equals("")){
            usuario.setLogin(txtLogin.getText().trim());
         }else{
             throw new CampoObrigatorioException();
         }
         
            String senha;
         if(!txtSenha.getText().trim().equals("")){
            senha = txtSenha.getText();
         }else{
             throw new CampoObrigatorioException();
         }
         
         String senha2;
         if(!txtConfirmaSenha.getText().trim().equals("")){
             senha2 = txtConfirmaSenha.getText();
         }
         else{
              throw new CampoObrigatorioException();
         }
                
        usuario.setLogin(txtLogin.getText().trim());
        usuario.setSenha(MD5(senha));
        if(usuario.getSenha() == null ? usuario.getConfirmaSenha() != null : !usuario.getSenha().equals(usuario.getConfirmaSenha())){
           throw new ValidarSenhaUsuarioConfirmarSenhaException();
        }
    }
     
    private void validarCamposObrigatorios() {
      if (txtNome.getText().trim().isEmpty()
            || txtLogin.getText().trim().isEmpty()
            || txtSenha.getText().trim().isEmpty()
            || txtConfirmaSenha.getText().trim().isEmpty()) {
            throw new CampoObrigatorioException();
        }   
    }

    private void fecharCadastroUsuario(){
        CadastroUsuarioForm.this.dispose();
        if ( pesquisaUsuarioForm == null) {
           pesquisaUsuarioForm = new PesquisaUsuarioForm();
        }
        pesquisaUsuarioForm.setVisible(true);
         pesquisaUsuarioForm.toFront();
    }  
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lblLogin = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblConfirmaSenha = new javax.swing.JLabel();
        txtLogin = new javax.swing.JTextField();
        txtSenha = new javax.swing.JPasswordField();
        txtConfirmaSenha = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cadastro Usuários - Sorveteria Januária");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cadastro de Usuários", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        btnSalvar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sorveteriajanuaria/apresentacao/icones/salvar.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
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

        lblNome.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNome.setText("Nome:");

        lblLogin.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblLogin.setText("Login:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Senha:");

        lblConfirmaSenha.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblConfirmaSenha.setText("Confirmar Senha:");

        txtSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSenhaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblNome)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addComponent(lblLogin)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSenha)
                    .addComponent(txtLogin)
                    .addComponent(txtNome)))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblConfirmaSenha)
                        .addGap(2, 2, 2)
                        .addComponent(txtConfirmaSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNome)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLogin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblConfirmaSenha)
                    .addComponent(txtConfirmaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.fecharCadastroUsuario();
    }//GEN-LAST:event_btnCancelarActionPerformed

    
    
    private void txtSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSenhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSenhaActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
           try {
               // TODO add your handling code here:
               this.salvarUsuario();
           } catch (Exception ex) {
               Logger.getLogger(CadastroUsuarioForm.class.getName()).log(Level.SEVERE, null, ex);
           }
    }//GEN-LAST:event_btnSalvarActionPerformed
     
    
    private void salvarUsuario() throws Exception{
        try {
            this.validarCamposObrigatorios();
            this.recuperarCamposTela();
            UsuarioBO usuarioBO = new UsuarioBO();
            String tituloMensagem;

            if (usuario.getIdUsuario() == -1) {
               tituloMensagem = "Cadastro de usuário";
               usuarioBO.inserir(usuario);
               JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!", tituloMensagem, JOptionPane.INFORMATION_MESSAGE);
            } else {
                tituloMensagem = "Editar usuário";
                usuarioBO.alterar(usuario);
                JOptionPane.showMessageDialog(this, "Usuário alterado com sucesso!", tituloMensagem, JOptionPane.INFORMATION_MESSAGE);
                if (pesquisaUsuarioForm == null) {
                    pesquisaUsuarioForm = new PesquisaUsuarioForm();
                }
                this.fecharCadastroUsuario();
            }
            this.limparCamposTela();
        } catch (SorveteriaJanuariaException e) {
            String mensagem = "Erro ao realizar cadastro:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Cadastro de usuário", JOptionPane.ERROR_MESSAGE);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | SQLException | HeadlessException e) {
            String mensagem = "Erro inesperado! Informe a mensagem de erro ao administrador do sistema.";
            mensagem += "\nMensagem de erro:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Cadastro de usuário", JOptionPane.ERROR_MESSAGE);
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
            java.util.logging.Logger.getLogger(CadastroUsuarioForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadastroUsuarioForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadastroUsuarioForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadastroUsuarioForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadastroUsuarioForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblConfirmaSenha;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblNome;
    private javax.swing.JPasswordField txtConfirmaSenha;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JTextField txtNome;
    private javax.swing.JPasswordField txtSenha;
    // End of variables declaration//GEN-END:variables


}