/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sorveteriajanuaria.negocio;

import br.com.sorveteriajanuaria.entidade.Fornecedor;
import br.com.sorveteriajanuaria.excecao.ConsultaSemResultadoException;
import br.com.sorveteriajanuaria.persistencia.FornecedorDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Késia Correiia
 */
public class FornecedorBO {
     public void inserir(Fornecedor fornecedor) throws SQLException {
      FornecedorDAO fornecedorDAO = new FornecedorDAO();
      fornecedorDAO.inserir(fornecedor);
    }
     
    public void excluir(int ID) throws SQLException{
      FornecedorDAO fornecedorDAO = new  FornecedorDAO();
      fornecedorDAO .excluir(ID);
    }
   
   public void alterar(Fornecedor fornecedor) throws Exception{
       FornecedorDAO fornecedorDAO = new FornecedorDAO();
       fornecedorDAO.alterar(fornecedor);
   }
    
    public List<Fornecedor> buscarTodos() throws SQLException{
       FornecedorDAO fornecedorDAO = new FornecedorDAO();
        return fornecedorDAO.buscarTodos();
    }
    
   public List<Fornecedor> pesquisar(String filtro, String palavraPesquisada) throws SQLException {
        FornecedorDAO fornecedorDAO = new FornecedorDAO();
        List<Fornecedor> listaFornecedores = new ArrayList<>();
        List<Fornecedor> listaFornecedoresFiltrada = new ArrayList<>();

       listaFornecedores = fornecedorDAO.buscarTodos();

        for (Fornecedor listaFornecedor : listaFornecedores){
            if ("NOME".equals(filtro)) {
                if (listaFornecedor.getNome().contains(palavraPesquisada)
                    || listaFornecedor.getNome().contains(palavraPesquisada.toUpperCase())
                    || listaFornecedor.getNome().contains(palavraPesquisada.toLowerCase())
                    ||listaFornecedor.getNome().contains(palavraPesquisada.substring(0,1).toUpperCase().concat(palavraPesquisada.substring(1)))) 
                {
                    listaFornecedoresFiltrada.add(listaFornecedor);
                }
            } else {
                if (listaFornecedor.getCNPJ().contains(palavraPesquisada)){
                    listaFornecedoresFiltrada.add(listaFornecedor);
                }
            }
        }
        if(listaFornecedoresFiltrada.size() > 0) {
            return listaFornecedoresFiltrada;
        } else {
            throw new ConsultaSemResultadoException();                    
        }
    }
}
