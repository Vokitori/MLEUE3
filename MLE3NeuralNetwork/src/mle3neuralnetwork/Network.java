package mle3neuralnetwork;

/**
 * @author Link
 */
public class Network {

    public static final double LEARNING_RATE = 0.2;
    public static final double MOMENTUM = 0.9;
    public static final double ALLOWED_ERROR_MARGIN = 0.005;
    public static final int INPUT_NEURON_COUNT = 784;
    public static final int OUTPUT_NEURON_COUNT = 10;
    // Hidden Neurons = 89

    public Network() {

    }

    public static double getNewWeight() {
        return 2 * Math.random() - 1;
    }

}
