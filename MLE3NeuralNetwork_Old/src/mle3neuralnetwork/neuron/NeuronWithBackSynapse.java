package mle3neuralnetwork.neuron;

/**
 * @author Link
 */
public interface NeuronWithBackSynapse extends Neuron {

    void receiveSynapseFromPrevious(Synapse previous);

    double getError();

    void adjustBias();

}
