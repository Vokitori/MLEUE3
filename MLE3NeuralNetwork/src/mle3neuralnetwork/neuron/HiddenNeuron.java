package mle3neuralnetwork.neuron;

import mle3neuralnetwork.neuron.Synapse;
import java.util.ArrayList;
import java.util.function.Consumer;
import mle3neuralnetwork.Network;

/**
 * @author Voki
 */
public class HiddenNeuron implements Neuron, NeuronWithBackSynapse, NeuronWithFrontSynapse {

    private double biasWeight = 1;
    private double value;

    private double error;

    private final Network network;

    private final ArrayList<Synapse> previousSynapseList = new ArrayList<>();
    private final ArrayList<Synapse> nextSynapseList = new ArrayList<>();

    private static final ActivationFunction activationFunction = ActivationFunction.STEP;

    public HiddenNeuron(Network network) {
        this.network = network;
        biasWeight = Network.getNewWeight();
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

    public void calculateError() {
        double sum = 0.0;
        for (Synapse s : nextSynapseList) {
            sum += s.next.getError() * s.weight;
        }
        error = sum * value * (1.0 - value);
    }

    @Override
    public void adjustWeights() {
        for (Synapse s : nextSynapseList) {
            double dw = network.learningRate * s.next.getError() * value;
            s.weight += dw + network.momentum * s.weightChange;
            s.weightChange = dw;
        }
    }

    @Override
    public void adjustBias() {
        biasWeight += network.learningRate * error;
    }

    public void connectWithPrevious(NeuronWithFrontSynapse previous) {
        Synapse.createSynapse(previous, this);
    }

    @Override
    public void receiveSynapseFromPrevious(Synapse previous) {
        previousSynapseList.add(previous);
    }

    public void connectWithNext(NeuronWithBackSynapse next) {
        Synapse.createSynapse(this, next);
    }

    @Override
    public void receiveSynapsisFromNext(Synapse next) {
        nextSynapseList.add(next);
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
