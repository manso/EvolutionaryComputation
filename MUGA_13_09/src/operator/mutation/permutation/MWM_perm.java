/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.mutation.permutation;

import genetic.population.Population;
import java.util.Iterator;
import java.util.StringTokenizer;
import operator.mutation.Mutation;
import problem.Individual;
import problem.PRM_Individual;

/**
 *
 * @author arm
 */
public class MWM_perm extends Mutation {
    //lenght of the wave for probability

    static double DEFAULT_EXP = 3;
    protected double EXPONENT = DEFAULT_EXP;
    //lenght of the wave for probability
    static double DEFAULT_WAVE = 2;
    double WAVE = DEFAULT_WAVE;

    public double waveFunctionSin(Individual ind, double exp) {
        double val = (-Math.sin(Math.PI / 2 + (ind.getNumCopies() - 1) / WAVE) + 1) / 2.0;
        return Math.pow(val, EXPONENT);
    }

    /**
     * mutate one individual ind must contains the copy number in .numOfCopys
     *
     * @param ind individual mutateded with the copy number
     */
    @Override
    public void doMutation(Individual ind1) {
        PRM_Individual ind = (PRM_Individual) ind1;
        int[] genes = ind.getGeneValues();
        //include minimal probability
        double prob = probability == 0 ? 0.1 / (double) genes.length : probability;
        prob += waveFunctionSin(ind, EXPONENT);
//        Console.println(ind.getNumCopies() + " Prob = " + prob);
        //individual is mutated ?
        boolean mut = false;
        //for all bits
        for (int j = 0; j < genes.length; j++) {
            //verify probability
            if (random.nextDouble() < prob) {
                mut = true;
                genes[j] = -genes[j];
            }
        }
        //update genome if necessary
        if (mut) {
            ind1.setIsEvaluated(false);
        }
    }

    /**
     * Mutate one population
     *
     * @param pop popupation to mutate
     * @return population mutated
     */
    @Override
    public Population execute(Population pop) {
        //new population
        Population childs = pop.getCleanCopie();
        //for all genotypes
        Iterator<Individual> iter = pop.getIterator();
        while (iter.hasNext()) {
            //select one individual
            Individual ind = iter.next();
            //for all copies
            for (int i = 0; i < ind.getNumCopies(); i++) {
                //make a clone
                Individual mut = ind.getClone();
                //set number of copie
                mut.setNumCopys(i + 1);
                //mutate
                doMutation(mut);
                //clean number of copies
                mut.setNumCopys(1);
                //append to new population
                childs.addIndividual(mut, 1);
            }
        }
        return childs;
    }

    @Override
    public String getInformation() {
        StringBuilder buf = new StringBuilder();
        buf.append(this.toString());
        buf.append("\n\nMultiset Flip Bit Mutation");
        buf.append("\nMulti individuals are splited in many single individuals");
        buf.append("\nEach individuals receives a copy number");
        buf.append("\nProbability of mutation is a wave function (SIN)");
        buf.append("\nwhere copy number are parameter");
        buf.append("\n\nParameters: <PROB> <WAVE> <EXP>");
        buf.append("\n    <PROB>  - minimal probability to mutate one bit");
        buf.append("\n             [0]- AUTOmatic probability ( 1 / lenght )");
        buf.append("\n    <WAVE>  - wave lenght (0.5-Short 2-default 6-Long)");
        buf.append("\n    <EXP>   - Exponential of the wave ");
        return buf.toString();
    }

    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    @Override
    public void setParameters(String param) {
        StringTokenizer iter = new StringTokenizer(param);
        if (iter.hasMoreTokens()) {
            //number of itens
            try {

                probability = Double.parseDouble(iter.nextToken());
                if (probability > 1 || probability < 0) {
                    probability = 0;
                }
            } catch (Exception e) {
                probability = DEFAULT_PROBABILITY;
            }
        }
        //-----------------------------------------------------------
        if (iter.hasMoreTokens()) {
            //number of itens
            try {

                WAVE = Double.parseDouble(iter.nextToken());
                if (WAVE < 0) {
                    WAVE = DEFAULT_WAVE;
                }
            } catch (Exception e) {
                WAVE = DEFAULT_WAVE;
            }
            DEFAULT_WAVE = WAVE;
        }
        //-----------------------------------------------------------
        if (iter.hasMoreTokens()) {
            //number of itens
            try {
                EXPONENT = Double.parseDouble(iter.nextToken());
            } catch (Exception e) {
                EXPONENT = 2;
            }
            DEFAULT_EXP = EXPONENT;
        }

    }
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------

    @Override
    public String getParameters() {
        return probability + " " + WAVE + " " + EXPONENT;
    }
    //-------------------------------------------------------------------------

    @Override
    public String toString() {
        if (probability == 0) {
            return this.getClass().getSimpleName() + "<0.1/L>"
                    + "<" + WAVE + "><" + EXPONENT + ">";
        }

        return this.getClass().getSimpleName() + "<" + probability + ">"
                + "<" + WAVE + "><" + EXPONENT + ">";
    }
    //-------------------------------------------------------------------------

    @Override
    public Mutation getClone() {
        MWM_perm mut = (MWM_perm) super.getClone();
        mut.WAVE = this.WAVE;
        mut.EXPONENT = this.EXPONENT;
        return mut;
    }
}
