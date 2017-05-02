package mle3neuralnetwork.neuron;

import java.util.ArrayList;
import mle3neuralnetwork.Network;

/**
 * @author Link
 */
public class OutputNeuron implements Neuron, NeuronWithBackSynapse {

    private double biasWeight = 1;
    private double value;

    private double error;

    private final Network network;
    private final ArrayList<Synapse> previousSynapseList = new ArrayList<>();

    private static final ActivationFunction activationFunction = ActivationFunction.STEP;

    public OutputNeuron(Network network) {
        this.network = network;
    }

    public void calculateValue() {
        value = activationFunction.activate(getSum() + biasWeight);
    }

    private double getSum() {
        double sum = 0;
        for (int i = 0; i < previousSynapseList.size(); i++) {
            sum += previousSynapseList.get(i).weight
                    * previousSynapseList.get(i).previous.getValue();
        }
        return sum;
    }

    @Override
    public void adjustBias() {
        biasWeight += activationFunction.activate(network.learningRate * error);
    }

    public void connectWithPrevious(NeuronWithFrontSynapse previous) {
        Synapse.createSynapse(previous, this);

    }

    public void calculateError(double correctValue) {
        error = (correctValue - value) * value * (1.0 - value);
    }

    @Override
    public void receiveSynapseFromPrevious(Synapse previous) {
        previousSynapseList.add(previous);
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public double getError() {
        return error;
    }
}
