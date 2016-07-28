/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sorveteriajanuaria.negocio;

import br.com.sorveteriajanuaria.entidade.Usuario;
import br.com.sorveteriajanuaria.excecao.ConsultaSemResultadoException;
import br.com.sorveteriajanuaria.excecao.UsuarioLoginExistenteException;
import br.com.sorveteriajanuaria.excecao.ValidarLoginESenhaException;
import br.com.sorveteriajanuaria.persistencia.UsuarioDAO;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Késia Correiia
 */
public class UsuarioBO {
    
    public void inserir(Usuario usuario) throws SQLException {
        this.verificaUsuarioLoginExistente(usuario);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.inserir(usuario);
    }
    
    static public String converterHexadecimalParaString(byte[] valorHexadecimal){
        StringBuilder valorConvertido = new StringBuilder();
          
        for(byte caracter : valorHexadecimal){
            valorConvertido.append(String.format("%02X", 0xFF & caracter));
        }
        return valorConvertido.toString();
    }
    
    static public String MD5(String texto) throws NoSuchAlgorithmException, UnsupportedEncodingException{  
        MessageDigest algoritmo = MessageDigest.getInstance("MD5");
        byte[] codigoHashHexaDecimal = algoritmo.digest(texto.getBytes("UTF-8"));
        
        String codigoHashFinal = converterHexadecimalParaString(codigoHashHexaDecimal);
        
        return codigoHashFinal;
    }
    
    public void atualizarDadosSenha(Usuario usuario) throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.atualizarDadosSenha(usuario);
    }
    
    public Usuario login (String login, String senha) throws SQLException{
        Usuario usuarioAtual = null;
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioAtual = usuarioDAO.buscarLoginAndSenha(login, senha);
        if (usuarioAtual != null) {
            return usuarioAtual;
        }else{
            throw new ValidarLoginESenhaException();
        }
    }
    
    public void verificaUsuarioLoginExistente(Usuario usuario) throws SQLException {
        Usuario usuarioAtual = null;
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioAtual = usuarioDAO.buscarLogin(usuario.getLogin());
        if (usuarioAtual != null) {
            throw new UsuarioLoginExistenteException();
        }
    }
    
    

    public List<Usuario> pesquisar(String filtro, String palavraPesquisada) throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> listaUsuarios = new ArrayList<>();
        List<Usuario> listaUsuariosFiltrada = new ArrayList<>();

        listaUsuarios = usuarioDAO.buscarTodos();

        for (Usuario listaUsuario : listaUsuarios) {

            if ("NOME".equals(filtro)) {
                if (listaUsuario.getNome().contains(palavraPesquisada)
                    || listaUsuario.getNome().contains(palavraPesquisada.toUpperCase())
                    || listaUsuario.getNome().contains(palavraPesquisada.toLowerCase())
                    ||listaUsuario.getNome().contains(palavraPesquisada.substring(0,1).toUpperCase().concat(palavraPesquisada.substring(1)))) 
                {
                    listaUsuariosFiltrada.add(listaUsuario);
                }
            } else {
                if (listaUsuario.getLogin().contains(palavraPesquisada)
                    || listaUsuario.getLogin().contains(palavraPesquisada.toUpperCase())
                    || listaUsuario.getLogin().contains(palavraPesquisada.toLowerCase())
                    ||listaUsuario.getLogin().contains(palavraPesquisada.substring(0,1).toUpperCase().concat(palavraPesquisada.substring(1)))) 
                {
                    listaUsuariosFiltrada.add(listaUsuario);
                }
            }
        }
        if(listaUsuariosFiltrada.size() > 0) {
            return listaUsuariosFiltrada;
        } else {
            throw new ConsultaSemResultadoException();                    
        }
    }
    
    public void excluir(int ID) throws SQLException{
      UsuarioDAO usuarioDAO = new UsuarioDAO();
      usuarioDAO.excluir(ID);
    }
   
   public void alterar(Usuario usuario) throws Exception{
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.alterar(usuario);
   }
    
    public List<Usuario> buscarTodos() throws SQLException{
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.buscarTodos();
    }
}
