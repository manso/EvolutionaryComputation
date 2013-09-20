package utils;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author manso
 */
public class MUGAClassLoader extends ClassLoader {

    /**
     * Loader of the aplication
     * @param parent application loader
     */
    public MUGAClassLoader(ClassLoader parent) {
        super(parent);
    }

    /**
     * Load a class from a filename to the JVM
     * @param filename name of the file including ".class"
     * @param className name of the class to be loaded including packages
     * @return  the class loaded
     */
    public Class getClass(String filename, String className) {
        try {
            //stream of data
            String url = "file:" + filename;
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            InputStream input = connection.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int data = input.read();
            //read the data
            while (data != -1) {
                buffer.write(data);
                data = input.read();
            }
            //close the stream
            input.close();
            //Get bytes
            byte[] clData = buffer.toByteArray();
            // call the parent loader to make a classe from data
            return this.defineClass(className, clData, 0, clData.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //something is wrong
        return null;
    }

    private String getClassName( String filename){
        //barra normal
        int ini = filename.lastIndexOf("/") >= 0 ? filename.lastIndexOf("/") +1: 0;
        //barra invertida
        ini = filename.lastIndexOf("\\") >= 0 ? filename.lastIndexOf("\\") +1: ini;
        int fin = filename.lastIndexOf(".class") >= 0 ? filename.lastIndexOf(".class") : filename.length();
        return filename.substring(ini, fin);

    }

    /**
     * Make a Objecto from a class compiled in the filename.
     * The method load the class to the jvm if this class is not loaded
     * or make a object from the class stored in the jvm
     * @param filename name of the file
     * @param pack name of the package class
     * @param params parametrs to call the constructor
     * @return object made by the contructor with parameters
     * @throws Exception something is wrong
     */
    public Object makeObject(String filename, String pack, Object[] objectParam) throws Exception {
        Class cl;
        String className = pack + "." + getClassName(filename);
        //if the class exists in the loader
        try {
            cl = this.loadClass(className);
        } catch (Exception e) {
            //load the class from the file
            cl = getClass(filename, className);
        }
        //parameters of the constructor
        Class[] classParam = null;
        //if have parameters
        if (objectParam != null) {
            //make the Class array of the parameters
            classParam = new Class[objectParam.length];
            for (int i = 0; i < objectParam.length; i++) {
                classParam[i] = objectParam[i].getClass();
            }
        }
        //get the constructor with the parameters
        Constructor co = cl.getConstructor(classParam);
        //make an object
        Object p = co.newInstance(objectParam);
        //return the object
        return p;
    }

    public Object makeObject(String filename, String pack) throws Exception {
        return makeObject(filename, pack, null);
    }
   
}
