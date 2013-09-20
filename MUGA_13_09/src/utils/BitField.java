/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import genetic.population.multiset.Hash.DataHasher;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.BitSet;
import java.util.Random;

public class BitField implements Serializable {

    protected int totalOfBits;
    protected BitSet arrayOfBits;

    //----------------------------Constructors----------------------------------
    /**
     * Creates a new instance of BitField with an empty array of bits
     */
    public BitField() {
        this.totalOfBits = 0;
        this.arrayOfBits = new BitSet(0);
    }

    /**
     * Creates a new instance of BitField with a given size
     *
     * @param totalOfBits the size of the new bitField
     */
    public BitField(final int totalOfBits) {
        this.totalOfBits = totalOfBits;
        this.arrayOfBits = new BitSet(calculate64(totalOfBits));
        arrayOfBits.set(0, totalOfBits, false);
    }

    /**
     * Creates a new instance of BitField given other BitField
     *
     * @param other The BitField from wich to build
     */
    public BitField(final BitField other) {
        // contruct the object
        this(other.getNumberOfBits());
        //copy bits to this object
        arrayOfBits = new BitSet();
        //copia
        for (int i = 0; i < other.arrayOfBits.length(); i++) {
            arrayOfBits.set(i, other.arrayOfBits.get(i));
        }

    }

    /**
     * Creates a new instance of BitField given an array of bits
     *
     * @param other The BitField from wich to build
     */
    public BitField(final boolean[] bits) {
        // contruct the object
        this(bits.length);
        //copy bits to this object
        arrayOfBits = new BitSet();
        //copia
        for (int i = 0; i < bits.length; i++) {
            arrayOfBits.set(i, bits[i]);
        }

    }

    /**
     * Creates a new instance of BitField from a given String of bits
     *
     * @param bitStr A String containing only
     * <code>1</code>'s or
     * <code>0</code>'s
     */
    public BitField(String bitStr) {
        this.totalOfBits = bitStr.length();
        this.arrayOfBits = new BitSet(calculate64(bitStr.length()));

        //copy contents of String to BitSet
        for (int i = 0; i < bitStr.length(); i++) {
            if (bitStr.charAt(i) == '1') {
                //the 'set' method puts 'true' in the selected bit
                this.arrayOfBits.set(i, true);
            } else if (bitStr.charAt(i) == '0') {
                //the 'clear' method puts 'false' in the selected bit
                this.arrayOfBits.set(i, false);
            } else {
                throw new IllegalArgumentException("The string may only contain 1's and 0's");
            }
        }
    }

    /**
     * Creates a new instance of BitField from a given String of bits
     *
     * @param bitStr A String containing only
     * <code>1</code>'s or
     * <code>0</code>'s
     */
    public BitField(int size, String bitStr) {
        this.totalOfBits = size;
        this.arrayOfBits = new BitSet(calculate64(size));

        //copy contents of String to BitSet
        for (int i = 0; i < size; i++) {
            if (i >= bitStr.length()) {
                this.arrayOfBits.set(i, false);
            } else if (bitStr.charAt(i) == '1') {
                //the 'set' method puts 'true' in the selected bit
                this.arrayOfBits.set(i, true);
            } else if (bitStr.charAt(i) == '0') {
                //the 'clear' method puts 'false' in the selected bit
                this.arrayOfBits.set(i, false);
            } else {
                throw new IllegalArgumentException("The string may only contain 1's and 0's");
            }
        }
    }

    //-----------------------------Getters--------------------------------------
    /**
     * Gets the total number of bit in this BitField
     *
     * @return The number of bit
     */
    public int getNumberOfBits() {
        return this.totalOfBits;
    }

    /**
     * Gets the entire array of bit
     *
     * @return The BitSet that stores the bits
     */
    public BitSet getArrayOfBits() {
        return this.arrayOfBits;
    }

