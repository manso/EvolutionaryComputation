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
package operator.recombination.permutation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Edge recombination
 *
 * @author ZULU
 */
class ERX_Edges {

    static Random random = new Random();
    ArrayList<Integer>[] arrayOfEdges = null;

    public void initialize(int size) {
        arrayOfEdges = new ArrayList[size];
        //create array of edges
        for (int i = 0; i < size; i++) {
            arrayOfEdges[i] = new ArrayList<>();
        }
    }

    public void addEdges(int[] n) {
        //initialize  array if necessary
        if (arrayOfEdges == null) {
            initialize(n.length);
        }
        //create adjacency list
        for (int i = 0; i < n.length; i++) {
            int start = n[i];
            int end = n[ (i + 1) % n.length];
            //insert unique numbers for the edges
            if (!arrayOfEdges[start].contains(end)) {
                arrayOfEdges[start].add(end);
            }
            if (!arrayOfEdges[end].contains(start)) {
                arrayOfEdges[end].add(start);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < arrayOfEdges.length; i++) {
            str.append("\n[" + i + "] = " + arrayOfEdges[i].toString());

        }
        return str.toString();
    }

    /**
     * get the index array with the minimum of arrayOfEdges if any elements have
     * the same minimum return random minimum
     *
     * @return index of the array
     */
    public int getIndexOfMinimalEdges(int[] tsp) {
        int index = -1;
        int min = Integer.MAX_VALUE;
        //random start position
        //random start garantee random index of same minimum
        int position = random.nextInt(arrayOfEdges.length);;
        //for all elements
        for (int i = 0; i < arrayOfEdges.length; i++) {
            //increase position 
            position = (position + 1) % arrayOfEdges.length;
            //calculate minimum
            if (!arrayOfEdges[position].isEmpty() && min >= arrayOfEdges[position].size()) {
                min = arrayOfEdges[position].size();
                index = position;
            }
        }
        //index of random minimum
        return index;
    }

    /**
     * calculate the city with minimum edges if have same minimum return random
     * minimum city
     *
     * @param startCity
     * @return minimum random city
     */
    public int getCityWithMinimalEdges(int startCity) {
        //no edges avaiable
        if (arrayOfEdges[startCity].isEmpty()) {
            return -1;
        }
        //edges of the city 
        ArrayList<Integer> lst = arrayOfEdges[startCity];
        int numCitys = lst.size();



        //random start
        int position = random.nextInt(numCitys);
        //minimum city
        int endCity = position;
        int min = arrayOfEdges[ lst.get(endCity)].size();
        //for others citys
        for (int i = 1; i < lst.size(); i++) {
            //rounding index
            position = (position + 1) % numCitys;
            //update minimum
            if (min > arrayOfEdges[ lst.get(position)].size()) {
                min = arrayOfEdges[ lst.get(position)].size();
                endCity = position;
            }
        }
        return arrayOfEdges[startCity].get(endCity);
    }

    /**
     * remove city to the edges
     *
     * @param city to remove
     */
    public void removeCity(Integer city) {
        for (ArrayList l : arrayOfEdges) {
            l.remove(city);
        }
    }

    /**
     * calculate new permutation using ERX
     *
     * @return
     */
    public int[] executeERX() {
        int numberofRandom = 0;
        //new permutation
        int[] tsp = new int[arrayOfEdges.length];
        //select first city
        tsp[0] = getIndexOfMinimalEdges(tsp);
        //select other citys
        for (int i = 1; i < tsp.length; i++) {
            //remove edges to the last city
            removeCity(tsp[i - 1]);
//            System.out.println("\n" +Arrays.toString(tsp) + " Remove \n" + toString());
            //calculate next city (city with minimal edges starting from the last
            int nextCity = getCityWithMinimalEdges(tsp[i - 1]);
            //city exists
            if (nextCity >= 0) {
                tsp[i] = nextCity;
            } //not edges avaiable from the last city
            else {
                numberofRandom++;
                //calculate city with minimal edges
                int randomCity = getIndexOfMinimalEdges(tsp);
                //if tsp contains the city
                if (contains(tsp, randomCity)) {
                    //select city in the other point of the edge
                    tsp[i] = getCityWithMinimalEdges(randomCity);
                } else {
                    tsp[i] = randomCity;
                }
                //  System.out.println("RANDOM " + tsp[i]);
            }
//            System.out.print("[" + tsp[i] + "]\t");
        }
//        System.out.println("Random Edges = " + numberofRandom);
//        if( !verify(tsp) ){
//            return null;
//        }
        return tsp;

    }
//
//    public boolean verify(int[] v) {
//        for (int i = 0; i < v.length; i++) {
//            if (!contains(v, i)) {
//                System.out.println("ERROR : not contains " + i);
//                return false;
//            }
//        }
//        return true;
//    }

    private static boolean contains(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] p1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        int[] p2 = {4, 1, 2, 8, 7, 6, 9, 3, 5, 0};
        ERX_Edges e = new ERX_Edges();
        e.addEdges(p1);
        e.addEdges(p2);
        System.out.println(e);
        int[] c = e.executeERX();
        System.out.println(Arrays.toString(c));

    }
}