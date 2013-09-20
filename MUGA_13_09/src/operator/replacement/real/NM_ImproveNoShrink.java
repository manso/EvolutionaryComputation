/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.replacement.real;

import utils.NelderMead.NelderMeadOriginalNoShrink;

/**
 *
 * @author manso
 */
public class NM_ImproveNoShrink extends NM_Improve{

    public NM_ImproveNoShrink() {
        simplex = new NelderMeadOriginalNoShrink();
    }
    
}
