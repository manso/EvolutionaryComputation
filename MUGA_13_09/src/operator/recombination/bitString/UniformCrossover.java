/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.recombination.bitString;

import problem.Individual;
import genetic.population.Population;
import operator.recombination.Recombination;
import utils.BitField;
import utils.DynamicLoad;

/**
 *
 * @author arm
 */
public class UniformCrossover extends Recombination {
    
    protected double PROB_OF_ONE = 0.5;
    /**
     * Build a Mask with cuts
     * uniform crossover do not use cuts
     * @param size size of the mas 
     * @param cuts number of cuts
     * @return 
     */
    public BitField buildMask(int size, int cuts) {
        BitField mask = new BitField(size);
        for (int i = 0; i < mask.getNumberOfBits(); i++) {
            if (random.nextDouble() < PROB_OF_ONE) {
                mask.setBitTrue(i);
            } 
        }
        return mask;
    }

    @Override
    public Population execute(Population pop) {
        Population childs = (Population) DynamicLoad.makeObject(pop);
        int numCrossovers = (int) ((pop.getNumIndividuals() * getProbability()) / 2.0);
        Individual i1, i2;
        //fazer os crossovers
        for (int i = 0; i < numCrossovers; i++) {
            i1 = pop.removeRandomIndividual().getClone();
            i2 = pop.removeRandomIndividual().getClone();
            //not the same genotype
            if (!i1.equals(i2)) {
                doCrossover(i1, i2);
            }
            childs.addIndividual(i1);
            childs.addIndividual(i2);
        }
        //insert remain individuals
        childs.appendPopulation(pop);
        return childs;
    }

    @Override
    public void doCrossover(Individual i1, Individual i2) {
        //build mask
        
        BitField b1 = i1.getBits();
        BitField b2 = i2.getBits();
        //uniform crossover do not use cuts
        BitField mask = buildMask(b1.getNumberOfBits() , 0);
        for (int i = 0; i < mask.getNumberOfBits(); i++) {
            //one in mask
            if (mask.getBit(i) && b1.getBit(i) != b2.getBit(i)) {
                //swap bits
                boolean aux = b1.getBit(i);
                b1.setBit(i, b2.getBit(i));
                b2.setBit(i, aux);
            }
        }
        i1.setBits(b1);
        i2.setBits(b2);
    }

   

    @Override
    public void setParameters(String s) {
        String[] params = s.split(" ");
        super.setParameters(params[0]);
        try {
            PROB_OF_ONE = Double.parseDouble(params[1]);
        } catch (Exception e) {
            PROB_OF_ONE = 0.5;
        }
        PROB_OF_ONE = PROB_OF_ONE < 0 || PROB_OF_ONE > 1 ? 0.5 : PROB_OF_ONE;

    }

    @Override
    public String getParameters() {
        return super.getParameters() + " " + PROB_OF_ONE;
    }

    /**
     * @return the probabiblityMask
     */
    public double getProbabiblityMask() {
        return PROB_OF_ONE;
    }

    /**
     * @param probabiblityMask the probabiblityMask to set
     */
    public void setProbabiblityMask(double probabiblityMask) {
        this.PROB_OF_ONE = probabiblityMask;
    }

    @Override
    public String toString() {
        return super.toString() + "<" + PROB_OF_ONE + ">";
    }

    @Override
    public String getInformation() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.toString());
        buf.append("\nUniform Crossover in the genotype");
        buf.append("\n\nParameters: <PROB CROSSOVER> <PROB BIT>");
        buf.append("\n<PROB CROSSOVER> - probability of crossover [0,1]");
        buf.append("\n<PROB BIT> - probability of 1 in the mask [0,1]");
        return buf.toString();
    }
}
