/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package problem.permutation;

import genetic.gene.GenePureInteger;
import problem.IndividualPermutation;
import utils.Funcs;

/**
 *
 * @author manso
 */
public class Queens extends IndividualPermutation {

    /**
     * Dimension of problem- number of queens and the dimension of board
     */
    public static int NUMBER_OF_QUEEN = 8;

    public Queens() {
        super(MINIMIZE);
        for (int index = 0; index < NUMBER_OF_QUEEN; index++) {
            GenePureInteger g = new GenePureInteger(0, NUMBER_OF_QUEEN - 1, index);
            this.genome.addGene(g);
        }
        setBest(0);
    }

    @Override
    protected double fitness() {
        int ataks = 0;
        for (int index = 0; index < NUMBER_OF_QUEEN; index++) {
            ataks += getAtaks(index);
        }
        return ataks;
    }

    /**
     * gets atacks of the queen
     * @param queen Position of genome
     * @return number of atacks
     */
    public int getAtaks(int queen) {
        double posQueen = genome.getGene(queen).getValue();
        int numAtaks = 0;
        for (int index = 0; index < NUMBER_OF_QUEEN; index++) {
            if (index == queen) {
                continue;
            }
            double posIndex = genome.getGene(index).getValue();
            if (java.lang.Math.abs(queen - index)
                    == java.lang.Math.abs((int) posQueen - (int) posIndex)) {
                numAtaks++;
            }
        }
        return numAtaks;
    }

    @Override
    public String toStringPhenotype() {
        StringBuilder result = new StringBuilder();
         //number of copies
        String elem = "";
        if (this.getNumCopies() > 1) {
            elem = "[" + Funcs.IntegerToString(getNumCopies(), 4) + "]";
        }
        result.append(Funcs.SetStringSize(elem, 8));
        result.append(Funcs.IntegerToString((int) getFitness(), 10) + " = ");
        for (int i = 0; i < this.getNumGenes(); i++) {
            if (getAtaks(i) > 0) {
                result.append(" X ");
            } else {
                result.append(" . ");
            }
        }
        return result.toString();
    }
//    

    @Override
    public String toStringGenotype() {
        StringBuilder result = new StringBuilder();
         //number of copies
        String elem = "";
        if (this.getNumCopies() > 1) {
            elem = "[" + Funcs.IntegerToString(getNumCopies(), 4) + "]";
        }
        result.append(Funcs.SetStringSize(elem, 8));
        result.append(Funcs.IntegerToString((int) getFitness(), 10) + " = ");
        for (int i = 0; i < this.getNumGenes(); i++) {
            result.append(Funcs.IntegerToString((int) getGene(i).getValue(), 4));
        }
        return result.toString();
    }

    public String getGenomeInformation() {
        // public String getInfo() {
        StringBuilder buf = new StringBuilder();
        buf.append("Queens Atacks");
        buf.append("\nMinimize Atack in the chess board");
        buf.append("\n\nParameters <QUEENS>");
        buf.append("    <QUEENS> number of queens");
        return buf.toString();
    }

    @Override
    public void setParameters(String param) {
        int old = NUMBER_OF_QUEEN;
        try {
            NUMBER_OF_QUEEN = Integer.parseInt(param);
            return;
        } catch (Exception e) {
        }
        NUMBER_OF_QUEEN = old;
    }
}
