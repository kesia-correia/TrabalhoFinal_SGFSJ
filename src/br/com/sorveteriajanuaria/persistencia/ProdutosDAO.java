/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sorveteriajanuaria.persistencia;

import br.com.sorveteriajanuaria.entidade.Produtos;
import br.com.sorveteriajanuaria.negocio.BancoDadosUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Késia Correiia
 */
public class ProdutosDAO {
   private static final String INSERT = "INSERT INTO  PRODUTO (NOME, SABOR, PRECO)VALUES (?,?,?)";
   private static final String BUSCAR_TODOS = "SELECT NOME, SABOR, PRECO, ID FROM PRODUTO ORDER BY NOME";
   private static final String DELETE = "DELETE FROM PRODUTO WHERE ID=?";
   private static final String UPDATE = "UPDATE PRODUTO SET NOME = ?, SABOR=?, PRECO = ? WHERE ID = ?";  
   
   
   public void inserir(Produtos produtos) throws SQLException{
        Connection conexao = null;
        PreparedStatement comando = null;
        try {
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(INSERT);
            comando.setString(1, produtos.getNome());
            comando.setString(2, produtos.getSabor());
            comando.setDouble(3, produtos.getPreco());
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
   
   public void alterar(Produtos produtos) throws SQLException {
       Connection conexao = null;
        PreparedStatement comando = null;

        try {
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(UPDATE);
            comando.setString(1, produtos.getNome());
            comando.setString(2, produtos.getSabor());
            comando.setDouble(3, produtos.getPreco());
            comando.setInt(4, produtos.getIdProdutos());
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
     
     public List<Produtos> buscarTodos() throws SQLException{
        List<Produtos> listaUsuarios = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;
        
        try{
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(BUSCAR_TODOS);
            resultado = comando.executeQuery();           
            while(resultado.next()){
                Produtos produtos = this.extrairLinhaResultadoBuscarTodas(resultado);
                listaUsuarios.add(produtos);
            }    
        }finally {
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando);
        }
        return listaUsuarios;
    }
     
    private Produtos extrairLinhaResultadoBuscarTodas(ResultSet resultado) throws SQLException {
        Produtos produtos = this.extrairLinhaResultado(resultado);
        return produtos;
    }

    private Produtos extrairLinhaResultado(ResultSet resultado) throws SQLException {
        Produtos produtos = new Produtos();
        produtos.setNome(resultado.getString(1));
        produtos.setSabor(resultado.getString(2));
        produtos.setPreco(resultado.getDouble(3));
        produtos.setIdProdutos(resultado.getInt(4));
        return produtos;
    }  
}
