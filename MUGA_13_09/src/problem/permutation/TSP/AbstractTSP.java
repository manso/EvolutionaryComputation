/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package problem.permutation.TSP;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import problem.Individual;
import problem.PRM_Individual;
import utils.Funcs;
import utils.MetricsA.Distances;

/**
 *
 * @author small
 */
public abstract class AbstractTSP extends PRM_Individual {
    public static double REMOVED_EDGE = 9999999;
    public static double[] _x;
    public static double[] _y;
    public static int[] _optimum = null;
    public static double[][] distance = null;
    public static Rectangle2D bounds = null;

    public AbstractTSP(int dimension) {
        super(MINIMIZE, dimension);
    }

    public AbstractTSP(double[] x, double[] y) {
        super(MINIMIZE, x.length);
        initializeData(x, y, null);
    }

    public AbstractTSP(double[] x, double[] y, int[] opt) {
        super(MINIMIZE, x.length);
        initializeData(x, y, opt);
    }

    public int[] getOptimumPath() {
        return _optimum;
    }

//    @Override
//    public void fillRandom() {
//        if (distance == null || _x.length != distance[0].length) {
//            super.fillRandom();
//        } else {
//            switch (random.nextInt(3)) {
////                case 0:
////                    genome = GreadyVertexInsertion.calculate(distance);
////                    break;
////                case 1:
////                    genome = NearestNeighbor.calculate(distance);
////                    break;
//                default:
//                    super.fillRandom();
//            }
//            repair();
//        }
//    }
    @Override
    protected double fitness() {
        repair(genome);
        fitness = calculateLenght(distance, this.genome);
        return fitness;
    }

    public double getLenghtOfpath(int i, int j) {
        i = (i + genome.length) % genome.length;
        j = (j + genome.length) % genome.length;
        return distance[genome[i]][genome[j]];
    }
    
    public static double getLenghtOfEge(int i, int j) {
        return distance[i][j];
    }

    @Override
    public void setGeneValues(int[] values) {
        super.setGeneValues(values);
        repair(genome);
    }

    /*
     * gets the gene at position index @param index position in genome @return
     * gene in the position
     */
    @Override
    public void setGeneValue(int index, double value) {
        super.setGeneValue(index, value);
        repair(genome);
    }

    protected static void repair(int cities[]) {
        if (cities.length < 3) {
            return;
        }
        //reverse if necessary
        reverse(cities);
        //already normalized
        if (cities[0] == 0) {
            return;
        }

        int index = 0;
        int aux[] = new int[cities.length];
        System.arraycopy(cities, 0, aux, 0, aux.length);
        //index of zero 
        while (cities[index] != 0) {
            index++;
        }
        //rotate
        for (int i = 0; i < aux.length; i++) {
            cities[i] = aux[(index + i) % aux.length];
        }

    }

    protected static void reverse(int cities[]) {
        int i = 1, f = cities.length - 1;
        //second gene must be lesser than the last
        if (cities[i] > cities[f]) {
            while (i < f) {
                int aux = cities[i];
                cities[i] = cities[f];
                cities[f] = aux;
                i++;
                f--;
            }
        }
    }

    public static double calculateLenght(double[][] distance, int[] genes) {
        double lenght = 0;
        for (int i = 0; i < genes.length; i++) {
            lenght += distance[genes[i]][genes[(i + 1) % genes.length]];
        }
        return lenght;
    }
    public static double calculateLenght(int[] genes) {
        return calculateLenght(distance, genes);
    }
    //---------------------------------------------------------------------
    //---------------------------------------------------------------------
    //---------------------------------------------------------------------
    //---------------------------------------------------------------------

    public static int getNumberOfCities() {
        return _x.length;
    }

    public static double[] getX() {
        return _x;
    }

    public static double[] getY() {
        return _y;
    }

    public static int[] getOptimum() {
        return _optimum;
    }

    public static Rectangle2D getBounds() {
        return bounds;
    }
    //---------------------------------------------------------------------

    public static Point2D getPoint(int index) {
        return new Point2D.Double(_x[index], _y[index]);
    }

    public static double getTSPDistance(int p1, int p2) {
        return distance[p1][p2];
    }

    public static Line2D getTSPEdge(int p1, int p2) {
//        System.out.println("EDGE : (" + getPoint(p1) + ")----(" + getPoint(p2) + ")");
        return new Line2D.Double(getPoint(p1), getPoint(p2));
    }

    public Line2D getEdge(int index) {
        int p1 = (index) % _x.length;
        int p2 = (p1 + 1) % _x.length;
//        System.out.println("EDGE : (" + getPoint(genome[p1]) + ")----(" + getPoint(genome[p2]) + ")");
        return new Line2D.Double(getPoint(genome[p1]), getPoint(genome[p2]));
    }

    public double getEdgeCost(int index) {
        return getTSPDistance(genome[index], genome[(index + 1) % _x.length]);
    }