    /**
     * Gets one specific bit
     *
     * @param index The zero based index of the intended bit
     * @return
     * <code>true</code> if the bit is set.
     * <code>false</code> otherwise
     */
    public boolean getBit(final int index) {
//        this.validateIndex(index);
//        //inverse order
//        return this.getArrayOfBits().get(index);
        return arrayOfBits.get(index);
    }

    /**
     * Gets one specific bit
     *
     * @param index The zero based index of the intended bit
     * @return
     * <code>true</code> if the bit is set.
     * <code>false</code> otherwise
     */
    public int getBitInteger(final int index) {
        this.validateIndex(index);
        return this.getArrayOfBits().get(index) ? 1 : 0;
    }

    /**
     * Gets an int value representation of the delimited bit interval
     *
     * @param startBit the 0 based index of the interval to represent
     * @param numBits the number of bits to represent
     * @return
     */
    public long getInteger(final int startBit, final int numBits) {
        this.validateInterval(startBit, numBits);
        BitField aux = this.getSubBitField(startBit, startBit + numBits - 1);
        return Long.parseLong(aux.toString(), 2);
    }

    /**
     * Gets an int value representation of the delimited bit interval
     *
     * @param startBit the 0 based index of the interval to represent
     * @param numBits the number of bits to represent
     * @return
     */
    public long getInteger() {
        if (getNumberOfBits() <= 64) {
            return getInteger(0, totalOfBits);
        }
        throw new IndexOutOfBoundsException("Too many bits (max 64) :" + totalOfBits);
    }

    public BigInteger getBigInteger() {
        return getBigInteger(0, totalOfBits);
    }

    public BigInteger getBigInteger(final int startBit, final int numBits) {
        this.validateInterval(startBit, numBits);
        BitField aux = this.getSubBitField(startBit, startBit + numBits - 1);
        BigInteger exp = new BigInteger("1");
        BigInteger two = new BigInteger("2");
        BigInteger total = new BigInteger("0");
        for (int i = aux.totalOfBits - 1; i >= 0; i--) {
            if (aux.getBit(i)) {
                total = total.add(exp);
            }
            exp = exp.multiply(two);
        }
        return total;
    }

    public BigDecimal getBigDecimal() {
        return getBigDecimal(0, totalOfBits);
    }

    public BigDecimal getBigDecimal(final int startBit, final int numBits) {
        this.validateInterval(startBit, numBits);
        BitField aux = this.getSubBitField(startBit, startBit + numBits - 1);
        BigDecimal exp = new BigDecimal("1");
        BigDecimal two = new BigDecimal("2");
        BigDecimal total = new BigDecimal("0");
        for (int i = aux.totalOfBits - 1; i >= 0; i--) {
            if (aux.getBit(i)) {
                total = total.add(exp);
            }
            exp = exp.multiply(two);
        }
        return total;
    }

    private static boolean XOR(boolean b1, boolean b2) {
        if (b1 == b2) {
            return false;
        }
        return true;
    }

    /**
     * Gets an int value representation of the delimited bit interval
     *
     * @param startBit the 0 based index of the interval to represent
     * @param numBits the number of bits to represent
     * @return
     */
    public long getGrayInteger() {
        if (getNumberOfBits() <= 64) {
            BitField tmp = new BitField(totalOfBits);
            int pos = totalOfBits - 1;
            tmp.setBit(0, this.getBit(0));
            for (int i = 1; i < totalOfBits; i++) {

                tmp.setBit(i, tmp.getBit(i - 1) ^ this.getBit(i));
            }
            return tmp.getInteger();

        }
        throw new IndexOutOfBoundsException("Too many bits (max 64) :" + totalOfBits);
    }

    public long getValue(int startBit, int length) {
        if (length <= 64 && startBit + length <= getNumberOfBits()) {
            return getInteger(startBit, length);
        }
        throw new IndexOutOfBoundsException("Too many bits (max 64) :" + totalOfBits);
    }

