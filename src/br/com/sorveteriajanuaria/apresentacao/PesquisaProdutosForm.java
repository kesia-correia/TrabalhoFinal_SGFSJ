/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sorveteriajanuaria.apresentacao;

import br.com.sorveteriajanuaria.entidade.Produtos;
import br.com.sorveteriajanuaria.excecao.CampoObrigatorioException;
import br.com.sorveteriajanuaria.excecao.SorveteriaJanuariaException;
import br.com.sorveteriajanuaria.negocio.ProdutosBO;
import java.awt.HeadlessException;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Késia Correiia
 */
public class PesquisaProdutosForm extends javax.swing.JFrame {
    private List<Produtos> listaProdutos;
     CadastroProdutosForm editarProdutosForm;
     CadastroProdutosForm novoCadstroProdutosForm;

    /**
     * Creates new form PesquisaProdutos
     */
    public PesquisaProdutosForm() {
       this.prepararTela();
    }
    
    private void carregarTabelaProdutos() throws SQLException {
        ProdutosBO produtosBO = new ProdutosBO();
        listaProdutos = produtosBO.buscarTodos();
        ModeloTabelaProdutos modelo = new ModeloTabelaProdutos();
        tblResultadoPesquisaProdutos.setModel(modelo);
    }
    
    public void carregarTabelaPesquisaProdutos() throws SQLException {
        ModeloTabelaProdutos modelo = new ModeloTabelaProdutos();
        tblResultadoPesquisaProdutos.setModel(modelo);
    }
    
