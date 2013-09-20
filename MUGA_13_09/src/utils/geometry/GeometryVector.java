/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.geometry;

/**
 *
 * @author manso
 */
public class GeometryVector {

    /**
     * Array containing the components of the vector.
     */
    protected double vector[];

    /**
     * Constructs an empty vector.
     * @param dim the dimension of the vector.
     */
    public GeometryVector(final int dim) {
        vector = new double[dim];
    }

    /**
     * Constructs a vector by wrapping an array.
     * @param array an assigned value.
     */
    public GeometryVector(final double array[]) {
        vector = array;
    }

    /**
     * Copy  constructor - hardcopy of the vector
     * @param vector to copy.
     */
    public GeometryVector(final GeometryVector v) {
        vector = new double[v.vector.length];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = v.getElement(i);
        }
    }

    /**
     * Returns a comma delimited string representing the value of this vector.
     */
    @Override
    public String toString() {
        final StringBuffer buf = new StringBuffer(8 * vector.length);
        int i;
        buf.append('[');
        for (i = 0; i < vector.length - 1; i++) {
            buf.append(vector[i]);
            buf.append(',');
        }
        buf.append(vector[i]);
        buf.append(']');
        return buf.toString();
    }

    /**
     * Returns a component of this vector.
     * @param n index of the vector component.
     */
    public double getElement(final int n) {
        return vector[n];
    }

    /**
     * Sets the value of a component of this vector.
     * @param n index of the vector component.
     * @param x a number.
     */
    public void setElement(final int n, final double x) {
        vector[n] = x;
    }

    public double norm() {
        double value = 0;
        for (int i = 0; i < vector.length; i++) {
            value += Math.pow(vector[i], 2);
        }
        return Math.sqrt(value);
    }

    public GeometryVector add(final GeometryVector v) {
        if (vector.length == v.vector.length) {
            final double array[] = new double[vector.length];
            for (int i = 0; i < vector.length; i++) {
                array[i] = vector[i] + v.vector[i];
            }
            return new GeometryVector(array);
        } else {
            throw new IllegalArgumentException("Vectors are different sizes.");
        }
    }

    public GeometryVector subtract(final GeometryVector v) {
        if (vector.length == vector.length) {
            final double array[] = new double[vector.length];
            for (int i = 0; i < vector.length; i++) {
                array[i] = vector[i] - v.vector[i];
            }
            return new GeometryVector(array);
        } else {
            throw new IllegalArgumentException("Vectors are different sizes.");
        }
    }

    /**
     * Returns the multiplication of this vector by a scalar.
     * @param x a double.
     */
    public GeometryVector scalarMultiply(final double x) {
        final double array[] = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            array[i] = x * vector[i];
        }
        return new GeometryVector(array);
    }

    /**
     * Returns the division of this vector by a scalar.
     * @param x a double.
     * @exception ArithmeticException If divide by zero.
     */
    public GeometryVector scalarDivide(final double x) {
        if (x != 0) {
            final double array[] = new double[vector.length];
            for (int i = 0; i < vector.length; i++) {
                array[i] = vector[i] / x;
            }
            return new GeometryVector(array);
        } else {
            throw new IllegalArgumentException("Scalar Divide : Division by zero");
        }
    }

    public double scalarProduct(final GeometryVector v) {
        if (vector.length == v.vector.length) {
            double answer = 0;
            for (int i = 0; i < vector.length; i++) {
                answer += vector[i] * v.vector[i];
            }
            return answer;
        } else {
            throw new IllegalArgumentException("Vectors are different sizes.");
        }
    }

    /**
     * Returns a normalised vector (a vector with norm equal to one).
     */
    public GeometryVector normalize() {
        if (norm() != 0) {
            return this.scalarDivide(norm());
        }else{
            //return this;
            throw new IllegalArgumentException("Normalize : Norm of vector is zero");
        }
    }

    /**
     * The Gram-Schmidt orthonormalization method.
     * @param vecs a set of linearly independent vectors.
     * @return a set of orthonormal vectors.
     */
    public static GeometryVector[] orthonormalize(GeometryVector vecs[]) {
        final int N = vecs.length;
        GeometryVector orthovecs[] = new GeometryVector[N];
        for (int i = 0; i < N; i++) {
            orthovecs[i] = vecs[i];
            for (int j = 0; j < i; j++) {
                orthovecs[i] = orthovecs[i].subtract(orthovecs[j].scalarMultiply(orthovecs[j].scalarProduct(orthovecs[i])));
            }
            orthovecs[i] = orthovecs[i].normalize();
        }
        return orthovecs;
    }
    

    public static double[] orthonormalize(double[] v1, double[] v2) {
        //build parameters
        GeometryVector dv1 = new GeometryVector(v1);
        GeometryVector dv2 = new GeometryVector(v2);
        GeometryVector[] params = {dv1, dv2};
        //calculate result
        GeometryVector[] result = orthonormalize(params);
        //return last vector
        return result[1].vector;
    }

    /**
     *  return the last orthnormal vector not null of a set of vectors
     * @param vecs set of vectores
     * @return orthonormal vector not null
     */
    public static double[] orthonormalizeNotNull(GeometryVector vecs[]) {
        GeometryVector orthovecs[] = orthonormalize(vecs);
        int i = orthovecs.length -1;
        while( i > 0 && orthovecs[i].norm() == 0.0)
            i--;
        return orthovecs[i].vector;
    }

    /**
     * return the last orthnormal vector not null of three vectors
     * @param v1 first vector
     * @param v2 second vector
     * @param v3 thirth vector
     * @return ortonormal vector not null
     */
    public static double[] orthonormalizeNotNull(double[] v1, double[] v2, double[] v3) {
         //build parameters
        GeometryVector dv1 = new GeometryVector(v1);
        GeometryVector dv2 = new GeometryVector(v2);
        GeometryVector dv3 = new GeometryVector(v3);
        GeometryVector[] params = {dv1, dv2, dv3};
        return orthonormalizeNotNull(params);
    }

    

    /**
     * return the instersection point of the hyperplane defined  by two points
     * with a point
     * @param hyperPt1 first point of the hyperplane
     * @param hyperPt2 second point of the  hyperplane
     * @param pt point
     * @return instersection point
     */
    public static double[] intersection(double[] hyperPt1, double[] hyperPt2, double[] pt) {
        /*
         * http://www.codeguru.com/forum/printthread.php?t=194400
        How do I find the distance from a point to a line?
        Let the point be C (Cx,Cy) and the line be AB (Ax,Ay) to (Bx,By).
        Let P be the point of perpendicular projection of C on AB.  The parameter
        r, which indicates P's position along AB, is computed by the dot product
        of AC and AB divided by the square of the length of AB:

        (1)    AC dot AB
        r = --------- 
        ||AB||^2

        r has the following meaning:

        r=0      P = A
        r=1      P = B
        r<0      P is on the backward extension of AB
        r>1      P is on the forward extension of AB
        0<r<1    P is interior to AB

        The length of a line segment in d dimensions, AB is computed by:
        L = sqrt( (Bx-Ax)^2 + (By-Ay)^2 + ... + (Bd-Ad)^2)
        and the dot product of two vectors in d dimensions, U dot V is computed:

        D = (Ux * Vx) + (Uy * Vy) + ... + (Ud * Vd)

         */


        //intersection point
        double[] pi = new double[hyperPt1.length];
        //diference of the components
        double[] delta = new double[hyperPt1.length];
        //norm of delta
        double delta2 = 0;
        //factor
        double u = 0;
        for (int i = 0; i < pi.length; i++) {
            delta[i] = hyperPt2[i] - hyperPt1[i];
            delta2 += delta[i] * delta[i];
            u += (pt[i] - hyperPt1[i]) * delta[i];
        }
        u /= delta2;
        //calculate intersection point
        for (int i = 0; i < pi.length; i++) {
            pi[i] = hyperPt1[i] + u * delta[i];
        }
        return pi;
    }

    /**
     * calculate the intersection point between a plane and the line perpendicular
     * wich cross the point pt
     * @param linePt1 vector of the plane
     * @param linePt2 vector of the plane
     * @param point point
     * @return point of intersection
     */
    public static GeometryVector intersection(GeometryVector linePt1, GeometryVector linePt2, GeometryVector point) {
        return new GeometryVector(intersection(linePt1.vector, linePt2.vector, point.vector));
    }

    /**
     * Euclidean distante between two points
     * @param pt1 first point
     * @param pt2 second point
     * @return distance between  v1 and v2
     */
    public static double distanceTo(double[] pt1, double[] pt2) {
        double sum = 0;
        for (int i = 0; i < pt2.length; i++) {
            sum += Math.pow(pt1[i] - pt2[i], 2);
        }
        return Math.sqrt(sum);
    }
    /**
     * Distance between a point and the line defined by two points
     * @param linePt1 first point of the line
     * @param linePt2 second point of the line
     * @param pt point
     * @return distance between point and line
     */
    public static double distanceTo(double[] linePt1, double[] linePt2, double[] pt) {
        GeometryVector intersect = new GeometryVector(intersection(linePt1, linePt2, pt));
        return distanceTo(intersect.vector, pt);
    }

    /**
     * http://www.cgafaq.info/wiki/Random_Point_In_Triangle
     * http://www.devmaster.net/forums/showthread.php?t=10469
     * return a uniform random point in the triagle
     * @param pt0 first point of triangle
     * @param pt1 second point of triangle
     * @param pt2 thirth point of triangle
     * @return uniform random point in triangle pto-pt1-pt2
     */
    public static double[] uniformRandomPointInTriangle(double[] pt0, double[] pt1, double[] pt2) {
        double [] rnd = new double[ pt0.length] ;
        double b0 = Math.random();
        double b1 = (1.0 - b0)*Math.random();
        double b2 = 1.0 - b0 - b1;
        for (int i = 0; i < rnd.length; i++) {
            rnd[i] =pt0[i]*b0  + pt1[i]*b1 + pt2[i]*b2;
        }
        return rnd ;
    }



    public static void main(String[] args) {
        double[] v1 = {1, -1, 1};
        double[] v2 = {1, 0, 1};
        double[] v3 = {1, 1, 2};


        GeometryVector dv1 = new GeometryVector(v1);
        GeometryVector dv2 = new GeometryVector(v2);
        GeometryVector dv3 = new GeometryVector(v3);

        GeometryVector[] basis = {dv1, dv2, dv3};
        GeometryVector[] ort = GeometryVector.orthonormalize(basis);
        for (int i = 0; i < ort.length; i++) {
            System.out.println(ort[i]);

        }

        double[] i1 = {4, -2, -1, -.3, 8, 9, -20, 45, .25, 36, -45, -86, .14};
        double[] i2 = {-.4, 2, -1, -3, 8, 9, -20, 45, 25, -36, -.45, 86, 14};
        GeometryVector vi1 = new GeometryVector(i1);
        GeometryVector vi2 = new GeometryVector(i1);
        GeometryVector[] b = {vi1, vi2};

        GeometryVector[] vo = GeometryVector.orthonormalize(b);
        System.out.println("VO = ");
        for (int i = 0; i < vo.length; i++) {
            System.out.println(vo[i]);

        }

        System.out.println("INTERSECTION");
        double[] p1 = {0, 0, 0};
        double[] p2 = {1, 1, 0};
        double[] p3 = {0.25, 0.25, 1};
        double[] pi = intersection(p1, p2, p3);
        GeometryVector vpi = new GeometryVector(pi);
        System.out.println("pi " + vpi);

        System.out.println("Distance " + distanceTo(pi, p3));



    }
}
