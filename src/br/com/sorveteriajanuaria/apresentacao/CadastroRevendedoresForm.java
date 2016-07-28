package br.com.sorveteriajanuaria.apresentacao;

import br.com.sorveteriajanuaria.entidade.Revendedor;
import br.com.sorveteriajanuaria.excecao.CampoObrigatorioException;
import br.com.sorveteriajanuaria.excecao.SorveteriaJanuariaException;
import br.com.sorveteriajanuaria.negocio.RevendedorBO;
import java.awt.HeadlessException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Késia Correiia
 */
public class CadastroRevendedoresForm extends javax.swing.JFrame {

    Revendedor revendedor;
    private PesquisaRevendedoresForm pesquisaRevendedoresForm;
    private List<Revendedor> listaRevendedores;
    private SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
    
    /**
     * Creates new form CadastroRevendedores
     */
    public CadastroRevendedoresForm() {
        revendedor = new Revendedor();
        initComponents();
        setLocationRelativeTo( null );
    }
    
     public CadastroRevendedoresForm(PesquisaRevendedoresForm pesquisaRevendedoresForm, Revendedor revendedorEdicao){
        this.pesquisaRevendedoresForm = pesquisaRevendedoresForm;
        this.revendedor = revendedorEdicao;
        initComponents();
        this.inicializarCamposTela();
        setLocationRelativeTo( null );
    }
    
    private void desabilitarCampos(){
         if (rdoPessoaJuridica.isSelected()) {
             this.txtCNPJ.setEnabled(true);
             this.txtCPF.setEnabled(false);
             this.lblCPF.setEnabled(false);
             rdoPessoaFisica.setEnabled(false);
         }else if(rdoPessoaFisica.isSelected()){
             rdoPessoaFisica.setEnabled(true);
             this.txtCPF.setEnabled(true);
             this.lblCPF.setEnabled(true);
             this.txtCNPJ.setEnabled(false);
             this.lblCNPJ.setEnabled(false);
             rdoPessoaJuridica.setEnabled(false);
         }else{
             rdoPessoaFisica.setEnabled(true);
             rdoPessoaJuridica.setEnabled(true);
             this.txtCPF.setEnabled(true);
             this.txtCNPJ.setEnabled(true);
             this.lblCPF.setEnabled(true);
             this.lblCNPJ.setEnabled(true);
         }    
    }
      
    private void inicializarCamposTela(){
        this.txtNome.setText(this.revendedor.getNomeRevendor());
        this.txtCNPJ.setText(this.revendedor.getCNPJ());
        this.txtCPF.setText(this.revendedor.getCPF());
        this.txtRG.setText(this.revendedor.getRG());
        String dataNascimento = formatoData.format(revendedor.getDataNascimento());
        txtDataNascimento.setText(dataNascimento);
        this.txtTelefone.setText(this.revendedor.getTelefone());
        this.txtEndereco.setText(this.revendedor.getEndereco());
    }
    
    private void limparCamposTela() {
       this.txtNome.setText("");
       this.txtCPF.setText("");
       this.txtCNPJ.setText("");
       this.txtRG.setText("");
       this.txtDataNascimento.setText("");
       this.txtTelefone.setText("");
       this.txtEndereco.setText("");
    }
    
    private void recuperarCamposTela() throws ParseException {
        if (!txtNome.getText().trim().equals("")) {
            revendedor.setNomeRevendor(txtNome.getText().trim());
        } else {
            throw new CampoObrigatorioException();
        }
        
         if (rdoPessoaJuridica.isSelected()) {
            revendedor.setCNPJ(txtCNPJ.getText().trim());
        } else if (rdoPessoaFisica.isSelected()) {
             revendedor.setCPF(txtCPF.getText().trim());
        }
        if (!txtRG.getText().trim().equals("")) {
            revendedor.setRG(txtRG.getText().trim());
        } else {
            throw new CampoObrigatorioException();
        }
        
         if (!txtDataNascimento.getText().trim().equals("")) {
           revendedor.setDataNascimento(formatoData.parse(txtDataNascimento.getText()));
        } else {
            throw new CampoObrigatorioException();
        }
        
          if (!txtTelefone.getText().trim().equals("")) {
            revendedor.setTelefone(txtTelefone.getText().trim());
        } else {
            throw new CampoObrigatorioException();
        }
          
         if (!txtEndereco.getText().trim().equals("")) {
            revendedor.setEndereco(txtEndereco.getText().trim());
        } else {
            throw new CampoObrigatorioException();
        }
    }
    
    private void fecharCadastroRevendedores(){
        CadastroRevendedoresForm.this.dispose();
        if ( pesquisaRevendedoresForm == null) {
           pesquisaRevendedoresForm = new PesquisaRevendedoresForm();
        }
        pesquisaRevendedoresForm.setVisible(true);
        pesquisaRevendedoresForm.toFront();
    } 
    
