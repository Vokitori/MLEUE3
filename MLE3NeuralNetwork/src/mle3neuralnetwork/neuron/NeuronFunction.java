package mle3neuralnetwork.neuron;

import mle3neuralnetwork.*;

/**
 * @author Link
 */
public interface NeuronFunction {

    void apply(Network network, Neuron neuron, Object parameter);

}