      private void prepararTela() {
        try {
            initComponents();
            setLocationRelativeTo( null );
            carregarTabelaProdutos();
        } catch (SorveteriaJanuariaException e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Consulta de produtos",
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
    
     private void pesquisarProdutos() {
        try {
            this.validarCamposPesquisa();
            String filtro = this.recuperarFiltro();
            String palavraPesquisada = this.recuperarPalavraPesquisada();
            ProdutosBO produtosBO = new ProdutosBO();
            this.listaProdutos = produtosBO.pesquisar(filtro, palavraPesquisada);
            this.carregarTabelaPesquisaProdutos();
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
        if ((!rdoNome.isSelected())
                || txtPalavraPesquisada.getText().trim().isEmpty()) {
            throw new CampoObrigatorioException();
        }
    }
    
    private String recuperarFiltro() {
        String filtro = null;
        if (rdoNome.isSelected()) {
            filtro = "NOME";
        } 
        return filtro;
    }
    
    private String recuperarPalavraPesquisada() {
        String palavraPesquisada =txtPalavraPesquisada.getText().trim();
        return palavraPesquisada;
    }
    
    private void editarProduto(){
        int linhaSelecionada = tblResultadoPesquisaProdutos.getSelectedRow();
        if (linhaSelecionada != -1) {
            Produtos produtosSelecionado = listaProdutos.get(linhaSelecionada);

            if (this.editarProdutosForm != null) {
                this.editarProdutosForm.dispose();
            }
            this.editarProdutosForm = new CadastroProdutosForm(this, produtosSelecionado);
            this.editarProdutosForm.setVisible(true);
            this.editarProdutosForm.setLocationRelativeTo( null );
            PesquisaProdutosForm.this.dispose();
        } else {
            String mensagem = "Gentileza selecionar um produto para poder realizar a edição!!!";
            JOptionPane.showMessageDialog(this, mensagem, "Edição de Produto", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void excluirProduto(){
               try {
            int linhaSelecionada = tblResultadoPesquisaProdutos.getSelectedRow();
            if (linhaSelecionada != -1) {
               Produtos produtosSelecionado = listaProdutos.get(linhaSelecionada);

                int resposta;
                String mensagem = "Deseja excluir o produto: "
                        + produtosSelecionado.getNome() +"?"; 
                String titulo = "Exclusão de Produto";
                resposta = JOptionPane.showConfirmDialog(this,
                        mensagem, titulo, JOptionPane.YES_NO_OPTION);

                if (resposta == JOptionPane.YES_OPTION) {
                    ProdutosBO produtosBO = new ProdutosBO();
                    produtosBO.excluir(produtosSelecionado.getIdProdutos());

                    String mensagemSuceso = "Produto: "
                            + produtosSelecionado.getNome() + ", "
                            + "excluído com sucesso!";
                    JOptionPane.showMessageDialog(this,
                            mensagemSuceso, titulo,
                            JOptionPane.INFORMATION_MESSAGE);

                    this.carregarTabelaProdutos();
                }
            } else {
                String mensagem = "Gentileza selecionar um produto para poder realizar a exclusão!!";
                JOptionPane.showMessageDialog(this,
                        mensagem,
                        "Exclusão de produto",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException | SQLException e) {
            String mensagem = "Erro inesperado! Informe a mensagem de erro ao administrador do sistema.";
            mensagem += "\nMensagem de erro:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Exclusão de produto", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }
    
     private void pesquisar(){
        if(rdoNome.isSelected()){
            this.pesquisarProdutos();
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

        jpPesquisaProdutos = new javax.swing.JPanel();
        lblPesquisa = new javax.swing.JLabel();
        rdoNome = new javax.swing.JRadioButton();
        btnPesquisar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblPalavra = new javax.swing.JLabel();
        txtPalavraPesquisada = new javax.swing.JTextField();
        jpResultadoPesquisaProdutos = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResultadoPesquisaProdutos = new javax.swing.JTable();
        btnNovo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pesquisa Produto - Sorveteria Januária");

        jpPesquisaProdutos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtro", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jpPesquisaProdutos.setToolTipText("");

        lblPesquisa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblPesquisa.setText("Pesquisar por:");

        rdoNome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoNome.setText("Nome");

        btnPesquisar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sorveteriajanuaria/apresentacao/icones/buscar.png"))); // NOI18N
        btnPesquisar.setText("Pesquisar");

        btnCancelar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sorveteriajanuaria/apresentacao/icones/fechar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");

        lblPalavra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblPalavra.setText("Palavra:");

        javax.swing.GroupLayout jpPesquisaProdutosLayout = new javax.swing.GroupLayout(jpPesquisaProdutos);
        jpPesquisaProdutos.setLayout(jpPesquisaProdutosLayout);
        jpPesquisaProdutosLayout.setHorizontalGroup(
            jpPesquisaProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPesquisaProdutosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpPesquisaProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpPesquisaProdutosLayout.createSequentialGroup()
                        .addComponent(lblPesquisa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdoNome)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPesquisaProdutosLayout.createSequentialGroup()
                        .addComponent(lblPalavra)
                        .addGap(18, 18, 18)
                        .addComponent(txtPalavraPesquisada))
                    .addGroup(jpPesquisaProdutosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnPesquisar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelar)))
                .addContainerGap())
        );
        jpPesquisaProdutosLayout.setVerticalGroup(
            jpPesquisaProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPesquisaProdutosLayout.createSequentialGroup()
                .addGroup(jpPesquisaProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPesquisa)
                    .addComponent(rdoNome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpPesquisaProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPalavra)
                    .addComponent(txtPalavraPesquisada, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpPesquisaProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpResultadoPesquisaProdutos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtro", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        tblResultadoPesquisaProdutos.setModel(new javax.swing.table.DefaultTableModel(
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
        tblResultadoPesquisaProdutos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblResultadoPesquisaProdutos);

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

        javax.swing.GroupLayout jpResultadoPesquisaProdutosLayout = new javax.swing.GroupLayout(jpResultadoPesquisaProdutos);
        jpResultadoPesquisaProdutos.setLayout(jpResultadoPesquisaProdutosLayout);
        jpResultadoPesquisaProdutosLayout.setHorizontalGroup(
            jpResultadoPesquisaProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpResultadoPesquisaProdutosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpResultadoPesquisaProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 647, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpResultadoPesquisaProdutosLayout.createSequentialGroup()
                        .addComponent(btnNovo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFechar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpResultadoPesquisaProdutosLayout.setVerticalGroup(
            jpResultadoPesquisaProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpResultadoPesquisaProdutosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpResultadoPesquisaProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNovo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpPesquisaProdutos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpResultadoPesquisaProdutos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpPesquisaProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpResultadoPesquisaProdutos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        // TODO add your handling code here:
        this.exibirTelaCadastroProdutos();
        
    }//GEN-LAST:event_btnNovoActionPerformed
    
    private void exibirTelaCadastroProdutos() {
        if (this.novoCadstroProdutosForm== null) {
            this.novoCadstroProdutosForm = new CadastroProdutosForm();
        }
        novoCadstroProdutosForm.setVisible(true);
        novoCadstroProdutosForm.toFront();
        this.dispose();
    }
    
    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        this.editarProduto();
      
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:
        this.excluirProduto();
    }//GEN-LAST:event_btnExcluirActionPerformed

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
            java.util.logging.Logger.getLogger(PesquisaProdutosForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PesquisaProdutosForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PesquisaProdutosForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PesquisaProdutosForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PesquisaProdutosForm().setVisible(true);
            }
        });
    }

    private class ModeloTabelaProdutos extends AbstractTableModel {

        @Override
        public int getRowCount() {
            return listaProdutos.size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
           Produtos produtoAtual = listaProdutos.get(rowIndex);
            if (columnIndex == 0) {
                return produtoAtual.getNome();
            } else if( columnIndex == 1){ 
                return produtoAtual.getSabor();
            } else{
                return produtoAtual.getPreco();
            }
        }

        @Override
        public String getColumnName(int coluna) {
            if (coluna == 0) {
                return "NOME:";
            } else if (coluna == 1){
                return "SABOR:";
            } else{
                return "PRECO";
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
    private javax.swing.JPanel jpPesquisaProdutos;
    private javax.swing.JPanel jpResultadoPesquisaProdutos;
    private javax.swing.JLabel lblPalavra;
    private javax.swing.JLabel lblPesquisa;
    private javax.swing.JRadioButton rdoNome;
    private javax.swing.JTable tblResultadoPesquisaProdutos;
    private javax.swing.JTextField txtPalavraPesquisada;
    // End of variables declaration//GEN-END:variables
}
