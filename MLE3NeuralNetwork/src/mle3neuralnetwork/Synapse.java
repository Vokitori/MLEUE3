package mle3neuralnetwork;

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
    }
}
