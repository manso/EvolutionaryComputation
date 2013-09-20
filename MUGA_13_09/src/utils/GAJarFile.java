/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import statistics.SolverStatistic;

/**
 *
 * @author manso
 */
public class GAJarFile {

    private static String GAPath;
    private static String GAPath2 = System.getProperty("java.class.path");
    private static String GAPath1 = "./dist/MUGA_13_02.jar";
    //List with all the classes in the jar
    static ArrayList<String> jarElements;

    static {
        //discover path
        try {
            JarFile testFile = new JarFile(GAPath1);
            GAPath = GAPath1;
            testFile.close();
        } catch (IOException ex) {
            GAPath = GAPath2;
        }
        //build array of jar classes
        jarElements = new ArrayList<>();
        JarFile jar;
        try {
            System.out.println("INIT");
            jar = new JarFile(GAPath);
            Enumeration e = jar.entries();
            while (e.hasMoreElements()) {
                String z = e.nextElement().toString();
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

            Logger.getLogger(GAJarFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<String> getClassName(String elem) {
        ArrayList<String> classname = new ArrayList<String>();
            for(String code : jarElements){
                 if (code.startsWith(elem)) {
                     classname.add( code.replace(elem + ".", ""));
                 }
            }
            return classname;

    }

    //---------------------------------------------------------
    //------EPIA 2011 -----------------------------------------
    //---------------------------------------------------------
    //---------------------------------------------------------
    public static void loadJar(String model, String filter, JList lst) {
        ArrayList<String> rec = GAJarFile.getClassName(model);
        DefaultListModel modelL = new DefaultListModel();
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

    public static void loadJar(String model, String filter, JComboBox lst) {
        ArrayList<String> rec = GAJarFile.getClassName(model);
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

    public static void loadJar(String model, String filter, JComboBox lst, String selected) {
        ArrayList<String> rec = GAJarFile.getClassName(model);
        DefaultComboBoxModel modelL = new DefaultComboBoxModel();
        //select the default
        int indexOfDefault = 0;
        for (int i = 0; i < rec.size(); i++) {
            String element = rec.get(i);
            if (filter.isEmpty() || element.contains(filter)) {
                modelL.addElement(rec.get(i));
                if (rec.get(i).endsWith(selected)) {
                    indexOfDefault = i;
                }
            }
        }
        lst.setModel(modelL);
        //set the default selected
        lst.setSelectedIndex(indexOfDefault);
        lst.repaint();
    }

    public static void loadJarStatistics(JPanel panel) {
        String lib = "statistics.elements";
        ArrayList<String> rec = GAJarFile.getClassName(lib);
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
    //---------------------------------------------------------
    //------EPIA 2011 -----------------------------------------
    //---------------------------------------------------------
    //---------------------------------------------------------
}
