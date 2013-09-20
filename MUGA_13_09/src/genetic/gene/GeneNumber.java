/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.gene;

/**
 *
 * @author arm
 */
public abstract class GeneNumber extends Gene {

    /**
     *  [minValue . . .maxValue]
     */
    protected double minValue;
    /**
     * [minValue . . .maxValue]
     */
    protected double maxValue;

    /**
     * gets minimumvalue
     * @return min
     */
    public double getMinValue() {
        return this.minValue;
    }

    /**
     * gets maximum value
     * @return max
     */
    public double getMaxValue() {
        return this.maxValue;
    }


    public double getDimension(){
        return maxValue - minValue;
    }

    /**
     * apply mutation in every bit of alels with that probability
     * @param probability value o probability to invert each bit
     */
    public void mutateGaussian(double standardDeviation) {
        double noise = randGenerator.nextGaussian() * standardDeviation;
        double value = this.getValue() + noise;
        this.setValue(value);
    }

//    /**
//     * atribui aleatoriamente valores aos alelos
//     */
//    @Override
//    public void fillRandom() {
//        double val = minValue + randGenerator.nextDouble() * (maxValue - minValue);
//        this.setValue(val);
//    }

    public int compareTo(Gene other) {
        double v1 = this.getValue();
        double v2 = other.getValue();
        if (v1 > v2) {
            return 1;

        }
        if (v1 < v2) {
            return -1;
            
        }
        return 0;
    }

    /**
     *  Indicates whether some other object is "equal to" this one.
     * @param obj object
     * @return equals
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof GeneNumber) {
            final GeneNumber other = (GeneNumber) obj;
            return this.getValue() == other.getValue();
        } else {
            return super.equals(obj);

        }
    }
//===================================================================================
//===================================================================================
//===================================================================================
//===================================================================================
//-----------------------------------------------------------------------------------
//               N O R M A L I Z A T I O N   O F   V A L U E S
//
//  values between [0..1]  => [minValue ... maxValue]
//-----------------------------------------------------------------------------------

    /**
     * return the value in the intver [0 - 1]
     * @return value normalized to 0..1
     */
    public double getNormalValue() {
        return (getValue() - minValue) / (maxValue - minValue);
    }
    /**
     * Sets the value normalized in 0..1 to the interval min..max
     * @param x normalized value
     */
    public void setNormalValue(double x) {
        setValue(x * (maxValue - minValue) + minValue);
    }
//===================================================================================
//===================================================================================
//===================================================================================
//===================================================================================
//-----------------------------------------------------------------------------------
//                        D I S C R E T I Z A T I O N
//-----------------------------------------------------------------------------------
    public void setValue(double value , long intervals)
    {
        //size of intervals
        double step = (maxValue - minValue) / intervals;
        //discretize
        value -= step/2.0;
        value = (long)(( value+minValue)/ step) * step -minValue;
        //set the value
        setValue(value);
    }
//===================================================================================
    public void discretize(long intervals)
    {
        setValue(getValue(), intervals);
    }
//===================================================================================
//===================================================================================

}
