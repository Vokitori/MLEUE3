package mle3neuralnetwork.layer;

import mle3neuralnetwork.Network;
import mle3neuralnetwork.neuron.NormalNeuron;

/**
 * @author Voki
 */
public class OutputLayer extends Layer {

    private final NormalNeuron[] neurons;

    public OutputLayer() {
        this.neurons = new NormalNeuron[Network.OUTPUT_NEURON_COUNT];
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new NormalNeuron();
        }
    }

    @Override
    public void updateNeurons() {
        for (NormalNeuron neuron : neurons) {
            neuron.calculateValue();
        }
    }

}
