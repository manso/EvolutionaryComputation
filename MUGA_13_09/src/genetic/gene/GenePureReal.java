/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genetic.gene;

import java.io.Reader;
import java.io.Writer;
import utils.BitField;
import utils.Funcs;

/**
 *
 * @author arm
 */
public class GenePureReal extends GeneNumber {
    private double value;
    public static BitField bits = new BitField("01");

    public GenePureReal(double min, double max,double value){
        this.minValue = min;
        this.maxValue = max;
        setValue(value);
    }
    public GenePureReal(double min, double max){
        this.minValue = min;
        this.maxValue = max;
        fillRandom();
        setValue(value);
    }

    public GenePureReal(){
        this(-Double.MAX_VALUE, Double.MAX_VALUE,0);
    }
    public GenePureReal(double value){
        this(-Double.MAX_VALUE, Double.MAX_VALUE,value);
    }
    public GenePureReal(GenePureReal other){
        this(other.minValue, other.maxValue,other.value);
    }
    @Override
    public double getValue() {
       return value;
    }

    @Override
    public void setValue(double value) {
        //normalizar os valors
        if (value > maxValue) {
            value=maxValue;
//            double dif = value-maxValue;
//            setValue(maxValue-dif/10.0);
        } else if (value < minValue) {
//            double dif = minValue-value;
//            setValue(minValue + dif/10.0);
            value = minValue;
        }
        this.value = value;
        alels = bits;//new BitField(64,Long.toBinaryString(Double.doubleToLongBits(value)));
    }

    @Override
    public Gene getClone() {
        return new GenePureReal(this);
    }
   
    @Override
    public void fillRandom() {
        setValue(minValue +  randGenerator.nextDouble() * (maxValue-minValue));
    }

     /**
     * converte para string
     * @return bits dos alelos
     */
    public String toBinString() {

        return String.format("%20s", ""+value);
    }
    
     /**
     * returns Gene
     * @return string
     */
    @Override
    public String toString() {
       return String.valueOf(Funcs.DoubleToString(getValue(),20));
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
            this.value = Double.parseDouble(val);

        } catch (Exception e) {
            System.out.println(" PURE Real READ ERROR " + e.toString());
        }

    }

    @Override
    public int hashCode(){
        return new Double(value).hashCode();
    }

}
