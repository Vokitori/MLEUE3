package mle3neuralnetwork.neuron;

import java.util.ArrayList;
import mle3neuralnetwork.Network;

/**
 *
 * @author Link
 */
public class Neuron {

    protected double value = 0;
    protected double error = 0;
    protected double biasWeight = Network.generateRandomWeight();

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
