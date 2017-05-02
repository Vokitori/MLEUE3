package mle3neuralnetwork.neuron;

import java.util.ArrayList;
import mle3neuralnetwork.Network;

/**
 * @author Link
 */
public class InputNeuron implements Neuron, NeuronWithFrontSynapse {

    private double value;

    private final Network network;
    private final ArrayList<Synapse> nextSynapseList = new ArrayList<>();

    public InputNeuron(Network network) {
        this.network = network;
    }

    @Override
    public void adjustWeights() {
        for (Synapse s : nextSynapseList) {
            double dw = network.learningRate * s.next.getError() * value;
            s.weight += dw + network.momentum * s.weightChange;
            s.weightChange = dw;
        }
    }

    public void connectWithNext(NeuronWithBackSynapse next) {
        Synapse.createSynapse(this, next);
    }

    @Override
    public void receiveSynapsisFromNext(Synapse next) {
        nextSynapseList.add(next);
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public double getValue() {
        return value;
    }

}