    //---------------------------------------------------------------------
    //---------------------------------------------------------------------
    //---------------------------------------------------------------------
    //calculate matrix of lenghts
    public static void initializeData(double[] xx, double[] yy, int[] opt) {
        //is the same problem
        if (xx == _x) {
            return;
        }
        _x = xx;
        _y = yy;
        _optimum = opt;

        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;

        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;

        distance = new double[_x.length][_x.length];
        for (int i = 0; i < distance.length; i++) {
            //calculate distances
            for (int j = 0; j < distance.length; j++) {
                double dx = _x[j] - _x[i];
                double dy = _y[j] - _y[i];;
                distance[i][j] = Math.sqrt(dx * dx + dy * dy);
                // System.out.printf("\t%4.2f" , distance[_y][_x] );
            }
            //calculate bounds
            if (minX > _x[i]) {
                minX = _x[i];
            }
            if (minY > _y[i]) {
                minY = _y[i];
            }
            if (maxX < _x[i]) {
                maxX = _x[i];
            }
            if (maxY < _y[i]) {
                maxY = _y[i];
            }
        }
        bounds = new Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY);

        //normalize Optimum
        repair(opt);
        //set _optimum
        if (opt != null) {
            _optimum = opt;
        } else {
            int[] nullOpt = {};
            _optimum = nullOpt;
        }
        //calculate lenght
        if (_optimum != null) {
            setBest(calculateLenght(distance, opt));
        } else {
            setBest(0);
        }

    }
    //---------------------------------------------------------------------------
    //---------------------------------------------------------------------------
    //---------------------------------------------------------------------------
    //---------------------------------------------------------------------------

    private String getPhenotype(int i) {
        int x1 = _optimum[i];
        int x2 = _optimum[(i + 1) % _optimum.length];

        for (int k = 0; k < genome.length; k++) {
            if (genome[k] == x1 && genome[(k + 1) % genome.length] == x2
                    || genome[k] == x2 && genome[(k + 1) % genome.length] == x1) {
                return "_";
            }
        }
        return "X";
    }

    @Override
    public String toStringPhenotype() {
        if (_optimum.length < 3) {
            return toStringGenotype();
        }
        StringBuilder result = new StringBuilder();

        if (this.isEvaluated()) {
            double error = (fitness - getBest())/getBest();
            result.append("Error " + Funcs.DoubleToString(error*100, 10) + " % ");
        } else {
            result.append(Funcs.SetStringSize("?T?S?P?", 10));
        }    

        int numberOfCorrectEdges = 0;
        //distance to optimum
        for (int i = 0; i < _optimum.length; i++) {
            int x1 = _optimum[i];
            int x2 = _optimum[(i + 1) % _optimum.length];
            boolean ok = false;
            for (int k = 0; k < genome.length; k++) {
                if (genome[k] == x1 && genome[(k + 1) % genome.length] == x2
                        || genome[k] == x2 && genome[(k + 1) % genome.length] == x1) {
                    ok = true;
                    break;
                }
            }
            if (ok) {
                result.append("_");
                numberOfCorrectEdges++;
            } else {
                result.append("X");
            }
        }//for
        result.append(" Correct: " + (numberOfCorrectEdges * 100.0) / _optimum.length + " %");


        return result.toString();

    }

    @Override
    public String toStringGenotype() {

        StringBuilder result = new StringBuilder();
        //number of copies
        String elem = "";
        if (this.getNumCopies() > 1) {
            elem = "[" + Funcs.IntegerToString(getNumCopies(), 4) + "]";
        }
        result.append(Funcs.SetStringSize(elem, 8));

        if (this.isEvaluated()) {
            result.append(Funcs.DoubleToString(fitness, 10));
        } else {
            result.append(Funcs.SetStringSize("?T?S?P?", 10));
        }
        result.append(" = ");

        //genotype
        for (int i = 0; i < genome.length; i++) {
            result.append(Funcs.IntegerToString(genome[i], 4));
        }

        if (isBest()) {
            result.append("\t<Best>");
        }
        return result.toString();
    }

    @Override
    public String getGenomeInformation() {
        // public String getInfo() {
        StringBuilder result = new StringBuilder();
        result.append(super.getGenomeInformation());
        result.append("\nDIMENSION :" + genome.length);

        if (_optimum != null && _optimum.length == genome.length) {
            result.append("\nOPTIMUM   : " + getBest());
            result.append("\nPATH      : ");
            for (int i = 0; i < _optimum.length; i++) {
                result.append(Funcs.IntegerToString(_optimum[i], 4));
            }
        } else {
            result.append("\nOPTIMUM   :UNKNOW");
        }

        result.append("\n\nCOORDINATES\nX =   :");
        //genotype
        for (int i = 0; i < genome.length; i++) {
            result.append(Funcs.DoubleToString(_x[i], 7) + " ");
        }
        result.append("\nY =   :");
        //genotype
        for (int i = 0; i < genome.length; i++) {
            result.append(Funcs.DoubleToString(_y[i], 7) + " ");
        }
        return result.toString();

    }
    //---------------------------------------------------------------------------
    //---------------------------------------------------------------------------
    //---------------------------------------------------------------------------
    /**
     * Distance between tsp using differents edges
     * @param other
     * @return 
     */
    public double distanceTo(Individual other) {
        AbstractTSP tsp = (AbstractTSP) other;
        return Distances.distanceTSP(this.genome, tsp.genome);
    }
    
    
}
