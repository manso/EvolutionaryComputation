package utils;

import java.io.File;
import javax.swing.filechooser.FileFilter;
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
public class GAFileFilter extends FileFilter {
    public final static String EXTENSION = "class";
    /** Creates a new instance of GAFileFilter */
    public GAFileFilter() {
    }
    
    public boolean accept(File f) {
        //não aceita aqueles que começam por abstract
        if( f.getName().startsWith("Abstract"))
            return false;
        // aceita apenas o .class
        if(getTypeDescription(f)== EXTENSION || f.isDirectory()==true)
            return true;
        return false;
        
    }
    
    public String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');
        
        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
    
    public String getTypeDescription(File f) {
        String extension = getExtension(f);
        String type = null;
        if (extension != null) {
            if (extension.equals(EXTENSION))
                type = EXTENSION;
        }
        return type;
    }
    
    
    public String getDescription(){
        return "MUGA Files (*.class) ";
    }
}
