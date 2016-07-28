/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sorveteriajanuaria.negocio;

import br.com.sorveteriajanuaria.entidade.Produtos;
import br.com.sorveteriajanuaria.excecao.ConsultaSemResultadoException;
import br.com.sorveteriajanuaria.persistencia.ProdutosDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Késia Correiia
 */
public class ProdutosBO {
    public void inserir(Produtos produtos) throws SQLException {
       ProdutosDAO produtosDAO = new ProdutosDAO();
       produtosDAO.inserir(produtos);
    }
    
    public void excluir(int ID) throws SQLException{
     ProdutosDAO produtosDAO = new  ProdutosDAO();
      produtosDAO .excluir(ID);
    }
   
   public void alterar(Produtos produtos) throws Exception{
       ProdutosDAO produtosDAO = new ProdutosDAO();
       produtosDAO.alterar(produtos);
   }
    
    public List<Produtos> buscarTodos() throws SQLException{
       ProdutosDAO produtosDAO = new ProdutosDAO();
        return produtosDAO.buscarTodos();
    }
    
    public List<Produtos> pesquisar(String filtro, String palavraPesquisada) throws SQLException {
        ProdutosDAO produtosDAO = new ProdutosDAO();
        List<Produtos> listaProdutos = new ArrayList<>();
        List<Produtos> listaProdutosFiltrada = new ArrayList<>();

        listaProdutos = produtosDAO.buscarTodos();

        for (Produtos listaProduto : listaProdutos){
            if ("NOME".equals(filtro)) {
                if (listaProduto.getNome().contains(palavraPesquisada)
                    || listaProduto.getNome().contains(palavraPesquisada.toUpperCase())
                    || listaProduto.getNome().contains(palavraPesquisada.toLowerCase())
                    ||listaProduto.getNome().contains(palavraPesquisada.substring(0,1).toUpperCase().concat(palavraPesquisada.substring(1)))) 
                {
                    listaProdutosFiltrada.add(listaProduto);
                }
            } 
        }
        if(listaProdutosFiltrada.size() > 0) {
            return listaProdutosFiltrada;
        } else {
            throw new ConsultaSemResultadoException();                    
        }
    }
}
