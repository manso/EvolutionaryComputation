/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.replacement;

import genetic.population.Population;
import problem.IndividualPermutation;
import problem.PRM_Individual;
import problem.RC_Individual;

/**
 *
 * @author arm
 */
public class Default extends Replacement {

    Replacement real = new Gerational();
    Replacement perm = new M_Decimation();
    Replacement bits = new M_ClearingRandom();

    @Override
    public Population execute(Population pop, Population childs) {

        if (solver.getTemplate() instanceof PRM_Individual) {
            perm.setSolver(this.solver);
            return perm.execute(pop, childs);
        }
        if (solver.getTemplate() instanceof RC_Individual) {
            real.setSolver(this.solver);
            return real.execute(pop, childs);
        }
        bits.setSolver(this.solver);
        return bits.execute(pop, childs);
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
        if (solver == null) {
            return "<Default>";
        }
        if (solver.getTemplate() instanceof IndividualPermutation) {
            return "<Default>" + perm.getInformation();
        }
        if (solver.getTemplate() instanceof RC_Individual) {
            return "<Default>" + real.getInformation();
        }
        return "<Default>" + bits.getInformation();
    }

    @Override
    public Replacement getClone() {
        if (solver.getTemplate() instanceof IndividualPermutation) {
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
        if (solver.getTemplate() instanceof IndividualPermutation) {
            perm.setParameters(str);
        } else if (solver.getTemplate() instanceof RC_Individual) {
            real.setParameters(str);
        } else {
            bits.setParameters(str);
        }
    }
}
