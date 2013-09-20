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
 *
 * @author ZULU
 */
public class Schaffer extends RealBitString {

    public static int number_of_bits = 24;
    //-------------------------------------------------------------------------
    static double[] schaffer = {
        0, 0
//        -3.14225645, 7.434654215
    };

    @Override
    public boolean isBest() {
        return fitness < 1E-4;
    }

//    static void RANDOMIZE(){
//        Random rnd = new Random();
//        for (int i = 0; i < schaffer.length; i++) {
//             schaffer[i] = rnd.nextDouble() * 20 - 10;
//
//        }
//    }
    public Schaffer() {
        super(MINIMIZE, -10, 10, 2, number_of_bits);
    }

    @Override
    protected double fitness() {
        double[] values = getGeneValues();
        double x = values[0] - schaffer[0];
        double y = values[1] - schaffer[1];
        return 0.5 + (Math.pow(Math.sin(Math.sqrt(x * x + y * y)), 2) - 0.5)
                / Math.pow(1.0 + 0.001 * (x * x + y * y), 2);
    }

    @Override
    public void setParameters(String param) {
        try {
            number_of_bits = Integer.parseInt(param);
        } catch (Exception e) {
            number_of_bits = 24;
        }
        restart();
//        RANDOMIZE();
    }

    @Override
    public String getParameters() {
        return number_of_bits + "";
    }
}
