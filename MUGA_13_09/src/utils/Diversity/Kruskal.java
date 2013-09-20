///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2012                                             ****/
///****     António Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt             ****/
///****     Instituto Politécnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****                                                                    ****/
///****************************************************************************/
///****     This software was build with the purpose of learning.          ****/
///****     Its use is free and is not provided any guarantee              ****/
///****     or support.                                                    ****/
///****     If you met bugs, please, report them to the author             ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/
package utils.Diversity;

import genetic.population.Population;
import genetic.population.SimplePopulation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
import problem.Individual;
import problem.realCoded.CEC2008.F1_Shifted_Sphere;

/**
 *
 * @author ZULU
 */
public class Kruskal {


    public double MST(Population pop) {
        int[] vertices = new int[pop.getNumGenotypes()]; // cluster of the point
        int maxSet = 0; // max cluster 
        PriorityQueue<Edge> edges = new PriorityQueue<>();
        ArrayList<Edge> krush = new ArrayList<>();
        //build edges
        for (int i = 0; i < pop.getNumGenotypes(); i++) {
            for (int j = i + 1; j < pop.getNumGenotypes(); j++) {
                edges.add(new Edge(i, j, pop.getGenotype(i), pop.getGenotype(j)));
            }
        }
        //create MST
        double value = 0;
        while (!edges.isEmpty()) {
            Edge e = edges.remove();
            //same set of points - (build a cicle) - continue to next
            if (vertices[e.start] != 0 && vertices[e.start] == vertices[e.end]) {
                continue;
            }
            //increase lenght
            value += e.value;
            //save edge
            krush.add(e);
            //New set of points (isolated edge) 
            if (vertices[e.start] == 0 && vertices[e.end] == 0) {
                //increse the number of set
                maxSet++;
                //update the cluster number of the points
                vertices[e.start] = maxSet;
                vertices[e.end] = maxSet;
            } else {
                //Join start to set
                if (vertices[e.start] == 0) {
                    vertices[e.start] = vertices[e.end];
                } //Join end to set
                else if (vertices[e.end] == 0) {
                    vertices[e.end] = vertices[e.start];
                } else {
                    //Edge Join two sets of points
                    //number of new joined set
                    int set = Math.max(vertices[e.start], vertices[e.end]);
                    //number of set to replace to "set"
                    int replace = Math.min(vertices[e.start], vertices[e.end]);
                    // update the set number of all points to nrw set number
                    for (int i = 0; i < vertices.length; i++) {
                        if (vertices[i] == replace) {
                            vertices[i] = set;
                        }
                    }
                }
            }

        }
        //return the lenght of MST
        return value;
    }

    private class Edge implements Comparable<Edge> {

        double value;
        int start, end;
        int group;

        public Edge(int i, int f, Individual p1, Individual p2) {
            start = i;
            end = f;
            value = p1.distanceTo(p2);
        }

        public int compareTo(Edge o) {
            if (o.value < this.value) {
                return 1;
            }
            if (o.value > this.value) {
                return -1;
            }
            return 0;
        }
    }

    public static void main(String[] args) {

        F1_Shifted_Sphere i = new F1_Shifted_Sphere();
        i.setParameters("2");
        Population p = new SimplePopulation();
        F1_Shifted_Sphere i1 = new F1_Shifted_Sphere();
        i1.setGeneValue(0, 0);
        i1.setGeneValue(1, 0);
        p.addIndividual(i1);

        F1_Shifted_Sphere i2 = new F1_Shifted_Sphere();
        i2.setGeneValue(0, 1);
        i2.setGeneValue(1, 0);
        p.addIndividual(i2);

        F1_Shifted_Sphere i3 = new F1_Shifted_Sphere();
        i3.setGeneValue(0, 1);
        i3.setGeneValue(1, 1);
        p.addIndividual(i3);

        F1_Shifted_Sphere i4 = new F1_Shifted_Sphere();
        i4.setGeneValue(0, 0);
        i4.setGeneValue(1, 1);
        p.addIndividual(i4);


        F1_Shifted_Sphere i5 = new F1_Shifted_Sphere();
        i5.setGeneValue(0, -1);
        i5.setGeneValue(1, 1);
        p.addIndividual(i5);


        F1_Shifted_Sphere i6 = new F1_Shifted_Sphere();
        i6.setGeneValue(0, 2);
        i6.setGeneValue(1, 1);
        p.addIndividual(i6);


        F1_Shifted_Sphere i7 = new F1_Shifted_Sphere();
        i7.setGeneValue(0, 0.5);
        i7.setGeneValue(1, 0.5);
        p.addIndividual(i7);


        Kruskal k = new Kruskal();
        System.out.println("MST = " + k.MST(p));

        System.out.println(p);


    }
}
