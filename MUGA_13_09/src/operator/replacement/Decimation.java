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

package operator.replacement;

import genetic.population.Population;
import java.util.StringTokenizer;
import problem.Individual;

/**
 * Fecundity and Selectivity in Evolutionary Computation
 * Lee Spector, Thomas Helmuth, Kyle Harrington.
 * In Natalio Krasnogor, Pier Luca Lanzi, editors, 
 * 13th Annual Genetic and Evolutionary Computation Conference, GECCO 2011,
 * Companion Material Proceedings, Dublin, Ireland, July 12-16, 2011. 
 * pages 129-130, ACM, 2011. [doi]
 *
 * @author arm
 */
public class Decimation extends Replacement {

    static int DEFAULTSIZE = 2;
    protected int TOURN_SIZE = DEFAULTSIZE;

    @Override
    public Population execute(Population parents, Population children) {
        int sizeNewPop = parents.getNumGenotypes();
        //make new population
        parents.appendPopulation(children);

        //while not selectet the number of genotypes
        while (parents.getNumGenotypes() > sizeNewPop) {

           
            //-------------------------------------------------------------
            //------------ get parents ------------------------------------
            //-------------------------------------------------------------
            Individual worstInd = parents.getRandomIndividual();
            for (int i = 0; i < TOURN_SIZE; i++) {
                Individual ind = parents.getRandomIndividual();
                if (ind.compareTo(worstInd) < 0) {
                    worstInd = ind;
                }
            }
            parents.removeGenotype(worstInd);
        }
        
        return parents;
    }
   

    @Override
    public String getInformation() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.toString());
        buf.append("\nDecimation Replacement ");
        buf.append("\nJoin parents and children");
        buf.append("\n\nMake tornaments with size TOURN");
        buf.append("\n\nremove the worst individual");
        buf.append("\n            <TOURN> - size of tournament (>= 1)");
        return buf.toString();
    }

    @Override
    public void setParameters(String str) {
        StringTokenizer par = new StringTokenizer(str);
        try {
            TOURN_SIZE = Integer.parseInt(par.nextToken());
        } catch (Exception e) {
            TOURN_SIZE =DEFAULTSIZE;
        }
    }
    @Override
    public String getParameters() {
           return  TOURN_SIZE +"";

    }

    @Override
    public String toString() {
        return super.toString() + " <" + TOURN_SIZE + ">";
    }

    @Override
    public Replacement getClone() {
        Decimation clone = (Decimation) super.getClone();
        clone.TOURN_SIZE = this.TOURN_SIZE;
        return clone;
    }
}
