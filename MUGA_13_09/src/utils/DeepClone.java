///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2012                                             ****/
///****     António Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt             ****/
///****     Instituto Politécnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****                                                                    ****/
///****************************************************************************/
///****     This software was build with the purpose of learning.          ****/
///****     Its use is free and is not provided any guarantee              ****/
///****     or support.                                                    ****/
///****     If you met bugs, please, report them to the author             ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/
package utils;

import genetic.Solver.GA;
import genetic.Solver.SimpleSolver;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import operator.mutation.Mutation;
import operator.mutation.bitString.M_FlipBitWave;
import operator.recombination.bitString.Crossover;



/**
 *
 * @author ZULU
 */
public class DeepClone {

    public static Object deepClone(Object obj) {
        ObjectOutputStream objOut = null;
        ObjectInputStream objIn = null;
        try {
            //stream em memoria
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            objOut = new ObjectOutputStream(bytes);
            //escrever o objecto na stream
            objOut.writeObject(obj);
            objOut.flush();
            bytes.flush();
            //stream de objectos
            objIn = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()));
            //ler o objecto da stream
            return objIn.readObject();
        } catch (Exception ex) {
            System.out.println(" obj " + obj.getClass().getName());
            System.out.println(" Seriazable " + (obj instanceof Serializable));
            Logger.getLogger(DeepClone.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                objOut.close();
                objIn.close();
            } catch (IOException ex) {
                Logger.getLogger(DeepClone.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }

    /**
     * converte um objecto num array de bytes
     *
     * @param obj objecto
     * @return array de bytes
     * @throws IOException
     */
    public static byte[] toByteArray(Object obj) throws IOException {
        //stream em memoria
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        //stream de objectos
        ObjectOutputStream o = new ObjectOutputStream(b);
        //escrever o objecto na stream
        o.writeObject(obj);
        //converter para bytes
        return b.toByteArray();
    }

    /**
     * converte array de bytes num objecto
     *
     * @param bytes array de bytes
     * @return objecto
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object toObject(byte[] bytes) throws IOException, ClassNotFoundException {
        //stream em memoria
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        //stream de objectos
        ObjectInputStream o = new ObjectInputStream(b);
        //ler o objecto da stream
        return o.readObject();
    }
    
    public static void main(String[] args) {
        SimpleSolver s = new GA();
        
        M_FlipBitWave m = new M_FlipBitWave();
        System.out.println("m =" + m);
        m.setParameters("0 10 20");
        System.out.println("m =" + m);
        s.setMutate(m);
        
        
        Mutation clone = (Mutation)m.getClone();
        System.out.println("clone =" + clone);
        
        Crossover cr = new Crossover();
        cr.setParameters(" 0.9 5");
        s.setRecombine(cr);
        System.out.println("CR :\t"+ cr);
        Crossover cl = (Crossover) cr.getClone();
        System.out.println("cl CR :\t"+ cl);
        
        

    }
}