    public void setInteger(long val) {
        String str = Long.toBinaryString(val);
        if (str.length() > totalOfBits) {
            str = str.substring(0, totalOfBits);
        }
        // STR =     110010
        // BTF = 0000110010
        for (int i = 0; i < totalOfBits; i++) {
            if (i < str.length() && str.charAt(str.length() - i - 1) == '1') {
                setBit(totalOfBits - i - 1, true);
            } else {
                setBit(totalOfBits - i - 1, false);
            }
        }
    }

    public void setGrayInteger(long val) {
        String str = Long.toBinaryString(val);
        if (str.length() > totalOfBits) {
            str = str.substring(str.length() - totalOfBits);
        }
        // STR =     110010
        // BTF = 0000110010
        //store the first
        boolean bit = str.charAt(0) == '1' ? true : false;
        this.fillBits(0, totalOfBits - 1, false);
        this.setBit(totalOfBits - str.length(), bit);
        for (int i = 1; i < str.length(); i++) {

            boolean bit2 = str.charAt(i) == '1';
            setBit(totalOfBits - str.length() + i, bit ^ bit2);
            bit = bit2;

        }


    }

    //-----------------------------Setters--------------------------------------
    /**
     * Modifies a bit value
     *
     * @param index Bit to be modified
     * @param value New logical value of the bit.
     * <code>true</code> sets the bit<br>
     * <code>false</code> clears the bit
     */
    public void setBit(final int index, final boolean value) {
        if (value) {
            this.setBitTrue(index);
        } else {
            this.setBitFalse(index);
        }
    }

    /**
     * Clears the specified bit
     *
     * @param index 0 based index of the bit to be cleared
     */
    public void setBitFalse(final int index) {
        this.validateIndex(index);
        this.getArrayOfBits().clear(index);
    }

    /**
     * Sets the specified bit
     *
     * @param index 0 based index of the bit to be set
     */
    public void setBitTrue(final int index) {
//        this.validateIndex(index);
        this.getArrayOfBits().set(index);
    }

    /**
     * Modifies the number of bit currently stored in this BitField
     *
     * @param numBits The new number of bit
     */
    public void setTotalOfBits(final int numBits) {
        if (numBits > this.totalOfBits) {
            for (int i = totalOfBits; i < numBits; i++) {
                arrayOfBits.set(i, false);
            }
        } //eliminar os que faltam
        else {
            BitSet tmp = new BitSet(numBits);
            for (int i = totalOfBits; i < numBits; i++) {
                tmp.set(i, arrayOfBits.get(i));
            }
            arrayOfBits = tmp;
        }
        this.totalOfBits = numBits;
    }

    /**
     * Fill a specified region of the BitField with a given
     * <code>value</code>
     *
     * @param startBit 0 based index of the start of the region
     * @param endBit 0 based index of the end of the region
     * @param value
     */
    public void fillBits(final int startBit, final int endBit, final boolean value) {
        //validate
//        this.validateIndex(startBit);
//        this.validateIndex(endBit);

        for (int i = startBit; i <= endBit; i++) {
            setBit(i, value);
        }
    }

    /**
     * Inverts the value of one bit. If the value is
     * <code>True</code><br/> the new value is
     * <code>False</code> and vice-versa.
     *
     * @param index 0 based index of the bit to be inverted
     */
    public void invertBit(final int index) {
//        this.validateIndex(index);
        this.getArrayOfBits().flip(index);
    }

    //--------------------------Miscellaneous-----------------------------------
    /**
     * Method to get a BitField that is contained in
     * <code>this</code> BitField
     *
     * @param beginIndex the start of the subBitField
     * @param endIndex the end of the subBitField
     * @return A BitField that contains the bit in positions from
     * <code>beginIndex</code><br/> to
     * <code>endIndex</code>.
     */
    public BitField getSubBitField(final int beginIndex, final int endIndex) {
//        this.validateIndex(beginIndex);
//        this.validateIndex(endIndex);
        int j = 0;
        //obtain the second part of BitField
        BitField ret = new BitField((endIndex - beginIndex) + 1);
        for (int i = beginIndex; i <= endIndex; i++) {
            ret.setBit(j, this.getBit(i));
            j++;
        }
        return ret;
    }

