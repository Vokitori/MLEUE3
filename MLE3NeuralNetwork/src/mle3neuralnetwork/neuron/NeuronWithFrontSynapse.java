package mle3neuralnetwork.neuron;

/**
 * @author Link
 */
public interface NeuronWithFrontSynapse extends Neuron {

    void receiveSynapsisFromNext(Synapse next);

    void adjustWeights();
}
