package br.com.sorveteriajanuaria.apresentacao;


import br.com.sorveteriajanuaria.entidade.Venda;
import br.com.sorveteriajanuaria.entidade.FormaPagamento;
import br.com.sorveteriajanuaria.entidade.ItemVenda;
import br.com.sorveteriajanuaria.entidade.Produtos;
import br.com.sorveteriajanuaria.entidade.Revendedor;
import br.com.sorveteriajanuaria.excecao.CampoObrigatorioException;
import br.com.sorveteriajanuaria.excecao.NenhumItemAdicionadoException;
import br.com.sorveteriajanuaria.excecao.SorveteriaJanuariaException;
import br.com.sorveteriajanuaria.negocio.ProdutosBO;
import br.com.sorveteriajanuaria.negocio.RevendedorBO;
import br.com.sorveteriajanuaria.negocio.VendaBO;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Késia Correiia
 */
public final class CadastroVendaForm extends javax.swing.JFrame {
    Venda venda;
    CadastroVendaForm cadastroVendaForm;
    PesquisaVendasForm pesquisaVendasForm;
    private SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
    
    
    private PesquisaRevendedoresForm pesquisaRevendedorForm;
    
    private List<FormaPagamento> listaFormaPagamento;
    
    private ItemVenda itemVenda = new ItemVenda();
    private List<ItemVenda> Itens = new ArrayList<>();
    
    private Produtos produto = new Produtos();
    private List<Produtos> listaProdutos = new ArrayList();
    
    
    private Revendedor revendedor;
    private Revendedor revendedorSelecionado = null;
    private List<Revendedor> listaRevendedores = new ArrayList<>();
    
    /**
     * Creates new form VendaForm
     */
    public CadastroVendaForm() {
        venda = new Venda();
        //revendedor = new Revendedor();
        this.prepararTela();   
    }
    
