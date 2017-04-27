package mle3neuralnetwork;

/**
 * @author Link
 */
public class InputData {

    private final double[] data;
    private final int label;

    public InputData(double[] data, int label) {
        this.data = data;
        this.label = label;
    }

    public double getData(int i) {
        return data[i];
    }

    public int getLabel() {
        return label;
    }

}
