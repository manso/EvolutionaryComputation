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
public class GrayBitField extends BitField {
    public GrayBitField(String bits) {
        super(bits);
    }

    /**
     * Creates a new instance of GrayBitField.
     * @param bitStr Must contain only 0's and 1's and be 64 char long.<br/>
     * It's convenient that it represents a number in gray binary encoding.
     */
    public GrayBitField(int dim) {
        super(dim);
    }

    /**
     * Creates a new instance of GrayBitField.
     * @param other A BitField that must represent a number in Gray Binary Encoding and 64 bit long
     */
    public GrayBitField(BitField other) {
        super(other);
    }
    ///ERROR ///ERROR ///ERROR ///ERROR ///ERROR ///ERROR ///ERROR ///ERROR
    ///ERROR ///ERROR ///ERROR ///ERROR ///ERROR ///ERROR ///ERROR ///ERROR
    ///ERROR ///ERROR ///ERROR ///ERROR ///ERROR ///ERROR ///ERROR ///ERROR
    ///ERROR ///ERROR ///ERROR ///ERROR ///ERROR ///ERROR ///ERROR ///ERROR 

//    /**
//     * Converts and store a integer number into gray coding.<br/>
//     * To recover the origunal number, use getInt()
//     * @param number the value to be stored
//     */
//    public void setInteger(long number) {
//        StringBuffer strb = new StringBuffer(this.getNumberOfBits());
//        //shift the number to the right one bit (or divide by 2)
//        long shifted = number >> 1;
//        //conversion to gray code
//        long gray = number ^ shifted;
//        String bingray = Long.toBinaryString(gray);
//        strb.insert(0,bingray);
//        //perform the 0-padding of the final binary code
//        while( strb.length() < super.getNumberOfBits()){
//            strb.insert(0,'0');
//        }
//        this.storeBitField(new BitField(strb.toString()));
//    }
//
//    /**
//     * Recovers a number previously stored with <code>.
//     * @return the number that is stored in this GrayBitField
//     */
//    @Override
    public long getInteger() {
        long ret = 0;
        StringBuffer gray = new StringBuffer(this.toString());
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
        return ret;
    }
  }
