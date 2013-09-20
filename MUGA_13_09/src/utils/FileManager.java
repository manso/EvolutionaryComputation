package utils;

import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;
import javax.swing.JFileChooser;

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
package Templates.Classes;

/**
 *
 * @author _arm
 */
public class FileManager {

    /** Creates a new instance of FileManager */
    public FileManager() {
    }
    private static String GAPath;
    private static String GAPath1 = System.getProperty("java.class.path");
    private static String GAPath2 = "./build/classes/";

    ;
    //Inicializaç?o da path

    static {
        try {
            JarFile jar = new JarFile(GAPath1);
            GAPath = "./";
        } catch (IOException ex) {
            GAPath = GAPath2;
        }
    }

    public static File OpenFileDialog(Frame parent, String path) {
        JFileChooser jfc = new JFileChooser(GAPath + path);
        jfc.setDialogTitle(GAPath + path);
        jfc.setDialogType(JFileChooser.OPEN_DIALOG);
        try {
            jfc.setFileFilter(new GAFileFilter());
            int returnValue = jfc.showOpenDialog(parent);
            if ((returnValue == jfc.ERROR_OPTION) || (returnValue == jfc.CANCEL_OPTION)) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
        return jfc.getSelectedFile();
    }

    public static String OpenFileGA(Frame parent, String path) {
        File f = OpenFileDialog(parent, path);
        if (f == null) {
            return null;
        }

        return makeClassName(path, f.getName());
    }

    public static String OpenClassFileGA(Frame parent, String path) {
        File f = OpenFileDialog(parent, path);
        if (f == null) {
            return null;
        }
        return f.getAbsolutePath();
    }

    public static String makeClassName(String packageName, String fileName) {
        StringBuffer str = new StringBuffer(packageName);
        str.append(".");
        //retirar a extensão do ficheiro
        int lastPoint = fileName.lastIndexOf('.');
        for (int index = 0; index < lastPoint; index++) {
            str.append(fileName.charAt(index));
        }
        return str.toString();
    }
}
