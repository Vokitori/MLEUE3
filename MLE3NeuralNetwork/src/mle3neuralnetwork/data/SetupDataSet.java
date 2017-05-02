package mle3neuralnetwork.data;

import java.util.List;

/**
 * Contains enough TestData to set up the neural network.
 *
 * @author Link
 */
public class SetupDataSet {

    private final List<SetupData> trainData;
    private final List<SetupData> testData;

    public SetupDataSet(List<SetupData> trainData, List<SetupData> testData) {
        this.trainData = trainData;
        this.testData = testData;
    }

    public List<SetupData> getTestData() {
        return testData;
    }

    public List<SetupData> getTrainData() {
        return trainData;
    }
}
