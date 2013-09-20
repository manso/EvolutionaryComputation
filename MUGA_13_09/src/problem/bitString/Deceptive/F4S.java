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
package problem.bitString.Deceptive;

import genetic.gene.GeneNumberBits;
import java.util.StringTokenizer;
import problem.Individual;
import utils.BitField;

/**
 * from paper:
 *Whitley L.D., “Fundamental Principles of Deception in Genetic Search,” Foundations of
 *Genetic Algorithms, Volume 1, Rowlins G.J.E. (ed). Morgan Kaufmann, ISBN 1-55860-170-
 * 8. 1991, pp 221-241.
 * http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.18.6847&rep=rep1&type=pdf
 * 
 * 
 * * The Island Model Genetic Algorithm: On Separability, Population Size and Convergence (1998)
 * by Darrell Whitley , Soraya Rana , Robert B. Heckendorn
 * Journal of Computing and Information Technology
 * http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.36.7225&rep=rep1&type=pdf

* 
 * 
 * @author ZULU
 */
public class F4S extends F4 {

    public static int NUMBER_OF_BLOCKS = 10;

    public F4S() {
        super();
    }

    @Override
    protected double fitness() {
        double fit = 0;
        BitField bits = getBits();
        for (int i = 0; i < getGenome().getNumGenes(); i++) {
            int g = getGeneValue(bits, i);
            fit += values[g];
        }
        return fit;
    }
    
   
    @Override
    protected BitField getGeneBits(BitField bits, int gene) {
         BitField value = new BitField(GENE_SIZE);
        int step = bits.getNumberOfBits() / GENE_SIZE;
        int index;
        for (int j = 0; j < GENE_SIZE; j++) {
            index = (gene + j*step);
            value.setBit(j, bits.getBit(index));        
        }
        return value;
    }

    @Override
    public String getGenomeInformation() {
        StringBuilder buf = new StringBuilder();
        buf.append("Fully Separated Deceptive Problem <").append(NUMBER_OF_BLOCKS).append(">");
        buf.append("\n\n 4-bit fully deceptive problem SEPARATED");        
        buf.append("\nf(1111) = 30 f(0000) = 28 f(0111) = 0  f(1011) = 2");
        buf.append("\nf(1101) = 4  f(1110) = 6  f(1100) = 8  f(1010) = 10");
        buf.append("\nf(1001) = 12 f(0110) = 14 f(0101) = 16 f(0011) = 18");
        buf.append("\nf(1000) = 20 f(0100) = 22 f(0010) = 24 f(0001) = 26");
        buf.append("\nSEPARATION: Bits of function are separated in the ");
        buf.append("\nBlocks");
        buf.append("\n\nParameters <BLOCKS>");
        buf.append("\n     <#BLOCKS>  number of deceptive block ");
        
        buf.append("\n\nL.  D.  Whitley Whitley");
        buf.append("\nFundamental  Principles  of Deception  in  Genetic  Search");
        buf.append("\nFoundations  of Genetic Algorithms, 1991");
        
        return buf.toString();

    }
    
    public static void main(String[] args) {
        F4.NUMBER_OF_BLOCKS = 3;
        F4S f = new F4S();
        System.out.println(f.toStringGenotype());
        System.out.println(f.toStringPhenotype());
    }

   
   
}
