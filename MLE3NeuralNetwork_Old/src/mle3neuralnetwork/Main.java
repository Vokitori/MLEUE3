package mle3neuralnetwork;

/**
 * @author Voki
 */
public class Main {

    public static final String PATH = "data/";

    public static void main(String[] args) {

        Network n = new Network(0.2, 0.9, 0.005, 1);
        n.train();
        n.test();
    }
}
