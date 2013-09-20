/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package problem.bitString.KSPenalty;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;
import problem.Individual;
import utils.BitField;
import utils.Funcs;
import utils.Knapsack.KnapSack_DP;
import utils.RandomVariable;

/**
 *
 * @author arm
 */
public abstract class Knapsack extends Individual {

    protected static double KBEST = 0; //value of the best
    protected static Random random = new Random();
    protected static double[] w;
    protected static double[] p;
    protected static int numItens = 0;
    //1996-Genetic Algorithms  Data Structures Evolution Programs (3ed)
    // pp 82 - multiplied 
    protected static int v = 10 * 10;//
    protected static int r = 5 * 10;
    protected static double sackSize = 0.5;
    protected static double capacity = 5;
    protected static double totalCapacity = 0;
    protected static double totalValue = 5;
    protected static double factorPenalty = 0;
    //correlation of itens
    public final static int UNCorrelated = 0;
    public final static int WEAKLYCorrelated = 1;
    public final static int STRONGCorrelated = 2;
    public final static String[] nameCorrelation = {"UNCorrelated", "WEAKLYCorrelated", "STRONGLYCorrelated"};
    protected static int typeCorrelation = UNCorrelated;

    public static void createProblem(double sackSize) {
        if (numItens <= 0) {
            numItens = 32;
        }
        switch (typeCorrelation) {
            case UNCorrelated:
                uncorrelated(numItens);
                break;
            case WEAKLYCorrelated:
                weaklyCorrelated(numItens);
                break;
            case STRONGCorrelated:
                strongCorrelated(numItens);
                break;
            default: {
                weaklyCorrelated(numItens);
                typeCorrelation = STRONGCorrelated;
            }
        }

        calcTotalValue();
        capacity = calcTotalCapacity() * sackSize;
        KBEST = KnapSack_DP.getBestknapSack01Value(capacity, w, p);
        setBest(KBEST);
    }

    public static void setTypeOfCorrelation(int type) {
        typeCorrelation = type;
        createProblem(sackSize);
    }

    public Knapsack() {
        super(MAXIMIZE);
    }

    @Override
    public boolean isBest() {
        return isViable() && Knapsack.valueSack(this.getBits()) >= getBest();
    }

    public boolean isViable() {
        return Knapsack.heightSack(this.getBits()) <= capacity;
    }

    public static double getCapacity() {
        return capacity;
    }

    public static double calcTotalCapacity() {
        totalCapacity = 0;
        for (int i = 0; i < w.length; i++) {
            totalCapacity += w[i];
        }
        return totalCapacity;
    }

    public static double getTotalCapacity() {
        return totalCapacity;
    }

    public static double getTotalValue() {
        return totalValue;
    }

    public static double calcTotalValue() {
        totalValue = 0;
        for (int i = 0; i < p.length; i++) {
            totalValue += p[i];
        }
        return totalValue;
    }

    public static void uncorrelated(int size) {
        numItens = size;
        w = new double[numItens];
        p = new double[numItens];
        for (int i = 0; i < numItens; i++) {
            w[i] = (int) RandomVariable.uniform(1, v + 1);
            p[i] = (int) RandomVariable.uniform(1, v + 1);
        }
        calcTotalValue();
    }

    public static void weaklyCorrelated(int size) {
        numItens = size;
        w = new double[numItens];
        p = new double[numItens];
        for (int i = 0; i < numItens; i++) {
            //repeat if profit is less than zero
            do {
                w[i] = (int) RandomVariable.uniform(1, v + 1);
                p[i] = w[i] + (int) RandomVariable.uniform(-r - 1, r + 1);
            } while (p[i] <= 0);
        }
        factorPenalty = calcFactPenalty();
        calcTotalValue();
    }

    public static void strongCorrelated(int size) {
        numItens = size;
        w = new double[numItens];
        p = new double[numItens];
        for (int i = 0; i < numItens; i++) {
            w[i] = (int) RandomVariable.uniform(1, v + 1);
            p[i] = w[i] + r;
        }
        factorPenalty = calcFactPenalty();
        calcTotalValue();
    }

    public static void restrictiveCapacity() {
        factorPenalty = calcFactPenalty();
        capacity = 2 * v;
    }

