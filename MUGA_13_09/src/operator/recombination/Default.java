/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.recombination;

import genetic.population.Population;
import operator.recombination.bitString.Crossover;
import operator.recombination.permutation.PMX1_Negative;
import operator.recombination.real.MuGA_AX_Copys;
import problem.PRM_Individual;
import problem.RC_Individual;

/**
 *
 * @author arm
 */
public class Default extends Recombination {

    Recombination real = new MuGA_AX_Copys();
    Recombination perm = new PMX1_Negative();
    Recombination bits = new Crossover();

    @Override
    public Population execute(Population pop) {
        if (solver.getTemplate() instanceof PRM_Individual) {
            perm.setSolver(this.solver);
            return perm.execute(pop);
        }
        if (solver.getTemplate() instanceof RC_Individual) {
            real.setSolver(this.solver);
            return real.execute(pop);
        }
        bits.setSolver(this.solver);
        return bits.execute(pop);
    }

    @Override
    public String toString() {

        if (solver.getTemplate() instanceof PRM_Individual) {
            return "(Default)" + perm.toString();
        }
        if (solver.getTemplate() instanceof RC_Individual) {
            return "(Default)" + real.toString();
        }
        return "(Default)" + bits.toString();
    }

    @Override
    public String getInformation() {
        if (solver.getTemplate() instanceof PRM_Individual) {
            return "(Default)" + perm.getInformation();
        }
        if (solver.getTemplate() instanceof RC_Individual) {
            return "(Default)" + real.getInformation();
        }
        return "(Default)" + bits.getInformation();
    }

    @Override
    public Recombination getClone() {
        if (solver.getTemplate() instanceof PRM_Individual) {
            return perm.getClone();
        }
        if (solver.getTemplate() instanceof RC_Individual) {
            return real.getClone();
        }
        return bits.getClone();
    }

    @Override
    public void setParameters(String str) {
        if (solver == null) {
            return;
        }
        if (solver.getTemplate() instanceof PRM_Individual) {
            perm.setParameters(str);
        } else if (solver.getTemplate() instanceof RC_Individual) {
            real.setParameters(str);
        } else {
            bits.setParameters(str);
        }
    }
}
