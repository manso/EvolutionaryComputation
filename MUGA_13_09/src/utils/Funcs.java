/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author arm
 */
public class Funcs {

    public static String getNormalizedFileName(String file) {
        String alpha = "abcdefghijklmnopqrstuvxywzABCDEFGHIJKLMNOPQRSTUVXYWZ1234567890-";
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < file.length(); i++) {
            char ch = file.charAt(i);
            if (alpha.indexOf(ch) >= 0) {
                buf.append(ch);
            } else {
                buf.append("_");
            }
        }
        return buf.toString();
    }

    /**
     * return the date and time
     *
     * @return
     */
    public static String getNow() {
        //String format = "yyyy-MM-dd HH:mm:ss";
        //String format = "H:mm:ss:SSS";
        //String format = "K:mm a,z";
        //String format = "yyyyMMdd";
        // String format = "dd MMMMM yyyy";
        String format = "yyyy-MM-dd_H_mm_ss_SSS";
        return getNow(format);
    }

    /**
     * return current time format = "yyyy-MM-dd HH:mm:ss"; format =
     * "H:mm:ss:SSS"; String format = "K:mm a,z"; String format = "yyyyMMdd";
     * String format = "dd MMMMM yyyy";
     *
     * @param format format of the current time
     * @return textual time
     */
    public static String getNow(String format) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(cal.getTime());
    }

    /**
     * create path
     *
     * @param path path to creat
     * @return sucess
     */
    public static boolean createPath(String path) {
        return (new File(path)).mkdirs();
    }

    /**
     * convert integer to string with size characters
     *
     * @param value number integer
     * @param size number of characters
     * @return string with number and white spaces if necessary
     */
    public static String IntegerToString(int value, int size) {
        StringBuffer str = new StringBuffer(value + "");
        StringBuilder tmp = new StringBuilder(str);
        while (tmp.length() < size) {
            tmp.insert(0," ");
        }
        return tmp.substring(0, size);
    }

    /**
     * convert double to string with size characters
     *
     * @param value double number
     * @param size number characters
     * @return string with number and white spaces if necessary
     */
    public static String DoubleToString(double value, int size) {
//        return DoubleExponentialToString(value, size);
        String str = Double.toString(value);
        StringBuilder tmp;
        if (value < 0) {
            tmp = new StringBuilder(str);
        } else {
            tmp = new StringBuilder(" " + str);
        }
        while (tmp.length() < size) {
            tmp.insert(0," ");
        }
        return tmp.substring(0, size);

    }

    /**
     * convert double to string with size characters
     *
     * @param value double number
     * @param size number characters
     * @return string with number and white spaces if necessary
     */
    public static String DoubleExponentialToString(double value, int size) {
        return DoubleExponentialToString(value, size, "0.00000E0");
    }

    /**
     * convert double to string with size characters
     *
     * @param value double number
     * @param size number characters
     * @return string with number and white spaces if necessary
     */
    public static String Double_or_INT_ToString(double value, int size) {
        int val = (int) value;
        if (val == value) {
            return IntegerToString(val, size);
        } else {
            return DoubleToString(value, size);
        }
    }

    /**
     * convert double to string with size characters
     *
     * @param value double number
     * @param size number characters
     * @return string with number and white spaces if necessary
     */
    public static String DoubleExponentialToString(double value, int size, String format) {
        NumberFormat formatter = new DecimalFormat(format);
        String str = Double.toString(value);
        StringBuilder tmp;
        if (value < 0) {
            tmp = new StringBuilder(formatter.format(value));
        } else {
            tmp = new StringBuilder(" " + formatter.format(value));
        }
        while (tmp.length() < size) {
            tmp.append(" ");
        }
        return tmp.substring(0, size);

    }

    /**
     * set the string with size lenght
     *
     * @param str string original
     * @param size new size
     * @return string cuted or with white spaces
     */
    public static String SetStringSize(String str, int size) {

        StringBuilder tmp = new StringBuilder();
        int index = 0;
        while (tmp.length() < size && tmp.length() < str.length()) {
            tmp.append(str.charAt(index++));
        }

        while (tmp.length() < size) {
            tmp.append(" ");
        }

        return tmp.toString();
    }
    //EPIA

    /**
     * return current time format = "yyyy-MM-dd HH:mm:ss"; format =
     * "H:mm:ss:SSS"; String format = "K:mm a,z"; String format = "yyyyMMdd";
     * String format = "dd MMMMM yyyy";
     *
     * @param format format of the current time
     * @return textual time
     */
    public static String getTimeFormated(long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(time);
    }
    //EPIA

    public static void main(String[] args) {
        double money = 111001000111.11111111;
        NumberFormat formatter = new DecimalFormat("0.0000E0");
        System.out.println(money);
        System.out.println(formatter.format(money));
        System.out.println(Funcs.getNow());
    }
}
