package mle3neuralnetwork.layer;

import mle3neuralnetwork.Network;
import mle3neuralnetwork.neuron.OutputNeuron;

/**
 * @author Voki
 */
public class OutputLayer extends Layer {

    private final OutputNeuron[] neurons;
    private double[] correctValues;
    private final Network network;

    public OutputLayer(Network network) {
        this.network = network;
        this.neurons = new OutputNeuron[network.outputLayerNeuronCount];
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new OutputNeuron(network);
        }
    }
    
    public OutputNeuron[] getNeurons(){
        return neurons;
    }

    public void setCorrectValues(double[] values) {
        this.correctValues = values;
    }

    @Override
    public void updateNeurons() {
        for (OutputNeuron neuron : neurons) {
            neuron.calculateValue();
        }
    }

    public void adjustBias() {
        for (OutputNeuron neuron : neurons) {
            neuron.adjustBias();
        }
    }

    public double calculateNetworkError() {
        double error = 0.0;
        for (int i = 0; i < neurons.length; i++) {
            error += Math.pow(neurons[i].getValue() - correctValues[i], 2);
        }
        error = error / neurons.length;
        return error;
    }

    public int expectedNumber() {
        double max = 0;
        int maxNeuron = -1;
        for (int i = 0; i < neurons.length; i++) {
            if (max < neurons[i].getValue() || maxNeuron == -1) {
                maxNeuron = i;
                max = neurons[i].getValue();
            }
        }
        return maxNeuron;
    }

    public void calculateErrors() {
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].calculateError(correctValues[i]);
        }
    }

}
