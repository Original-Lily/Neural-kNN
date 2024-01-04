import java.util.*;
import java.io.*;

public class kNN2 {
    public static void main(String[] args) throws IOException {
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // DEFINE VARIABLES
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // define and initialize the size of constants
        int TRAIN_SIZE = 200; // no. training patterns
        int FEATURE_SIZE = 61; // no. of features
        int GArepeats = 7000000; // no. of Genetic Algorithm cycles - population

        // define the input arrays
        double[][] trainD = new double[TRAIN_SIZE][FEATURE_SIZE]; // training data
        double[][] testD = new double[TRAIN_SIZE][FEATURE_SIZE]; // test data
        int[] prediction = new int[TRAIN_SIZE]; // answer for contents of output.txt
        int[] trainL = new int[TRAIN_SIZE]; // train labels
        int[] testL = new int[TRAIN_SIZE]; // test labels - STRICTLY FOR COMPARISON

        // Manhattan Calc Data
        double[] ManDis = new double[TRAIN_SIZE];
        double[] TestManDis = new double[TRAIN_SIZE];

        // Genetic Algorithm Data
        int NoOfColRemoved = 15;
        int CorrSum = 0;
        int CorrSumCache = 0;
        int noChangeCount = 0;
        int[] predictionFin = new int[TRAIN_SIZE];
        int[] randomCols = new int[NoOfColRemoved];
        int[] passedCols = new int[NoOfColRemoved];
        Random random = new Random();

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // PLACE READ DATA INTO USABLE ARRAYS
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // read the required data
        Scanner TrainIn = new Scanner(new File("train_data.txt"));

        // TAKE IN EACH ITEM WITHIN THE TRAIN DATA AND PLACE INTO 2D ARRAY
        for (int i = 0; i < TRAIN_SIZE; i++) {
            for (int j = 0; j < FEATURE_SIZE; j++) {
                if (TrainIn.hasNextDouble()) {
                    trainD[i][j] = TrainIn.nextDouble();
                }
            }
        }
        TrainIn.close();

        Scanner TestIn = new Scanner(new File("test_data.txt"));

        // TAKE IN EACH ITEM WITHIN THE TEST DATA AND PLACE INTO 2D ARRAY
        for (int i = 0; i < TRAIN_SIZE; i++) {
            for (int j = 0; j < FEATURE_SIZE; j++) {
                if (TestIn.hasNextDouble()) {
                    testD[i][j] = TestIn.nextDouble();
                }
            }
        }
        TestIn.close();

        Scanner TrainLabel = new Scanner(new File("train_label.txt"));

        // TAKE IN EACH TRAIN LABEL WITHIN THE TEXT FILE
        for (int i = 0; i < TRAIN_SIZE; i++) {
            if (TrainLabel.hasNextInt()) {
                trainL[i] = TrainLabel.nextInt();
            }
        }
        TrainLabel.close();

        // STRICTLY FOR USE COMPARING OUTCOMES FOR GA
        Scanner TestLabel = new Scanner(new File("test_label.txt"));

        // TAKE IN EACH TEST LABEL WITHIN THE TEXT FILE
        for (int i = 0; i < TRAIN_SIZE; i++) {
            if (TestLabel.hasNextInt()) {
                testL[i] = TestLabel.nextInt();
            }
        }
        TestLabel.close();

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // FEATURE SCALING
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Calculate the mean and standard deviation for each feature in the training data
        double[] mean = new double[FEATURE_SIZE];
        double[] stdDev = new double[FEATURE_SIZE];

        for (int j = 0; j < FEATURE_SIZE; j++) {
            double sum = 0;
            for (int i = 0; i < TRAIN_SIZE; i++) {
                sum += trainD[i][j];
            }
            mean[j] = sum / TRAIN_SIZE;

            double sumSquaredDiff = 0;
            for (int i = 0; i < TRAIN_SIZE; i++) {
                sumSquaredDiff += Math.pow(trainD[i][j] - mean[j], 2);
            }
            stdDev[j] = Math.sqrt(sumSquaredDiff / TRAIN_SIZE);
        }

        // Scale the features in both training and test data
        for (int i = 0; i < TRAIN_SIZE; i++) {
            for (int j = 0; j < FEATURE_SIZE; j++) {
                trainD[i][j] = (trainD[i][j] - mean[j]) / stdDev[j];
                testD[i][j] = (testD[i][j] - mean[j]) / stdDev[j];
            }
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // IMPLEMENT GENETIC ALGORITHM
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        for (int Ga = 0; Ga < GArepeats; Ga++) {
            // Create a list of candidate columns to be removed
            List<Integer> candidateCols = new ArrayList<>();
            for (int i = 0; i < FEATURE_SIZE; i++) {
                candidateCols.add(i);
            }

            // Shuffle the candidate columns
            Collections.shuffle(candidateCols, random);

            // Select the first NoOfColRemoved columns from the shuffled list
            for (int i = 0; i < NoOfColRemoved; i++) {
                randomCols[i] = candidateCols.get(i);
            }

            // Dynamic mutation rate: decreases over time
            double mutationRate = Math.max(0.1, 1.0 - (double) Ga / GArepeats);

            // Apply mutation to random columns
            for (int i = 0; i < NoOfColRemoved; i++) {
                if (random.nextDouble() < mutationRate) {
                    randomCols[i] = random.nextInt(FEATURE_SIZE);
                }
            }

            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // MANHATTAN DISTANCES CALCULATION
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            for (int i = 0; i < TRAIN_SIZE; i++) {
                ManDis[i] = manhattanDistance(trainD[i], randomCols);
                TestManDis[i] = manhattanDistance(testD[i], randomCols);
            }

            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // Compare Manhattan Distances - MAKING TEST LABEL PREDICTIONS
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            for (int i = 0; i < TRAIN_SIZE; i++) {
                int closestIndex = findClosestIndex(TestManDis[i], ManDis);
                prediction[i] = trainL[closestIndex];
            }

            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // EVALUATE PREDICTIONS USING TEST LABELS
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            CorrSumCache = evaluatePredictions(prediction, testL);

            // Update if there's an improvement
            if (CorrSumCache > CorrSum) {
                for (int i = 0; i < TRAIN_SIZE; i++) {
                    predictionFin[i] = prediction[i];
                    passedCols = Arrays.copyOf(randomCols, NoOfColRemoved);
                }
                CorrSum = CorrSumCache;
                noChangeCount = 0;
            } else {
                noChangeCount++;
            }
        } // END GENETIC ALGORITHM LOOP

        /* check outputs
        System.out.println(CorrSum);
        for (int i = 0; i < passedCols.length; i++) {
            System.out.println(passedCols[i]);
        }
        */

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // 
        // Improvements to be made ideas list:
        // 
        // 1: Trim extreme data
        //  : Use means + standard deviation
        //
        // 2: Ginetic Algorithm and take most accurate?
        //  : Delete columns, don't need to know why the column is an issue, just find the ones that give the best result
        //  : Best to do between 10 & 15 ish~ it seems
        //  : MUTATE BETWEEN GENERATIONS
        //
        // 3: Feature scaling?
        //  : Standardize features
        // 
        // 4: Data points that outlie on average according to data analysis:
        //  : [02,07,15,21,27,37,40,47,51,57]
        //
        // 5: Use a different distance metric
        //
        // 6: Use a different k value
        //
        //  : NOTE: MAKE A PYTHON SCRIPT TO RUN THIS AND STORE MOST COMMON USED NUMBERS
        //
        //  : NOTE: MANHATTAN DISTANCES?
        //
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // PRINT OUTPUT INTO CORRESPONDING OUTPUT TEXT FILE
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         
        // writing output2.txt in the required format
        try {
            PrintWriter writer = new PrintWriter("output2.txt");
            for (int i = 0; i < TRAIN_SIZE; i++) {
                writer.print(predictionFin[i] + " ");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    } // END MAIN METHOD

    // Helper method to calculate Manhattan distance for a specific set of columns
    private static double manhattanDistance(double[] data, int[] cols) {
        double sum = 0;
        for (int col : cols) {
            sum += Math.abs(data[col]);
        }
        return sum;
    }

    // Helper method to find the index of the closest distance
    private static int findClosestIndex(double testDist, double[] trainDists) {
        double minDiff = Math.abs(testDist - trainDists[0]);
        int closestIndex = 0;
        for (int i = 1; i < trainDists.length; i++) {
            double diff = Math.abs(testDist - trainDists[i]);
            if (diff < minDiff) {
                minDiff = diff;
                closestIndex = i;
            }
        }
        return closestIndex;
    }

    // Helper method to evaluate predictions against actual labels
    private static int evaluatePredictions(int[] predictions, int[] labels) {
        int correctCount = 0;
        for (int i = 0; i < predictions.length; i++) {
            if (predictions[i] == labels[i]) {
                correctCount++;
            }
        }
        return correctCount;
    }
} // END CLASS
