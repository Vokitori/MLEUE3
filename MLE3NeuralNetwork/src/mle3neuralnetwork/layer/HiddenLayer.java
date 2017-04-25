package mle3neuralnetwork.layer;

import java.util.ArrayList;
import mle3neuralnetwork.neuron.InputNeuron;
import mle3neuralnetwork.neuron.NormalNeuron;

/**
 * @author Voki
 */
public class HiddenLayer extends Layer {

    private final NormalNeuron[] neurons;

    public HiddenLayer(int neuronCount) {
        this.neurons = new NormalNeuron[neuronCount];
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
