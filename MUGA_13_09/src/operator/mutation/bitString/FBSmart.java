/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.mutation.bitString;

import genetic.gene.Gene;
import genetic.population.MultiPopulation;
import problem.Individual;
import genetic.population.Population;
import java.util.Arrays;
import java.util.Iterator;
import operator.mutation.Mutation;
import problem.bitString.OnesMax;
import utils.BitField;

/**
 *
 * @author arm
 */
public class FBSmart extends Mutation {

    double[] probToMutBit;
    double accumulatedProb;

    protected void calculateProbabilityOfBitMutation(Population p) {
        int genes = p.getGenotype(0).getNumGenes();
        int size = p.getGenotype(0).getGene(0).getNumBits();
        double numGenotypes = p.getNumGenotypes();

        probToMutBit = new double[genes * size];
        accumulatedProb = 0;
        //calculate sum of ones
        Iterator<Individual> it = p.getIterator();
        while (it.hasNext()) {
            BitField bits = it.next().getBits();
            for (int j = 0; j < size; j++) {
                if (bits.getBit(j)) {
                    probToMutBit[j] += 1;
                }
            }
        }
        //calculate mean
        for (int j = 0; j < probToMutBit.length; j++) {
            //calculate mean
            probToMutBit[j] /= numGenotypes;
            //subtract 0.5 to equal probability of zeros and ones
            probToMutBit[j] = Math.abs(probToMutBit[j] - 0.5);
            //accumulated probabiliies of gene
            accumulatedProb += probToMutBit[j];
        }
    }

    /**
     * Mutate one bit in the Genome
     *
     * @param ind individual
     * @param indexOfGene gene to mutate one bit
     */
    protected void mutateBit(BitField genome) {
        //---------------------------------------------------------
        //accumulated negative ( rounding in double variables)
        if (accumulatedProb < 0) {
            int i = random.nextInt(genome.getNumberOfBits());
//            //print mutation
//            for (int j = 0; j < i; j++) {
//                System.out.print(" ");
//            }
//            System.out.print("X");
            genome.invertBit(i);
            return;
        }
        //-----------------------------------------------------------        
        //generate a randomNumber between [0 ..Acummulated[index] ]
        double rnd = random.nextDouble() * accumulatedProb;
//        System.out.print("\nrnd =" + rnd + " AC=" + accumulatedProb[indexOfGene]);
        double acProb = 0;
        for (int i = 0; i < probToMutBit.length; i++) {
            acProb += probToMutBit[i];
            if (acProb >= rnd) {
                genome.invertBit(i);
                //-------------------------------
                //update probabilities
                //------------------------------
                //new gene probability
                double newProb = probToMutBit[i] / genome.getNumberOfBits();
//                System.out.print(" newProb = " + newProb);
                //update accumulated probabilities (old-new)
                accumulatedProb -= probToMutBit[i] - newProb;
                //decrease probability
                probToMutBit[i] = newProb;
//                System.out.print(" AC new =" + accumulatedProb[indexOfGene]);
//
//                //print mutation
//                for (int j = 0; j < i; j++) {
//                    System.out.print(" ");
//                }
//                System.out.print("X");
                return;
            }
        }
    }

    @Override
    public void doMutation(Individual ind) {
        BitField genome = ind.getBits();
        double prob = probability == 0.0 ? 1.0 / genome.getNumberOfBits() : probability;
        prob *= ind.getNumCopies();
        boolean mutated = false;
        for (int j = 0; j < genome.getNumberOfBits(); j++) {
            if (random.nextDouble() < prob) {
                mutated = true;
                mutateBit(genome);
            }
        }
        if (mutated) {
            ind.setBits(genome);
        }
    }

    @Override
    public Population execute(Population pop) {
        //probability of bit mutation
        calculateProbabilityOfBitMutation(pop);
        //execute in all individuals
        Population childs = pop.getCleanCopie();
        Iterator<Individual> it = pop.getIterator();
        while (it.hasNext()) {
            Individual ind = it.next();
            for (int i = 0; i < ind.getNumCopies(); i++) {
                ind.setNumCopys(i + 1);
                doMutation(ind);
                childs.addIndividual(ind, 1);
            }
        }
        return childs;
    }

    @Override
    public String getInformation() {
        StringBuilder buf = new StringBuilder();
        buf.append(this.toString());
        buf.append("\nFlip on bit in Genes in the smart way");
        buf.append("\nThe probaility is dinamically adaptaded");
        buf.append("\n\nParameters: <PROB> ");
        buf.append("\n    <PROB>  - Probability to mutate one bit");
        buf.append("\n             0 =  AUTO (1 / genome.lenght)");
        return buf.toString();
    }

    public static void main(String[] args) {
        OnesMax i1 = new OnesMax("111101010000");
        OnesMax i2 = new OnesMax("111110100000");
        OnesMax i3 = new OnesMax("111111000000");
        OnesMax i4 = new OnesMax("111100110000");
//        Deceptive.NUMBER_OF_BLOCKS = 10;
//        Deceptive.SIZE_OF_BLOCK = 8;
//        Individual i1 = new Deceptive();
//        Individual i2 = new Deceptive();
//        Individual i3 = new Deceptive();
//        Individual i4 = new Deceptive();
        Population pop = new MultiPopulation();
        pop.addGenotype(i1);
        pop.addGenotype(i1);
        pop.addGenotype(i1);
        pop.addGenotype(i2);
        pop.addGenotype(i2);
        pop.addGenotype(i3);
        pop.addGenotype(i4);
        pop.evaluate();
        System.out.println(pop);

        FBSmart m = new FBSmart();
        m.calculateProbabilityOfBitMutation(pop);

        System.out.println("\nPROB = ");

        System.out.print(Arrays.toString(m.probToMutBit) + "="
                + m.accumulatedProb + " \t ");


        System.out.println(i1.getGenome().getBinString());
        for (int i = 0; i < 1000; i++) {
            m.doMutation(i4);
//            System.out.println("\n " + i1.getGenome().getBinString() + " \t " + Arrays.toString(m.probToMutBit[0]));
//            System.out.println(i);

        }
        System.out.println(pop);
        System.out.println("\nPROB = ");
        System.out.print(Arrays.toString(m.probToMutBit) + "="
                + m.accumulatedProb + " \t ");
    }
}
