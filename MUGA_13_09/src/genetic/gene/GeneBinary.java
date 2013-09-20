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
public class GeneBinary extends Gene {

    public GeneBinary() {
        alels = new BitField(1);
    }

    public GeneBinary(String bits) {
        alels = new BitField(bits);
    }

    public GeneBinary(int size) {
        alels = new BitField(size);
        fillRandom();
    }

    public GeneBinary(GeneBinary g) {
        alels = new BitField(g.alels);
    }

    @Override
    public double getValue() {
        return alels.getInteger();
    }

    @Override
    public void setValue(double value) {
        alels.setInteger(Math.round(value));
    }

    @Override
    public Gene getClone() {
        return new GeneBinary(this);
    }

    /**
     * returns binary representation [11100011010]
     * @return string
     */
    @Override
    public String toString() {
        return alels.toString();
    }
   
    public int compareTo(Gene other) {
        return this.alels.getHammingDistance(other.alels);
    }
}
