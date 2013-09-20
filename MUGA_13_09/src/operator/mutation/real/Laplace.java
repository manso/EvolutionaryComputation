/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.mutation.real;

import utils.RandomVariable;

/**
 *
 * @author manso
 */
public class Laplace extends MuGA_Gauss_Contract {
    @Override
 public double getMutation() {
        return RandomVariable.laplace(0.0, 1);
    }
   
}
