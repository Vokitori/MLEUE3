package mle3neuralnetwork.neuron;

import mle3neuralnetwork.neuron.Synapse;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * @author Voki
 */
public class NormalNeuron implements Neuron {

    private double biasWeight = 1;
    private double value;

    private final ArrayList<Synapse> previousSynapseList = new ArrayList<>();
    private final ArrayList<Synapse> nextSynapseList = new ArrayList<>();

    private static final ActivationFunction activationFunction = ActivationFunction.SIGMOID;

    public NormalNeuron() {
    }

    public void calculateValue() {
        value = getSum() + biasWeight;
    }

    private double getSum() {
        double sum = 0;
        for (int i = 0; i < previousSynapseList.size(); i++) {
            sum += previousSynapseList.get(i).weight
                    * previousSynapseList.get(i).previous.getValue();
        }
        return sum;
    }

    public void connectWithPreviousNeutron(Neuron previous) {
        previousSynapseList.add(new Synapse(previous, this));
    }

    @Override
    public double getValue() {
        return value;
    }
}
