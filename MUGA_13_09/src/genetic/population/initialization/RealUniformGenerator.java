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
package genetic.population.initialization;

import genetic.population.MultiPopulation;
import genetic.population.Population;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import problem.Individual;
import problem.RC_Individual;
import problem.realCoded.SimpleRC;
import utils.RandomVariable;

/**
 * this class generate random population with equal number of ones and zeros
 *
 * @author Administrator
 */
public class RealUniformGenerator extends InitUniformPop {

    Population points;
    Random random = new Random();
    double dimGrid;
    RC_Individual template;
//
//    public static ArrayList<Double> cloneList(ArrayList<Double> list) {
//        ArrayList<Double> clone = new ArrayList(list.size());
//        for (Double item : list) {
//            clone.add(item.doubleValue());
//        }
//        return clone;
//    }
//
//    private void generatePopulation(ArrayList<Double> ind, double[] v, int gene) {
//        //finish 
//        if (gene == template.getNumGenes() - 1) {
//            for (int i = 0; i < v.length; i++) {
//                ArrayList clone = cloneList(ind);
//                clone.add(v[i]);
//                RC_Individual newind = template.getClone();
//                for (int j = 0; j < clone.size(); j++) {
//                    newind.setGeneValue(j, (Double) clone.get(j));
//                }
//                points.addGenotype(newind);
//            }
//        } else {
//            for (int i = 0; i < v.length; i++) {
//                if (gene == 0) {
//                    ind = new ArrayList();
//                }
//                ArrayList clone = cloneList(ind);
//                clone.add(v[i]);
//                generatePopulation(clone, v, gene + 1);
//            }
//        }
//    }

    /**
     * initialize the vector of probabilities
     *
     * @param size template individual
     * @param size size of population
     */
    @Override
    public void initialize(Individual ind, int size) {
        template = (RC_Individual) ind;

        int numGenes = template.getValues().length;
        int divs = (int) Math.ceil(Math.pow(size, 1.0 / template.getNumGenes()));

        //calculateDivs(numGenes, size);
        double[] v = new double[divs + 1];
        //dimension of grid
        dimGrid = template.getDimension() / divs;
        for (int i = 0; i < divs + 1; i++) {
            v[i] = template.getMinValue() + dimGrid * i;
//            System.out.println(i + " \t" + v[i]);
        }
       //generate points
        MultiPopulation leftCorner = new MultiPopulation();
        while (leftCorner.size() < size) {
            RC_Individual newInd = template.getClone();
            for (int i = 0; i < numGenes; i++) {
                 newInd.setGeneValue(i, v[random.nextInt(v.length - 1)]);
            }
            leftCorner.addGenotype(newInd);
        }
        points = new MultiPopulation();
        //randomize intervall
        Iterator<Individual> it = leftCorner.getIterator();
        while(it.hasNext()){
            RC_Individual rc = ((RC_Individual) it.next()).getClone();
            double noise;
            double values[] = rc.getCloneValues();
            for (int i = 0; i < numGenes; i++) {
                 noise = RandomVariable.triangular(0, dimGrid);
                 values[i]+=noise;
            }
            rc.setValues(values);
            points.addGenotype(rc);
        }
    }

    /**
     * generate a random values
     *
     * @return values
     */
    @Override
    public Individual generateUniform() {
        return points.removeRandomGenotype();
    }

    public static int calculateDivs(int genes, int size) {
        double log = Math.log(size) / Math.log(genes);
        return (int) Math.ceil(log);
    }

    public static void main(String[] args) {

        int GENES = 100;
//        for (int i = 1; i < 1000; i*=2) {
//            int divs = calculateDivs(GENES, i);
//            System.out.println(i + " \t " + divs + " \t " + Math.pow(GENES, divs));
//        }
        Individual ind = new SimpleRC(GENES);
        int SIZE_POP = 128;
        RealUniformGenerator bin = new RealUniformGenerator();
        bin.initialize(ind, SIZE_POP);

        for (int i = 0; i < SIZE_POP; i++) {
            ind = bin.generateUniform();
            ind.evaluate();
            System.out.println(i + " \t" + ind);
        }
    }
}
