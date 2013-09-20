///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2012                                             ****/
///****     António Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt             ****/
///****     Instituto Politécnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****                                                                    ****/
///****************************************************************************/
///****     This software was build with the purpose of learning.          ****/
///****     Its use is free and is not provided any guarantee              ****/
///****     or support.                                                    ****/
///****     If you met bugs, please, report them to the author             ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/
package operator.recombination.permutation;

/**
 * Genetic Algorithm Solution of the TSP Avoiding Special Crossover and Mutation
 * http://www.ceng.metu.edu.tr/~ucoluk/research/publications/tspnew.pdf
 * http://www.ceng.metu.edu.tr/~ucoluk/research/publications/tsp.pdf
 *
 * @author ZULU
 */
public class PMX1_Negative extends PMX2_Negative {

    @Override
    public String getInformation() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.toString());
        buf.append("\n PMX 1 -  Partially Mapped Crossover");
        return buf.toString();
    }
    //--------------------------------------------------------------
    //--------------------------------------------------------------
    //--------------------------------------------------------------
    //--------------------------------------------------------------
    //--------------------------------------------------------------
    //--------------------------------------------------------------
    //--------------------------------------------------------------
    //--------------------------------------------------------------

    /**
     * doCrossover doCrossover crossover between two parents
     *
     * @param tmp1 parent
     * @param tmp2 parent
     */
    public int[][] executePMX(int[] parent1, int[] parent2) {
        int UNDEFINED = -9999999;
        int[][] offspring = new int[2][parent1.length];
        //alias to bidimensional array
        int[] child1 = offspring[0];
        int[] child2 = offspring[1];
        //make copys of parents and clean genes
        for (int i = 0; i < parent2.length; i++) {
            //clean genes
            child1[i] = UNDEFINED;
            child2[i] = UNDEFINED;
        }

        //select two points of crossover
        int cut1 = 0, cut2 = 6;
//        //------------------------------------------------
        do {
            cut2 = random.nextInt(child1.length - 1) + 1;
        } while (cut2 <= cut1);
        //-----------------------------------------------
//        System.out.println("[" + cut1 + " , " + cut2 + "]");
        //copy genes between cut points
        for (int j = cut1; j < cut2; ++j) {
            child1[j] = parent1[j];
            child2[j] = parent2[j];
        }
//        System.out.println("C1 =" + Arrays.toString(child1));
        //fill the left side of the 1st cut point
        for (int j = 0; j < cut1; ++j) {
            child1[j] = getMappedGene(parent2[j], child1, parent2, cut1, cut2);
            child2[j] = getMappedGene(parent1[j], child2, parent1, cut1, cut2);
//            System.out.println("C1 =" + Arrays.toString(child1));

        }
        // fill the right side of the 2st cut point
        for (int j = cut2; j < child1.length; ++j) {
            child1[j] = getMappedGene(parent2[j], child1, parent2, cut1, cut2);
            child2[j] = getMappedGene(parent1[j], child2, parent1, cut1, cut2);
//            System.out.println("C1 =" + Arrays.toString(child1));
        }
        offspring[0] = repair(child1);
        offspring[1] = repair(child2);
        return offspring;
    }

    public int[] repair(int[] genome) {
        int[] ordered = new int[genome.length];
        int min = Math.abs(genome[0]);
        int index = 0;
        for (int i = 1; i < genome.length; i++) {
            if (Math.abs(genome[i]) < min) {
                min = Math.abs(genome[i]);
                index = i;
            }
        }
        for (int i = 0; i < ordered.length; i++) {
            ordered[i] = genome[ (index + i) % ordered.length];
        }
        return ordered;
    }
}
