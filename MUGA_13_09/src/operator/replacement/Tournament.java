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
 *
 * @author arm
 */
public class Tournament extends Replacement {

    static int DEFAULTSIZE = 2;
    protected int TOURN_SIZE = DEFAULTSIZE;

    @Override
    public Population execute(Population parents, Population children) {
        int sizeNewPop = parents.getNumGenotypes();
        //make new population
        Population selected = children.getCleanCopie();

        //while not selectet the number of genotypes
        while (selected.getNumGenotypes() < sizeNewPop && children.getNumGenotypes() > 0) {

            if (parents.getNumIndividuals() == 0) {
                System.out.println("ERROR " + this.getClass().getCanonicalName());
                break;
            }
            //-------------------------------------------------------------
            //------------ get parents ------------------------------------
            //-------------------------------------------------------------
            Individual bestP = parents.getRandomIndividual();
            for (int i = 0; i < TOURN_SIZE / 2 - 1; i++) {
                Individual ind = parents.getRandomIndividual();
                if (ind.compareTo(bestP) > 0) {
                    bestP = ind;
                }
            }
            //-------------------------------------------------------------
            //------------ get children -----------------------------------
            //-------------------------------------------------------------
            Individual bestC = children.getRandomIndividual();
            for (int i = 0; i < TOURN_SIZE / 2 - 1; i++) {
                Individual ind = children.getRandomIndividual();
                if (ind.compareTo(bestC) > 0) {
                    bestC = ind;
                }
            }

            if (bestC.compareTo(bestP) >= 0) {
                children.removeIndividual(bestC);
                selected.addIndividual(bestC);
            } else {
                parents.removeIndividual(bestP);
                selected.addIndividual(bestP);
            }

        }
        parents.appendPopulation(children);
        //add random genotypes to complete population
        while (selected.getNumGenotypes() < sizeNewPop) {
            selected.addIndividual(parents.removeRandomGenotype());
        }
        return selected;
    }
   

    @Override
    public String getInformation() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.toString());
        buf.append("\nTournament Replacement ");
        buf.append("\nSelect the best of the tournament with parents and children");
        buf.append("\n\nParameters: <TOURN>");
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
        Tournament clone = (Tournament) super.getClone();
        clone.TOURN_SIZE = this.TOURN_SIZE;
        return clone;
    }
}
