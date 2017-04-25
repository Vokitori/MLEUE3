package mle3neuralnetwork;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ml.technikum.at.nn.DigitImage;
import ml.technikum.at.nn.DigitImageLoadingService;

/**
 * @author Voki
 */
public class Main {

    public static final String PATH = "data/";

    public static void main(String[] args) {
        DigitImageLoadingService service = new DigitImageLoadingService(PATH + "t10k-labels.idx1-ubyte", PATH + "t10k-images.idx3-ubyte");
        List<DigitImage> imageList;

        try {
            imageList = service.loadDigitImages();
            for (int i = 0; i < imageList.size(); i++) {
                System.out.println(imageList.get(i).getLabel());
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
