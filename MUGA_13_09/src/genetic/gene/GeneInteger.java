/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.gene;

import utils.BitField;

/**
 *
 * @author arm
 */
public class GeneInteger extends GeneNumber {

    /**
     * novo gene
     * @param minValue minimo do intervalo
     * @param maxValue máximo do intervalo
     * @param value valor do gene
     */
    public GeneInteger(long minValue, long maxValue, long value) {
        int bits = (int) (Math.floor(Math.log((int) maxValue - (int) minValue) / Math.log(2.0) + 1));
        alels = new BitField(bits);
        this.minValue = minValue;
        this.maxValue = maxValue;
        setValue(value);
    }

    public GeneInteger(long minValue, long maxValue) {
        this(minValue, maxValue, minValue + randGenerator.nextInt((int)maxValue - (int)minValue));
    }

    public GeneInteger(GeneInteger g) {
        this((long) g.minValue, (long) g.maxValue, (long) g.getValue());
    }

    /**
     * convert bits to double [0101010010] -> 123.432
     * @return returns double value of bits
     */
    public double getValue() {
        long val = alels.getInteger();
        double pow2 = alels.getMaxValue();
        return Math.round(minValue + val * ((maxValue - minValue) / pow2));
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
        double maxint = alels.getMaxValue();
        double max = maxValue - minValue;
        value -= minValue;
        //regra tressimples
        double vint = (value * maxint) / max;
        alels.setInteger(Math.round(vint));
    }

    @Override
    public Gene getClone() {
        return new GeneInteger(this);
    }
    /**
     * returns Gene
     * @return string
     */
    @Override
    public String toString() {
        return (int) this.getValue() + "";
    }
}
