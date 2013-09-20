/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Graphics;

import java.awt.Graphics;

/**
 *
 * @author manso
 */
public class DisplayQueens extends DisplayPopulation {

//    int stepX;
//    int stepY;
//
//    @Override
    public void showFunc(Graphics gr) {
//
//        int cx = this.getWidth() / 2;
//        int cy = this.getHeight() / 2;
//        int d = Math.min(cy, cx) / 3;
//        Iterator<Individual> it = pop.getSortedIterable().iterator();
//        gr.setColor(Color.BLACK);
//        gr.fillRect(0, 0, this.getWidth(), this.getHeight());
//        VirtualDisplay w1 = new VirtualDisplay(cx - 3 * d, cx + d, cy - 3 * d, cy + d);
//        DrawQueen(gr, w1, (Queens) it.next());
//
//        VirtualDisplay w2 = new VirtualDisplay(cx - 3 * d, cx - d, cy + d, cy + 3 * d);
//        DrawQueen(gr, w2, (Queens) it.next());
//
//        VirtualDisplay w3 = new VirtualDisplay(cx - d, cx + d, cy + d, cy + 3 * d);
//        DrawQueen(gr, w3, (Queens) it.next());
//
//
//        VirtualDisplay w4 = new VirtualDisplay(cx + d, cx + 3 * d, cy + d, cy + 3 * d);
//        DrawQueen(gr, w4, (Queens) it.next());
//
//        VirtualDisplay w5 = new VirtualDisplay(cx + d, cx + 3 * d, cy - d, cy + d);
//        DrawQueen(gr, w5, (Queens) it.next());
//
//        VirtualDisplay w6 = new VirtualDisplay(cx + d, cx + 3 * d, cy - 3 * d, cy - d);
//        DrawQueen(gr, w6, (Queens) it.next());
//
////        VirtualDisplay w4 = new VirtualDisplay(cx + d , cx +3*d, cy + d, cy + 3*d);
////        DrawQueen(gr, w4, (Queens)it.next());
//
//
    }
//
//    protected void DrawQueen(Graphics gr, VirtualDisplay window, Queens q) {
//        stepX = (window.virtualXmax - window.virtualXmin) / (q.NUMBER_OF_QUEEN + 1);
//        stepY = (window.virtualYmax - window.virtualYmin) / (q.NUMBER_OF_QUEEN + 1);
//
//
//        gr.setColor(Color.lightGray);
//        gr.fillRoundRect(window.virtualXmin, window.virtualYmin,
//                window.getVirtualWidth(), window.getVirtualHeight(), stepX, stepY);
//        gr.setColor(Color.BLACK);
//        gr.drawRoundRect(window.virtualXmin, window.virtualYmin,
//                window.getVirtualWidth(), window.getVirtualHeight(), stepX, stepY);
//        //factor de encolhimento para as queens
//        int SHRINK = 0;
//
//        window.virtualXmin += stepX / 2;
//        window.virtualXmax -= stepX / 2;
//        window.virtualYmin += stepY / 2;
//        window.virtualYmax -= stepY / 2;
//        if (stepX < 15 || stepY < 15) {
//            SHRINK = -2;
//        }
//        if (stepX > 20 || stepY > 20) {
//            SHRINK = 5;
//        }
//        DrawBoard(gr, window, q.NUMBER_OF_QUEEN);
//        for (int x = 0; x < q.NUMBER_OF_QUEEN; x++) {
//            int y = (int) q.getGene(x).getValue();
//            if (q.getAtaks(x) > 0) {
//                gr.setColor(Color.RED);
//            } else {
//                gr.setColor(Color.GREEN);
//            }
//
//            gr.fillOval(window.virtualXmin + x * stepX + SHRINK,
//                    window.virtualYmin + y * stepY + SHRINK,
//                    stepX - 2 * SHRINK, stepY - 2 * SHRINK);
//
//        }
//    }
//
//    private void DrawBoard(Graphics gr, VirtualDisplay window, int dim) {
//        int square = 0;
//        for (int x = 0; x < dim; x++) {
//            square++;
//            for (int y = 0; y < dim; y++) {
//                square++;
//                if (square % 2 == 0) {
//                    gr.setColor(Color.DARK_GRAY);
//                } else {
//                    gr.setColor(Color.WHITE);
//                }
//
//                gr.fillRect(window.virtualXmin + x * stepX, window.virtualYmin + y * stepY, stepX, stepY);
//            }
//        }
//        gr.draw3DRect(window.virtualXmin, window.virtualYmin,
//                stepX * dim, stepY * dim, true);
//    }
}