    /**
     * This method splits a BitField in <b>two or more</b> BitFields given the
     * index where the cut happens
     *
     * @param indexes the 0-based indexes of the cut points
     * @return The pieces of the BitField.<br/> The BitField in position
     * <code>i</code>-th of the return corresponds to the part<br/> of the
     * original BitField that is limited by the
     * <code>i-1</code>-th and <br/>
     * <code>i</code>-th positions of the
     * <code>indexes</code> parameter. If<br/> the
     * <code>(i-1)<0</code> then the
     * <code>i-1</code> position becomes the <br/> first (
     * <code>0</code>) position of
     * <code>indexes</code>.
     */
    public BitField[] split(final int[] indexes) {
        int numberOfCuts = indexes.length;
        int fromI = 0, toI = indexes[0], i = 0;
        BitField[] ret = new BitField[numberOfCuts + 1];

        //do partitioning of BitField
        do {
            ret[i] = this.getSubBitField(fromI, toI);
            fromI = toI + 1;
            i++;
            if (i < numberOfCuts) {
                toI = indexes[i];
            } else {
                toI = this.totalOfBits - 1;
            }
        } while (i < numberOfCuts + 1);

        return ret;
    }

    /**
     * Inserts the specified BitField into this BitField.<br/> The bit are
     * inserted starting from position
     * <code>index+1</code> onward.
     *
     * @param bits the BitField to be inserted
     * @param index the 0-based index where to insert the BitField
     */
    public void insertBits(final BitField bits, final int index) {
        int[] temp = {index};
        BitField[] splitted = this.split(temp);
        BitField aux = new BitField(splitted[0]);
        aux.appendBits(bits);
        aux.appendBits(splitted[1]);
        this.storeBitField(aux);
    }

    /**
     * Deletes a specified number of bits starting at a given index.<br/> The
     * <code>index</code>-th bit is also deleted
     *
     * @param index The 0-based bit index where to start deleting
     * @param numBits The quantity of bits to delete
     */
    public void deleteBits(final int index, final int numBits) {
        this.validateInterval(index, numBits);

        for (int i = index; i < (this.totalOfBits - numBits); i++) {
            this.setBit(i, this.getBit(i + numBits));
            System.out.println("bit final " + i + ":" + this.getBit(i));
        }
        this.totalOfBits -= numBits;
    }

    /**
     * Deletes the sequence of bit, given by the argument, if such a sequence
     * exists.
     *
     * @param bits The sequence to delete
     */
    public void deleteBits(final BitField bits) {
        int startPos = this.toString().indexOf(bits.toString());
        if (startPos != -1) {
            this.deleteBits(startPos, bits.getNumberOfBits());
        }
    }

    /**
     * Adds the bits of the specified BitField to the end of this BitField
     *
     * @param other The bits to append
     */
    public void appendBits(final BitField other) {
        int aux = this.totalOfBits;
        this.totalOfBits += other.getNumberOfBits();
        for (int i = 0; i < other.getNumberOfBits(); i++) {
            this.setBit(aux + i, other.getBit(i));
        }
    }

    /**
     * Substitutes this BitField with another
     *
     * @param other The new contents of thi BitField
     */
    public void storeBitField(final BitField other) {
        arrayOfBits = new BitSet(other.getNumberOfBits());
        this.totalOfBits = other.getNumberOfBits();
        for (int i = 0; i < other.totalOfBits; i++) {
            arrayOfBits.set(i, other.getBit(i));
        }
    }

    /**
     * Performs a logical
     * <code>AND</code> operation with other BitField.<br /> Useful for
     * Arithmetic Combination
     *
     * @param other
     */
    public BitField and(final BitField other) {
        BitField temp = new BitField(this);
        temp.getArrayOfBits().and(other.getArrayOfBits());
        return temp;
    }

