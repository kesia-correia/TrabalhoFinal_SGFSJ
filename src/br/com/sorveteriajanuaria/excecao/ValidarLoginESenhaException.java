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
public class ValidarLoginESenhaException extends SorveteriaJanuariaException{
    public ValidarLoginESenhaException(){
        super("O Login ou senha inseridos não correspondem a nenhum usuário cadastrado!!");
    }
}