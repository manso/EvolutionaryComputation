/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.selection;

import genetic.population.MultiPopulation;
import genetic.population.Population;
import java.util.StringTokenizer;
import problem.Individual;
import problem.bitString.OnesMin;

/**
 * Unbiased Tournament selection Introduction to Evolutionary Algorithms xinjie
 * yu mittsuo gen pp 75
 *
 * Unbiased tournament selection (2005) by Artem Sokolov , Darrell Whitley
 * Zitzler (Eds.), GECCO 2005
 * http://citeseerx.ist.psu.edu/viewdoc/download;jsessionid=D5CC8A77A941290F6E69AAD9A7122827?doi=10.1.1.152.7874&rep=rep1&type=pdf
 * http://www.cs.colostate.edu/~genitor/2005/GECCO247.pdf
 *
 *
 * @author arm
 */
public class TournamentUnbias extends Selection {

    /**
     * Size of tournament 1- uniform 2- linear 3- quadratic (DJONG pp 58)
     */
    protected int SIZE_OF_TOURNAMENT = 3;

    public int[][] generatePermutaion(Population pop, int numChilds) {
        int numInd = pop.getNumIndividuals();
        int sizeOfPermutation = (int) Math.ceil((double) numChilds / (double) pop.getNumIndividuals());
        //global permutation
        int perm[][] = new int[SIZE_OF_TOURNAMENT][sizeOfPermutation * pop.getNumIndividuals()];
        //permutation of one population
        int[] localPerm = new int[pop.getNumIndividuals()];
        //Tournament size
        for (int tournament = 0; tournament < SIZE_OF_TOURNAMENT; tournament++) {
            //generate sequencial numbers
            for (int numPerm = 0; numPerm < sizeOfPermutation; numPerm++) {
               //generate sequencial numbers
                for (int k = 0; k < numInd; k++) {
                    localPerm[k] = k;
                }
                //permutate
                for (int i = localPerm.length - 1; i > 1; i--) {
                    //random position
                    int pos = random.nextInt(i);
                    //exchange value to position i
                    int aux = localPerm[pos];
                    localPerm[pos] = localPerm[i];
                    localPerm[i] = aux;
                }
                //copy permutation to global permutation
                for (int k = 0; k < numInd; k++) {
                    perm[tournament][numPerm * numInd + k] = localPerm[k];
                }
            }


            //print values
//            System.out.print("\n PERMUT " + tournament + " :" );
//            for (int i = 0; i < perm[tournament].length; i++) {
//                System.out.print(" " + perm[tournament][i] );
//            }

        }
        return perm;
    }

    /**
     * Tournament selection
     *
     * @param pop population of parents
     * @param numChilds number of children to select
     * @return population selected
     */
    @Override
    public Population execute(final Population pop, int numChilds) {
        //generate permutation
        int perm[][] = generatePermutaion(pop, numChilds);
        //make new population
        Population selected = pop.getCleanCopie();
        //select NumChilds Individuals
        for (int count = 0; count < numChilds; count++) {
            //get first individual of permutation
            Individual best = pop.getIndividual(perm[0][count]);
            //get other individuals
            for (int i = 1; i < SIZE_OF_TOURNAMENT; i++) {
                //get other individual of permutation
                Individual ind = pop.getIndividual(perm[i][count]);;
                //select the best of the tournament
                if (ind.compareTo(best) > 0) {
                    best = ind;
                }
            }
            //append one copie of the best
            selected.addIndividual(best.getClone(), 1);
        }
        return selected;
    }

    @Override
    public String getInformation() {
        StringBuilder buf = new StringBuilder(toString());
        buf.append("\nUnbiased Tournament");
        buf.append("\nGenerate permutations of individuals");
        buf.append("\nto make tournaments");
        buf.append("\n\nParameters: <SIZE> <TOURN>");
        buf.append("\n    <SIZE>  - # of individuals to be selected ");
        buf.append("\n             0 =  AUTO (size of population)");
        buf.append("\n    <TOURN> - size of tournament ( >= 1)");
        buf.append("\n\nUnbiased tournament selection ");
        buf.append("\nby Artem Sokolov , Darrell Whitley GECCO 2005");
        return buf.toString();
    }

    @Override
    public void setParameters(String param) {
        StringTokenizer iter = new StringTokenizer(param);
        if (iter.hasMoreTokens()) {
            super.setParameters(iter.nextToken());
        }
        if (iter.hasMoreTokens()) {
            //number of itens
            try {
                SIZE_OF_TOURNAMENT = Integer.parseInt(iter.nextToken());
                if (SIZE_OF_TOURNAMENT < 1) {
                    SIZE_OF_TOURNAMENT = 1;
                }
            } catch (Exception e) {
                SIZE_OF_TOURNAMENT = 2;
            }
        }
    }

    @Override
    public String getParameters() {
        return super.getParameters() + " " + SIZE_OF_TOURNAMENT;
    }

    @Override
    public String toString() {
        return super.toString() + " <" + SIZE_OF_TOURNAMENT + ">";
    }

    @Override
    public TournamentUnbias getClone() {
        TournamentUnbias clone = (TournamentUnbias) super.getClone();
        clone.SIZE_OF_TOURNAMENT = this.SIZE_OF_TOURNAMENT;
        return clone;
    }

    public static void main(String[] args) {
        Population pop = new MultiPopulation();
        Individual o1;


        o1 = new OnesMin("10001");
        pop.addGenotype(o1);
        o1 = new OnesMin("10001");
        pop.addGenotype(o1);
        o1 = new OnesMin("11010");
        pop.addGenotype(o1);
        o1 = new OnesMin("100011");
        pop.addGenotype(o1);
        o1 = new OnesMin("10111");
        pop.addGenotype(o1);
        o1 = new OnesMin("10000");
        pop.addGenotype(o1);
        o1 = new OnesMin("10000");
        pop.addGenotype(o1);


//        o1 = new OnesMax("10001");
//        pop.addGenotype(o1);
//        o1 = new OnesMax("11111");
//        pop.addGenotype(o1);
//        o1 = new OnesMax("10011");
//        pop.addGenotype(o1);
//        o1 = new OnesMax("10111");
//        pop.addGenotype(o1);
//        o1 = new OnesMax("00000");
//        pop.addGenotype(o1);




        pop.evaluate();
        System.out.println("\n" + pop);

//        RWS sel = new RWS();
//        RWS sel = new SUS();
        TournamentUnbias sel = new TournamentUnbias();
        sel.SIZE_OF_TOURNAMENT = 2;
//        sel.generatePermutaion(pop, 10);
        Population selected = sel.execute(pop, 100);
        System.out.println("\nSELECTED:" + selected);

    }
}