    /**
     * Performs a logical
     * <code>OR</code> operation with other BitField.<br /> Useful for
     * Arithmetic Combination
     *
     * @param other
     */
    public BitField or(final BitField other) {
        BitField temp = new BitField(this);
        temp.getArrayOfBits().or(other.getArrayOfBits());
        return temp;
    }

    /**
     * Performs a logical
     * <code>OR</code> operation with other BitField.<br /> Useful for
     * Arithmetic Combination
     *
     * @param other
     */
    public BitField not() {
        BitField temp = new BitField(this.totalOfBits);
        for (int i = 0; i < totalOfBits; i++) {
            temp.setBit(i, !this.getBit(i));
        }
        return temp;
    }

    /**
     * Performs a logical
     * <code>XOR</code> operation with other BitField.<br /> Useful for
     * Arithmetic Combination
     *
     * @param other
     */
    public BitField xor(final BitField other) {
        BitField temp = new BitField(this);
        temp.getArrayOfBits().xor(other.getArrayOfBits());
        return temp;
    }

//    public void writeTo(final String file) {
//        File f = new File(file);
//        //if file does not exist, create one
//        if (!f.exists()) {
//            try {
//                f.createNewFile();
//            } catch (IOException ex) {
//                System.out.println("Erro: "+ex.toString());
//            }
//        }
//        //write the object
//    }
    //----------------------protecteds-------------------
    /**
     * Checks if the
     * <code>index</code> given is valid for this instance of BitField
     *
     * @param index Index to be validated
     */
    protected void validateIndex(final int index) {
        if (index < 0 || index >= getNumberOfBits()) {
            throw new IndexOutOfBoundsException("Illegal index :" + index + " out of range[ 0 ... " + (getNumberOfBits() - 1) + " ]");
        }
    }

    /**
     * Checks if the
     * <code>index</code> given is valid for this instance of BitField<br> and
     * if the offset is not greater than the interval
     * <code>totalOfBits-index</code>
     *
     * @param index Index to be validated
     * @param offset Offset to be validated
     */
    protected void validateInterval(final int index, final int offset) {
        this.validateIndex(index);
        this.validateIndex(index + offset - 1);

    }

    /**
     * Calculates the number of 64 bit words to store the bits
     *
     * @param bits Number of bit to store in bitfield
     * @return Number of words (64 bit)
     */
    protected int calculate64(int bits) {
        int size = bits / 64;
        if (bits % 64 == 0) {
            return size;
        }
        return size + 1;

    }

    //----------------------Overrides-------------------
    /**
     * Returns a copy of this instance
     *
     * @return The copy
     */
    public BitField getClone() {
        return new BitField(this);
    }

    /**
     * Checks if another BitField is equal to this BitField. This is done by
     * comparing the two string representations of the objects
     *
     * @param obj the Bitfield to check for equality
     * @return
     * <code>True</code> if the the BitFields are equal,
     * <code>False</code> otherwise.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof BitField) {  //tests if object passed is a BitField
            BitField bt = (BitField) obj;
            return bt.arrayOfBits.equals(arrayOfBits);

        } else {
            return false;
        }
    }

    /**
     * Returns a string representation of this class
     *
     * @return A String with the values of each bit in this instance
     */
    @Override
    public String toString() {
        StringBuffer ret = new StringBuffer();
        for (int i = 0; i < this.totalOfBits; i++) {
            if (this.getBit(i)) {
                ret.append("1");
            } else {
                ret.append("0");
            }
        }
        return ret.toString();
    }

