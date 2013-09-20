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
package utils.geometry;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * http://www.ecole-art-aix.fr/IMG/pde/Intersect.pde
 * workshop.evolutionzone.com/2007/09/10/code-2d-line-intersection/
 *
 * @author ZULU
 */
public class Test {

    int DONT_INTERSECT = 0;
    int COLLINEAR = 1;
    int DO_INTERSECT = 2;
    double x = 0, y = 0;

    public static boolean intersects(Point2D p1, Point2D p2, Point2D p3, Point2D p4) {
        return intersectionPoint(new Line2D.Double(p1, p2), new Line2D.Double(p3, p4)) != null;
    }

    public static boolean intersects(Line2D l1, Line2D l2) {
        return intersectionPoint(l1, l2) != null;
    }

    public static Point2D intersectionPoint(Point2D p1, Point2D p2, Point2D p3, Point2D p4) {
        return intersectionPoint(new Line2D.Double(p1, p2), new Line2D.Double(p3, p4));
    }

    public static Point2D intersectionPoint(Line2D l1, Line2D l2) {
        //workshop.evolutionzone.com/2007/09/10/code-2d-line-intersection/
        //http://www.ecole-art-aix.fr/IMG/pde/Intersect.pde
        double x1 = l1.getX1();
        double y1 = l1.getY1();
        double x2 = l1.getX2();
        double y2 = l1.getY2();
        double x3 = l2.getX1();
        double y3 = l2.getY1();
        double x4 = l2.getX2();
        double y4 = l2.getY2();
        double a1, a2, b1, b2, c1, c2;
        double r1, r2, r3, r4;
        double denom;
        // Compute a1, b1, c1, where line joining points 1 and 2
        // is "a1 x + b1 y + c1 = 0".
        a1 = y2 - y1;
        b1 = x1 - x2;
        c1 = (x2 * y1) - (x1 * y2);

        // Compute r3 and r4.
        r3 = ((a1 * x3) + (b1 * y3) + c1);
        r4 = ((a1 * x4) + (b1 * y4) + c1);

        // Check signs of r3 and r4. If both point 3 and point 4 lie on
        // same side of line 1, the line segments do not intersectionPoint.
        if ((r3 != 0) && (r4 != 0) && same_sign(r3, r4)) {
            //DONT_INTERSECT;
            return null;
        }
        // Compute a2, b2, c2
        a2 = y4 - y3;
        b2 = x3 - x4;
        c2 = (x4 * y3) - (x3 * y4);
        // Compute r1 and r2
        r1 = (a2 * x1) + (b2 * y1) + c2;
        r2 = (a2 * x2) + (b2 * y2) + c2;

        // Check signs of r1 and r2. If both point 1 and point 2 lie
        // on same side of second line segment, the line segments do
        // not intersectionPoint.
        if ((r1 != 0) && (r2 != 0) && (same_sign(r1, r2))) {
            //DONT_INTERSECT;
            return null;
        }
        //Line segments intersectionPoint: compute intersection point.
        denom = (a1 * b2) - (a2 * b1);
        if (denom == 0) {
            // COLLINEAR
            return null;
        }
        //calculate intersection point
        double x = ((b1 * c2) - (b2 * c1)) / denom;
        double y = ((a2 * c1) - (a1 * c2)) / denom;
        // lines_intersect
        return new Point2D.Double( x, y);
    }

    int intersect(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        double a1, a2, b1, b2, c1, c2;
        double r1, r2, r3, r4;
        double denom, offset, num;
        // Compute a1, b1, c1, where line joining points 1 and 2
        // is "a1 x + b1 y + c1 = 0".
        a1 = y2 - y1;
        b1 = x1 - x2;
        c1 = (x2 * y1) - (x1 * y2);

        // Compute r3 and r4.
        r3 = ((a1 * x3) + (b1 * y3) + c1);
        r4 = ((a1 * x4) + (b1 * y4) + c1);

        // Check signs of r3 and r4. If both point 3 and point 4 lie on
        // same side of line 1, the line segments do not intersectionPoint.
        if ((r3 != 0) && (r4 != 0) && same_sign(r3, r4)) {
            return DONT_INTERSECT;
        }

        // Compute a2, b2, c2
        a2 = y4 - y3;
        b2 = x3 - x4;
        c2 = (x4 * y3) - (x3 * y4);

        // Compute r1 and r2
        r1 = (a2 * x1) + (b2 * y1) + c2;
        r2 = (a2 * x2) + (b2 * y2) + c2;

        // Check signs of r1 and r2. If both point 1 and point 2 lie
        // on same side of second line segment, the line segments do
        // not intersectionPoint.
        if ((r1 != 0) && (r2 != 0) && (same_sign(r1, r2))) {
            return DONT_INTERSECT;
        }

        //Line segments intersectionPoint: compute intersection point.
        denom = (a1 * b2) - (a2 * b1);

        if (denom == 0) {
            return COLLINEAR;
        }

        if (denom < 0) {
            offset = -denom / 2;
        } else {
            offset = denom / 2;
        }

        // The denom/2 is to get rounding instead of truncating. It
        // is added or subtracted to the numerator, depending upon the
        // sign of the numerator.
        num = (b1 * c2) - (b2 * c1);
        if (num < 0) {
            x = (num - offset) / denom;
        } else {
            x = (num + offset) / denom;
        }

        num = (a2 * c1) - (a1 * c2);
        if (num < 0) {
            y = (num - offset) / denom;
        } else {
            y = (num + offset) / denom;
        }

        // lines_intersect
        return DO_INTERSECT;
    }

    private static boolean same_sign(double a, double b) {
        return ((a * b) >= 0);
    }

    public static void main(String[] args) {
        double x1 = 2;
        double y1 = 3;
        double x2 = 3;
        double y2 = 2;
        Line2D l1 = new Line2D.Double(x1, y1, x2, y2);
        double x3 = 0;
        double y3 = 0;
        double x4 = 2;
        double y4 = 4;
        
        for (x4 = 0; x4 <10; x4+=0.1) {
            Point2D p = new Point2D.Double(x4, y4);
            Line2D l2 = new Line2D.Double(x3, y3, x4, y4);
            System.out.println(p +  " Intersect " + intersectionPoint(l1, l2));
        }        
}
}
