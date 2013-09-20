/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

/**
 * This class stores numbers in reflected binary code, or Gray Binary Code.<br/>
 * Use to store numbers only. Size is fixed at 64 bit
 * @author Pedro Manha
 */
public class GrayBitFieldNumber extends BitField {
    /**
     * Creates a new instance of GrayBitField.
     * @param bitStr Must contain only 0's and 1's and be 64 char long.<br/>
     * It's convenient that it represents a number in gray binary encoding.
     */
    public GrayBitFieldNumber(int dim) {
        super(dim);
    }

    /**
     * Creates a new instance of GrayBitField.
     * @param other A BitField that must represent a number in Gray Binary Encoding and 64 bit long
     */
    public GrayBitFieldNumber(BitField other) {
        super(other);
    }

    /**
     * Converts and store a integer number into gray coding.<br/>
     * To recover the origunal number, use getInt()
     * @param number the value to be stored
     */
    public void storeInt(long number) {
        StringBuffer strb = new StringBuffer(this.getNumberOfBits());
        //shift the number to the right one bit (or divide by 2)
        long shifted = number >> 1;
        //conversion to gray code
        long gray = number ^ shifted;
        String bingray = Long.toBinaryString(gray);
        //se for negativo
        if(number < 0)
            strb.append('1');
        else
            strb.append('0');

        strb.insert(0,bingray);
        //perform the 0-padding of the final binary code
        while( strb.length() < super.getNumberOfBits()){
            strb.insert(0,'0');
        }
        this.storeBitField(new BitField(strb.toString()));
    }

    /**
     * Recovers a number previously stored with <code>.
     * @return the number that is stored in this GrayBitField
     */
    public long getInt() {
        long ret = 0;
        StringBuffer gray = new StringBuffer(this.toString());
        //last bit is the signal
        char signal = gray.charAt(gray.length()-1);
        //remove signal
        gray = gray.deleteCharAt(gray.length()-1);

        StringBuffer binary = new StringBuffer();
        binary.append(gray.charAt(0));
        int j = 1;

        //reverse conversion from gray code
        for (int i = 1; i < gray.length(); i++) {
            binary.append(gray.charAt(i) ^ binary.charAt(j - 1));
            j++;
        }
       // System.out.println("b: " + binary);
        ret = Long.valueOf(binary.toString(), 2);

        //corrects the recovered number to two's complement
        //if the number is suposed to be negative
        if (signal =='1') {
            ret = ~ret;
        }
        return ret;
    }
    
  }
