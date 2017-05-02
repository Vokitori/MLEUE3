package mle3neuralnetwork;

import java.util.*;
import mle3neuralnetwork.data.Data;
import mle3neuralnetwork.data.SetupData;
import mle3neuralnetwork.data.SetupDataSet;
import mle3neuralnetwork.neuron.*;
import static mle3neuralnetwork.neuron.ActivationFunction.SIGMOID;
import static mle3neuralnetwork.neuron.FunctionList.ADJUST_BIAS_FUNCTION;
import static mle3neuralnetwork.neuron.FunctionList.ADJUST_WEIGHT_FUNCTION;
import static mle3neuralnetwork.neuron.FunctionList.CALCULATE_ERROR_FUNCTION;
import static mle3neuralnetwork.neuron.FunctionList.CALCULATE_NETWORK_ERROR_FUNCTION;
import static mle3neuralnetwork.neuron.FunctionList.CALCULATE_OUTPUT_ERROR_FUNCTION;
import static mle3neuralnetwork.neuron.FunctionList.CONNECT_TO_RIGHT_FUNCTION;
import static mle3neuralnetwork.neuron.FunctionList.SET_VALUE_FUNCTION;
import static mle3neuralnetwork.neuron.FunctionList.CALCULATE_VALUE_FUNCTION;
import static mle3neuralnetwork.neuron.FunctionList.GET_GUESSED_DIGIT_FUNCTION;
import static mle3neuralnetwork.neuron.FunctionList.PRINT_FUNCTION;

/**
 * @author Link
 */
public class Network {

    private static final ActivationFunction ACTIVATION_FUNCTION = SIGMOID;

    public final double learningRate;
    public final double momentum;
    public final double allowedErrorMargin;

    public final int hiddenNeuronCount;
    public static final int INPUT_NEURON_COUNT = 784;
    public static final int OUTPUT_NEURON_COUNT = 10;

    private final AccuracyMatrix accuracyMatrix = new AccuracyMatrix(10);

    public final Layer inputLayer;
    public final Layer hiddenLayer;
    public final Layer outputLayer;

    public long time;

    /**
     * A neural network for recognising digits.
     *
     * @param learningRate
     * @param momentum
     * @param allowedErrorMargin
     * @param hiddenLayerNeuronCount
     */
    public Network(double learningRate, double momentum, double allowedErrorMargin, int hiddenLayerNeuronCount) {
        this.learningRate = learningRate;
        this.momentum = momentum;
        this.allowedErrorMargin = allowedErrorMargin;
        this.hiddenNeuronCount = hiddenLayerNeuronCount;
        inputLayer = new Layer(INPUT_NEURON_COUNT);
        hiddenLayer = new Layer(hiddenLayerNeuronCount);
        outputLayer = new Layer(OUTPUT_NEURON_COUNT);

        inputLayer.apply(this, CONNECT_TO_RIGHT_FUNCTION, hiddenLayer);
        hiddenLayer.apply(this, CONNECT_TO_RIGHT_FUNCTION, outputLayer);
    }

    /**
     * Uses the specified SetupDataSet to set up the network until it is below the specified allowed ErrorMargin
     *
     * @param dataSet
     */
    public void initialise(SetupDataSet dataSet) {
        train(dataSet.getTrainData());
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
        inputLayer.apply(this, SET_VALUE_FUNCTION, new FunctionList.SetValueParameter(data.getData()));
        hiddenLayer.apply(this, CALCULATE_VALUE_FUNCTION, ACTIVATION_FUNCTION);
        outputLayer.apply(this, CALCULATE_VALUE_FUNCTION, ACTIVATION_FUNCTION);

        FunctionList.GetGuessedDigitParameter digit = new FunctionList.GetGuessedDigitParameter();
        outputLayer.apply(this, GET_GUESSED_DIGIT_FUNCTION, digit);
        return digit.guess;
    }

    /**
     * Calculates the errors based on the current input and expected output and
     * adjusts all weights (also the bias weights) accordingly
     *
     * @param label the output that was expected
     * @param guess the value guessed by the network
     */
    private void reduceError(int label, int guess) {
        outputLayer.apply(this, CALCULATE_OUTPUT_ERROR_FUNCTION, generateLabelArray(label).iterator());
        hiddenLayer.apply(this, CALCULATE_ERROR_FUNCTION, null);
        hiddenLayer.apply(this, ADJUST_WEIGHT_FUNCTION, null);
        outputLayer.apply(this, ADJUST_BIAS_FUNCTION, null);
        inputLayer.apply(this, ADJUST_WEIGHT_FUNCTION, null);
        hiddenLayer.apply(this, ADJUST_BIAS_FUNCTION, null);
    }

    /**
     * Uses training data to train the network until the network error is below the allowed error margin.
     */
    private void train(List<SetupData> dataSet) {
        double currentError = 1;
        for (long i = 0; currentError > allowedErrorMargin; i++) {
            time = System.nanoTime();
            int iModSize = (int) (i % dataSet.size());
            SetupData data = dataSet.get(iModSize);
            int guessedValue = guessValue(data);
            reduceError(data.getLabel(), guessedValue);

            FunctionList.CalculateNetworkErrorParameter p = new FunctionList.CalculateNetworkErrorParameter(data.getData());
            outputLayer.apply(this, CALCULATE_NETWORK_ERROR_FUNCTION, p);

            currentError = p.getNetworkError(outputLayer.neurons.length);
            if (i % 10000 == 0) {
                System.out.println("Iteration " + i / 10000 + "0k(" + (int) ((System.nanoTime() - time) / 1000) + "ms): " + currentError);
                //   hiddenLayer.apply(this, PRINT_FUNCTION, null);
            }
            if (i > 5000)
                break;
        }
        hiddenLayer.apply(this, PRINT_FUNCTION, null);
        System.out.println();
        outputLayer.apply(this, PRINT_FUNCTION, null);

        System.out.println("Final error: " + currentError);
    }

    /**
     * Uses test data to calculate the accuracy of the network,
     * which is stored in the accuracyMatrix.
     *
     * @param dataSet
     */
    private void test(List<SetupData> dataSet) {
        for (SetupData data : dataSet) {
            int guess = guessValue(data);
            accuracyMatrix.addGuess(guess, data.getLabel());
        }
    }

    private static List<Double> generateLabelArray(int label) {
        ArrayList<Double> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(label == i ? 1d : 0d);
        }
        return list;
    }

    public static double generateRandomWeight() {
        return 2 * Math.random() - 1;
    }
}
