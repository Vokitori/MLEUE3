package mle3neuralnetwork.neuron;

import java.util.*;
import mle3neuralnetwork.Network;

/**
 * @author Link
 */
public class Layer {

    public final Neuron[] neurons;
    private final ArrayList<Neuron> list = new ArrayList<>();

    public Layer(int neuronCount) {
        this.neurons = new Neuron[neuronCount];
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new Neuron();
            list.add(neurons[i]);
        }
    }

    public void applyParallel(Network network, NeuronFunction function, Object parameter) {
        list.parallelStream().forEach((neuron) -> {
            function.apply(network, neuron, parameter);
        });
    }
    
    public void apply(Network network, NeuronFunction function, Object parameter) {
        for (Neuron neuron : neurons) {
            function.apply(network, neuron, parameter);
        }
    }
}
