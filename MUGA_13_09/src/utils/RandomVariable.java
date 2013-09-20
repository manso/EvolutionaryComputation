/**
 * Copyright 2007 The Apache Software Foundation
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package utils;

import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

/**
 *
 * @author manso
 */
public class RandomVariable {

    /**
     * Generate a random number between 0 and 1.
     *
     * @return a double between 0 and 1.
     */
    public static double rand() {
        double x = Math.random();
        return x;
    }

    /**
     * Generate a random integer.
     *
     * @param i0 min of the random variable.
     * @param i1 max of the random variable.
     * @return an int between i0 and i1.
     */
    public static int randInt(int i0, int i1) {
        double x = rand();
        int i = i0 + (int) Math.floor((i1 - i0 + 1) * x);
        return i;
    }

    /**
     * Generate a random string using the specified prefix and a fixed length.
     * @param prefix
     *        the specified string prefix.
     * @param length
     *        the length of the string to be appended.
     * @return random string.
     */
    public static String randString(String prefix, int length) {
        StringBuilder result = new StringBuilder(prefix);
        for (int i = 0; i < length; i++) {
            char ch = (char) ((Math.random() * 26) + 97);
            result.append(ch);
        }

        return result.toString();
    }

    /**
     * Generate a random number from a uniform random variable.
     *
     * @param min min of the random variable.
     * @param max max of the random variable.
     * @return a double.
     */
    public static double uniform(double min, double max) {
        if( min> max){
            return max + (min - max) * rand();
        }
        return min + (max - min) * rand();
    }

    /**
     * Generate a random number from a discrete random variable.
     *
     * @param values discrete values.
     * @param prob probability of each value.
     * @return a double.
     */
    public static double dirac(double[] values, double[] prob) {
        double[] prob_cumul = new double[values.length];
        prob_cumul[0] = prob[0];
        for (int i = 1; i < values.length; i++) {
            prob_cumul[i] = prob_cumul[i - 1] + prob[i];
        }
        double y = rand();
        double x = 0;
        for (int i = 0; i < values.length - 1; i++) {
            if ((y > prob_cumul[i]) && (y < prob_cumul[i + 1])) {
                x = values[i];
            }
        }
        return x;
    }

    /**
     * Generate a random number from a Gaussian (Normal) random variable.
     *
     * @param mu mean of the random variable.
     * @param sigma standard deviation of the random variable.
     * @return a double.
     */
    public static double normal(double mu, double sigma) {
        double x = mu + sigma * Math.cos(2 * Math.PI * rand())
                * Math.sqrt(-2 * Math.log(rand()));
        return x;
    }

    /**
     * Generate a random number from a Chi-2 random variable.
     *
     * @param n degrees of freedom of the chi2 random variable.
     * @return a double.
     */
    public static double chi2(int n) {
        double x = 0;
        for (int i = 0; i < n; i++) {
            double norm = normal(0, 1);
            x += norm * norm;
        }
        return x;
    }

    /**
     * Generate a random number from a LogNormal random variable.
     *
     * @param mu mean of the Normal random variable.
     * @param sigma standard deviation of the Normal random variable.
     * @return a double.
     */
    public static double logNormal(double mu, double sigma) {
        double x = mu + sigma * Math.cos(2 * Math.PI * rand())
                * Math.sqrt(-2 * Math.log(rand()));
        return x;
    }

    /**
     * Generate a random number from an exponantial random variable (Mean =
     * 1/lambda, variance = 1/lambda^2).
     *
     * @param lambda parmaeter of the exponential random variable.
     * @return a double.
     */
    public static double exponential(double lambda) {
        double x = -1 / lambda * Math.log(rand());
        return x;
    }

    /**
     * Generate a random number from a symetric triangular random variable.
     *
     * @param min min of the random variable.
     * @param max max of the random variable.
     * @return a double.
     */
    public static double triangular(double min, double max) {
        double x = min / 2 + (max - min) * rand() / 2 + min / 2 + (max - min)
                * rand() / 2;
        return x;
    }

