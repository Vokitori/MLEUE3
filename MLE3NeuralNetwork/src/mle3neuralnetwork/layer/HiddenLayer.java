package mle3neuralnetwork.layer;

import mle3neuralnetwork.Network;
import mle3neuralnetwork.neuron.HiddenNeuron;
import mle3neuralnetwork.neuron.NeuronWithBackSynapse;
import mle3neuralnetwork.neuron.NeuronWithFrontSynapse;

/**
 * @author Voki
 */
public class HiddenLayer extends Layer {

    private final HiddenNeuron[] neurons;
    private final Network network;

    public HiddenLayer(Network network, int neuronCount) {
        this.network = network;
        this.neurons = new HiddenNeuron[neuronCount];
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new HiddenNeuron(network);
        }
    }

    public void connectWithNeurons(NeuronWithBackSynapse[] otherNeurons) {
        for (int i = 0; i < neurons.length; i++) {
            HiddenNeuron neuron = neurons[i];
            for (int j = 0; j < otherNeurons.length; j++) {
                neuron.connectWithNext(otherNeurons[j]);
            }
        }
    }

    public void connectWithNeurons(NeuronWithFrontSynapse[] otherNeurons) {
        for (int i = 0; i < neurons.length; i++) {
            HiddenNeuron neuron = neurons[i];
            for (int j = 0; j < otherNeurons.length; j++) {
                neuron.connectWithPrevious(otherNeurons[j]);
            }
        }
    }

    public void printAll() {
        for (HiddenNeuron neuron : neurons) {
            System.out.print("\t" + neuron.getValue());
        }
    }

    @Override
    public void updateNeurons() {
        for (HiddenNeuron neuron : neurons) {
            neuron.calculateValue();
        }
    }

    public void calculateErrors() {
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].calculateError();
        }
    }

    public void adjustWeights() {
        for (HiddenNeuron neuron : neurons) {
            neuron.adjustWeights();
        }
    }

    public void adjustBias() {
        for (HiddenNeuron neuron : neurons) {
            neuron.adjustBias();
        }
    }
}