    private void validarCamposObrigatorios() {
        if (txtNome.getText().trim().isEmpty()
                || txtRG.getText().trim().isEmpty()
                || txtTelefone.getText().trim().isEmpty()
                || txtEndereco.getText().trim().isEmpty()
                || txtDataNascimento.getText().trim().isEmpty()
                || (!rdoPessoaJuridica.isSelected() && !rdoPessoaFisica.isSelected())){
            throw new CampoObrigatorioException();
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

        jPanel1 = new javax.swing.JPanel();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        rdoPessoaJuridica = new javax.swing.JRadioButton();
        rdoPessoaFisica = new javax.swing.JRadioButton();
        lblCNPJ = new javax.swing.JLabel();
        txtCNPJ = new javax.swing.JFormattedTextField();
        txtCPF = new javax.swing.JFormattedTextField();
        lblCPF = new javax.swing.JLabel();
        lblTelefone = new javax.swing.JLabel();
        txtTelefone = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        txtEndereco = new javax.swing.JTextField();
        lbRG = new javax.swing.JLabel();
        txtRG = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        txtDataNascimento = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cadastro Revendedores - Sorveteria Januária");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cadastro de Revendores", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

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

        lblNome.setText("Nome:");

        jLabel1.setText("Pessoa:");

        rdoPessoaJuridica.setText("Jurídica");
        rdoPessoaJuridica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoPessoaJuridicaActionPerformed(evt);
            }
        });

        rdoPessoaFisica.setText("Física");
        rdoPessoaFisica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoPessoaFisicaActionPerformed(evt);
            }
        });

        lblCNPJ.setText("CNPJ:");

        try {
            txtCNPJ.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###/####-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCNPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCNPJActionPerformed(evt);
            }
        });

        try {
            txtCPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        lblCPF.setText("CPF:");

        lblTelefone.setText("Telefone:");

        try {
            txtTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel3.setText("Endereço:");

        lbRG.setText("RG:");

        try {
            txtRG.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###-#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel4.setText("Data de Nascimento:");

        try {
            txtDataNascimento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalvar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(lblNome)
                    .addComponent(lblCNPJ)
                    .addComponent(lbRG)
                    .addComponent(lblTelefone)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNome)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rdoPessoaJuridica)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdoPessoaFisica)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblCPF)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCPF))
                    .addComponent(txtEndereco, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtRG, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDataNascimento, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE))
                    .addComponent(txtTelefone)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNome)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(rdoPessoaJuridica)
                    .addComponent(rdoPessoaFisica))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCNPJ)
                    .addComponent(txtCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCPF)
                    .addComponent(txtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRG, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbRG))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTelefone))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
       this.fecharCadastroRevendedores();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void rdoPessoaJuridicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoPessoaJuridicaActionPerformed
        // TODO add your handling code here:
         this.desabilitarCampos();
    }//GEN-LAST:event_rdoPessoaJuridicaActionPerformed

    private void txtCNPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCNPJActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCNPJActionPerformed

    private void rdoPessoaFisicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoPessoaFisicaActionPerformed
        // TODO add your handling code here:
         this.desabilitarCampos();
    }//GEN-LAST:event_rdoPessoaFisicaActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        try {
            // TODO add your handling code here:
            this.salvarRevendedor();
        } catch (Exception ex) {
            Logger.getLogger(CadastroRevendedoresForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void salvarRevendedor() throws Exception{
        try {
            this.validarCamposObrigatorios();
            this.recuperarCamposTela();
            RevendedorBO revendedorBO = new  RevendedorBO();
            String tituloMensagem;

            if (revendedor.getIdRevendedor() == -1) {
               tituloMensagem = "Cadastro de revendedor";
               revendedorBO.inserir(revendedor);
               JOptionPane.showMessageDialog(this, "Revendedor cadastrado com sucesso!", tituloMensagem, JOptionPane.INFORMATION_MESSAGE);
            } else {
                tituloMensagem = "Editar revendedor";
                revendedorBO.alterar(revendedor);
                JOptionPane.showMessageDialog(this, "Revendedor alterado com sucesso!", tituloMensagem, JOptionPane.INFORMATION_MESSAGE);
                if (pesquisaRevendedoresForm == null) {
                   pesquisaRevendedoresForm = new PesquisaRevendedoresForm();
                }
                this.fecharCadastroRevendedores();
            }
            this.limparCamposTela();
        } catch (SorveteriaJanuariaException e) {
            String mensagem = "Erro ao realizar cadastro:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Cadastro de revendedor", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException | SQLException | HeadlessException e) {
            String mensagem = "Erro inesperado! Informe a mensagem de erro ao administrador do sistema.";
            mensagem += "\nMensagem de erro:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Cadastro de revendedor", JOptionPane.ERROR_MESSAGE);
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
            java.util.logging.Logger.getLogger(CadastroRevendedoresForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadastroRevendedoresForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadastroRevendedoresForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadastroRevendedoresForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadastroRevendedoresForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbRG;
    private javax.swing.JLabel lblCNPJ;
    private javax.swing.JLabel lblCPF;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblTelefone;
    private javax.swing.JRadioButton rdoPessoaFisica;
    private javax.swing.JRadioButton rdoPessoaJuridica;
    private javax.swing.JFormattedTextField txtCNPJ;
    private javax.swing.JFormattedTextField txtCPF;
    private javax.swing.JFormattedTextField txtDataNascimento;
    private javax.swing.JTextField txtEndereco;
    private javax.swing.JTextField txtNome;
    private javax.swing.JFormattedTextField txtRG;
    private javax.swing.JFormattedTextField txtTelefone;
    // End of variables declaration//GEN-END:variables
}
