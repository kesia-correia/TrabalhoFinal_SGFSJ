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
public class ValidarTamanhoConfirmaSenhaException extends SorveteriaJanuariaException {
     public ValidarTamanhoConfirmaSenhaException(){
       super("O confirma senha só pode conter no máximo 32 caracteres!!");
     }
}
