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
public class ValidarSenhaException extends SorveteriaJanuariaException {
    public ValidarSenhaException(){
        super("Senha incorreta!");
    }
    
}
