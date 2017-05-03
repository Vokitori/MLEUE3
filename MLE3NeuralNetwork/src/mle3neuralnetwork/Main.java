package mle3neuralnetwork;

import java.util.*;
import mle3neuralnetwork.data.SetupData;
import mle3neuralnetwork.data.SetupDataSet;
import mle3neuralnetwork.reader.*;

/**
 * @author Voki
 */
public class Main {

    public static final String PATH = "data/";

    public static SetupData b111 = new SetupData() {
        @Override
        public int getLabel() {
            return 1;
        }

        @Override
        public double[] getData() {
            return new double[]{1, 1};
        }
    };

    public static SetupData b010 = new SetupData() {
        @Override
        public int getLabel() {
            return 0;
        }

        @Override
        public double[] getData() {
            return new double[]{0, 1};
        }
    };

    public static SetupData b100 = new SetupData() {
        @Override
        public int getLabel() {
            return 0;
        }

        @Override
        public double[] getData() {
            return new double[]{1, 0};
        }
    };

    public static SetupData b000 = new SetupData() {
        @Override
        public int getLabel() {
            return 0;
        }

        @Override
        public double[] getData() {
            return new double[]{1, 0};
        }
    };

    public static void main(String[] args) throws Exception {

        DigitImageLoadingService service = new DigitImageLoadingService(PATH + "t10k-labels.idx1-ubyte", PATH + "t10k-images.idx3-ubyte");
        List<SetupData> testData = service.loadDigitImages();
        service = new DigitImageLoadingService(PATH + "train-labels.idx1-ubyte", PATH + "train-images.idx3-ubyte");
        List<SetupData> trainData = service.loadDigitImages();

        Network n = new Network(0.2, 0.9, 0.05,784, 89, 10);

        /* List<SetupData> trainData = new ArrayList<>();
        trainData.add(b000);
        trainData.add(b010);
        trainData.add(b100);
        trainData.add(b111);
        trainData.add(b111);
        trainData.add(b111);

        List<SetupData> testData = new ArrayList<>();
        testData.add(b000);
        testData.add(b010);
        testData.add(b100);
        testData.add(b111);
        Network n = new Network(0.2, 0.9, 0.05, 2, 2, 2);*/
        Thread t = new Thread(() -> {
            System.out.println("Initialising! Press enter cut training short...");
            n.initialise(new SetupDataSet(trainData, testData));
            System.out.println("Initialised! Press enter to continue...");
        });
        t.start();
        Scanner s = new Scanner(System.in);
        s.nextLine();
        n.interrupt = true;
        t.join();
        n.getAccuracyMatrix().display();

        /*    n.takeGuess(new Data() {
            @Override
            public double[] getData() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });*/
    }
}
