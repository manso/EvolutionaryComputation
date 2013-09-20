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
import genetic.population.SimplePopulation;
import java.util.Random;
import problem.Individual;
import utils.BitField;

/**
 *
 * @author ZULU
 */
public class SolverCGA extends SimpleSolver {

    public static Random random = new Random();
    public double prob[][] = null;
    Individual best = null;
    double learningRate;
    int sizeOfIndividual = 0;

    public SolverCGA() {
    }

    @Override
    public void restartSolver() {
        super.restartSolver();
        prob = restartModel(parents);
        best = null;

    }

    public double[][] restartModel(Population pop) {
        if (parents == null) {
            return null;
        }
        sizeOfIndividual = 0;
        // probaility[gene][bits]
        double[][] p = new double[template.getNumGenes()][];
        //all genes    
        for (int i = 0; i < p.length; i++) {
            p[i] = new double[template.getGene(i).getAlels().getNumberOfBits()];
            //update size of individual
            sizeOfIndividual += p[i].length;
            //all bits
            for (int j = 0; j < p[i].length; j++) {
                //probability 0.5
                p[i][j] = 0.5;
            }
        }
        return p;
    }

    public void updateProbabilities(Individual winner, Individual looser) {
        //all genes    
        for (int gene = 0; gene < prob.length; gene++) {
            BitField w = winner.getGene(gene).getAlels();
            BitField l = looser.getGene(gene).getAlels();
            //all bits
            for (int bit = 0; bit < w.getNumberOfBits(); bit++) {
                //different bits
                if (w.getBit(bit) != l.getBit(bit)) {
                    //if the bit is true increase probability
                    if (w.getBit(bit)) {                        
                        prob[gene][bit] += random.nextDouble() * learningRate;
                        //normalize probability
                        prob[gene][bit] = prob[gene][bit] >= 1 ? 0.999 : prob[gene][bit];
                    } else {
                        //decrease probability
                        prob[gene][bit] -= random.nextDouble() * learningRate;
                        //normalize probability
                        prob[gene][bit] = prob[gene][bit] <=0 ? 0.001 : prob[gene][bit];
                    }
                }
            }
        }
    }

    private Individual generateIndividual(double[][] p) {
        Individual ind = template.getClone();
        //for all genes
        for (int gene = 0; gene < p.length; gene++) {
            //---- generate new Gene -------
            BitField alells = new BitField(p[gene].length);
            for (int alel = 0; alel < alells.getNumberOfBits(); alel++) {
                if (random.nextDouble() < p[gene][alel]) {
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
        //-------------------RESTART ----------------------
        if (prob == null || best == null || template.getClass() != best.getClass()) {
            prob = restartModel(original);
        }
        //----------------------------------------------------------------------
        int sizePOP = original.getNumGenotypes();
        learningRate = 1.0 / sizeOfIndividual;
        //--------------- S I M P L E    P O P U L A T I O N -------------------
        children = new SimplePopulation();
        //----------------------------------------------------------------------
        while (children.size() < sizePOP) {
            //--------- generate individuals from model ---------
            Individual s1 = generateIndividual(prob);
            Individual s2 = generateIndividual(prob);
            s1.evaluate();
            s2.evaluate();
            EVALUATIONS += 2;
            //-------------- selecet winner -----------------------
            Individual winner = s1.compareTo(s2) > 0 ? s1 : s2;
            Individual looser = s1.compareTo(s2) <= 0 ? s1 : s2;
            //update best
            if (best == null || winner.compareTo(best) > 0) {
                best = winner;
            }
            //-------------- update probabilities ----------------
            updateProbabilities(winner, looser);
            children.addGenotype(winner);
        }
        parents = children.getClone();
        return parents;
    }

    public String getAlgorithm() {
        StringBuilder str = new StringBuilder();
        str.append("\n Capact Genetic Algirthm Solver");
        str.append("\nPopulation Model-Building Genetic Algorithms (PMBGA)");
        str.append("\n 1 - initialize probalibities to 0.5");
        str.append("\n 2 - until STOP criteria");
        str.append("\n    2.1 - Generate S1 and S2 with probabilities");
        str.append("\n    2.2 - Select Winner and Loser");
        str.append("\n    2.3 - update bits probabilities");
        str.append("\n          For all bits");
        str.append("\n             if Winner[bit] != Looser[bit]");
        str.append("\n                if winner[bit] is true ");
        str.append("\n                  Increase of bit probilitie");
        str.append("\n                else  Decrease of bit probilitie");
        str.append("\n\nG. R. Harik and F. G. Lobo and D. E. Goldberg");
        str.append("\nThe compact genetic algorithm,1999. ");
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
        txt.append("\nStatistics    " + this.stats.getInfo().trim());
        txt.append("\nEvaluations   " + EVALUATIONS);
        txt.append("\nGenerations   " + GENERATION);
        return txt.toString();
    }

}
