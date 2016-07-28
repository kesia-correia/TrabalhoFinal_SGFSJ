/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sorveteriajanuaria.persistencia;

import br.com.sorveteriajanuaria.entidade.Fornecedor;
import br.com.sorveteriajanuaria.entidade.ItemVenda;
import br.com.sorveteriajanuaria.entidade.Venda;
import br.com.sorveteriajanuaria.negocio.BancoDadosUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Késia Correia
 */
public class VendaDAO {
    private static final String INSERT_VENDA = "INSERT INTO VENDA (ID_REVENDEDOR,DATA_VENDA,FORMA_PAGAMENTO) VALUES (?,?,?)";
    private static final String SELECT_VENDA_ID = "SELECT ID FROM VENDA WHERE ID_REVENDEDOR = ?";
    private static final String INSERT_ITEM_VENDA = "INSERT INTO  ITENS_VENDA (ID_VENDA, ID_PRODUTO,QUANTIDADE) VALUES (?, ?,?)";
    private static final String DELETE = "DELETE FROM VENDA WHERE ID=?";
     private static final String BUSCAR_TODOS = "SELECT ID, FORMA_PAGAMENTO, DATA_VENDA FROM VENDA";
    
    
    
    public void inserir(Venda venda, List<ItemVenda> itens) throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;
        try {

            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(INSERT_VENDA);
            comando.setInt(1, venda.getIdRevendedor());
            java.sql.Date dataSql = new java.sql.Date(venda.getDataPedido().getTime());
            comando.setDate(2, dataSql);
            comando.setString(3, venda.getFormaPagamento());
            comando.execute();

            comando = conexao.prepareStatement(SELECT_VENDA_ID);
            comando.setInt(1, venda.getIdRevendedor());
            comando.execute();

            resultado = comando.executeQuery();
            while (resultado.next()) {
                venda.setIdVenda(resultado.getInt(1));
            }

            comando = conexao.prepareStatement(INSERT_ITEM_VENDA);
            for (ItemVenda item : itens) {
                comando.setInt(1, venda.getIdVenda());
                comando.setInt(2, item.getIdProduto());
                comando.setInt(3, item.getQuantidade());
                comando.execute();
            }

            conexao.commit();
        } catch (Exception e) {
            if (conexao != null) {
                conexao.rollback();
            }
            throw e;
        } finally {
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando);
        }
    }
    
    public void excluir(int ID) throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;

        try {
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(DELETE);        
            comando.setInt(1, ID);
            comando.execute();
            conexao.commit();
        } catch (Exception e) {
            if (conexao != null) {
                conexao.rollback();
            }
            throw e;
        } finally {
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando);
        }
    }
    
    public List<Venda> buscarTodos() throws SQLException{
        List<Venda> listaVendas = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;
        
        try{
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(BUSCAR_TODOS);
            resultado = comando.executeQuery();           
            while(resultado.next()){
                Venda venda = this.extrairLinhaResultadoBuscarTodas(resultado);
                listaVendas.add(venda);
            }    
        }finally {
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando);
        }
        return listaVendas;
    }
    
    private Venda extrairLinhaResultadoBuscarTodas(ResultSet resultado) throws SQLException {
        Venda venda = this.extrairLinhaResultado(resultado);
        return venda;
    }

    private Venda extrairLinhaResultado(ResultSet resultado) throws SQLException {
        Venda venda = new Venda();
        venda.setIdVenda(resultado.getInt(1));
        venda.setFormaPagamento(resultado.getString(2));
        venda.setDataPedido(resultado.getTimestamp(3));
        return venda;
    }
    
    
}
