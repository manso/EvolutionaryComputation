/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author manso
 */
public class FileUtils {

    public static void writeDouble( FileWriter f , double value) throws IOException{
        String str = value + "";
        str = str.replace(".",",");
        f.write(str);
    }

    public static void writeStr( FileWriter f , String str) throws IOException{
        f.write(str);
    }

}
