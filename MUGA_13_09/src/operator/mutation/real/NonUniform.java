/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.mutation.real;

import java.util.ArrayList;
import problem.Individual;

/**
 *
 * @author manso
 */
public class NonUniform extends MuGA_Gauss_Contract {

    @Override
    public double getMutation() {
        if (solver != null && solver.getStop() != null) {
            return random.nextGaussian() * solver.getStop().getProgress();
        } else {
            return random.nextGaussian();
        }
    }
}
