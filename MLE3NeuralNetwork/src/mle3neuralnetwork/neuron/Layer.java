package mle3neuralnetwork.neuron;

import java.util.*;
import java.util.logging.*;
import mle3neuralnetwork.Network;

/**
 * @author Link
 */
public class Layer {

    public final Neuron[] neurons;

    private static final int HELPER_THREAD_COUNT = 0;

    private final HelperThread[] helperThreads = new HelperThread[HELPER_THREAD_COUNT];

    public Layer(int neuronCount) {
        this.neurons = new Neuron[neuronCount];
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new Neuron();
        }
        for (int i = 0; i < helperThreads.length; i++) {
            helperThreads[i] = new HelperThread(this);
            helperThreads[i].start();
        }
    }

    public void apply(Network network, NeuronFunction function, Object parameter) {
        for (Neuron neuron : neurons) {
            function.apply(network, neuron, parameter);
        }
    }

    public void applyParallel(Network network, NeuronFunction function, Object parameter) {
        int neuronCount = 0;
        int split = neurons.length / HELPER_THREAD_COUNT;
        for (int i = 0; i < helperThreads.length - 1; i++) {
            helperThreads[i].work(neuronCount, split, network, function, parameter);
            neuronCount += split;
        }
        helperThreads[helperThreads.length - 2].work(neuronCount, neurons.length - 1, network, function, parameter);
        for (int i = 0; i < helperThreads.length; i++) {
            while (helperThreads[i].active) {
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Layer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    private static class HelperThread extends Thread {

        private boolean active = false;

        private Layer layer;

        private Network network;
        private NeuronFunction function;
        private Object parameter;
        private int start;
        private int end;

        public HelperThread(Layer layer) {
            this.layer = layer;
            setDaemon(true);
        }

        public boolean isActive() {
            return active;
        }

        public synchronized void work(int start, int end, Network network, NeuronFunction function, Object parameter) {
            this.start = start;
            this.end = end;
            this.network = network;
            this.function = function;
            this.parameter = parameter;
            active = true;
            notify();
        }

        @Override
        public void run() {
            while (true) {
                while (!active) {
                    synchronized (this) {
                        try {
                            wait();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Layer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                for (int i = start; i < end; i++) {
                    function.apply(network, layer.neurons[i], parameter);
                }
                synchronized (layer) {
                    active = false;
                    layer.notify();
                }
            }
        }

    }
}
