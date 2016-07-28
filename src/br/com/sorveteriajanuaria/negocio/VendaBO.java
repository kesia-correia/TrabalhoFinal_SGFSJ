/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sorveteriajanuaria.negocio;

import br.com.sorveteriajanuaria.entidade.ItemVenda;
import br.com.sorveteriajanuaria.entidade.Produtos;
import br.com.sorveteriajanuaria.entidade.Venda;
import br.com.sorveteriajanuaria.excecao.ConsultaSemResultadoException;
import br.com.sorveteriajanuaria.excecao.ValidarQuantidadeDeItensException;
import br.com.sorveteriajanuaria.persistencia.ProdutosDAO;
import br.com.sorveteriajanuaria.persistencia.VendaDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Késia Correiia
 */
public class VendaBO {
     public void inserir(Venda venda, List<ItemVenda> itens) throws SQLException {
        this.verificarQuantidadeItens(itens);
        VendaDAO vendaDAO = new VendaDAO();
        vendaDAO.inserir(venda, itens);
    }

    public void excluir(int id) throws SQLException {
        VendaDAO vendaDAO = new VendaDAO();
        vendaDAO.excluir(id);
    }

    public List<Venda> buscarTodas() throws SQLException {
        VendaDAO vendaDAO = new VendaDAO();
        return vendaDAO.buscarTodos();
    }

    public List<Produtos> pesquisar(String filtro, String palavraPesquisada) throws SQLException {
        ProdutosDAO produtosDAO = new ProdutosDAO();
        List<Produtos> listaProdutos = new ArrayList<>();
        List<Produtos> listaProdutosFiltrada = new ArrayList<>();
        
        listaProdutos = produtosDAO.buscarTodos();

        for (Produtos listaProduto : listaProdutos) {

            if ("NOME".equals(filtro)) {
                if (listaProduto.getNome().contains(palavraPesquisada)
                    || listaProduto.getNome().contains(palavraPesquisada.toUpperCase())
                    || listaProduto.getNome().contains(palavraPesquisada.toLowerCase())
                    ||listaProduto.getNome().contains(palavraPesquisada.substring(0,1).toUpperCase().concat(palavraPesquisada.substring(1)))) 
                {
                    listaProdutosFiltrada.add(listaProduto);
                }
            } else if ("SABOR".equals(filtro)) {
                if (listaProduto.getSabor().contains(palavraPesquisada)
                    || listaProduto.getSabor().contains(palavraPesquisada.toUpperCase())
                    || listaProduto.getSabor().contains(palavraPesquisada.toLowerCase())
                    ||listaProduto.getSabor().contains(palavraPesquisada.substring(0,1).toUpperCase().concat(palavraPesquisada.substring(1)))) 
                {
                    listaProdutosFiltrada.add(listaProduto);
                }
            } 
        }
        if (listaProdutosFiltrada.size() > 0) {
            return listaProdutosFiltrada;
        } else {
            throw new ConsultaSemResultadoException();
        }
    }

    public void verificarQuantidadeItens(List<ItemVenda> itens) {
        if (itens == null || itens.size() <= 0) {
            throw new ValidarQuantidadeDeItensException();
        }
    }
    
}
