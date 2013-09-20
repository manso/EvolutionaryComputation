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
package problem.bitString.Deceptive;

import genetic.gene.GeneNumberBits;
import java.util.StringTokenizer;
import problem.Individual;

/**
 *Designing Efficient And Accurate Parallel Genetic Algorithms (1999)
 * Erick Cantú-Paz
 * 
 * Fully deceptive trap functions are used in many studies of GAs becauset heir
 * diculty is well understood and it can be regulated easily
 * Trapfunctions are hard for traditional optimizers, because they will tend to
 * climb to the deceptive peak, but, GAs with properly sized populations can solve
 * them satisfactorily.
 * To solve the trap functions, tight linkage was used.
 * Tight linkage = the bits that define each trap function are positioned next
 * to eachother in the chromosome
 * 
 * Solved by messyGA (Goldberg,Korb,&Deb,1989) and its relatives, that are able 
 * to find tight linkages automatacally.
 * 
 * Solved by BOA Bayesian optimization algorithm(Pelikan,Goldberg,&antu-Paz,1999)
 * 
 * solved by  linkage Learning GA (LLGA)
 * 
 * The global maximum of the function occurs precisely at the oppositive extreme
 * where all the bits are seted to one.
 * 
 * Results:
 * 
 * TRAP-5
 * Martin Pelikan, David E. Goldberg, & Erick Cantú-Paz, E. (1999)
 * BOA: The Bayesian optimization algorithm.
 * Proceedings of the Genetic and Evolutionary Computation Conference (GECCO-99), I, 525-532
 * http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.46.8131&rep=rep1&type=pdf
 * 
 *  * A Parameter-Less Genetic Algorithm (1999)
 * by Georges Harik , Fernando Lobo
 * IEEE TRANSACTIONS ON EVOLUTIONARY COMPUTATION
 * http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.50.3269&rep=rep1&type=pdf
 * TRAP - 4
 * 10 blocks - ~2E6 200 000
 * 
 * 
 * 
 * 
 * @author ZULU
 */
public class Trap extends Individual {

    public static int NUMBER_OF_BLOCKS = 10;
    public static int SIZE = 5;

    public Trap() {
        super(MAXIMIZE);
        setBest(NUMBER_OF_BLOCKS * SIZE);
        for (int i = 0; i < NUMBER_OF_BLOCKS; i++) {
            addGene(new GeneNumberBits(0, 1, SIZE));
        }
    }

    @Override
    protected double fitness() {
        double fit = 0;
        for (int i = 0; i < getGenome().getNumGenes(); i++) {
            int u = getGene(i).getAlels().getNumberOfOnes();
            if (u < SIZE) {
                fit += SIZE - 1 - u;
            } else {
                fit += SIZE;
            }
        }
        return fit;
    }

    @Override
    public String getGenomeInformation() {
        StringBuilder buf = new StringBuilder();
        buf.append(" Trap Function  <"+SIZE+"><"+NUMBER_OF_BLOCKS+">");
        buf.append("\n            |SIZE         (u=SIZE) ");
        buf.append("\nTrap(SIZE) =|");
        buf.append("\n            |SIZE - 1 - u (otherwise)");
        buf.append("\n u = unititation");
        buf.append("\n\nParameters <SIZE><BLOCKS>");
        buf.append("\n     <SIZE>  size of deceptive block ");
        buf.append("\n     <BLOCKS>  number of deceptive block ");
        return buf.toString();
    }

    @Override
    public String getParameters() {
        return SIZE + " " + NUMBER_OF_BLOCKS;
    }

    @Override
    public void setParameters(String param) {
        StringTokenizer iter = new StringTokenizer(param);
        if (iter.hasMoreTokens()) {
            //number of itens
            try {

                SIZE = Integer.parseInt(iter.nextToken());
                if (SIZE < 1) {
                    SIZE = 4;
                }
            } catch (Exception e) {
                SIZE = 4;
            }
        }
        if (iter.hasMoreTokens()) {
            //number of itens
            try {

                NUMBER_OF_BLOCKS = Integer.parseInt(iter.nextToken());
                if (NUMBER_OF_BLOCKS <= 0) {
                    NUMBER_OF_BLOCKS = 1;
                }
            } catch (Exception e) {
                NUMBER_OF_BLOCKS = 1;
            }
        }
        this.restart();
    }
}
