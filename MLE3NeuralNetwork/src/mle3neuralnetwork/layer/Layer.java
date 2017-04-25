package mle3neuralnetwork.layer;

import java.util.ArrayList;
import mle3neuralnetwork.Neuron;

/**
 * @author Voki
 */
public abstract class Layer {
    private final ArrayList<Neuron> neuronList = new ArrayList<>();

    
    
    public int size() {
        return neuronList.size();
    }

    public int indexOf(Neuron neuron) {
        return neuronList.indexOf(neuron);
    }

    public Neuron get(int index) {
        return neuronList.get(index);
    }

    public boolean add(Neuron neuron) {
        return neuronList.add(neuron);
    }

    public Neuron remove(int index) {
        return neuronList.remove(index);
    }

    public boolean remove(Neuron neuron) {
        return neuronList.remove(neuron);
    }

    public ArrayList<Neuron> getNeuronList() {
        return neuronList;
    }
    
}
