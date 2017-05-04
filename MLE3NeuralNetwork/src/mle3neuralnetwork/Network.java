package mle3neuralnetwork;

import java.util.*;
import mle3neuralnetwork.data.*;
import mle3neuralnetwork.neuron.*;
import static mle3neuralnetwork.neuron.FunctionList.*;

/**
 * @author Link
 */
public class Network {

    private static final ActivationFunction ACTIVATION_FUNCTION = ActivationFunction.SIGMOID;

    public final double learningRate;
    public final double momentum;
    public final double allowedErrorMargin;

    public final int inputNeuronCount;
    public final int outputNeuronCount;

    private final AccuracyMatrix accuracyMatrix;

    public final Layer inputLayer;
    public final Layer[] hiddenLayerArray;
    public final Layer outputLayer;

    public boolean interrupt = false;

    public long time;

    /**
     * A neural network for recognising digits.
     *
     * @param learningRate
     * @param momentum
     * @param allowedErrorMargin
     * @param inputNeuronCount
     * @param hiddenLayerCount
     * @param outputNeuronCount
     */
    public Network(double learningRate, double momentum, double allowedErrorMargin, int inputNeuronCount, int outputNeuronCount, int... hiddenLayerCount) {
        this.learningRate = learningRate;
        this.momentum = momentum;
        this.allowedErrorMargin = allowedErrorMargin;

        this.inputNeuronCount = inputNeuronCount;
        this.outputNeuronCount = outputNeuronCount;

        inputLayer = new Layer(inputNeuronCount);
        outputLayer = new Layer(outputNeuronCount);
        hiddenLayerArray = new Layer[hiddenLayerCount.length];
        for (int i = 0; i < hiddenLayerArray.length; i++) {
            hiddenLayerArray[i] = new Layer(hiddenLayerCount[i]);
        }
        connectLayers();

        accuracyMatrix = new AccuracyMatrix(outputNeuronCount);
    }

    private void connectLayers() {
        inputLayer.apply(this, CONNECT_TO_RIGHT_FUNCTION, hiddenLayerArray[0]);
        for (int i = 0; i < hiddenLayerArray.length - 1; i++) {
            hiddenLayerArray[i].apply(this, CONNECT_TO_RIGHT_FUNCTION, hiddenLayerArray[i + 1]);
        }
        hiddenLayerArray[hiddenLayerArray.length - 1].apply(this, CONNECT_TO_RIGHT_FUNCTION, outputLayer);
    }

    /**
     * Uses the specified SetupDataSet to set up the network until it is below
     * the specified allowed ErrorMargin
     *
     * @param dataSet
     */
    public void initialise(SetupDataSet dataSet) {
        train(dataSet.getTrainData(), dataSet.getTestData());
        test(dataSet.getTestData());
    }

    /**
     *
     * @return the accuracyMatrix of this network
     */
    public AccuracyMatrix getAccuracyMatrix() {
        return accuracyMatrix;
    }

    /**
     * Guesses what digit the specified data represents.
     *
     * @param data contains a single digit
     * @return the guessed value
     */
    public int guessValue(Data data) {
        feedForward(data);

        FunctionList.GetGuessedDigitParameter digit = new FunctionList.GetGuessedDigitParameter();
        outputLayer.apply(this, GET_GUESSED_DIGIT_FUNCTION, digit);
        return digit.guess;
    }

    private void feedForward(Data data) {
        inputLayer.apply(this, SET_VALUE_FUNCTION, new FunctionList.SetValueParameter(data.getData()));
        for (Layer hiddenLayer : hiddenLayerArray) {
            hiddenLayer.apply(this, CALCULATE_VALUE_FUNCTION, ACTIVATION_FUNCTION);
        }
        outputLayer.apply(this, CALCULATE_VALUE_FUNCTION, ACTIVATION_FUNCTION);
    }

    /**
     * Calculates the errors based on the current input and expected output and
     * adjusts all weights (also the bias weights) accordingly
     *
     * @param label the output that was expected
     * @param guess the value guessed by the network
     */
    private void backPropagate(int label) {
        outputLayer.apply(this, CALCULATE_OUTPUT_ERROR_FUNCTION, generateLabelArray(label).iterator());
        for (Layer hiddenLayer : hiddenLayerArray) {
            hiddenLayer.apply(this, CALCULATE_HIDDEN_ERROR_FUNCTION, null);
        }
        for (Layer hiddenLayer : hiddenLayerArray) {
            hiddenLayer.apply(this, ADJUST_WEIGHT_FUNCTION, null);
        }

        outputLayer.apply(this, ADJUST_BIAS_FUNCTION, null);
        inputLayer.apply(this, ADJUST_WEIGHT_FUNCTION, null);
        for (Layer hiddenLayer : hiddenLayerArray) {
            hiddenLayer.apply(this, ADJUST_BIAS_FUNCTION, null);
        }

    }

    /**
     * Uses training data to train the network until the network error is below
     * the allowed error margin.
     */
    private void train(List<SetupData> dataSet, List<SetupData> deleteThis) {
        double currentError = 0;
        int count = 0;
        do {
            time = System.nanoTime();
            currentError = 0;
            for (SetupData data : dataSet) {
                feedForward(data);
                backPropagate(data.getLabel());

                FunctionList.CalculateMeanSquareErrorParameter p = new FunctionList.CalculateMeanSquareErrorParameter(data.getData());
                outputLayer.apply(this, CALCULATE_MEAN_SQUARE_ERROR_FUNCTION, p);
                currentError += p.getNetworkError(outputLayer.neurons.length);

                /*
                inputLayer.apply(this, PRINT_VALUE_FUNCTION, null);
                System.out.println();
                hiddenLayer.apply(this, PRINT_LEFT_SYNAPSES_FUNCTION, null);
                System.out.println();
                outputLayer.apply(this, PRINT_LEFT_SYNAPSES_FUNCTION, null);
                System.out.println();
                System.out.println("-----------------------------------------");
                 */
            }
            count += dataSet.size();
            currentError /= dataSet.size();
            System.out.println("Iteration " + count + "(" + (int) ((System.nanoTime() - time) / 1000000) + "ms): " + currentError);
            test(deleteThis);

            accuracyMatrix.displayMatrix();
            accuracyMatrix.displayAccuracy();
            accuracyMatrix.clear();
            // System.out.println("#########################################");
        } while (/*currentError > allowedErrorMargin && */!interrupt);
        System.out.println("Finished! Last Iteration:");
        System.out.println("Iteration " + count + "(" + (int) ((System.nanoTime() - time) / 1000000) + "ms): " + currentError);
    }

    /**
     * Uses test data to calculate the accuracy of the network, which is stored
     * in the accuracyMatrix.
     *
     * @param dataSet
     */
    private void test(List<SetupData> dataSet) {
        for (SetupData data : dataSet) {
            int guess = guessValue(data);
            accuracyMatrix.addGuess(guess, data.getLabel());
        }
    }

    private List<Double> generateLabelArray(int label) {
        ArrayList<Double> list = new ArrayList<>();
        for (int i = 0; i < outputNeuronCount; i++) {
            list.add(label == i ? 1d : 0d);
        }
        return list;
    }

    public static double generateRandomWeight() {
        return 2 * Math.random() - 1;
    }
}
