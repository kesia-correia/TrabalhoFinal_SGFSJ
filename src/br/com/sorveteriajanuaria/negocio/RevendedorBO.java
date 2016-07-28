/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sorveteriajanuaria.negocio;

import br.com.sorveteriajanuaria.entidade.Revendedor;
import br.com.sorveteriajanuaria.excecao.ConsultaSemResultadoException;
import br.com.sorveteriajanuaria.persistencia.RevendedorDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Késia Correiia
 */
public class RevendedorBO {
    public void inserir(Revendedor revendedor) throws SQLException {
      RevendedorDAO revendedorDAO = new RevendedorDAO();
      revendedorDAO.inserir(revendedor);
    }
    
    public void excluir(int ID) throws SQLException{
     RevendedorDAO revendedorDAO = new  RevendedorDAO();
      revendedorDAO .excluir(ID);
    }
   
    public void alterar(Revendedor revendedor) throws Exception{
      RevendedorDAO revendedorDAO = new RevendedorDAO();
       revendedorDAO.alterar(revendedor);
    }
    
    public List<Revendedor> buscarTodos() throws SQLException{
       RevendedorDAO revendedorDAO = new RevendedorDAO();
        return revendedorDAO.buscarTodos();
    }

    public List<Revendedor> pesquisar(String filtro, String palavraPesquisada) throws SQLException {
        RevendedorDAO revendedorDAO = new RevendedorDAO();
        List<Revendedor> listaRevendedores = new ArrayList<>();
        List<Revendedor> listaRevendedoresFiltrada = new ArrayList<>();

        listaRevendedores = revendedorDAO.buscarTodos();

        for (Revendedor listaRevendedor : listaRevendedores){
            if ("NOME".equals(filtro)) {
                if (listaRevendedor.getNomeRevendor().contains(palavraPesquisada)
                    || listaRevendedor.getNomeRevendor().contains(palavraPesquisada.toUpperCase())
                    || listaRevendedor.getNomeRevendor().contains(palavraPesquisada.toLowerCase())
                    ||listaRevendedor.getNomeRevendor().contains(palavraPesquisada.substring(0,1).toUpperCase().concat(palavraPesquisada.substring(1)))) 
                {
                    listaRevendedoresFiltrada.add(listaRevendedor);
                }
            } else {
                if (listaRevendedor.getRG().contains(palavraPesquisada)){
                    listaRevendedoresFiltrada.add(listaRevendedor);
                }
            }
        }
        if(listaRevendedoresFiltrada.size() > 0) {
            return listaRevendedoresFiltrada;
        } else {
            throw new ConsultaSemResultadoException();                    
        }
    }
}
