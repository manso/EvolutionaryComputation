/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package problem.bitString.KSPenalty;

import genetic.gene.GeneBinary;
import utils.Funcs;

/**
 * # Bests :4 [2 ]Profit: 1920,000000 Height: 1468,000000 Fitness: 1920,000000
 * Genome: 00000111110100101100100101111010011010101111110111 [3 ]Profit:
 * 1920,000000 Height: 1463,000000 Fitness: 1920,000000 Genome:
 * 00001111110100101100100101111010001010101111110111 [1 ]Profit: 1920,000000
 * Height: 1466,000000 Fitness: 1920,000000 Genome:
 * 00001111110100101100100101111010111010101110110111 [3 ]Profit: 1920,000000
 * Height: 1473,000000 Fitness: 1920,000000 Genome:
 * 00001110110100101100100101111110111010001111110111
 *
 *
 * @author manso
 */
public class K50 extends Knapsack {
//--------------------------------------------------------------------
//-------- B E S T   I N D I V I D U A L     -------------------------
//--------------------------------------------------------------------

    private static double bestFitness = 1920;
//--------------------------------------------------------------------
//--------------------------------------------------------------------
//--------------------------------------------------------------------
    public static double w5[] = {
        94, 70, 90, 97, 54, 31, 82, 97, 1, 58, 96, 96, 87, 53, 62, 89, 68, 58, 81, 83, 67, 41, 50, 58, 61,
        45, 64, 55, 12, 87, 32, 53, 25, 59, 23, 77, 22, 18, 64, 85, 14, 23, 76, 81, 49, 47, 88, 19, 74, 31
    };
    public static double p5[] = {
        3, 41, 22, 30, 45, 99, 75, 76, 79, 77, 41, 98, 31, 28, 58, 32, 99, 48, 20, 3, 81, 17, 3, 62, 39,
        76, 94, 75, 44, 63, 35, 11, 21, 45, 43, 46, 26, 2, 53, 37, 32, 78, 74, 66, 61, 51, 11, 85, 90, 40
    };

    static {
        sort(p5, w5);
    }

    public K50() {
        super();
        if (numItens != 50 || w5 != w) {
            setBest(1920.0);
            numItens = w5.length;
            w = w5;
            p = p5;
            calcTotalValue();
            calcTotalCapacity();
            averageCapacity();
            setBest(bestFitness);
        }
        setBest(bestFitness);
        addGene(new GeneBinary(50));
    }

    @Override
    public double fitness() {
        double val = valueSack(this.getBits());
        double height = heightSack(this.getBits());
        double pen = penaltyLinear(height);
        return val - pen;
    }

    @Override
    public void setParameters(String param) {
       numItens = w5.length;
        w = w5;
        p = p5;
        calcTotalValue();
        calcTotalCapacity();
        averageCapacity();
        setBest(1920.0);
        
    }

    public String getGenomeInformation() {
        // public String getInfo() {
        StringBuilder buf = new StringBuilder();
        buf.append("\n" + this.getClass().getSimpleName());
        buf.append("\n\nSack Capacity:" + capacity + " / " + totalCapacity + " = " + (capacity / totalCapacity) * 100.0 + "%");
        buf.append("\nBest Value   :" + getBest());
        
        buf.append("\nElements     :" + numItens);
        buf.append("\n\nItem      = ");
        for (int i = 0; i < w.length; i++) {
            buf.append(Funcs.IntegerToString(i, 6));
        }
        buf.append("\nWeight    = ");
        for (int i = 0; i < w.length; i++) {
            buf.append(Funcs.DoubleToString(w[i], 6));
        }
        buf.append("\nProfit    = ");
        for (int i = 0; i < p.length; i++) {
            buf.append(Funcs.DoubleToString(p[i], 6));
        }
        return buf.toString();

    }
//    public static String getInfo() {
//        return " KnapSack - Evolutionary Computation Benchmark Repository \n"+
//                "http://www.cs.bham.ac.uk/research/projects/ecb/displayDataset.php?id=5"+
//                "Best\n"+
//                "00000111110100101100100101111010011010101111110111	 = 1920.0"+
//                "00001111110100101100100101111010001010101111110111	 = 1920.0"+
//                "00001111110100101100100101111010111010101110110111	 = 1920.0"+
//                "00001110110100101100100101111110111010001111110111	 = 1920.0"+
//                "\n"+
//                Knapsack.getInfo();
//    }
};