    public static void averageCapacity() {
        factorPenalty = calcFactPenalty();
        double sum = 0.0;
        for (int i = 0; i < numItens; i++) {
            sum += w[i];
        }
        capacity = 0.5 * sum;
    }

    //----------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------
    private static double calcFactPenalty() {
        double max = p[0] / w[0];
        for (int i = 1; i < p.length; i++) {
            if (p[i] / w[i] > max) {
                max = p[i] / w[i];
            }
        }
        return max;
    }
//----------------------------------------------------------------------------------

    public static double penaltyLog(double value) {
        if (value <= capacity) {
            return 0.0;
        }
        return Math.log10(1.0 + factorPenalty * (value - capacity)) / Math.log10(2);
    }
//----------------------------------------------------------------------------------

    public static double penaltyLinear(double value) {
        if (value <= capacity) {
            return 0.0;
        }
        return factorPenalty * (value - capacity);
    }
//----------------------------------------------------------------------------------

    public static double penaltyQuadratic(double value) {
        if (value <= capacity) {
            return 0.0;
        }
        return Math.pow(factorPenalty * (value - capacity), 2.0);
    }
//----------------------------------------------------------------------------------

    public static double heightSack(BitField bits) {
        double value = 0.0;
        for (int i = 0; i < bits.getNumberOfBits(); i++) {
            if (bits.getBit(i)) {
                value += w[i];
            }

        }
        return value;
    }

    public double heightSack() {
        return heightSack(this.getBits());
    }

    public static double valueSack(BitField bits) {
        double value = 0.0;
        for (int i = 0; i < bits.getNumberOfBits(); i++) {
            if (bits.getBit(i)) {
                value += p[i];
            }

        }
        return value;
    }
    //----------------------------------------------------------------------------------

    public static void repairRandom(BitField x) {
        while (heightSack(x) > capacity) {
            x.setBitFalse(random.nextInt(x.getNumberOfBits()));
        }
    }

    public static void deleteWorstItem(BitField bits) {
        int index = -1;
        double value = Double.POSITIVE_INFINITY;

        for (int i = 0; i < bits.getNumberOfBits(); i++) {
            if (bits.getBit(i) && p[i] / w[i] < value) {
                value = p[i] / w[i];
                index = i;
            }
        }
        if (index >= 0) {
            bits.setBitFalse(index);
        }
    }
    //----------------------------------------------------------------------------------

    public static void repairGreedy(BitField x) {
        while (heightSack(x) > capacity) {
            deleteWorstItem(x);
        }
    }

//    public static String getInfo() {
//        StringBuffer txt = new StringBuffer();
//        txt.append("\nCapacity            :" + Knapsack.capacity);
//        txt.append("\nBest Value          :" + Knapsack.getBest());
//        txt.append("\nBest Individual     :\n");
//        if (bestIndividual != null) {
//            txt.append("\nIndividual     :" + Individual.bestIndividual.toString());
//            txt.append("\nHeight Sack     :" + heightSack(Individual.bestIndividual.getBits()));
//            txt.append("\nValue  Sack     :" + valueSack(Individual.bestIndividual.getBits()));
//        }
//        txt.append("\nnumber of Itens     : " + Knapsack.numItens);
//        txt.append("\ntype of Correlation : " + Knapsack.typeCorrelation +
//                " <" + UNCorrelated + " Uncorrelated> <" + WEAKLYCorrelated + " WeaklyCorrelated> <" +
//                +STRONGCorrelated + " StrongCorrelated >");
//        txt.append(Funcs.SetStringSize("\nweights", 15));
//        for (double wi : w) {
//            txt.append(Funcs.SetStringSize(wi + "", 7) + " ");
//        }
//        txt.append(Funcs.SetStringSize("\nprofits", 15));
//        for (double pi : p) {
//            txt.append(Funcs.SetStringSize(pi + "", 7) + " ");
//        }
//        return txt.toString();
//    }
    @Override
    public String toStringPhenotype() {
        double val = valueSack(this.getBits());
        double height = heightSack(this.getBits());

        ByteArrayOutputStream bytes = new ByteArrayOutputStream(80);
        PrintStream txt = new PrintStream(bytes);
        txt.printf("[%8d ]Profit: %15f        Height: %15f   Fitness: % 15f  Genome: %s", this.getNumCopies(), val, height, fitness, genome.getBinString());
        if (height > capacity) {
            txt.printf(" <EXCESS>");
        }
        return new String(bytes.toByteArray());
    }

