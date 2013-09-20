/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.gene;

import java.io.Reader;
import java.io.Writer;
import utils.BitField;

/**
 *
 * @author arm
 */
public class GenePureInteger extends GeneNumber {

    long value;

    public GenePureInteger(long min, long max, long value) {
        this.value = value;
        this.minValue = min;
        this.maxValue = max;
        alels = new BitField(Long.toBinaryString(value));

    }

    public GenePureInteger(long min, long max) {
        this.minValue = min;
        this.maxValue = max;
        fillRandom();
        alels = new BitField(Long.toBinaryString(value));
    }

    public GenePureInteger(long value) {
        this(Long.MIN_VALUE / 2, Long.MAX_VALUE / 2, value);
    }

    public GenePureInteger() {
        this(Long.MIN_VALUE, Long.MAX_VALUE, 0);
    }

    public GenePureInteger(GenePureInteger other) {
        this((long) other.minValue, (long) other.maxValue, other.value);
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public void setValue(double value) {
        //normalizar os valors
        if (value > maxValue) {
            value = maxValue;
        } else if (value < minValue) {
            value = minValue;
        }
        this.value = (long) value;
        alels = new BitField(Long.toBinaryString(this.value));
    }

    @Override
    public Gene getClone() {
        return new GenePureInteger(this);
    }

    @Override
    public void fillRandom() {
        value = (long) (minValue + randGenerator.nextInt((int) (maxValue - minValue)));
    }

    /**
     * converte para string
     * @return bits dos alelos
     */
    public String toBinString() {
        return Long.toBinaryString(value);
    }

    /**
     * returns Gene
     * @return string
     */
    @Override
    public String toString() {
        return this.value + "";
    }

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
    /**
     * write a Gene in a stream
     * @param file stream
     */
    @Override
    public void Write(Writer file) {
        try {
            file.write(value + "");
        } catch (Exception e) {
            System.out.println("GENE WRITE ERROR " + e.toString());
        }
    }
//----------------------------------------------------------------------------

    /**
     * reads a Gene from a stream
     * @param file stream file
     */
    @Override
    public void Read(Reader file) {
        try {
            String val = "";
            int index = 0;
            do {
                int ch = file.read();
                if (Character.isSpaceChar(ch)) {
                    break;
                }
                val += (char)ch;
            } while (file.ready());
            //atribuir a variavel temporaria a this
            this.value = Long.parseLong(val);

        } catch (Exception e) {
            System.out.println(" PURE INTEGER READ ERROR " + e.toString());
        }

    }

    @Override
    public int hashCode(){
        return (int) value;
    }
     public boolean equals(final Object obj) {
        if (obj instanceof GenePureInteger) {
            final GenePureInteger other = (GenePureInteger)obj;
            return other.value == value;
        } else
            return super.equals(obj);
    }
}
