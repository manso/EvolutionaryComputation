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
package operator.recombination.real;

import genetic.population.Population;
import java.util.StringTokenizer;
import operator.recombination.Recombination;
import problem.Individual;
import utils.RandomVariable;

/**
 *
 * @author ZULU
 */
public class MuGA_AX_Mean extends Recombination {
 double EXPAND = 0.5;

    public Population execute(Individual i1, Individual i2) {
        //arrays of gene Values
        final double[] v1 = i1.getValues();
        final double[] v2 = i2.getValues();

        int numberOfGenes = i1.getNumGenes();
        //create a population to the childrens
        Population children = solver.getParents().getCleanCopie();
        //calculate the number of crossovers
        // mean of all the individuals
        int numberOfCrossovers = (i1.getNumCopies() + i2.getNumCopies()) / 2;
        //for all crossovers
        for (int k = 0; k < numberOfCrossovers; k++) {
            //calculate the number of the individual copy
            // minimum between K and number of Multi individual #copies
            int copy1 = Math.min(k + 1, i1.getNumCopies());
            int copy2 = Math.min(k + 1, i2.getNumCopies());
            //Create new childrens as as clone
            Individual c1 = i1.getClone();
            Individual c2 = i2.getClone();
            //temporary array of genes
            double[] cv1 = c1.getValues();
            double[] cv2 = c2.getValues();
            //perform AX crossover
            for (int i = 0; i < numberOfGenes; i++) {
//                 //generate random variable into expanded interval
                double alpha = RandomVariable.uniform(-EXPAND, 1 + EXPAND);
//                //genererate new gene value into expanded interval
                double x1 = alpha * v1[i] + (1 - alpha) * v2[i];
//                //calculate distance to V2
//                //double dim = x1 - v2[i];
                double dim = x1 - v1[i];
//                //generate two new genes spread by number of copies
                cv1[i] = v1[i] + copy2 * dim;
                cv2[i] = v2[i] - copy1 * dim;
            }
            //set Genes and add childrens to population
            c1.setValues(cv1);
            children.addIndividual(c1, 1);
            c2.setValues(cv2);
            children.addIndividual(c2, 1);
        }
        //put the remain parent in the population
        if (i1.getNumCopies() > i2.getNumCopies()) {
            children.addIndividual(i1, 1);
        } else if (i1.getNumCopies() < i2.getNumCopies()) {
            children.addIndividual(i2, 1);
        }
        return children;
    }

    @Override
    public Population execute(Population pop) {
        Population offspring = pop.getCleanCopie();
        double probToX = probability;
        //Automatic probability
        if (probability == 0) {
            probToX = 0.75;
        }
        while (pop.getNumGenotypes() > 1) {
            // not recombinated - directly to the children population
            if (random.nextDouble() > probToX) {
                offspring.addGenotype(pop.removeRandomGenotype());
                continue;
            }
            //select worst parent
            Individual p1 = pop.removeRandomGenotype();
            //select random parent
            Individual p2 = pop.removeRandomGenotype();
            //execute recombination
            Population children = execute(p2, p1);
            //save childrens 
            offspring.appendPopulation(children);
        }
        //append remain parents
        offspring.appendPopulation(pop);
        return offspring;
    }

    @Override
    public String toString() {
        return super.toString() + "<" + this.EXPAND + ">";
    }

    @Override
    public String getInformation() {
        StringBuilder buf = new StringBuilder();
        buf.append(toString());
        buf.append("\nMultiset Aritmetic Uniform Crossover ");
        buf.append("\nSelect two RANDOM parents");        
        buf.append("\nAX Range[-" + EXPAND + "," + (1.0 + EXPAND) + "]");
        buf.append("\nExpand the range of crossover");
        buf.append("\nby the number of copies\n");
        buf.append("\nParameters <PROBABILITY><EXPANSION>");
        buf.append("\n<PROBABILITY> to perform crossover");
        buf.append("\n<EXPANSION> expansion factor");
        return buf.toString();
    }

    @Override
    public void setParameters(String str) {
        StringTokenizer par = new StringTokenizer(str);
        double oldProb = this.probability;
        double old = this.EXPAND;
        try {
            this.probability = Double.parseDouble(par.nextToken());
        } catch (Exception e) {
            this.probability = oldProb;
        }
        try {
            this.EXPAND = Double.parseDouble(par.nextToken());
        } catch (Exception e) {
            this.EXPAND = old;
        }
    }

    @Override
    public MuGA_AX_Mean getClone() {
        MuGA_AX_Mean clone = (MuGA_AX_Mean) super.getClone();
        clone.probability = this.probability;
        clone.EXPAND = this.EXPAND;
        return clone;
    }
}
