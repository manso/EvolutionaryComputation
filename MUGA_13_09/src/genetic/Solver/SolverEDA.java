///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2012                                             ****/
///****     Antonio Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso                             ****/
///****     Instituto Politecnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****************************************************************************/
///****************************************************************************/
///****     This software was built with the purpose of investigating      ****/
///****     and learning. Its use is free and is not provided any          ****/
///****     guarantee or support.                                          ****/
///****     If you met bugs, please, report them to the author             ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/
package genetic.Solver;

import genetic.population.Population;
import java.util.Random;
import problem.Individual;
import utils.BitField;

/**
 *
 * @author ZULU
 */
public class SolverEDA extends SimpleSolver {

    public static Random random = new Random();
    public double prob[][];

    private int[][] getSumOfOnes(Population pop) {
        int numGenes = template.getNumGenes();
        //initialize matrix of ones
        int[][] ones = new int[numGenes][];
        for (int i = 0; i < ones.length; i++) {
            ones[i] = new int[template.getGene(i).getNumBits()];
        }
        //all individuals
        for (int i = 0; i < pop.getNumGenotypes(); i++) {
            Individual ind = pop.getGenotype(i);
            //number of copies of genotype
            int copies = ind.getNumCopies();
            //for all Genes
            for (int gene = 0; gene < numGenes; gene++) {
                BitField bits = ind.getGene(gene).getAlels();
                for (int bit = 0; bit < bits.getNumberOfBits(); bit++) {
                    if (bits.getBit(bit)) {
                        ones[gene][bit] += copies;
                    }
                }
            }
        }
        return ones;
    }

    private double[][] calculateBitProbabilities(Population pop) {
        //calculate the ones distibution
        int[][] ones = getSumOfOnes(pop);
        double[][] p = new double[template.getNumGenes()][];
        double numIndividuals = pop.getNumIndividuals();
//        System.out.print("\nED : ");
        for (int i = 0; i < ones.length; i++) {
            p[i] = new double[ones[i].length];
            for (int j = 0; j < ones[i].length; j++) {
                p[i][j] = (double) ones[i][j] / (double) numIndividuals;
            }
        }
        return p;
    }

    private Individual getIndividual(double[][] model) {
        Individual ind = template.getClone();
        //for all genes
        for (int gene = 0; gene < model.length; gene++) {
            //---- generate new Gene -------
            BitField alells = new BitField(model[gene].length);
            for (int alel = 0; alel < alells.getNumberOfBits(); alel++) {
                if (random.nextDouble() < model[gene][alel]) {
                    alells.setBitTrue(alel);
                }
            }
            //set gene to individual
            ind.getGene(gene).setAlels(alells);
        }

        return ind;
    }

    @Override
    public Population evolve(Population original) {
        int sizePOP = original.getNumGenotypes();
        //----------------------------------------------------------------------
        //-------------- select individuals ------------------------------------
        //----------------------------------------------------------------------        
        selected = select.execute(original);
        //----------------------------------------------------------------------
        //------------------ calculate model -----------------------------------
        //----------------------------------------------------------------------
        prob = calculateBitProbabilities(selected);
        //----------------------------------------------------------------------
        //------------------ generate new Pop ----------------------------------
        //----------------------------------------------------------------------
        children = original.getCleanCopie();
        while (children.size() < sizePOP) {
            //-----------------------------------
            //---- generate new individual -------
            //build new individual
            Individual edi = getIndividual(prob);
            //increase number of copies
            int copies = 1;
            if (children.contains(edi)) {
                copies = children.getGenotype(edi).getNumCopies();
            }
            edi.setNumCopys(copies);
            //mutate individual
            mutate.doMutation(edi);
            edi.evaluate();
            EVALUATIONS++;
            children.addIndividual(edi, 1);
        }
        parents = rescale.execute(children);
        return parents;
    }

    public String getAlgorithm() {
        StringBuilder str = new StringBuilder();
        str.append("\n Estimation of Distribution Algorithm Solver");
        str.append("\n 1 - create random POP");
        str.append("\n 2 - until STOP criteria");
        str.append("\n    2.1 - SEL = selection(POP)");
        str.append("\n    2.2 - DISTRIBUITION = calculate(SEL)");
        str.append("\n    2.3 - Create population POP");
        str.append("\n           Generate IND with Distribuition");
        str.append("\n           mutate IND ");
        str.append("\n           evaluate IND");
        str.append("\n           insert IND in pop");
        return str.toString();
    }

    public String getInfo() {
        StringBuffer txt = new StringBuffer();
        txt.append("GeneticSolver " + getTitle().trim());
        txt.append("\nSolver type   " + this.getClass().getSimpleName());
        txt.append("\nStop Criteria " + this.getStop().toString().trim());
        txt.append("\nIndividual    " + this.getTemplate().getName());
        txt.append("\nPopulation    " + this.parents.getClass().getSimpleName().trim());
        txt.append("\nGenotypes     " + this.parents.getNumGenotypes());
        txt.append("\nIndividuals   " + this.parents.getNumIndividuals());
        txt.append("\nSelection     " + this.getSelect().toString().trim());
//        txt.append("\nRecombination " + "NOT USED");
        txt.append("\nMutation      " + this.mutate.toString().trim());
//        txt.append("\nReparation    " + this.reparation.toString().trim());
//        txt.append("\nReplacement   " + "NOT USED");
//        txt.append("\nRescaling     " + this.rescale.toString().trim());
//        txt.append("\nStatistics    " + this.stats.getInfo().trim());
        txt.append("\nEvaluations   " + EVALUATIONS);
        txt.append("\nGenerations   " + GENERATION);
        return txt.toString();
    }

    public static void main(String[] args) {
        SolverEDA eda = new SolverEDA();
//        UniquePopulation pop = new UniquePopulation();
//        pop.createRandomPopulation(10,new Deceptive());
//        eda.select.setParameters("0 3");
//        eda.setParents( pop);
        for (int i = 0; i < 10000; i++) {
            eda.evolve(eda.parents);
            System.out.println("generation " + i + " \t" + eda.getBest().getBestIndividal());

        }

    }
}
