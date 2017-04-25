package mle3neuralnetwork.neuron;

import mle3neuralnetwork.Network;

/**
 * @author Voki
 */
public class Synapse {

    public final Neuron previous;
    public final Neuron next;
    public double weight;

    public Synapse(Neuron previous, Neuron next) {
        this.previous = previous;
        this.next = next;
        weight = Network.getNewWeight();
    }
}
