import java.util.*;
import java.io.*;

public class kNN1{
    public static void main(String[] args) throws IOException {  
        //define and initialise the size of constants
        int TRAIN_SIZE = 200; //no. training patterns
        int FEATURE_SIZE = 61; //no. of features

        //define the input arrays
        double[][] trainD = new double[TRAIN_SIZE][FEATURE_SIZE]; //training data
        double[][] testD = new double[TRAIN_SIZE][FEATURE_SIZE]; //test data
        int[] prediction = new int[TRAIN_SIZE]; //answer for contents of output.txt
        int[] trainL = new int[TRAIN_SIZE]; //train labels

        //Euclidean Calc Data
        double[] EucDis = new double[TRAIN_SIZE];
        double[] TestEucDis = new double[TRAIN_SIZE];
        double EucSum = 0;
        double TestEucSum = 0;
        double EucDiff = 0;
        double EucDiffCache = 0;
        int CompareLabel = 0;
 
        //read the required data
        Scanner TrainIn = new Scanner(new File("train_data.txt"));

            // TAKE IN EACH ITEM WITHIN THE TRAIN DATA AND PLACE INTO 2D ARRAY
            for (int i = 0; i < TRAIN_SIZE; i++){
                for (int j = 0; j < FEATURE_SIZE; j++){
                    if (TrainIn.hasNextDouble()){
                        trainD[i][j] = TrainIn.nextDouble();
                    }
                }
            }
        TrainIn.close();

        Scanner TestIn = new Scanner(new File("test_data.txt"));

            // TAKE IN EACH ITEM WITHIN THE TEST DATA AND PLACE INTO 2D ARRAY
            for (int i = 0; i < TRAIN_SIZE; i++){
                for (int j = 0; j < FEATURE_SIZE; j++){
                    if (TestIn.hasNextDouble()){
                        testD[i][j] = TestIn.nextDouble();
                    }
                }
            }
        TestIn.close();

        Scanner TrainLable = new Scanner(new File("train_label.txt"));

            //TAKE IN EACH TEST LABEL WITHIN THE TEXT FILE
            for (int i = 0; i < TRAIN_SIZE; i++){
                if (TrainLable.hasNextInt()){
                    trainL[i] = TrainLable.nextInt();
                }
            }
        TrainLable.close();
 
        // CALCULATE EUCLIDEAN DISTANCES FOR EACH ROW IN TRAIN DATA
        for (int i = 0; i < TRAIN_SIZE; i++){
            for (int j = 0; j < FEATURE_SIZE; j++){
                EucSum =+ Math.pow(trainD[i][j], 2);
            }
            EucDis[i] = Math.sqrt(EucSum);
            EucSum = 0;
        }

        // CALCULATE EUCLIDEAN DISTANCES FOR EACH ROW IN TEST DATA
        for (int i = 0; i < TRAIN_SIZE; i++){
            for (int j = 0; j < FEATURE_SIZE; j++){
                TestEucSum =+ Math.pow(testD[i][j], 2);
            }
            TestEucDis[i] = Math.sqrt(TestEucSum);
            TestEucSum = 0;
        }

        for (int i = 0; i < TestEucDis.length; i++){ //for every item within the TEST euclidian list
            for (int j = 0; j < EucDis.length; j++){ //for every item within the TRAINING euclidian list
                EucDiffCache = Math.abs(TestEucDis[i] - EucDis[j]); //compare the two values, TEST - TRAIN
                if (j == 0){
                    EucDiff = EucDiffCache; //store it as the highest
                    CompareLabel = trainL[j];
                }
                else if (EucDiffCache < EucDiff && j > 0){ //if theyre closer than the current closest value
                    EucDiff = EucDiffCache; //store it as the highest
                    CompareLabel = trainL[j];
                }
            }
            prediction[i] = CompareLabel;
        }          
              
        //writing output1.txt in the required format
        try {
            PrintWriter writer = new PrintWriter("output1.txt");
            for (int i = 0; i < TRAIN_SIZE; i++){
                writer.print(prediction[i] + " ");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    } //end main loop
} //end class loop