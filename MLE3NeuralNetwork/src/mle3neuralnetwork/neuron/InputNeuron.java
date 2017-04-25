package mle3neuralnetwork.neuron;

/**
 * @author Link
 */
public class InputNeuron implements Neuron {

    private double value;

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public double getValue() {
        return value;
    }

}
