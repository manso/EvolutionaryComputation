/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.lang.reflect.Constructor;
import operator.GeneticOperator;
import problem.Individual;

/**
 *
 * @author manso
 */
public class DynamicLoad {

    /**
     * make a object given a string name
     * @param className name of the class
     * @return object of the class
     */
    public static Object makeObject(String className, String params) {
        Object obj = makeObject(className);
        if (obj != null && obj instanceof GeneticOperator) {
            GeneticOperator op = (GeneticOperator) obj;
            op.setParameters(params);
            return op;
        } else if (obj != null && obj instanceof Individual) {
            Individual i = (Individual) obj;
            i.setParameters(params);
            return i;
        }
        return obj;
    }

    
    /**
     * make a object given a string name
     * @param className name of the class
     * @return object of the class
     */
    public static Object makeObject(String className) {
        //tranformar a path em package
        className = pathToPackage(className);
        //normalizar o nome
        className = getClassLib(className) + "." + getClassName(className);
        try {
            Class[] classParam = null;
            Object[] objectParam = null;
            //-------------------------------------
            Thread t = Thread.currentThread();
            ClassLoader cloader = t.getContextClassLoader();
            Class cl = cloader.loadClass(className);
            //------------------------------------
            Constructor co = cl.getConstructor(classParam);
            Object p = co.newInstance(objectParam);
            return p;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * construct one object of the class of object.
     * @param template object template
     * @return new object
     */
    public static Object makeObject(Object template) {
        try {

            Class[] classParam = null;
            Object[] objectParam = null;
            Class cl = template.getClass();
            Constructor co = cl.getConstructor(classParam);
            Object obj = co.newInstance(objectParam);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getClassName(String str) {
        String name = str;
        //tirar o class
        if (str.endsWith(".class")) {
            name = name.substring(0, str.length() - ".class".length());
        }
        // tirar i package
        int posCl = name.lastIndexOf(".");
        if (posCl >= 0) {
            name = name.substring(posCl + 1, name.length());
        }
        return name;
    }

    public static String getClassLib(String str) {
        String name = str;
        //tirar o class
        if (str.endsWith(".class")) {
            name = name.substring(0, str.length() - ".class".length());
        }
        // tirar i package
        int posCl = name.lastIndexOf(".");
        if (posCl >= 0) {
            name = name.substring(0, posCl);
        }
        return name;
    }

    public static String pathToPackage(String name) {
        if (name.contains("/")) {
            return name.replaceAll("/", ".");
        }
        if (name.contains("\\")) {
            String n = "";
            for (char ch : name.toCharArray()) {
                if (ch == '\\') {
                    ch = '.';
                }
                n += ch;
            }
            return n;
        }
        return name;
    }
}
