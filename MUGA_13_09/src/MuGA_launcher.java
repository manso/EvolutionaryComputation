
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DynamicLoad;
import utils.GAJarFile;
import utils.MuGA_JarFile;

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
/**
 *
 * @author ZULU
 */
public class MuGA_launcher {

    private static String GAPath1 = "./dist/MUGA_13_02.jar";
    private static String GAPath2 = System.getProperty("java.class.path");
    
    public static String MUGA_PATH;
            
    static {
        //discover path
        try {
            JarFile testFile = new JarFile(GAPath1);
            MUGA_PATH = GAPath1;
            testFile.close();
        } catch (IOException ex) {
            MUGA_PATH = GAPath2;
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("Running Muga");
            Process exec = Runtime.getRuntime().exec("java -jar " + MUGA_PATH);
            System.out.println(""+ exec.getErrorStream());
        } catch (IOException ex) {
            Logger.getLogger(MuGA_launcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
