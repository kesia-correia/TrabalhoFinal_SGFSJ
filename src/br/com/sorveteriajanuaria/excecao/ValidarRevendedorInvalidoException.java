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
public class ValidarRevendedorInvalidoException extends SorveteriaJanuariaException{
      public ValidarRevendedorInvalidoException(){
          super("Não há nenhum revendedor cadastrado!! Favor, cadastrar um revendedor!!");
      }
}
