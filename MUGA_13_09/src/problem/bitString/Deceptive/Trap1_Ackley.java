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
 *
 * @author ZULU
 */
public class Trap1_Ackley extends Individual {

    public static int NUMBER_OF_BLOCKS = 1;

    public Trap1_Ackley() {
        super(MAXIMIZE);
        setBest(NUMBER_OF_BLOCKS * 200);
        for (int i = 0; i < NUMBER_OF_BLOCKS; i++) {
            addGene(new GeneNumberBits(0, 1, 20));
        }
    }

    @Override
    protected double fitness() {
        double fit = 0;
        for (int i = 0; i < getGenome().getNumGenes(); i++) {
            int u = getGene(i).getAlels().getNumberOfOnes();
            if (u < 15) {
                fit += 160.0 / 15 * (15 - u);
            } else {
                fit += 200.0 / 5 * (u - 15);
            }
        }
        return fit;
    }

    @Override
    public String getGenomeInformation() {
        StringBuilder buf = new StringBuilder();
        buf.append("Ackley Trap Function  <").append(NUMBER_OF_BLOCKS).append(">");

        buf.append("\n\nParameters <BLOCKS>");
        buf.append("\n     <#BLOCKS>  number of deceptive block ");
        buf.append("\n\nA Sequential Niche Technique "
                + "\nfor Multimodal Function Optimization (1993)");
        buf.append("\nhttp://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.30.3068");
        return buf.toString();

    }

    @Override
    public String getParameters() {
        return "" + NUMBER_OF_BLOCKS;
    }

    @Override
    public void setParameters(String param) {
        StringTokenizer iter = new StringTokenizer(param);
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
