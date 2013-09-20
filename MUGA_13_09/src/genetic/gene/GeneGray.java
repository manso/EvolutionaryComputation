/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genetic.gene;

import utils.BitField;
import utils.GrayBitField;

/**
 *
 * @author arm
 */
public  class  GeneGray extends Gene{

   public GeneGray(String bits) {
        alels = new BitField(bits);
    }
    /**
     * Creates a new instance of GeneBinary
     * @param size number of bits
     */
    public GeneGray(int size) {
        alels = new BitField(size);
    }
    /**
     * Copy Constructor - make a object copy
     * @param other template o copy
     */
    public GeneGray(GeneGray other) {
        this(other.alels.getNumberOfBits());
        alels.storeBitField(other.alels);
    }

    
   public void setValue(double val) {
        alels.setGrayInteger( Math.round(val));
    }

    @Override
    public double getValue() {
       return  alels.getGrayInteger();
    }

    

     /**
     * returns binary representation [11100011010]
     * @return string
     */
    public String toString() {
        return alels.toString();
    }
   

    @Override
    public Gene getClone() {
        return new GeneGray(this);
    }

    public int compareTo(Gene other) {
          boolean b1 = alels.getBit(0);
        boolean b2 = other.alels.getBit(0);

        for (int index = 1; index < alels.getNumberOfBits(); index++) {

            if (b1 != b2) {
                break;
            }
            // XOR with the next
            b1 = b1 ^ alels.getBit(index);
            b2 = b2 ^ other.alels.getBit(index);

        }
        if(b1==b2) return 0;
        if (b1) {
            return 1;
        }
            return -1;
    }



}
