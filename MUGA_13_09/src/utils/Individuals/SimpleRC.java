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
 * Ernesto1.java
 *
 * Created on 14 de Janeiro de 2005, 0:56
 */
package utils.Individuals;

import genetic.gene.GeneDouble;
import genetic.gene.GenePureReal;
import problem.Individual;
import problem.RC_Individual;
import statistics.elements.Mean;

/**
 * Example of two genes of double value <br>
 * <B>g1</B> = 16 bits of interval [-3.0 , 12.1] <br>
 * <B>g2</B> = 16 bits of interval [4.1 , 5.8] <br>
 * 
 * <B>fitness</B> = 21.5 + g1*sin(PI* 4 *g1 )+ g2 * sin(PI* 20 *g2 )<br>
 * @author António Manuel Rodrigues Manso
 */
public class SimpleRC extends RC_Individual {

    public static double bestValue = 0;
    static double Min = -3;
    static double Max = 3;
    static double Dim = Max - Min;

    /**
     * create chromossom
     */
    public SimpleRC(int numGenes) {
        super(MINIMIZE, -10, 10, 2);        
    }
    @Override
    public void fillRandom(){

         double m = Min + Dim / 2;
         for (int i = 0; i < getNumGenes(); i++) {
            setGeneValue(i, m + (Math.random()-0.5)*Dim*0.5 );
        }
    }

    public SimpleRC(){
        this(2);
    }            

    /**
     * evaluate chromossom
     * @return fitness
     */
    public double fitness() {        
        double sum=0;
        for (int i = 0; i < genome.length; i++) {           
            sum += genome[i]*genome[i];
        }
        return sum;
// individuo do nead meldel simplex
//        double x = getGene(0).getValue();
//        double y = getGene(1).getValue();
//
//        return Math.pow(1-x, 2) + 100 * Math.pow( y-x*x, 2);

    }
}
