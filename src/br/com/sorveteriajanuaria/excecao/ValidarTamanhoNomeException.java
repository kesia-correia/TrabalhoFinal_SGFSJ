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
public class ValidarTamanhoNomeException extends SorveteriaJanuariaException{
    public ValidarTamanhoNomeException(){
        super("O nome só pode conter no máximo 60 caracteres!!");
    }
}
