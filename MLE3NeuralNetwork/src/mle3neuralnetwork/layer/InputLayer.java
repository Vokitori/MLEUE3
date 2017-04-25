package mle3neuralnetwork.layer;

import java.util.ArrayList;
import mle3neuralnetwork.Network;
import mle3neuralnetwork.neuron.InputNeuron;

/**
 * @author Voki
 */
public class InputLayer extends Layer {

    private final InputNeuron[] neurons;
    private int[] newNeuronValues;

    public InputLayer() {
        this.neurons = new InputNeuron[Network.INPUT_NEURON_COUNT];
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new InputNeuron();
        }
    }

    @Override
    public void updateNeurons() {
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].setValue(newNeuronValues[i]);
        }
    }

    public void setNewNeuronValues(int[] newNeuronValues) {
        this.newNeuronValues = newNeuronValues;
    }

}
