/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author manso
 */
public class FileFilterMuga extends FileFilter {
     @Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".muga") || f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "MuGA Solver (.muga)";
            }    
}
