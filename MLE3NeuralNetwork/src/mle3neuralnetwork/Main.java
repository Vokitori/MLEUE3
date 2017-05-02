package mle3neuralnetwork;

import java.util.List;
import mle3neuralnetwork.data.Data;
import mle3neuralnetwork.data.SetupData;
import mle3neuralnetwork.data.SetupDataSet;
import mle3neuralnetwork.reader.DigitImageLoadingService;

/**
 * @author Voki
 */
public class Main {

    public static final String PATH = "data/";

    public static void main(String[] args) throws Exception {

        DigitImageLoadingService service = new DigitImageLoadingService(PATH + "t10k-labels.idx1-ubyte", PATH + "t10k-images.idx3-ubyte");
        List<SetupData> testData = service.loadDigitImages();
        service = new DigitImageLoadingService(PATH + "train-labels.idx1-ubyte", PATH + "train-images.idx3-ubyte");
        List<SetupData> trainData = service.loadDigitImages();

        Network n = new Network(0.2, 0.9, 0, 89);
        n.initialise(new SetupDataSet(trainData, testData));

        n.getAccuracyMatrix().display();

        /*    n.takeGuess(new Data() {
            @Override
            public double[] getData() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });*/
    }
}