     private void pesquisarRevendedor() {
        RevendedorBO revendedorBO = new RevendedorBO();
        try {
            int linhaSelecionada = tblRevendedor.getSelectedRow();   
            listaRevendedores = revendedorBO.pesquisar("NOME", txtNomeRevendedor.getText());
            ModeloTabelaRevendedor modelo = new ModeloTabelaRevendedor();
            tblRevendedor.setModel(modelo);
        } catch (Exception e) {
            String mensagem = "Erro!! Entre em contato com o administrador do sistema!! \n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Pesquisa revendedor", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prepararTela() {
        try{
            this.initComponents();
            this.preencherCmbFormaPagamento();
            this.buscarProduto();
            setLocationRelativeTo( null );  
         } catch (SorveteriaJanuariaException e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Cadastro de vendas",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            System.out.println("Erro inesperado! Informe a mensagem de erro ao administrador do sistema.");
            e.printStackTrace(System.out);
            JOptionPane.showMessageDialog(
                    this,
                    "Erro inesperado! Informe o erro ao administrador do sistema",
                    "Cadasrto de vendas",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private void preencherCmbFormaPagamento() {
        this.listaFormaPagamento = new ArrayList<>();

        FormaPagamento dinheiro = new FormaPagamento("AV", "Dinheiro");
        FormaPagamento duplicata = new FormaPagamento ("DP", "Duplicata");
        FormaPagamento promissoria = new FormaPagamento("PM", "Promissória");
        FormaPagamento cartaoCredito = new FormaPagamento("CC", "Cartão de Crédito");
        FormaPagamento cartaoDebito = new FormaPagamento("CD", "Cartão de Débito");
        FormaPagamento cheque = new FormaPagamento("CH", "Cheque");
        FormaPagamento boleto = new FormaPagamento ("BL", "Boleto");

        this.listaFormaPagamento.add(boleto);
        this.listaFormaPagamento.add(cartaoCredito);
        this.listaFormaPagamento.add(cartaoDebito);
        this.listaFormaPagamento.add(cheque);
        this.listaFormaPagamento.add(dinheiro);
        this.listaFormaPagamento.add(duplicata);
        this.listaFormaPagamento.add(promissoria);
     
        this.cmbFormaPagamento.addItem("Selecione...");
        for (FormaPagamento formaPagamento : listaFormaPagamento) {
            this.cmbFormaPagamento.addItem(formaPagamento.getDescricao());
        }
    } 
    
    private void recuperarCampos() throws ParseException{
        
         if (!txtData.getText().trim().equals("")) {
           venda.setDataPedido(formatoData.parse(txtData.getText()));
        } else {
            throw new CampoObrigatorioException();
        }
         
        int posicaoSelecionada
                = this.cmbFormaPagamento.getSelectedIndex();

        if (posicaoSelecionada > 0) {
            FormaPagamento formaPagamento
                    = this.listaFormaPagamento.get(posicaoSelecionada - 1);
            venda.setFormaPagamento(formaPagamento.getCodigo());
        } else {
            throw new CampoObrigatorioException();
        }
    }
    
    private void adicionarRevendedor(){    
        if (revendedorSelecionado != null) {
                venda.setIdRevendedor(revendedorSelecionado.getIdRevendedor()); 
                revendedorSelecionado = listaRevendedores.get(tblRevendedor.getSelectedRow());
                txtNomeRevendedor.setText(revendedorSelecionado.getNomeRevendor());
            } else {
                venda.setIdRevendedor(0);
        }
    }

     private void buscarProduto() {
        produto = new Produtos();
        ProdutosBO produtoBO = new ProdutosBO();
        try {
            listaProdutos = produtoBO.buscarTodos();
            ModeloTabelaProduto modelo = new ModeloTabelaProduto();
            tblProdutos.setModel(modelo);

        } catch (Exception e) {
            String mensagem = "Erro.\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Buscar Produtos", JOptionPane.ERROR_MESSAGE);
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
        pnlDadosVenda = new javax.swing.JPanel();
        lblFormaPagamento = new javax.swing.JLabel();
        lblDataVenda = new javax.swing.JLabel();
        txtData = new javax.swing.JFormattedTextField();
        cmbFormaPagamento = new javax.swing.JComboBox();
        pnlFiltro = new javax.swing.JPanel();
        lblRevendedor = new javax.swing.JLabel();
        txtNomeRevendedor = new javax.swing.JTextField();
        btnPesquisarRevendedor = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblRevendedor = new javax.swing.JTable();
        pnlItensVenda = new javax.swing.JPanel();
        lblProdutos = new javax.swing.JLabel();
        lblQuantidade = new javax.swing.JLabel();
        txtQuantidade = new javax.swing.JTextField();
        btnAdicionar = new javax.swing.JButton();
        lblProdutosAderidos = new javax.swing.JLabel();
        btnRemover = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProdutos = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProdutosAderidos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cadastro de Vendas - SGFSJ");
        setExtendedState(6);

        pnlDadosVenda.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados da Venda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        lblFormaPagamento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblFormaPagamento.setText("Forma de Pagamento:");

        lblDataVenda.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDataVenda.setText("Data:");

        try {
            txtData.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout pnlDadosVendaLayout = new javax.swing.GroupLayout(pnlDadosVenda);
        pnlDadosVenda.setLayout(pnlDadosVendaLayout);
        pnlDadosVendaLayout.setHorizontalGroup(
            pnlDadosVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDadosVendaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDataVenda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(lblFormaPagamento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbFormaPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlDadosVendaLayout.setVerticalGroup(
            pnlDadosVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDadosVendaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDadosVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDadosVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblDataVenda)
                        .addComponent(lblFormaPagamento)
                        .addComponent(cmbFormaPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtData, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        pnlFiltro.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtro", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        lblRevendedor.setText("Revendedor:");

        txtNomeRevendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeRevendedorActionPerformed(evt);
            }
        });

        btnPesquisarRevendedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sorveteriajanuaria/apresentacao/icones/buscar.png"))); // NOI18N
        btnPesquisarRevendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarRevendedorActionPerformed(evt);
            }
        });

        tblRevendedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "NOME", "RG", "ENDEREÇO", "TELEFONE"
            }
        ));
        tblRevendedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRevendedorMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblRevendedor);

