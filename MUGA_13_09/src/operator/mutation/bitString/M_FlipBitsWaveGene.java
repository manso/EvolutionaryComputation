/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.mutation.bitString;

import genetic.gene.Gene;
import genetic.population.Population;
import java.util.StringTokenizer;
import operator.mutation.Mutation;
import problem.Individual;

/**
 *
 * @author arm
 */
public class M_FlipBitsWaveGene extends Mutation {
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

    @Override
    public void doMutation(Individual ind) {
        //for all genes
        for (int i = 0; i < ind.getNumGenes(); i++) {
            Gene g = ind.getGene(i);
            //automatic probability
            double prob = probability == 0 ? 0.1 / (double) g.getAlels().getNumberOfBits() : probability;
            prob += waveFunctionSin(ind, EXPONENT);
            //for all bits
            for (int j = 0; j < g.getNumBits(); j++) {
                if (random.nextDouble() < prob) {
                    ind.setIsEvaluated(false);
                    g.getAlels().invertBit(j);
                }
            }
        }
    }

    @Override
    public Population execute(Population pop) {
        Population childs = pop.getCleanCopie();
        //all individuals
        for (int i = 0; i < pop.getNumIndividuals(); i++) {
            //clone of original
            Individual ind = pop.getIndividual(i).getClone();
            ind.setNumCopys(1);
            //add clone mutated
            doMutation(ind);
            childs.addIndividual(ind);
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
        M_FlipBitsWaveGene mut = (M_FlipBitsWaveGene) super.getClone();
        mut.WAVE = this.WAVE;
        mut.EXPONENT = this.EXPONENT;
        return mut;
    }
}
