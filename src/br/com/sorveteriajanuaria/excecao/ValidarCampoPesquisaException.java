/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sorveteriajanuaria.excecao;

/**
 *
 * @author Késia Correiia
 */
public class ValidarCampoPesquisaException extends SorveteriaJanuariaException {
    public ValidarCampoPesquisaException(){
        super("Para realizar a pesquisa e necessario que se preencha o(s) campo(s)!!");
    }
}
