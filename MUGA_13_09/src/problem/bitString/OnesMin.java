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
package problem.bitString;

import genetic.gene.GeneBinary;
import java.util.StringTokenizer;
import problem.Individual;
import utils.Funcs;

/**
 * Designing Efficient And Accurate Parallel Genetic Algorithms (1999) Erick
 * Cantú-Paz
 *
 * Theone-max problem is probably the mostfrequently usedfitness function in
 * genetic algorithms research because of its simplicity. Thefitness of an
 * individual is equal to the number of bits on its chromosome This is very easy
 * for GAs since there no isolation or deception
 *
 * A Parameter-Less Genetic Algorithm (1999) by Georges Harik , Fernando Lobo
 * IEEE TRANSACTIONS ON EVOLUTIONARY COMPUTATION
 * http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.50.3269&rep=rep1&type=pdf
 *
 * 100 bits ~3.00 evaluations
 *
 * [SE91] J.D. Schaffer and L.J. Eshelman. "On crossover as an evolutionary
 * viable strategy". In R.K. Belew and L.B. Booker, editors. Proceedings of the
 * 4th International Conference on Genetic Algorithms, pages 61-68, Morgan
 * Kaufmann, 1991.
 *
 *
 * @author ZULU
 */
public class OnesMin extends Individual {

    public static int SIZE_OF_BLOCK = 100;

    public OnesMin() {
        super(MINIMIZE);
        setBest(SIZE_OF_BLOCK);
        addGene(new GeneBinary(SIZE_OF_BLOCK));
    }

    public OnesMin(int size) {
        super(MINIMIZE);
        SIZE_OF_BLOCK = size;
        setBest(SIZE_OF_BLOCK);
        addGene(new GeneBinary(SIZE_OF_BLOCK));
    }

    public OnesMin(String bits) {
        super(MINIMIZE);
        SIZE_OF_BLOCK = bits.length();
        setBest(SIZE_OF_BLOCK);
        addGene(new GeneBinary(bits));
    }

    @Override
    public String toString() {
        return toStringGenotype();
    }

    @Override
    public String toStringGenotype() {
        StringBuilder txt = new StringBuilder();
        //number of copies
        String elem = "";
        if (this.getNumCopies() > 1) {
            elem = "[" + Funcs.IntegerToString(getNumCopies(), 4) + "]";
        }
        txt.append(Funcs.SetStringSize(elem, 8));

        if (this.isEvaluated()) {
            txt.append(Funcs.DoubleToString(fitness, 10));
        } else {
            txt.append(Funcs.SetStringSize("????", 10));
        }
        txt.append(" = ");
        for (int i = 0; i < getNumGenes(); i++) {
            txt.append(getGene(i).toBinString() + " ");
        }
        return txt.toString();
    }

    @Override
    public String toStringPhenotype() {
        return toStringGenotype();
    }

    @Override
    public String getGenomeInformation() {
        // public String getInfo() {
        StringBuilder buf = new StringBuilder();

        buf.append("\n\nParameters <SIZE>");
        buf.append("\n     <SIZE>  size of genome");
        return buf.toString();

    }

    @Override
    protected double fitness() {
        return getGene(0).getAlels().getNumberOfOnes() - getGene(0).getAlels().getNumberOfBits();
    }
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------

    @Override
    public String getParameters() {
        return "" + SIZE_OF_BLOCK;
    }

    @Override
    public void setParameters(String param) {
        StringTokenizer iter = new StringTokenizer(param);
        if (iter.hasMoreTokens()) {
            //number of itens
            try {
                SIZE_OF_BLOCK = Integer.parseInt(iter.nextToken());
                if (SIZE_OF_BLOCK <= 0) {
                    SIZE_OF_BLOCK = 8;
                }
            } catch (Exception e) {
                SIZE_OF_BLOCK = 8;
            }
        }
        //----------------------------------------------
        // Set new Genotype to this individual
        restart();
        //----------------------------------------------
    }
}
