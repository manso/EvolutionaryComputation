/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.gene;

import utils.BitField;
import utils.Funcs;

/**
 *
 * @author arm
 */
public class GeneDouble extends GeneNumber {

    /**
     * Creates a new instance of GeneDouble
     * @param size number of bits of gene - precision of real number
     * @param minValue minimum value
     * @param maxValue maximum value
     */
    public GeneDouble(int size, double minValue, double maxValue, double value) {
        //alels = new GrayBitField(size);
        alels = new BitField(size);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.setValue(value);
    }

    public GeneDouble(double minValue, double maxValue) {
        this(32, minValue, maxValue, 0);
        this.fillRandom();
    }

    public GeneDouble(double minValue, double maxValue, double val) {
        this(32, minValue, maxValue, val);
    }

    public GeneDouble(int size, double minValue, double maxValue) {
        this(size, minValue, maxValue, minValue);
        this.fillRandom();
    }

    /**
     * Creates a new instance of GeneDouble
     * @param size number of bits of gene - precision of real number
     * @param minValue minimum value
     * @param maxValue maximum value
     */
    /**
     * Copy Constructor - [hard copy] of Gene
     * @param other template of copy
     */
    public GeneDouble(GeneDouble other) {
        this(other.alels.getNumberOfBits(), other.minValue, other.maxValue, other.getValue());
    }

    /**
     * convert bits to double [0101010010] -> 123.432
     * @return returns double value of bits
     */
    public double getValue() {
        long val = alels.getGrayInteger();
        double pow2 = alels.getMaxValue();
        double xx = minValue + val * ((maxValue - minValue) / pow2);
        return xx;
    }

    /**
     * convert bits to double [0101010010] -> 123.432
     * @return returns double value of bits
     */
    public void setValue(double value) {
        //normalizar os valors
        if (value > maxValue) {
            value = maxValue;
        } else if (value < minValue) {
            value = minValue;
        }
        long maxint = alels.getMaxValue();
        double max = maxValue - minValue;
        value -= minValue;
        //regra tressimples
        double vint = (value * maxint) / max;
        alels.setGrayInteger(Math.round(vint));
    }

    /**
     * [hard-copy]
     * @return clone
     */
    @Override
    public Gene getClone() {
        return new GeneDouble(this);
    }

    /**
     * return the double value of gene
     * @return double value
     */
    @Override
    public String toString() {
        return String.valueOf(Funcs.DoubleToString(getValue(),20));
    }

    public static void main(String[] args) {
        double v = 3;
        GeneDouble g1 = new GeneDouble(17, -6, 6, 3);
        System.out.printf("\nreal %10.8f  Gene %10.8f  Bits  %s", v, g1.getValue(), g1.toBinString());
        g1.setAlels(new BitField("10111111111111111"));
        System.out.printf("\nreal %10.8f  Gene %10.8f  Bits  %s", v, g1.getValue(), g1.toBinString());
        g1.setAlels(new BitField("11000000000000000"));
        System.out.printf("\nreal %10.8f  Gene %10.8f  Bits  %s", v, g1.getValue(), g1.toBinString());

        v=2;
        GeneDouble g2 = new GeneDouble(17, -6, 6, v);
        System.out.printf("\nreal %10.8f  Gene %10.8f  Bits  %s", v, g2.getValue(), g2.toBinString());
        g2.setAlels(new BitField("10101010101010100"));
        System.out.printf("\nreal %10.8f  Gene %10.8f  Bits  %s", v, g2.getValue(), g2.toBinString());


    }
}
