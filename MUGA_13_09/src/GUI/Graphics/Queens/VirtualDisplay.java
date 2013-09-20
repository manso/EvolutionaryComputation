/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Graphics.Queens;

/**
 *
 * @author manso
 */
public class VirtualDisplay {

    public VirtualDisplay(int maxX, int maxY) {
        this(0,maxX,0,maxY);
    }
    public VirtualDisplay(int Xmin, int xMax,int Ymin, int Ymax) {
        this.virtualXmin=Xmin;
        this.virtualXmax = xMax;
        this.virtualYmin = Ymin;
        this.virtualYmax = Ymax;
        
        this.realXmin = 0;
        this.realXmax =1;
        this.realYmin=0;
        this.realYmax=1;
    }
    
    
       
    /**
     * <B>Real world </B>- rectangle defined by <br>
     * [realYmin,realXmin] - [realYmax,realXmax]<br>
     */
    public double realXmin=0.0;
    /**
     * <B>Real world </B>- rectangle defined by <br>
     * [realYmin,realXmin] - [realYmax,realXmax]<br>
     */
    public double realXmax=-1.0;
    /**
     * <B>Real world </B>- rectangle defined by <br>
     * [realYmin,realXmin] - [realYmax,realXmax]<br>
     */
    public double realYmin=0.0;
    /**
     * <B>Real world </B>- rectangle defined by <br>
     * [realYmin,realXmin] - [realYmax,realXmax]<br>
     */
    public double realYmax=-1.0;
    
    /**
     * <B>Virtual world</B> - rectangle defined by <br>
     * [virtualYmin,virtualXmin] - [virtualYmax,virtualXmax]<br>
     */
    public int virtualXmin=10;
    /**
     * <B>Virtual world</B> - rectangle defined by <br>
     * [virtualYmin,virtualXmin] - [virtualYmax,virtualXmax]<br>
     */
    public int virtualXmax=10;
    /**
     * <B>Virtual world</B> - rectangle defined by <br>
     * [virtualYmin,virtualXmin] - [virtualYmax,virtualXmax]<br>
     */
    public int virtualYmin=100;
    /**
     * <B>Virtual world</B> - rectangle defined by <br>
     * [virtualYmin,virtualXmin] - [virtualYmax,virtualXmax]<br>
     */
    public int virtualYmax=100;
    
   private int scaleX;
   private int scaleY;
     
    
    
    /**
     * Convert a real the value of coordinate X of real world <br>
     * to integer value in virtual world
     * @param real coordinate X of real world
     * @return coordinate X of virtual world
     */
   public int getXVirtual(double real){
        double stepX = (virtualXmax - virtualXmin)/( realXmax- realXmin);
        return  virtualXmin + (int)((real -realXmin) * stepX);
    }
    
    /**
     * Convert a real the value of coordinate Y of real world <br>
     * to integer value in virtual world
     * @param real coordina Y in real world
     * @return coordinate Y in virtual world
     */
    public int getYVirtual(double real){
        double stepY = (virtualYmax - virtualYmin) /( realYmax- realYmin);
        return  virtualYmax -  (int)((real -realYmin) * stepY);
    }
    
    /**
     * normalize the scale of X axis<br>
     * i.e. calculate the best number for the scale
     */
    public void NormalizeXScale(){
        double tam = realXmax - realXmin;
        scaleX=1;
        while( tam > 10) {
            tam /=10;
            scaleX*=10;
        }        
        realXmax = java.lang.Math.floor(realXmax/scaleX + 1)* scaleX;
        realXmin = java.lang.Math.ceil(realXmin/scaleX  - 1)* scaleX;        
    }
    
    /**
     * normalize the scale of X axis<br>
     * i.e. calculate the best number for the scale
     */
     public void NormalizeYScale(){
        double tam = realYmax - realYmin ;
        scaleY=1;
        while( tam > 10) {
            tam /= 10;
            scaleY *= 10;
        }
       
        realYmax = java.lang.Math.floor(realYmax/scaleY + 1)* scaleY;
        realYmin = java.lang.Math.ceil(realYmin/scaleY  - 1) * scaleY;
        
        
    }

    public int getVirtualWidth() {
        return virtualXmax-virtualXmin;
    }
    public int getVirtualHeight() {
        return virtualYmax-virtualYmin;
    }
    
}