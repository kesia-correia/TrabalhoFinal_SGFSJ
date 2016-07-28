/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sorveteriajanuaria.persistencia;

import br.com.sorveteriajanuaria.entidade.Revendedor;
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
public class RevendedorDAO {
   private static final String INSERT = "INSERT INTO  REVENDEDOR (NOME, CNPJ, CPF,RG, DATA_NASCIMENTO, TELEFONE, ENDERECO)VALUES (?,?,?,?,?,?,?)";
   private static final String BUSCAR_TODOS = "SELECT NOME, CNPJ, CPF, RG, DATA_NASCIMENTO, TELEFONE, ENDERECO, ID FROM REVENDEDOR ORDER BY NOME";
   private static final String DELETE = "DELETE FROM REVENDEDOR WHERE ID=?";
   private static final String UPDATE = "UPDATE REVENDEDOR SET NOME = ?, CNPJ=?, CPF = ?, RG = ?, DATA_NASCIMENTO = ?, TELEFONE = ?, ENDERECO = ? WHERE ID = ?";  
   
   public void inserir(Revendedor revendedor) throws SQLException{
        Connection conexao = null;
        PreparedStatement comando = null;
        try {
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(INSERT);
            comando.setString(1, revendedor.getNomeRevendor());
            comando.setString(2, revendedor.getCNPJ());
            comando.setString(3, revendedor.getCPF());
            comando.setString(4, revendedor.getRG());
            java.sql.Date data = new java.sql.Date(revendedor.getDataNascimento().getTime());
            comando.setDate(5, data);
            comando.setString(6, revendedor.getTelefone());
            comando.setString(7, revendedor.getEndereco());  
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
        
    public void alterar(Revendedor revendedor) throws SQLException {
       Connection conexao = null;
        PreparedStatement comando = null;

        try {
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(UPDATE);
            comando.setString(1, revendedor.getNomeRevendor());
            comando.setString(2, revendedor.getCNPJ());
            comando.setString(3, revendedor.getCPF());
            comando.setString(4, revendedor.getRG());
             
            java.sql.Date data = new java.sql.Date(revendedor.getDataNascimento().getTime());
            comando.setDate(5, data);
            comando.setString(6, revendedor.getTelefone());
            comando.setString(7, revendedor.getEndereco());
            comando.setInt(8, revendedor.getIdRevendedor());
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
     
     public List<Revendedor> buscarTodos() throws SQLException{
        List<Revendedor> listaRevendedores = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;
        
        try{
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(BUSCAR_TODOS);
            resultado = comando.executeQuery();           
            while(resultado.next()){
                Revendedor revendedor = this.extrairLinhaResultadoBuscarTodas(resultado);
                listaRevendedores.add(revendedor);
            }    
        }finally {
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando);
        }
        return listaRevendedores;
    }
     
    private Revendedor extrairLinhaResultadoBuscarTodas(ResultSet resultado) throws SQLException {
        Revendedor revendedor = this.extrairLinhaResultado(resultado);
        return revendedor;
    }

    private Revendedor extrairLinhaResultado(ResultSet resultado) throws SQLException {
        Revendedor revendedor = new Revendedor();
        revendedor.setNomeRevendor(resultado.getString(1));
        revendedor.setCNPJ(resultado.getString(2));
        revendedor.setCPF(resultado.getString(3));
        revendedor.setRG(resultado.getString(4));
        java.sql.Date dataNascimento = resultado.getDate(5);
        revendedor.setDataNascimento(new java.sql.Date(dataNascimento.getTime()));
        revendedor.setTelefone(resultado.getString(6));
        revendedor.setEndereco(resultado.getString(7));
        revendedor.setIdRevendedor(resultado.getInt(8));
        return revendedor;
    }  
    
}
