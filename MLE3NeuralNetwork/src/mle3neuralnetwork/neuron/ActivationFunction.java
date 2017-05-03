package mle3neuralnetwork.neuron;

/**
 * @author Link
 */
public interface ActivationFunction {

    double activate(double x);

    public static ActivationFunction SIGMOID = x -> {
        double y = 1.0 / (1.0 + Math.exp(-x));
        return y < 0.001 ? 0.001 : y > 0.999 ? 0.999 : y;
    };

    public static ActivationFunction STEP = x -> (x <= 0) ? 0 : 1;

    public static ActivationFunction LINEAR = x -> x;

}