        javax.swing.GroupLayout pnlFiltroLayout = new javax.swing.GroupLayout(pnlFiltro);
        pnlFiltro.setLayout(pnlFiltroLayout);
        pnlFiltroLayout.setHorizontalGroup(
            pnlFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFiltroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(pnlFiltroLayout.createSequentialGroup()
                        .addComponent(lblRevendedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNomeRevendedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPesquisarRevendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        pnlFiltroLayout.setVerticalGroup(
            pnlFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFiltroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPesquisarRevendedor, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblRevendedor)
                        .addComponent(txtNomeRevendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))
        );

        pnlItensVenda.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Itens da Venda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        lblProdutos.setText("Produtos");

        lblQuantidade.setText("Quantidade:");

        btnAdicionar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sorveteriajanuaria/apresentacao/icones/add.png"))); // NOI18N
        btnAdicionar.setText("Adicionar");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        lblProdutosAderidos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblProdutosAderidos.setText("Produtos Aderidos:");

        btnRemover.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sorveteriajanuaria/apresentacao/icones/deletar.png"))); // NOI18N
        btnRemover.setText("Remover");
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sorveteriajanuaria/apresentacao/icones/fechar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSalvar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sorveteriajanuaria/apresentacao/icones/salvar.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        tblProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "NOME", "SABOR", "PREÇO"
            }
        ));
        jScrollPane1.setViewportView(tblProdutos);

        tblProdutosAderidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "PRODUTO", "DESCRIÇÃO", "PREÇO UNITÁRIO", "QUANTIDADE", "VALOR"
            }
        ));
        jScrollPane2.setViewportView(tblProdutosAderidos);

        javax.swing.GroupLayout pnlItensVendaLayout = new javax.swing.GroupLayout(pnlItensVenda);
        pnlItensVenda.setLayout(pnlItensVendaLayout);
        pnlItensVendaLayout.setHorizontalGroup(
            pnlItensVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlItensVendaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlItensVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1)
                    .addGroup(pnlItensVendaLayout.createSequentialGroup()
                        .addGroup(pnlItensVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblProdutos)
                            .addGroup(pnlItensVendaLayout.createSequentialGroup()
                                .addComponent(lblQuantidade)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAdicionar))
                            .addComponent(lblProdutosAderidos))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlItensVendaLayout.createSequentialGroup()
                        .addComponent(btnRemover)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelar)
                        .addGap(2, 2, 2))))
        );
        pnlItensVendaLayout.setVerticalGroup(
            pnlItensVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlItensVendaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblProdutos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlItensVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblQuantidade)
                    .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblProdutosAderidos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlItensVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRemover, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlItensVenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlDadosVenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlFiltro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlDadosVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlItensVenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPesquisarRevendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarRevendedorActionPerformed
        // TODO add your handling code here:
        this.pesquisarRevendedor();
    }//GEN-LAST:event_btnPesquisarRevendedorActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
       this.cancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        // TODO add your handling code here:
        this.removerItem();
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // TODO add your handling code here:
          this.adicionarItemTabela();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void txtNomeRevendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeRevendedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeRevendedorActionPerformed

    private void tblRevendedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRevendedorMouseClicked
        // TODO add your handling code here:
        revendedorSelecionado = listaRevendedores.get(tblRevendedor.getSelectedRow());
        txtNomeRevendedor.setText(revendedorSelecionado.getNomeRevendor());
    }//GEN-LAST:event_tblRevendedorMouseClicked

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        try {
            // TODO add your handling code here:
            this.salvarVenda();
        } catch (SQLException ex) {
            Logger.getLogger(CadastroVendaForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(CadastroVendaForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

     private void fecharCadastroVenda(){
        CadastroVendaForm.this.dispose();
        if ( pesquisaVendasForm == null) {
           pesquisaVendasForm = new PesquisaVendasForm();
        }
        pesquisaVendasForm.setVisible(true);
         pesquisaVendasForm.toFront();
        } 
    
    

    private void carregarTabelaProdutosAderidos() {
        ModeloTabelaProdutosAderidos modelo = new ModeloTabelaProdutosAderidos();
        tblProdutosAderidos.setModel(modelo);
    }
     
    private void adicionarItemTabela() {
        itemVenda = new ItemVenda();
        venda = new Venda();
         String quantidade = txtQuantidade.getText().trim();
        if (!quantidade.equals("")) {
            itemVenda.setQuantidade(Integer.parseInt(quantidade));
        } else {
            throw new CampoObrigatorioException();
        }
        int linhaSelecionada = tblProdutos.getSelectedRow();
            if (linhaSelecionada > -1) {
                  itemVenda.setIdProduto(listaProdutos.get(linhaSelecionada).getIdProdutos());
                  Itens.add(itemVenda);
                  this.carregarTabelaProdutosAderidos();
            } else {
                String mensagem = "Gentileza selecione algum item!!";
                JOptionPane.showMessageDialog(this, mensagem, "Adicionar item",
                        JOptionPane.ERROR_MESSAGE);
            }
    }
    
    private void cancelar(){
        int resposta;
                String mensagem = "Realmente deseja cancelar essa venda? ";
                String titulo = " Cancelar";
                resposta = JOptionPane.showConfirmDialog(this,
                        mensagem, titulo, JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.YES_OPTION) {
                    this.fecharCadastroVenda();
                }
    }
    
      private void removerItem() {
        int linhaSelecionada = tblProdutosAderidos.getSelectedRow();
            if (linhaSelecionada != -1) {
            String mensagem = "Deseja excluir esse item da compra ?";
            String titulo = "Excluir Item Pedido";
            int resposta;
            resposta = JOptionPane.showConfirmDialog(null, mensagem, titulo, JOptionPane.NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {
                Itens.remove(linhaSelecionada);
                this.carregarTabelaProdutosAderidos();
                exibirMensagemSucesso("Item excluído com sucesso!", "Cadastro de Itens");
            }
            } else {
                String mensagem = "Nenhum item selecionado.";
                JOptionPane.showMessageDialog(this, mensagem, "Remover item",
                        JOptionPane.ERROR_MESSAGE);
            }
    }
      
    private void exibirMensagemSucesso(String mensagem, String titulo) {
        JOptionPane.showMessageDialog(this, mensagem, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
  
    private void salvarVenda() throws SQLException, ParseException {
        try {
            this.validarCampos();
            this.adicionarRevendedor();
            this.recuperarCampos();
            VendaBO vendaBO = new VendaBO();
            vendaBO.inserir(venda, Itens);
            String tituloMensagem;
            tituloMensagem = "Cadastro de venda";
            JOptionPane.showMessageDialog(this, "Venda cadastrada com sucesso!", tituloMensagem, JOptionPane.INFORMATION_MESSAGE);
            this.limparCampos();
        } catch (NenhumItemAdicionadoException e) {
            JOptionPane.showMessageDialog(null, e, "Salvar venda", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void validarCampos() {
        if (txtData.getText().trim().isEmpty()){
            throw new CampoObrigatorioException();
        }
    }

    private void limparCampos(){
        this.txtData.setText("");
        this.txtNomeRevendedor.setText("");
        this.txtQuantidade.setText("");
        this.descarregarTabelaProdutosAderidos();
        this.descarregarTabelaRevendedores();
    }

    public void carregarTabelaPesquisaProdutos() throws SQLException {
        CadastroVendaForm.ModeloTabelaProduto modelo = new CadastroVendaForm.ModeloTabelaProduto();
        tblProdutos.setModel(modelo);
    }
    
  
    public void descarregarTabelaProdutosAderidos(){
         CadastroVendaForm.ModeloTabelaProdutosAderidosDescarregado modelo = new CadastroVendaForm.ModeloTabelaProdutosAderidosDescarregado();
         tblProdutosAderidos.setModel(modelo);
    }
    
    public void descarregarTabelaRevendedores(){
        CadastroVendaForm.ModeloTabelaProdutosAderidosDescarregado modelo = new CadastroVendaForm.ModeloTabelaProdutosAderidosDescarregado();
         tblRevendedor.setModel(modelo);
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
            java.util.logging.Logger.getLogger(CadastroVendaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadastroVendaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadastroVendaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadastroVendaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadastroVendaForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnPesquisarRevendedor;
    private javax.swing.JButton btnRemover;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox cmbFormaPagamento;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblDataVenda;
    private javax.swing.JLabel lblFormaPagamento;
    private javax.swing.JLabel lblProdutos;
    private javax.swing.JLabel lblProdutosAderidos;
    private javax.swing.JLabel lblQuantidade;
    private javax.swing.JLabel lblRevendedor;
    private javax.swing.JPanel pnlDadosVenda;
    private javax.swing.JPanel pnlFiltro;
    private javax.swing.JPanel pnlItensVenda;
    private javax.swing.JTable tblProdutos;
    private javax.swing.JTable tblProdutosAderidos;
    private javax.swing.JTable tblRevendedor;
    private javax.swing.JFormattedTextField txtData;
    private javax.swing.JTextField txtNomeRevendedor;
    private javax.swing.JTextField txtQuantidade;
    // End of variables declaration//GEN-END:variables

    private class ModeloTabelaRevendedor extends AbstractTableModel {

        @Override
        public String getColumnName(int coluna) {
            if (coluna == 0) {
                return "NOME";
            } else if (coluna == 1) {
                return "RG";
            } else if (coluna == 2) {
                return "ENDEREÇO";
            } else {
                return "TEFONE";
            }
        }

        @Override
        public int getRowCount() {
            return listaRevendedores.size();
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Revendedor revendedor = listaRevendedores.get(rowIndex);
            if (columnIndex == 0) {
                return revendedor.getNomeRevendor();
            } else if (columnIndex == 1) {
                return revendedor.getRG();
            } else if (columnIndex == 2) {
                return revendedor.getEndereco();
            } else {
                return revendedor.getTelefone();
            }
        }
    }
    
    private class ModeloTabelaRevendedorDescarregada extends AbstractTableModel {

        @Override
        public String getColumnName(int coluna) {
            if (coluna == 0) {
                return "NOME";
            } else if (coluna == 1) {
                return "RG";
            } else if (coluna == 2) {
                return "ENDEREÇO";
            } else {
                return "TEFONE";
            }
        }

        @Override
        public int getRowCount() {
            return listaRevendedores.size();
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Revendedor revendedor = listaRevendedores.get(rowIndex);
            if (columnIndex == 0) {
                return null;
            } else if (columnIndex == 1) {
                return null;
            } else if (columnIndex == 2) {
                return null;
            } else {
                return null;
            }
        }
    }
    
    

    private class ModeloTabelaProdutosAderidos extends AbstractTableModel {

        @Override
        public int getRowCount() {
            return Itens.size();
        }

        @Override
        public int getColumnCount() {
            return 5;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
           ItemVenda itemVenda = Itens.get(rowIndex);
                 if (columnIndex == 0) {
                    for (Produtos produtos : listaProdutos) {
                        if (produtos.getIdProdutos() == Itens.get(rowIndex).getIdProduto()) {
                            return produtos.getNome();
                        }
                   }    
                } else if (columnIndex == 1) {
                    for (Produtos produtos : listaProdutos) {
                        if (produtos.getIdProdutos() == Itens.get(rowIndex).getIdProduto()) {
                            return produtos.getSabor();
                        }
                    }
            } else if( columnIndex == 2){
                for (Produtos produtos : listaProdutos) {
                        if (produtos.getIdProdutos() == Itens.get(rowIndex).getIdProduto()) {
                          return produtos.getPreco();    
                        }
                }
            }else if(columnIndex == 3){
                return Itens.get(rowIndex).getQuantidade();
               
            }else{
               for (Produtos produtos : listaProdutos) {
                        if (produtos.getIdProdutos() == Itens.get(rowIndex).getIdProduto()) {
                          return produtos.getPreco() * Itens.get(rowIndex).getQuantidade();
                        }
                }  
            } 
            return null;
        }
    
        @Override
        public String getColumnName(int coluna) {
            if (coluna == 0) {
                return "PRODUTO";
            } else if (coluna == 1) {
                return "DESCRIÇÃO";
            } else if(coluna == 2){
                return "PREÇO UNITÁRIO";
            }else if(coluna == 3){
               return "QUANTIDADE";
            }else{
                return "VALOR";
            }
        }
    }

    private class ModeloTabelaProdutosAderidosDescarregado extends AbstractTableModel{
        @Override
        public int getRowCount() {
            return Itens.size();
        }

        @Override
        public int getColumnCount() {
            return 5;
        }
        
         @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            ItemVenda itens = Itens.get(rowIndex);
                 if (columnIndex == 0) {
                            return null;
                   }else if (columnIndex == 1) {
                            return null;
                    } else if( columnIndex == 2){
                          return null;    
                        }else if(columnIndex == 3){
                return null;
               
            }else{
                            return null;
            } 
        }
    }
    
    private class ModeloTabelaProduto extends AbstractTableModel {

        @Override
        public String getColumnName(int coluna) {
            if (coluna == 0) {
                return "NOME";
            } else if (coluna == 1) {
                return "SABOR";
            } else {
                return "PREÇO UNITÁRIO";
            }
        }

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
             Produtos produto = listaProdutos.get(rowIndex);
            if (columnIndex == 0) {
                return produto.getNome();
            } else if (columnIndex == 1) {
                return produto.getSabor();
            } else{
                return produto.getPreco();
            } 
        }
    }
}
