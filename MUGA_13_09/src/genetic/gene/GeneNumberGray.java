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

import utils.BitField;

/**
 *
 * @author ZULU
 */
public class GeneNumberGray extends GeneNumber {

//    public GeneNumberGray(String bits) {
//        alels = new BitField(bits);
//        minValue = -1;
//        maxValue = 1;
//    }
//
//    /**
//     * Creates a new instance of GeneBinary
//     *
//     * @param size number of bits
//     */
//    public GeneNumberGray(int size) {
//        this(-1, 1, size);
//    }
    /**
     * Creates a new instance of GeneBinary
     *
     * @param size number of bits
     */
    public GeneNumberGray(double min, double max, int size) {
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
    public GeneNumberGray(GeneNumberGray other) {
        this(other.minValue, other.maxValue, other.alels.getNumberOfBits());
        alels.storeBitField(other.alels);
    }

    @Override
    public double getValue() {
        double pow2 = Math.pow(2, alels.getNumberOfBits());
        double val = alels.getGrayInteger();
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
        alels.setGrayInteger(val);
    }

    @Override
    public Gene getClone() {
        return new GeneNumberGray(this);
    }

    public void resize(int newSize) {
        if (newSize < alels.getNumberOfBits()) {
            //get actual value
            double value = getValue();
            //resize alels
            alels = new BitField(newSize);
            //set value
            setValue(value);
        } else {
            BitField b = new BitField(newSize - alels.getNumberOfBits());
            b.fillRandom();
            alels.appendBits(b);
        }
    }

    public static void main(String[] args) {
        GeneNumberGray g = new GeneNumberGray(0, 8, 32);

        for (int i = 32; i > 5; i--) {
            g.resize(i);
            System.out.println(i + " \tGene " + g.toBinString() + " = " + g.getValue());
        }

    }
}
