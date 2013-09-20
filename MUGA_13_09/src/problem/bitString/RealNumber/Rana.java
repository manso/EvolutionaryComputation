///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2012                                             ****/
///****     Antonio Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso                             ****/
///****     Instituto Politecnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****************************************************************************/
///****************************************************************************/
///****     This software was built with the purpose of investigating      ****/
///****     and learning. Its use is free and is not provided any          ****/
///****     guarantee or support.                                          ****/
///****     If you met bugs, please, report them to the author             ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/
package problem.bitString.RealNumber;

/**
 * from paper: The Island Model Genetic Algorithm: On Separability, Population
 * Size and Convergence http://neo.lcc.uma.es/Articles/WRH98.pdf Original
 * published in: D. Goldberg, B. Korb, and K. Deb. Messy Genetic Algorithms:
 * Motivation, Analysis, and First Results. Complex Systems, 4:415{444, 1989
 *
 * @author ZULU
 */
public class Rana extends RealBitString {

    public static int number_of_bits = 10;
    public static int DIMENSION = 2;

    public Rana() {
        super(MINIMIZE, -512, 511, DIMENSION, number_of_bits);
    }

    @Override
    protected double fitness() {
        double[] values = getGeneValues();
        double rana = 0;
        for (int i = 0; i < values.length - 1; i++) {
            rana += ranaFunction(values[i], values[(i + 1) % values.length]);
        }
        return rana;
    }

    protected double ranaFunction(double x, double y) {
        double t1 = x * Math.sin(Math.sqrt(Math.abs(y + 1 - x)));
        double t2 = Math.cos(Math.sqrt(Math.abs(x + y + 1)));
        double t3 = (y + 1) * Math.cos(Math.sqrt(Math.abs(y + 1 - x)));
        double t4 = Math.sin(Math.sqrt(Math.abs(x + y + 1)));
        return t1 * t2 + t3 * t4;
    }

    @Override
    public void setParameters(String param) {
        try {
            number_of_bits = Integer.parseInt(param);
        } catch (Exception e) {
            number_of_bits = 12;
        }
        restart();
    }

    @Override
    public String getParameters() {
        return number_of_bits + "";
    }
}
