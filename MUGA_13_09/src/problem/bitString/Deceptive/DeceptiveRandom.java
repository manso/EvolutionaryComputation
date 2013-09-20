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
package problem.bitString.Deceptive;

/**
 *
 * @author ZULU
 */
public class DeceptiveRandom extends Deceptive {
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    protected void initializePattern() {
        //pattern - initialize all bits to False
        PATTERN = new boolean[NUMBER_OF_BLOCKS][SIZE_OF_BLOCK];
        //all genes
        for (int i = 0; i < NUMBER_OF_BLOCKS; i++) {
            //randomPattern
            for (int j = 0; j < SIZE_OF_BLOCK ; j++) {
                PATTERN[i][j] = rnd.nextBoolean();
            }
           }//all blocks
    }//shuffle
    
      @Override
    public String getGenomeInformation() {
        // public String getInfo() {
        StringBuilder buf = new StringBuilder();
        buf.append("Deceptive Random Problem <").append(NUMBER_OF_BLOCKS).append("> <").append(SIZE_OF_BLOCK).append(">");
        buf.append("\nInitialize the Deceptive Pattern with random bits");
        buf.append("\nDeceptive Pattern:\n");
        for (int i = 0; i < PATTERN.length; i++) {
            for (int j = 0; j < PATTERN[i].length; j++) {
                if (PATTERN[i][j]) {
                    buf.append("1");
                } else {
                    buf.append("0");
                }
            }
            buf.append(" ");
        }
        buf.append("\n\nParameters <BLOCKS> <SIZE>");
        buf.append("\n     <BLOCKS> number of blocks");
        buf.append("\n     <SIZE>  size of block ");
        return buf.toString();

    }
}
