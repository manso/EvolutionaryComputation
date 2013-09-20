/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.recombination.real;

import genetic.population.Population;
import java.util.StringTokenizer;
import operator.recombination.Recombination;
import problem.Individual;
import utils.RandomVariable;

/**
 *
 * @author manso
 */
public class RCGA_AX extends Recombination {

    double EXPAND = 0.5;
    //temporary variable
    double x0, x1;
    int i, k;

    public Individual[] execute(Individual i0, Individual i1) {
        //int numChildren = i0.getNumCopies() + i1.getNumCopies();
        final double[] v0 = i0.getValues();
        final double[] v1 = i1.getValues();

        //multiplo de  2
        int numChildren = 2;
        int numberOfGenes = i0.getNumGenes();
        Individual[] children = new Individual[numChildren];

        for (k = 0; k < children.length; k += 2) {
            children[k] = i0.getClone();
            children[k + 1] = i1.getClone();
            double[] cv1 = children[k].getValues();
            double[] cv2 = children[k + 1].getValues();
            //calculate new genes
            for (int i = 0; i < numberOfGenes; i++) {
                double alpha = RandomVariable.uniform(-EXPAND, 1 + EXPAND);
                //Crossover individuals           
                cv1[i] = alpha * v0[i] + (1.0 - alpha) * v1[i];
                cv2[i] = (1.0 - alpha) * v0[i] + alpha * v1[i];
            }
            //set gene values
            children[k].setValues(cv1);
            children[k].setNumCopys(1);

            children[k + 1].setValues(cv2);
            children[k + 1].setNumCopys(1);
        }
        return children;
    }

    @Override
    public Population execute(Population pop) {
        //make new population
        Population offspring = pop.getCleanCopie();

        //calculate the number of individuals selected to crossover
        int numCrossovers = (int) (pop.getNumIndividuals() * getProbability() / 2);
        //make crossovers
        int countCrossover = 0;
        while (countCrossover < numCrossovers && pop.getNumGenotypes() > 1) {
            //increment crossovers
            countCrossover++;
            //select two individuals at random
            Individual p1 = pop.removeRandomIndividual();
            Individual p2 = pop.removeRandomIndividual();
            //make the combinations
            Individual[] children = execute(p2, p1);
            //put individuals in population
            for (Individual chld : children) {
                offspring.addIndividual(chld, 1);
            }
        }
        //append the individuals wich not do recombination
        offspring.appendPopulation(pop);
        return offspring;
    }

    @Override
    public String toString() {
        return super.toString() + "<" + EXPAND + ">";
    }

    public String getInformation() {
        StringBuilder buf = new StringBuilder();
        buf.append(toString());
        buf.append("\nAritmetic Uniform Crossover ");
        buf.append("\nRange[-" + EXPAND + "," + (1.0 + EXPAND) + "]");
        buf.append("\nParameters <PROBABILITY><EXPANSION>");
        buf.append("\n<PROBABILITY> to perform crossover");
        buf.append("\n<EXPANSION> expansion factor");
        return buf.toString();
    }

    @Override
    public void setParameters(String str) {
        StringTokenizer par = new StringTokenizer(str);
        double oldProb = probability;
        double old = EXPAND;
        try {
            probability = Double.parseDouble(par.nextToken());
        } catch (Exception e) {
            probability = oldProb;
        }
        try {
            EXPAND = Double.parseDouble(par.nextToken());
        } catch (Exception e) {
            EXPAND = old;
        }
    }
    @Override
    public String getParameters() {
         return super.getParameters() + " " +EXPAND;
    }

    @Override
    public RCGA_AX getClone() {
        RCGA_AX clone = (RCGA_AX) super.getClone();
        clone.probability = this.probability;
        clone.EXPAND = this.EXPAND;
        return clone;
    }
}