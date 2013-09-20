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
package operator.replacement.permutation;

import genetic.population.Population;
import java.util.Iterator;
import java.util.StringTokenizer;
import operator.replacement.Replacement;
import problem.Individual;
import problem.PRM_Individual;

/**
 *
 * @author ZULU
 */
public class InverOver extends Replacement {

    double p = 0.1;

    @Override
    public Population execute(Population pop, Population childs) {
        Population offspring = pop.getCleanCopie();
        int numCitys = pop.getIndividual(0).getNumGenes();
        Iterator<Individual> it = pop.getIterator();
        //for each individual
        while (it.hasNext()) {
            PRM_Individual parent = (PRM_Individual) it.next();
            //select  randomly  a  city 
            int indexOfFirstCity = random.nextInt(numCitys);
            int indexOfSecondCity;
            int firstCity = parent.get(indexOfFirstCity);
            int secondCity;

            //children is a clone of father;
            PRM_Individual children = parent.getClone();

            //repeat
            while (true) {
                //select other city in the current individual
                if (random.nextDouble() <= p) {
                    //select onother city
                    do {
                        indexOfSecondCity = children.get(random.nextInt(numCitys));
                    } while (indexOfFirstCity == indexOfSecondCity);
                    secondCity = children.get(indexOfSecondCity);
                } //select another city in the other individual
                else {
                    PRM_Individual otherParent;
                    do {//select random individual from population
                        otherParent = (PRM_Individual) pop.getRandomGenotype();
                    } while (parent.compareTo(otherParent) == 0);
                    //-----select second city-----------
                    //index of the first city in the other parent
                    int index2 = otherParent.indexOf(indexOfFirstCity);
                    //next city in the second parent
                    secondCity = otherParent.getNext(index2);
                    //calculate index of city in the children
                    indexOfSecondCity = children.indexOf(secondCity);
                }
                //if city selected is the next or the previous of Children -break
                if (secondCity == children.getNext(indexOfFirstCity) || secondCity == children.getPrevious(indexOfFirstCity)) {
                    break;
                }
                //invert genes [startcity + 1 , secondcity]
                children.invert((indexOfFirstCity + 1) % numCitys, indexOfSecondCity);
                //continue using second city
                indexOfFirstCity = indexOfSecondCity;
                
                //evaluate children
                children.evaluate();
                solver.addEvaluation();
                //replace parent with children
                if (children.compareTo(parent) >= 0) {
                    parent.setIndividual(children);
                }
            }//while true
            offspring.addGenotype(parent.getClone());
        }//while
        return offspring;
    }

    @Override
    public String getInformation() {
        StringBuilder buf = new StringBuilder();
        buf.append(this.toString());
        buf.append("\nInver-Over Replacement ");
        buf.append("\n");
        buf.append("\n\nParameters: <Probability>");
        buf.append("\n<Probility> - prob. to select inside individual");
        return buf.toString();
    }

    @Override
    public void setParameters(String str) {
        StringTokenizer par = new StringTokenizer(str);
        double newP = p;
        try {
            newP = Double.parseDouble(par.nextToken());
        } catch (Exception e) {
        }
        p = newP < 0 || newP > 1 ? p : newP;
    }

    @Override
    public String toString() {
        return super.toString() + " <" + p + ">";
    }

    @Override
    public Replacement getClone() {
        InverOver clone = (InverOver) super.getClone();
        clone.p = this.p;
        return clone;
    }
}
