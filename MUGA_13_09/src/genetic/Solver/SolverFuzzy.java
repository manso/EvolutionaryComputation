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
import java.util.Iterator;
import problem.Individual;
import problem.bitString.RealNumber.RealBitString;

/**
 *
 * @author ZULU
 */
public class SolverFuzzy extends GA{
    public static double EXPONENT = 2;
    
    @Override
    public Population evolve(Population original) {
        Population p = super.evolve(original);        
        int bits = 4 + (int)(Math.pow(stopCriteria.getProgress(),EXPONENT) * 28);
        Population newPop = original.getCleanCopie();
        Iterator<Individual> it= p.getIterator();
        while( it.hasNext()){
            RealBitString ind = (RealBitString) it.next();
            ind.resizeBits(bits);
            ind.evaluate();
            newPop.addGenotype(ind);            
        }
        return newPop;
    }
    
    public void setParameters(String params) {
        try{
            EXPONENT = Double.parseDouble(params);
        }catch(Exception e){
            EXPONENT = 2;
        }
    }

    public String getParameters() {
        //do nothing
        return EXPONENT+"";
    }
    
      public String getInformation() {
        StringBuilder str = new StringBuilder();
        str.append("\n Fuzzy Genetic Solver ( " + EXPONENT+" )");
        str.append("\n 1 - create random POP");
        str.append("\n 2 - evaluate POP");
        str.append("\n 3 - until STOP criteria");
        str.append("\n    3.1 - MATE = selection(POP)");
        str.append("\n    3.2 - MATE = recombination(MATE)");
        str.append("\n    3.3 - MATE = mutation(MATE)");
        str.append("\n    3.4 - MATE = reparation(MATE)");
        str.append("\n    3.5 - POP  = replacement(POP,MATE)");
        str.append("\n    3.6 - POP  = rescaling(POP)");
        
        str.append("\n Dimension in Bits of Genes");
        str.append("\n ranges between 4 and 32");
        str.append("\n\nParameters : Velocity of Variation ");
        str.append("\n\n<0.5>Square root <1> linear <2> Quadratic ");
        return str.toString();
    }

}
