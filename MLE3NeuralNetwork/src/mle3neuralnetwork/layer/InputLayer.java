package mle3neuralnetwork.layer;

import mle3neuralnetwork.InputData;
import mle3neuralnetwork.Network;
import mle3neuralnetwork.neuron.InputNeuron;

/**
 * @author Voki
 */
public class InputLayer extends Layer {

    private final InputNeuron[] neurons;
    private final Network network;
    private InputData data;

    public InputLayer(Network network) {
        this.network = network;
        this.neurons = new InputNeuron[this.network.inputLayerNeutronCount];
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new InputNeuron(network);
        }
    }

    public InputNeuron[] getNeurons() {
        return neurons;
    }

    public void adjustWeights() {
        for (InputNeuron neuron : neurons) {
            neuron.adjustWeights();
        }
    }

    public void setInputData(InputData data) {
        this.data = data;
    }

    @Override
    public void updateNeurons() {
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].setValue(network.getInputData().getData(i));
        }
    }
}
