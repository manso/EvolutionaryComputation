/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package statistics.elements;

import genetic.Solver.SimpleSolver;
import genetic.population.Population;



/**
 *
 * @author manso
 */
public class DifferentAlleles extends GeneticDiversity{

    public DifferentAlleles() {
    }
    public DifferentAlleles(Population pop) {
        super(pop);
    }

    @Override
    public double execute(SimpleSolver s) {
        super.execute(s);
        double difs=0;
        for(int i =0; i < hist.length; i++){
            if( hist[i]!= 0  && hist[i]!= s.getParents().getNumGenotypes())
                difs++;
        }
        setValue(difs/hist.length);
        return difs/hist.length ;
    }

     @Override
    public String getName() {
        return "Different Allels";
    }


}
