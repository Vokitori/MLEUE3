package mle3neuralnetwork;

/**
 * @author Link
 */
public final class AccuracyMatrix {

    private final int valueCount;
    private final int[][] matrix;
    private final int defaultValue = 0;

    public AccuracyMatrix(int valueCount) {
        this.valueCount = valueCount;
        matrix = new int[valueCount][valueCount];
        clear();
    }

    public void clear() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = defaultValue;
            }
        }
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getValueCount() {
        return valueCount;
    }

    public void addGuess(int guessed, int is) {
        matrix[is][guessed]++;
    }

    public void displayMatrix() {
        System.out.println();
        System.out.println("         G   U   E   S   S   E   D");
        System.out.print("\t");
        for (int i = 0; i < matrix.length; i++) {
            System.out.print(i + "\t");
        }
        System.out.println();
        System.out.println("\t------------------------------------------------------------------------------");

        for (int i = 0; i < matrix.length; i++) {
            for (int j = -1; j < matrix[i].length; j++) {
                if (j == -1) {
                    System.out.print(i + "\t");
                } else {
                    System.out.print(matrix[i][j] + "\t");
                }
            }
            System.out.println();
        }
    }

    public void displayAccuracy() {
        int sum = 0;
        int correct = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sum += matrix[i][j];
                if (i == j) {
                    correct += matrix[i][j];
                }
            }
        }
        System.out.println("Accuracy: " + (correct * 1.0 / sum));
    }
}
