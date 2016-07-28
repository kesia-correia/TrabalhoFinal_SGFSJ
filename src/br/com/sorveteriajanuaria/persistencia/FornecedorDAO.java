/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sorveteriajanuaria.persistencia;

import br.com.sorveteriajanuaria.entidade.Fornecedor;
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
public class FornecedorDAO {
    private static final String INSERT = "INSERT INTO  FORNECEDOR (NOME, CNPJ, TELEFONE, ENDERECO, OBSERVACOES)VALUES (?,?,?,?,?)";
    private static final String BUSCAR_TODOS = "SELECT NOME, CNPJ, TELEFONE, ENDERECO, OBSERVACOES,ID FROM FORNECEDOR ORDER BY NOME";
    private static final String DELETE = "DELETE FROM FORNECEDOR WHERE ID=?";
    private static final String UPDATE = "UPDATE FORNECEDOR SET NOME = ?, CNPJ=?, TELEFONE = ?, ENDERECO = ?,  OBSERVACOES = ? WHERE ID = ?";  
   
   
     public void inserir(Fornecedor fornecedor) throws SQLException{
        Connection conexao = null;
        PreparedStatement comando = null;
        try {
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(INSERT);
            comando.setString(1, fornecedor.getNome());
            comando.setString(2, fornecedor.getCNPJ());
            comando.setString(3, fornecedor.getTelefone());
            comando.setString(4, fornecedor.getEndereco());
            comando.setString(5, fornecedor.getObservacoes());
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
     
     public void alterar(Fornecedor fornecedor) throws SQLException {
       Connection conexao = null;
        PreparedStatement comando = null;

        try {
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(UPDATE);
            comando.setString(1, fornecedor.getNome());
            comando.setString(2, fornecedor.getCNPJ());
            comando.setString(3,fornecedor.getTelefone());
            comando.setString(4, fornecedor.getEndereco());
            comando.setString(5, fornecedor.getObservacoes());
            comando.setInt(6, fornecedor.getIdFornecedor());
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
     
     public List<Fornecedor> buscarTodos() throws SQLException{
        List<Fornecedor> listaFornecedores = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;
        
        try{
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(BUSCAR_TODOS);
            resultado = comando.executeQuery();           
            while(resultado.next()){
                Fornecedor fornecedor = this.extrairLinhaResultadoBuscarTodas(resultado);
                listaFornecedores.add(fornecedor);
            }    
        }finally {
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando);
        }
        return listaFornecedores;
    }
     
    private Fornecedor extrairLinhaResultadoBuscarTodas(ResultSet resultado) throws SQLException {
        Fornecedor fornecedor = this.extrairLinhaResultado(resultado);
        return fornecedor;
    }

    private Fornecedor extrairLinhaResultado(ResultSet resultado) throws SQLException {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(resultado.getString(1));
        fornecedor.setCNPJ(resultado.getString(2));
        fornecedor.setTelefone(resultado.getString(3));
        fornecedor.setEndereco(resultado.getString(4));
        fornecedor.setObservacoes(resultado.getString(5));
        fornecedor.setIdFornecedor(resultado.getInt(6));
        return fornecedor;
    }
} 

