package mle3neuralnetwork;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static mle3neuralnetwork.Main.PATH;
import mle3neuralnetwork.layer.HiddenLayer;
import mle3neuralnetwork.layer.InputLayer;
import mle3neuralnetwork.layer.OutputLayer;
import mle3neuralnetwork.reader.DigitImage;
import mle3neuralnetwork.reader.DigitImageLoadingService;

/**
 * @author Link
 */
public class Network {

    public final double learningRate;
    public final double momentum;
    public final double allowedErrorMargin;

    public final int hiddenLayerNeuronCount;
    public final int inputLayerNeutronCount = 784;
    public final int outputLayerNeuronCount = 10;

    private final InputLayer inputLayer;
    private final HiddenLayer hiddenLayer;
    private final OutputLayer outputLayer;

    private List<DigitImage> imageTrainingsList;
    private List<DigitImage> imageTestList;

    private InputData inputData;

    public Network(double learningRate, double momentum, double allowedErrorMargin, int hiddenLayerNeuronCount) {
        this.learningRate = learningRate;
        this.momentum = momentum;
        this.allowedErrorMargin = allowedErrorMargin;
        this.hiddenLayerNeuronCount = hiddenLayerNeuronCount;
        inputLayer = new InputLayer(this);
        hiddenLayer = new HiddenLayer(this, this.hiddenLayerNeuronCount);
        outputLayer = new OutputLayer(this);

        hiddenLayer.connectWithNeurons(inputLayer.getNeurons());
        hiddenLayer.connectWithNeurons(outputLayer.getNeurons());

        init();
    }

    private void init() {
        try {
            DigitImageLoadingService service = new DigitImageLoadingService(PATH + "t10k-labels.idx1-ubyte", PATH + "t10k-images.idx3-ubyte");
            imageTestList = service.loadDigitImages();
            service = new DigitImageLoadingService(PATH + "train-labels.idx1-ubyte", PATH + "train-images.idx3-ubyte");
            imageTrainingsList = service.loadDigitImages();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public InputData getInputData() {
        return inputData;
    }

    public void train() {
        double currentError = 1;
        while (currentError > 0.005) {
            for (DigitImage digitImage : imageTrainingsList) {
                inputData = new InputData(digitImage.getData(), digitImage.getLabel());
                outputLayer.setCorrectValues(generateCorrectValues(digitImage.getLabel()));
                feedForward();
                backPropagate();
            }
            currentError = outputLayer.calculateNetworkError();
            System.out.println(currentError);
        }
    }

    public void test() {
        for (DigitImage digitImage : imageTestList) {
            inputData = new InputData(digitImage.getData(), digitImage.getLabel());
            feedForward();
            System.out.print(outputLayer.expectedNumber());
            System.out.println("/" + inputData.getLabel());
        }
    }

    private static double[] generateCorrectValues(int label) {
        double[] d = new double[10];
        for (int i = 0; i < d.length; i++) {
            d[i] = label == i ? 1 : 0;
        }
        return d;
    }

    private void feedForward() {
        inputLayer.updateNeurons();
        hiddenLayer.updateNeurons();
        outputLayer.updateNeurons();
    }

    private void backPropagate() {
        outputLayer.calculateErrors();
        hiddenLayer.calculateErrors();
        hiddenLayer.adjustWeights();
        outputLayer.adjustBias();
        inputLayer.adjustWeights();
        hiddenLayer.adjustBias();

    }

    public static double getNewWeight() {
        return 2 * Math.random() - 1;
    }

}
