package mle3neuralnetwork.neuron;

import mle3neuralnetwork.Network;

/**
 * @author Voki
 */
public class Synapse {

    public final NeuronWithFrontSynapse previous;
    public final NeuronWithBackSynapse next;
    public double weight;
    public double weightChange;
    
    private Synapse(NeuronWithFrontSynapse previous, NeuronWithBackSynapse next) {
        this.previous = previous;
        this.next = next;
        previous.receiveSynapsisFromNext(this);
        next.receiveSynapseFromPrevious(this);
        weight = Network.getNewWeight();
    }
    
    public static void createSynapse(NeuronWithFrontSynapse previous, NeuronWithBackSynapse next){
        new Synapse(previous, next);
    }
}
