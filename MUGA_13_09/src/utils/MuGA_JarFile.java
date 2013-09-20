/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import statistics.SolverStatistic;

/**
 *
 * @author manso
 */
public class MuGA_JarFile {

    private static String PATH;
    private static String jarPath1 = System.getProperty("java.class.path");
    private static String jarPath2 = "./dist/MUGA_13_07.jar";
    //List with all the classes in the jar
    static ArrayList<String> jarElements;

    static {
        //discover path
        if ((new File(jarPath2)).isFile()) {
            PATH = jarPath2;
        } else {
            PATH = jarPath1;
        }
        //build array of jar classes
        jarElements = new ArrayList<>();
        JarFile jar;
        try {
            jar = new JarFile(PATH);

            Enumeration e = jar.entries();
            while (e.hasMoreElements()) {
                String z = e.nextElement().toString();
                if (z.startsWith("GUI") || z.startsWith("utils") || z.startsWith("libs")) {
                    continue;
                }
//                System.out.println(z);
                //replace / to  .
                z = DynamicLoad.pathToPackage(z);
                // only instantiable classes
                if (z.endsWith(".class")) {
                    // have default constructor
                    Object obj = DynamicLoad.makeObject(z);
                    if (obj != null) {
                        jarElements.add(obj.getClass().getName());
                    }
                }
            }
            jar.close();
        } catch (IOException ex) {

            Logger.getLogger(MuGA_JarFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<String> getClassesByFilter(String elem) {
        ArrayList<String> classname = new ArrayList<String>();
        for (String code : jarElements) {
            if (code.startsWith(elem)) {
                classname.add(code.replace(elem + ".", ""));
            }
        }
        return classname;

    }

    public static void putElementsToList(String model, String filter, JComboBox lst) {
        ArrayList<String> rec = MuGA_JarFile.getClassesByFilter(model);
        DefaultComboBoxModel modelL = new DefaultComboBoxModel();
        //select the default
        int indexOfDefault = 0;
        for (int i = 0; i < rec.size(); i++) {
            String element = rec.get(i);
            if (filter.isEmpty() || element.contains(filter)) {
                modelL.addElement(rec.get(i));
                if (rec.get(i).equalsIgnoreCase("default")) {
                    indexOfDefault = i;
                }
            }
        }
        lst.setModel(modelL);
        //set the default selected
        lst.setSelectedIndex(indexOfDefault);
        lst.repaint();
    }

    public static void putElementsToList(String model, String filter, JComboBox lst, String selected) {
        ArrayList<String> rec = MuGA_JarFile.getClassesByFilter(model);
        DefaultComboBoxModel modelL = new DefaultComboBoxModel();
        //select the default
        int indexOfDefault = -1;
        for (int i = 0; i < rec.size(); i++) {
            String element = rec.get(i);
            if (filter.isEmpty() || element.contains(filter)) {
                modelL.addElement(rec.get(i));
                if (rec.get(i).equalsIgnoreCase(selected) || rec.get(i).endsWith("." + selected)) {
                    indexOfDefault = i;
                }
            }
        }

//        if( indexOfDefault < 0 ){
//            putElementsToList(model, filter, lst,selected);
//        }

        lst.setModel(modelL);
        //set the default selected
        lst.setSelectedIndex(indexOfDefault);
        lst.repaint();
    }

    public static void loadJarStatistics(JPanel panel) {
        String lib = "statistics.elements";
        ArrayList<String> rec = MuGA_JarFile.getClassesByFilter(lib);
        panel.removeAll();
        for (int i = 0; i < rec.size(); i++) {
            JCheckBox checkbox = new javax.swing.JCheckBox(rec.get(i), false);
            if (SolverStatistic.model.contains(lib + "." + rec.get(i))) {
                checkbox.setSelected(true);
            }
            panel.add(checkbox);
        }
        panel.revalidate();
    }
}
