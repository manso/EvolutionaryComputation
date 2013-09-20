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
public class Blend extends Recombination {

    static double DEFAULT_ALPHA = 1; // Eshelman e Schaffer
    // A Crossover Operator Using Independent Component Analysis for
    //Real-Coded Genetic Algorithms
    protected double ALPHA = DEFAULT_ALPHA;

    public Population execute(Individual i1, Individual i2) {

        int numberOfGenes = i1.getNumGenes();
        double[] center = new double[numberOfGenes];
        double x0[] = i1.getValues();
        double x1[] = i2.getValues();
        //calculate Centroid
        for (int i = 0; i < numberOfGenes; i++) {
            center[i] = (x0[i] + x1[i]) / 2.0;
        }

        Population children = solver.getParents().getCleanCopie();
        int numberOfCrossovers = Math.max(i1.getNumCopies(), i2.getNumCopies());

        for (int k = 0; k < numberOfCrossovers; k++) {

            int copy1 = Math.min(k + 1, i1.getNumCopies());
            int copy2 = Math.min(k + 1, i2.getNumCopies());

            //make childrens
            Individual c1 = i1.getClone();
            Individual c2 = i2.getClone();

            double[] cv1 = c1.getValues();
            double[] cv2 = c2.getValues();
            //perform AX crossover
            for (int i = 0; i < numberOfGenes; i++) {
                double rnd = random.nextDouble();
//              //expand individuals
                double xx0 = x0[i];
                double xx1 = x1[i];;
                xx0 += (xx0 - center[i]) * ALPHA * copy1;
                xx1 += (xx1 - center[i]) * ALPHA * copy2;
                
                cv1[i] = rnd * xx0 + (1.0 - rnd) * xx1;
                cv2[i] = (1.0 - rnd) * xx0 + rnd * xx1;
            }
            //set Genes and add childrens to population
            c1.setValues(cv1);
            if (k < i2.getNumCopies()) {
                children.addIndividual(c1, 1);
            }
            c2.setValues(cv2);
            if (k < i1.getNumCopies()) {
                children.addIndividual(c2, 1);
            }
        }
        return children;
    }

    @Override
    public Population execute(Population pop) {
        Population offspring = pop.getCleanCopie();
//Automatic probability
        double probToX = getProbability();

        while (pop.getNumGenotypes() > 1) {
            // not recombinated
            if (random.nextDouble() > probToX) {
                offspring.addGenotype(pop.removeRandomGenotype());
                continue;
            }
            //select random parents
            Individual p1 = pop.removeRandomGenotype();

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
        return super.toString() + "<" + this.ALPHA + ">";
    }

    @Override
    public String getInformation() {
        StringBuilder buf = new StringBuilder();
        buf.append(toString());
        buf.append("\nMultiset Aritmetic Uniform Crossover ");
        buf.append("\nSelect two RANDOM parents");
        buf.append("\nAX Range[-" + ALPHA + "," + (1.0 + ALPHA) + "]");
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
        double old = this.ALPHA;
        try {
            this.probability = Double.parseDouble(par.nextToken());
        } catch (Exception e) {
            this.probability = oldProb;
        }
        try {
            this.ALPHA = Double.parseDouble(par.nextToken());
        } catch (Exception e) {
            this.ALPHA = old;
        }
    }
    
    @Override
    public String getParameters() {
         return super.getParameters() + " " +ALPHA;
    }

    @Override
    public Blend getClone() {
        Blend clone = (Blend) super.getClone();
        clone.probability = this.probability;
        clone.ALPHA = this.ALPHA;
        return clone;
    }
}
