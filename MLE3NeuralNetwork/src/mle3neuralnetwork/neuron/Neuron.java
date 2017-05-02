package mle3neuralnetwork.neuron;

import java.util.ArrayList;

/**
 *
 * @author Link
 */
public class Neuron {

    protected double value;
    protected double error;
    protected double bias = 1;

    protected ArrayList<Synapse> leftSynapses = new ArrayList<>();
    protected ArrayList<Synapse> rightSynapses = new ArrayList<>();

    protected ArrayList<Neuron> leftNeurons = new ArrayList<>();
    protected ArrayList<Neuron> rightNeurons = new ArrayList<>();

    public double getError() {
        return error;
    }

    public double getValue() {
        return value;
    }

    protected double getLeftWeightSum() {
        double sum = 0;
        for (int i = 0; i < leftSynapses.size(); i++) {
            sum += leftSynapses.get(i).weight
                   * leftNeurons.get(i).getValue();
        }
        return sum;
    }
}
