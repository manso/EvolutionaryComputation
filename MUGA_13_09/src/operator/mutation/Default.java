/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.mutation;

import genetic.population.Population;
import operator.mutation.bitString.FlipBitsInGene;
import operator.mutation.permutation.MWM_perm;
import operator.mutation.real.MuGA_Gauss_Contract;
import problem.Individual;
import problem.PRM_Individual;
import problem.RC_Individual;

/**
 *
 * @author arm
 */
public class Default extends Mutation {

    Mutation real = new MuGA_Gauss_Contract();
    Mutation perm = new MWM_perm();
    Mutation bits = new FlipBitsInGene();

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
        if (solver == null) {
            return "(Default)";
        }
        if (solver.getTemplate() instanceof PRM_Individual) {
            return "(Default)" + perm.getInformation();
        }
        if (solver.getTemplate() instanceof RC_Individual) {
            return "(Default)" + real.getInformation();
        }
        return "(Default)" + bits.getInformation();
    }

    @Override
    public Mutation getClone() {
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

    @Override
    public void doMutation(Individual ind) {
        if (solver.getTemplate() instanceof PRM_Individual) {
            perm.doMutation(ind);
        } else if (solver.getTemplate() instanceof RC_Individual) {
            real.doMutation(ind);
        } else {
            bits.doMutation(ind);
        }
    }
}