    /**
     * Generate a random number from a non-symetric triangular random variable.
     *
     * @param min min of the random variable.
     * @param med value of the random variable with max density.
     * @param max max of the random variable.
     * @return a double.
     */
    public static double triangular(double min, double med, double max) {
        double y = rand();
        double x = (y < ((med - min) / (max - min))) ? (min + Math.sqrt(y
                * (max - min) * (med - min))) : (max - Math.sqrt((1 - y) * (max - min)
                * (max - med)));
        return x;
    }

    /**
     * Generate a random number from a beta random variable.
     *
     * @param a first parameter of the Beta random variable.
     * @param b second parameter of the Beta random variable.
     * @return a double.
     */
    public static double beta(double a, double b) {
        double try_x;
        double try_y;
        do {
            try_x = Math.pow(rand(), 1 / a);
            try_y = Math.pow(rand(), 1 / b);
        } while ((try_x + try_y) > 1);
        return try_x / (try_x + try_y);
    }

    /**
     * Generate a random number from a Cauchy random variable (Mean = Inf, and
     * Variance = Inf).
     *
     * @param mu median of the Weibull random variable
     * @param sigma second parameter of the Cauchy random variable.
     * @return a double.
     */
    public static double cauchy(double mu, double sigma) {
        double x = sigma * Math.tan(Math.PI * (rand() - 0.5)) + mu;
        return x;
    }

    /**
     * Generate a random number from a Weibull random variable.
     *
     * @param lambda first parameter of the Weibull random variable.
     * @param c second parameter of the Weibull random variable.
     * @return a double.
     */
    public static double weibull(double lambda, double c) {
        double x = Math.pow(-Math.log(1 - rand()), 1 / c) / lambda;
        return x;
    }

    public static double laplace(double mean, double scale) {
        //http://en.wikipedia.org/wiki/Laplace_distribution
        double u = rand() - 0.5;       //Uniform -0.5 .. 0.5
        if (u <= 0) {
            return mean + scale * Math.log((1.0 + 2 * u));
        }
        return mean - scale * Math.log((1.0 - 2 * u));

    }
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------

    public static void main(String[] args) {
        double[][] value = new double[2][100000];
        for (int i = 1; i < 100000; i++) {
            //double v = RandomVariable.normal(0, 1);
            //double v = RandomVariable.cauchy(0, 1);
            //double v = RandomVariable.chi2(5);
            //double v = RandomVariable.exponential(1);
//            double v = RandomVariable.laplace(0, 1);
            // double v = RandomVariable.laplace(-3,1);
            //double v = RandomVariable.triangular(-6, -5, -3);
            double v = RandomVariable.weibull(4, 5);
//            if (v > 10 || v < -10) {
//                value[0][i] = 10d;
//            } else {
//                value[0][i] = v;
//            }

            //v = RandomVariable.laplace(-3,0.25);

//            v = RandomVariable.weibull(1, 1.5);
            if (v > 10 || v < -10) {
                value[1][i] = 10d;
            } else {
                value[1][i] = v;
            }

        }
        int number = 40;
        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.FREQUENCY);
        dataset.addSeries("Value 1", value[0], number);
        dataset.addSeries("Value 2", value[1], number);
        String plotTitle = "Histogram";
        String xaxis = "number";
        String yaxis = "value";
        PlotOrientation orientation = PlotOrientation.VERTICAL;
        boolean show = false;
        boolean toolTips = false;
        boolean urls = false;
        JFreeChart chart = ChartFactory.createHistogram(plotTitle, xaxis, yaxis,
                dataset, orientation, show, toolTips, urls);
//        int width = 500;
//        int height = 300;
//        try {
//            ChartUtilities.saveChartAsPNG(new File("histogram3.PNG"), chart, width, height);
//        } catch (IOException e) {
//        }
        //Display Chart
        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame("Histogram plot demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);


    }
}
