package mle3neuralnetwork.neuron;

import mle3neuralnetwork.Network;

/**
 * @author Voki
 */
public class Synapse {

    public double weight = Network.generateRandomWeight();
    public double weightChange = 0;
    public final Neuron left;
    public final Neuron right;

    private Synapse(Neuron left, Neuron right) {
        this.left = left;
        this.right = right;
    }

    public static void connectNeurons(Neuron left, Neuron right) {
        Synapse synapse = new Synapse(left, right);
        left.rightSynapses.add(synapse);
        left.rightNeurons.add(right);

        right.leftSynapses.add(synapse);
        right.leftNeurons.add(left);

    }
}
