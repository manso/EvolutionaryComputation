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
package genetic.gene;

import problem.bitString.RealNumber.F5_Griewank;
import utils.BitField;

/**
 *
 * @author ZULU
 */
public class GeneNumberBits extends GeneNumber {

    /**
     * Creates a new instance of GeneBinary
     *
     * @param size number of bits
     */
    public GeneNumberBits(double min, double max, int size) {
        alels = new BitField(size);
        minValue = min;
        maxValue = max;
        fillRandom();
    }

    /**
     * Copy Constructor - make a object copy
     *
     * @param other template o copy
     */
    public GeneNumberBits(GeneNumberBits other) {
        this(other.minValue, other.maxValue, other.alels.getNumberOfBits());
        alels.storeBitField(other.alels);
    }

    @Override
    public double getValue() {
        double pow2 = Math.pow(2, alels.getNumberOfBits());
        double val = alels.getBigInteger().longValue();
        return minValue + val * ((maxValue - minValue) / pow2);
    }

    @Override
    public void setValue(double value) {
        if (value < minValue) {
            value = minValue;
        }
        if (value > maxValue) {
            value = maxValue;
        }

        double pow2 = Math.pow(2, alels.getNumberOfBits());
        long val = (long) (((value - minValue) / (maxValue - minValue)) * pow2);
        alels.setInteger(val);
    }

    @Override
    public Gene getClone() {
        return new GeneNumberBits(this);
    }

    public void resize(int newSize) {
        //get actual value
        double value = getValue();
        //resize alels
        alels = new BitField(newSize);
        //set value
        setValue(value);
    }

    public static void main(String[] args) {
        GeneNumberBits g = new GeneNumberBits(0, 16, 32);
        double val = 7.123456789;
        for (int i = 32; i > 5; i--) {
            g.resize(i);
            g.setValue(val);
            //g.resize(i);            
            System.out.println(i + " \tGene " + g.toBinString() + " = " + g.getValue());
        }
        F5_Griewank f5 = new F5_Griewank();
        f5.evaluate();
        System.out.println(f5.toStringPhenotype());

    }
}
