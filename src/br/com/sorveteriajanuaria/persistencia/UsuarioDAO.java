/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sorveteriajanuaria.persistencia;

import br.com.sorveteriajanuaria.entidade.Usuario;
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
public class UsuarioDAO {
  private static final String INSERT = "INSERT INTO  USUARIO (NOME, LOGIN, SENHA)VALUES (?,?,?)";
  private static final String BUSCAR_TODOS = "SELECT NOME, LOGIN, SENHA,ID FROM USUARIO ORDER BY NOME";
  private static final String DELETE = "DELETE FROM USUARIO WHERE ID=?";
  private static final String UPDATE = "UPDATE USUARIO SET NOME = ?, LOGIN = ?, SENHA = ? WHERE ID = ?";  
  private static final String BUSCAR_LOGIN = "SELECT * FROM USUARIO WHERE LOGIN = ?";
  private static final String BUSCAR_LOGIN_E_SENHA = "SELECT * FROM USUARIO WHERE LOGIN = ? AND SENHA = ? ";
  private static final String UPDATE_SENHA = "UPDATE USUARIO SET SENHA = ? WHERE ID = ?";
  
  public void inserir(Usuario usuario) throws SQLException{
        Connection conexao = null;
        PreparedStatement comando = null;
        try {
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(INSERT);
            comando.setString(1, usuario.getNome());
            comando.setString(2, usuario.getLogin());
            comando.setString(3, usuario.getSenha());
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
  
     public void atualizarDadosSenha(Usuario usuario) throws SQLException{
        PreparedStatement comando = null;
        Connection conexao = null;    
        try{
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(UPDATE_SENHA);
            comando.setString(1, usuario.getSenha());
            comando.setInt(2, usuario.getIdUsuario());
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

    public Usuario buscarLogin(String login) throws SQLException {
      Usuario usuario = null;
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;
        try{
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(BUSCAR_LOGIN);
            comando.setString(1, login);
            resultado = comando.executeQuery();
            
            while(resultado.next()){
                usuario = new Usuario();
                usuario.setIdUsuario(resultado.getInt(1));
                usuario.setNome(resultado.getString(2));
                usuario.setLogin(resultado.getString(3));
                usuario.setSenha(resultado.getString(4));
            }
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando, resultado);
        } catch (Exception e) {
            if (conexao != null) {
                conexao.rollback();
            }
        } finally {
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando, resultado);
        }
        return usuario;
    }
    
     public Usuario buscarLoginAndSenha(String login, String senha) throws SQLException{
    
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;
        Usuario usuario = null;   
        try{
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(BUSCAR_LOGIN_E_SENHA); 
            comando.setString(1, login); 
            comando.setString(2, senha);
            resultado = comando.executeQuery();           
            while(resultado.next()){
                usuario = new Usuario();
                usuario.setIdUsuario(resultado.getInt(1));
                usuario.setNome(resultado.getString(2));
                usuario.setLogin(resultado.getString(3));
                usuario.setSenha(resultado.getString(4));
            }
        }catch(Exception e){
            if(conexao != null){
            conexao.rollback();           
            } 
        }finally{
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando, resultado);
        }
        return usuario;
    }
     
     
     public void alterar(Usuario usuario) throws SQLException {
       Connection conexao = null;
        PreparedStatement comando = null;

        try {
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(UPDATE);
            comando.setString(1, usuario.getNome());
            comando.setString(2, usuario.getLogin());
            comando.setString(3, usuario.getSenha());
            comando.setInt(4, usuario.getIdUsuario());
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
     
     public List<Usuario> buscarTodos() throws SQLException{
        List<Usuario> listaUsuarios = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;
        
        try{
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(BUSCAR_TODOS);
            resultado = comando.executeQuery();           
            while(resultado.next()){
                Usuario usuario = this.extrairLinhaResultadoBuscarTodas(resultado);
                listaUsuarios.add(usuario);
            }    
        }finally {
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando);
        }
        return listaUsuarios;
    }
 
    private Usuario extrairLinhaResultadoBuscarTodas(ResultSet resultado) throws SQLException {
        Usuario usuario = this.extrairLinhaResultado(resultado);
        return usuario;
    }

    private Usuario extrairLinhaResultado(ResultSet resultado) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setNome(resultado.getString(1));
        usuario.setLogin(resultado.getString(2));
        usuario.setSenha(resultado.getString(3));
        usuario.setIdUsuario(resultado.getInt(4));
        return usuario;
    }   
    
}