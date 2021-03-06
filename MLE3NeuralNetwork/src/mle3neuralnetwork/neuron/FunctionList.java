package mle3neuralnetwork.neuron;

import java.util.*;
import mle3neuralnetwork.Network;

/**
 * @author Link
 */
public class FunctionList {

    private FunctionList() {
    }

    public static final NeuronFunction PRINT_VALUE_FUNCTION = (Network network, Neuron neuron, Object parameter) -> {
        System.out.print(neuron.getValue() + "\t");
    };

    public static final NeuronFunction PRINT_LEFT_SYNAPSES_FUNCTION = (Network network, Neuron neuron, Object parameter) -> {
        for (Synapse s : neuron.leftSynapses) {
            System.out.print(s.weight + "\t");
        }
        System.out.println("");
    };

    public static final NeuronFunction CONNECT_TO_RIGHT_FUNCTION = (Network network, Neuron neuron, Object parameter) -> {
        for (Neuron right : ((Layer) parameter).neurons) {
            Synapse.connectNeurons(neuron, right);
        }
    };

    public static final NeuronFunction SET_VALUE_FUNCTION = (Network network, Neuron neuron, Object parameter) -> {
        neuron.value = ((SetValueParameter) parameter).next();
    };

    public static final NeuronFunction CALCULATE_VALUE_FUNCTION = (Network network, Neuron neuron, Object parameter) -> {
        double sum = neuron.getLeftWeightSum();
        neuron.value = ((ActivationFunction) parameter).activate(sum + neuron.biasWeight);
    };

    public static final NeuronFunction ADJUST_WEIGHT_FUNCTION = (Network network, Neuron neuron, Object parameter) -> {
        for (int i = 0; i < neuron.rightNeurons.size(); i++) {
            Neuron rightNeuron = neuron.rightNeurons.get(i);
            Synapse rightSynapse = neuron.rightSynapses.get(i);

            double dw = network.learningRate * rightNeuron.error * neuron.value; 
            rightSynapse.weight += dw + network.momentum * rightSynapse.weightChange;
            rightSynapse.weightChange = dw;
        }
    };

    public static final NeuronFunction ADJUST_BIAS_FUNCTION = (Network network, Neuron neuron, Object parameter) -> {
        neuron.biasWeight += network.learningRate * neuron.error;
    };

    
    public static final NeuronFunction CALCULATE_OUTPUT_ERROR_FUNCTION = (Network network, Neuron neuron, Object parameter) -> {
        neuron.error = (((Iterator<Double>) parameter).next() - neuron.value) * neuron.value * (1.0 - neuron.value);/////////////
    };

    public static final NeuronFunction CALCULATE_HIDDEN_ERROR_FUNCTION = (Network network, Neuron neuron, Object parameter) -> {
        double sum = 0.0;
        for (int i = 0; i < neuron.rightNeurons.size(); i++) {
            Neuron rightNeuron = neuron.rightNeurons.get(i);
            Synapse rightSynapse = neuron.rightSynapses.get(i);
            sum += rightNeuron.error * rightSynapse.weight;
        }
        neuron.error = sum * neuron.value * (1.0 - neuron.value);//////////////
    };

    
    public static final NeuronFunction CALCULATE_MEAN_SQUARE_ERROR_FUNCTION = (Network network, Neuron neuron, Object parameter) -> {
        CalculateMeanSquareErrorParameter p = (CalculateMeanSquareErrorParameter) parameter;
        p.errorSum += Math.pow(neuron.value - p.next(), 2);
    };

    public static final NeuronFunction GET_GUESSED_DIGIT_FUNCTION = (Network network, Neuron neuron, Object parameter) -> {
        GetGuessedDigitParameter gdc = (GetGuessedDigitParameter) parameter;
        if (gdc.max < neuron.getValue() || gdc.guess == -1) {
            gdc.guess = gdc.current;
            gdc.max = neuron.getValue();
        }
        gdc.current++;
    };

    public static class SetValueParameter {

        protected int index = 0;
        protected final double[] values;

        public SetValueParameter(double[] values) {
            this.values = values;
        }

        public double next() {
            return values[index++];
        }
    }

    public static class CalculateMeanSquareErrorParameter extends SetValueParameter {

        public double errorSum = 0;

        public CalculateMeanSquareErrorParameter(double[] correctValues) {
            super(correctValues);
        }

        public double getNetworkError(double neuronCount) {
            return errorSum / neuronCount;
        }
    }

    public static class GetGuessedDigitParameter {

        public double max = 0;
        public int guess = 0;
        public int current = 0;
    }

}
