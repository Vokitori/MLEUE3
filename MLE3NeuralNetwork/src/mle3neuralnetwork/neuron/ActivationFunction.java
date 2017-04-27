package mle3neuralnetwork.neuron;

/**
 * @author Link
 */
public interface ActivationFunction {

    double activate(double x);

    public static ActivationFunction SIGMOID
            = x -> 1.0 / (1.0 + Math.pow(Math.E, -x));

    public static ActivationFunction STEP
            = x -> (x <= 0) ? 0 : 1;
    
    public static ActivationFunction LINEAR
            = x -> x;

}
