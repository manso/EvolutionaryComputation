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
package problem.bitString.RealNumber;

import genetic.gene.GeneNumberBits;
import genetic.gene.GeneNumberGray;
import java.util.StringTokenizer;
import problem.Individual;
import utils.Funcs;

/**
 *
 * @author ZULU
 */
public abstract class RealBitString extends Individual {

    public static double MINIMUM = -10;
    public static double MAXIMUM = 10;
    public static int SIZE_OF_GENE = 16;
    public static int NUMBER_OF_GENES = 10;
    protected double minValue;
    protected double maxValue;

    public RealBitString(boolean typeOptimization, double min, double max, int numGenes, int sizeOfGenes) {
        super(typeOptimization);
        minValue = min;
        maxValue = max;
        NUMBER_OF_GENES = numGenes;
        SIZE_OF_GENE = sizeOfGenes;
        for (int i = 0; i < NUMBER_OF_GENES; i++) {
            addGene(new GeneNumberGray(minValue, maxValue, SIZE_OF_GENE));
        }
    }

    public RealBitString(boolean typeOptimization, double min, double max) {
        this(typeOptimization, min, max, NUMBER_OF_GENES, SIZE_OF_GENE);
    }

    public RealBitString(boolean typeOptimization) {
        this(typeOptimization, MINIMUM, MAXIMUM, NUMBER_OF_GENES, SIZE_OF_GENE);
    }

    public void resizeBits(int newSize) {
        for (int i = 0; i < getNumGenes(); i++) {
            GeneNumberBits g = (GeneNumberBits) getGene(i);
            g.resize(newSize);
        }
    }

    protected double[] getGeneValues() {
        double[] v = new double[genome.getNumGenes()];
        for (int i = 0; i < v.length; i++) {
            v[i] = getGene(i).getValue();
        }
        return v;
    }

    @Override
    public String getGenomeInformation() {
        StringBuilder str = new StringBuilder(this.getClass().getSimpleName());
        str.append("\n[ G= " + NUMBER_OF_GENES);
        str.append(", S= " + SIZE_OF_GENE);
        str.append(", Li= " + minValue);
        str.append(", Lf= " + maxValue + "]");
        str.append("\nParameters :");
        str.append("\n<G> number of variables");
        str.append("\n<S> size of variables in bits");
        str.append("\n<Li> Minimum of Interval");
        str.append("\n<Lf> Maximum of Interval");
        return str.toString();
    }

    @Override
    public void setParameters(String param) {
        StringTokenizer iter = new StringTokenizer(param);
        try {
            if (iter.hasMoreTokens()) {
                NUMBER_OF_GENES = Integer.parseInt(iter.nextToken());
            }
            if (iter.hasMoreTokens()) {
                SIZE_OF_GENE = Integer.parseInt(iter.nextToken());
            }
            if (iter.hasMoreTokens()) {
                MINIMUM = Double.parseDouble(iter.nextToken());
            }
            if (iter.hasMoreTokens()) {
                MAXIMUM = Double.parseDouble(iter.nextToken());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        restart();
    }

    @Override
    public String getParameters() {
        return NUMBER_OF_GENES + " " + SIZE_OF_GENE + " " + minValue + " " + maxValue;
    }
    //---------------------------------------------------------------------------

    @Override
    public String toStringPhenotype() {
        final StringBuilder result = new StringBuilder();
        //number of copies
        String elem = "";

        if (isEvaluated) {
            result.append(Funcs.DoubleToString(fitness, 24));
        } else {
            result.append(Funcs.SetStringSize(" NOT EVALUATED ! ", 24));
        }
        result.append(" = ");

        if (this.numCopys > 1) {
            elem = "[" + Funcs.IntegerToString(numCopys, 4) + "]";
        }
        result.append(Funcs.SetStringSize(elem, 8));
        //genotype
        for (int i = 0; i < genome.getNumGenes(); i++) {
            double value = ((GeneNumberGray) genome.getGene(i)).getValue();
            result.append(Funcs.DoubleToString(value, 24));
        }
        if (isBest()) {
            result.append("\t<Best>");
        }
        result.append(" [" + minValue + " , " + maxValue + "]");
        return result.toString();
    }
}
