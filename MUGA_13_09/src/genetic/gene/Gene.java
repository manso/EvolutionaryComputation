/*****************************************************************************/
/****     G E N E T I C   A L G O R I T H M S   S I M U L A T O R         ****/
/****                           ver 1.0 (Sept/2005)                       ****/
/****                                                                     ****/
/****     authors:                                                        ****/
/****              António Manso                                          ****/
/****              URL   : http://orion.ipt.pt/~manso/                    ****/
/****              e-mail: manso@ipt.pt                                   ****/
/****                                                                     ****/
/****              Luís Correia                                          ****/
/****              URL   : http://www.di.fc.ul.pt/~lcorreia/              ****/
/****              e-mail: Luis.Correia@di.fc.ul.pt                       ****/
/****                                                                     ****/
/*****************************************************************************/
/*****************************************************************************/
/*  This library is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published
 *  by the Free Software Foundation; either version 2.1 of the License, or
 *  (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
/*
 * Gene.java
 *
 * Created on 12 de Dezembro de 2004, 17:05
 */

package genetic.gene;
import java.io.Reader;
import java.io.Writer;
import java.util.Random;
import java.io.Serializable;
import utils.BitField;




/**
 * Representa um gene de um cromossoma abstracto
 * @author António Manuel Rodrigues Manso
 */
public abstract class Gene implements Serializable,Comparable<Gene> {

    /**
     * ABSTRACT - returns the value (double) of Gene
     * @return double value of gene
     */
    public abstract double getValue();

    public abstract void setValue(double value);
    /**
     * Clone Object
     * @return clone
     */
    public abstract Gene getClone();
    /**
     * bitfield of gene [101000111]
     */
    protected BitField alels;
    static protected Random randGenerator = new Random();      
    /**
     * Getter for property alels.
     * @return Value of property alels.
     */
    public BitField getAlels() {
        return this.alels;
    }

    /**
     * Getter for property alels.
     * @return Value of property alels.
     */
    public int getNumBits() {
        return this.alels.getNumberOfBits();
    }
    /**
     * Setter for property alels.
     * @param alels New value of property alels.
     */
    public void setAlels(BitField alels) {       
        this.alels = (BitField) alels.getClone();
    }
    /**
     * atribui aleatoriamente valores aos alelos
     */
    public void fillRandom() {
        for(int i = 0 ; i< alels.getNumberOfBits();i++)
            alels.setBit(i ,  randGenerator.nextBoolean());
    }
    
     
    /**
     * converte para string
     * @return bits dos alelos
     */
    public String toBinString() {
        return alels.toString();
    }
   
          
    
    /**
     * Actualiza o gerador de números aleatórios
     * @param rg gerador Random
     */
    public void setRandomGenerator(Random rg) {
        randGenerator = rg;
    }
    
    /**
     *  Indicates whether some other object is "equal to" this one.
     * @param obj object
     * @return equals
     */
    public boolean equals(final Object obj) {
        if (obj instanceof Gene) {
            final Gene other = (Gene)obj;
            return alels.equals(other.alels);
        } else
            return super.equals(obj);
    }
    
    /**
     * apply mutation in every bit of alels with that probability
     * @param probability value o probability to invert each bit
     */
    public void mutate(double probability) {
         for(int i = 0 ; i< alels.getNumberOfBits();i++){
            if( randGenerator.nextDouble() < probability)
                alels.invertBit(i);
         }
    }
//----------------------------------------------------------------------------  
//----------------------------------------------------------------------------    
//----------------------------------------------------------------------------  
//----------------------------------------------------------------------------    

    /**
     * write a Gene in a stream
     * @param file stream
     */
    public void Write(Writer file) {
        try{
            alels.Write(file);
        }catch(Exception e) {
            System.out.println("GENE WRITE ERROR " + e.toString());
        }
    }
//----------------------------------------------------------------------------     
    /**
     * reads a Gene from a stream
     * @param file stream file
     */
    public void Read(Reader file) {
        try{
            alels.Read(file);
        }catch(Exception e) {
            System.out.println("GENE READ ERROR " + e.toString());
        }
    }
//----------------------------------------------------------------------------
@Override
    public int hashCode(){
        return this.alels.hashCode();
    }
//----------------------------------------------------------------------------    
//----------------------------------------------------------------------------  
//----------------------------------------------------------------------------    
    
}