    /**
     * Returns a hash code value for the object. This method is supported to
     * enable this class to be used in HashTables
     *
     * @return The HashCode of the BitField
     */
    @Override
    public int hashCode() {
        // Error in hashcode of bitset if strings are simetrics
        // 1111111100000000000000000000000011111111111111110000000000000000 
        // 0000000011111111111111111111111100000000000000001111111111111111 
//        return arrayOfBits.hashCode();

//        int h = 429496729 ;
//        byte[] words = arrayOfBits.toByteArray();
//        for (int i = words.length - 1; i >= 0;) {
//            h ^= (words[i--] ^ (h << 3)) + 1;
//        }
//        return h;//(int) (h >>> 32) ^ (int) h;

        //is ok
//        return this.toString().hashCode();

        //increase performance
        //string hashcode 1.6
//        int h = 0;
//        byte[] words = arrayOfBits.toByteArray();
//        for (int i = 0; i < words.length; i++) {
//            h = 257* h + words[i];
//        }
//        return h;
        return DataHasher.hashInt(arrayOfBits.toByteArray());
    }

    /*
     * -----------------------------------------------------------------------
     */
    /**
     * Write bitfield in a stream
     *
     * @param file stream to write
     */
    public void Write(Writer file) {
        try {
            file.write(this.toString());
        } catch (Exception e) {
            System.out.println(" BITFIELD WRITE ERROR " + e.toString());
        }

    }

    /**
     * Reads a bitfield
     *
     * @param file stream to read
     */
    public void Read(Reader file) {
        //variavel temporaria de leitura
        BitField tmp = new BitField(1000);
        try {
            int index = 0;
            do {
                int ch = file.read();
                if (Character.digit((char) ch, 10) == 1) {
                    tmp.setBitTrue(index);
                } else if (Character.digit((char) ch, 10) == 0) {
                    tmp.setBitFalse(index);
                } else {
                    index--;
                    break;
                }
                index++;
            } while (file.ready() && index < tmp.totalOfBits);
            //atribuir a variavel temporaria a this
            this.storeBitField(tmp.getSubBitField(0, index));

        } catch (Exception e) {
            System.out.println(" BITFIELD READ ERROR " + e.toString());
        }

    }
    //------------------------------------------------------------------------
    //------------------------------------------------------------------------

    public int getNumberOfOnes() {
        int ones = 0;
        for (int i = 0; i < this.getNumberOfBits(); i++) {
            if (getBit(i)) {
                ones++;
            }
        }
        return ones;
    }
    //------------------------------------------------------------------------

    public int getNumberOfZeros() {
        return this.getNumberOfBits() - getNumberOfOnes();
    }
    //-----------------------------------------------------------------------

    public int getHammingDistance(BitField other) {
        int difs = 0;
        for (int i = 0; i < this.getNumberOfBits(); i++) {
            if (this.arrayOfBits.get(i) != other.arrayOfBits.get(i)) {
                difs++;
            }
        }
        return difs ;//+ Math.abs(this.getNumberOfBits() - other.getNumberOfBits());
    }

    public long getMaxValue() {
        if (getNumberOfBits() <= 64) {
            return (long) (Math.pow(2, getNumberOfBits()) - 1);
        }
        throw new IndexOutOfBoundsException("Too many bits (max 64) : " + getNumberOfBits());
    }

    public int compareTo(BitField other) {
        for (int i = 0; i < this.getNumberOfBits() && i < other.getNumberOfBits(); i++) {
            if (getBit(i) != other.getBit(i)) {
                if (getBit(i)) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }
        return 0;
    }
    public static Random rnd = new Random();

    public void fillRandom() {
        for (int i = 0; i < getNumberOfBits(); i++) {
            setBit(i, rnd.nextBoolean());
        }
    }

    public static void main(String[] args) {
//        for (int i = 1; i < 200; i++) {
//            BitField b = new BitField(i);
//            for (int j = 0; j < i; j++) {
//              b.setBit(j, true);
//            }
//            System.out.println(b.getBigInteger() + " =\t " + b + " =>" + b.getNumberOfBits());
//            
//        }

        BitField b = new BitField("1111111100000000000000000000000011111111111111110000000000000000");
        System.out.println(b + " = " + b.hashCode());
        b = new BitField("0000000011111111111111111111111100000000000000001111111111111111");
        System.out.println(b + " = " + b.hashCode());

    }
}