    @Override
    public String toStringGenotype() {
        StringBuilder result = new StringBuilder();
        //number of copies
        String elem = "";
        if (this.getNumCopies() > 1) {
            elem = "[" + Funcs.IntegerToString(getNumCopies(), 4) + "]";
        }
        result.append(Funcs.SetStringSize(elem, 8));

        if (this.isEvaluated()) {
            result.append(Funcs.DoubleToString(fitness, 10));
        } else {
            result.append(Funcs.SetStringSize("?K?K?", 10));
        }
        result.append(" = ");

        //genotype
        for (int i = 0; i < genome.getNumGenes(); i++) {
            result.append(genome.getGene(i).toBinString() + "\t");
        }

        if (isBest()) {
            result.append("\t<Best>");
        }
        return result.toString();
    }

    public String getGenomeInformation() {
        // public String getInfo() {
        StringBuilder buf = new StringBuilder();
        buf.append("PROBLEM      : ");
        buf.append(getClass().getName());
        buf.append("\n\nSack Capacity:" + capacity + " / " + totalCapacity + " = " + (capacity / totalCapacity) * 100.0 + "%");
        //update Best
        setBest(KnapSack_DP.getBestknapSack01Value(capacity, w, p));
        
        buf.append("\nBest Value   :" + getBest());
        buf.append("\nElements     :" + numItens);
        buf.append("\nCorrelation  :" + Knapsack.nameCorrelation[typeCorrelation]);
        
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


        buf.append("\n\nParameters:<Size><Sack Size><Correlation>");
        buf.append("\nSize       :  >= 1");
        buf.append("\nSack Size  :  [0.0  1.0]");
        buf.append("\nCorrelation: ");
        for (int i = 0; i < Knapsack.nameCorrelation.length; i++) {
            buf.append("\n\t\t: " + i + "\t:" + Knapsack.nameCorrelation[i]);
        }

        return buf.toString();

    }

    @Override
    public void setParameters(String param) {
        String[] par = param.split(" ");
        if (param.isEmpty()) {
            return;
        }
        //number of itens
        try {
            numItens = Integer.parseInt(par[0]);
            if (numItens <= 0) {
                numItens = 32;
            }
        } catch (Exception e) {
            numItens = 32;
        }
        //Sack Size
        try {
            sackSize = Double.parseDouble(par[1]);
            if (sackSize < 0 || sackSize > 1) {
                sackSize = 0.5;
            }
        } catch (Exception e) {
            sackSize = 0.5;
        }
        //type of correlation
        try {
            typeCorrelation = Integer.parseInt(par[2]);
            if (typeCorrelation < 0 || typeCorrelation >= Knapsack.nameCorrelation.length) {
                typeCorrelation = WEAKLYCorrelated;
            }
        } catch (Exception e) {
            typeCorrelation = WEAKLYCorrelated;
        }
        createProblem(sackSize);
        sort( p,w);
        //----------------------------------------------
        // Set new Genotype to this individual
        restart();
        //----------------------------------------------
    }

    public String getParameters() {
        return w.length + " " + sackSize + " " + typeCorrelation;
    }

    /**
     * fill random all bits of every gene in genome
     */
    @Override
    synchronized public void fillRandom() {
        genome.fillRandom();
        isEvaluated = false;
        fitness = Double.NaN;
    }

    public static boolean insertBestItem(BitField bits, double weight) {
        int index = -1;
        double best = -1;
        for (int i = 0; i < bits.getNumberOfBits(); i++) {
            if (bits.getBit(i) == false && w[i] <= weight && p[i] / w[i] < best) {
                best = p[i] / w[i];
                index = i;
            }
        }
        if (index >= 0) {
            bits.setBitTrue(index);
            return true;
        }
        return false;
    }
    
      public static void sort(double master[], double[] slave) {
        for (int i = master.length - 1; i > 0; i--) {
            for (int k = 0; k < i; k++) {
                if (master[k]/slave[k] < master[k + 1]/slave[k+1]) {
                    double aux = master[k];
                    master[k] = master[k + 1];
                    master[k + 1] = aux;

                    aux = slave[k];
                    slave[k] = slave[k+1];
                    slave[k+1] = aux;
                }

            }
        }
    }
}
