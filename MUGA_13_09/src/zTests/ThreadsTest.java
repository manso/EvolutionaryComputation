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
package zTests;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ZULU
 */
public class ThreadsTest extends Thread {

    public void run() {
        System.out.println("THREAD RUN");
    }

    public static void main(String[] args) {
        try {
            ThreadsTest t = new ThreadsTest();
            Thread t1 = new Thread(t);
            t1.start();
            t1.join();
            Thread t2 = new Thread(t);
            t2.start();
            t2.join();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
