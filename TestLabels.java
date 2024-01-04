import java.util.*;
import java.io.*;

public class TestLabels{
    public static void main(String[] args) throws IOException {

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // DEFINE VARIABLES AND INTAKE ARRAYS TO COMPARE
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        //define and initialise the size of constants
        int TRAIN_SIZE = 200; //no. training patterns

        //define the input arrays
        int[] correctD = new int[TRAIN_SIZE];//training data
        int[] outputD = new int[TRAIN_SIZE];//test data kNN1
        int[] outputD2 = new int[TRAIN_SIZE];//test data kNN2
        int sum = 0;
        int sum2 = 0;

        //read the required data
        Scanner testIn = new Scanner(new File("test_label.txt"));

            // TAKE IN EACH ITEM WITHIN THE TRAIN DATA AND PLACE INTO 2D ARRAY
            for (int i = 0; i < TRAIN_SIZE; i++){
                if (testIn.hasNextInt()){
                    correctD[i] = testIn.nextInt();
                }
            }
        testIn.close();

        Scanner PredictionIn = new Scanner(new File("output1.txt"));
            // TAKE IN EACH ITEM WITHIN THE TEST DATA AND PLACE INTO 2D ARRAY
            for (int i = 0; i < TRAIN_SIZE; i++){
                if (PredictionIn.hasNextInt()){
                    outputD[i] = PredictionIn.nextInt();
                }
            }
        PredictionIn.close();

        Scanner PredictionIn2 = new Scanner(new File("output2.txt"));
            // TAKE IN EACH ITEM WITHIN THE TEST DATA AND PLACE INTO 2D ARRAY
            for (int i = 0; i < TRAIN_SIZE; i++){
                if (PredictionIn2.hasNextInt()){
                    outputD2[i] = PredictionIn2.nextInt();
                }
            }
        PredictionIn2.close();

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // COMPARE ARRAYS
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        for (int i = 0; i < TRAIN_SIZE; i++){
            if (outputD[i] == correctD[i]){ sum++;}
        }
        System.out.println(sum + " out of 200 correct, output1");
        int maths = 0;
        maths = (sum*100)/TRAIN_SIZE;
        System.out.println(maths + "% correct, output1");

        for (int i = 0; i < TRAIN_SIZE; i++){
            if (outputD2[i] == correctD[i]){ sum2++;}
        }
        System.out.println(sum2 + " out of 200 correct, output2");
        int maths2 = 0;
        maths2 = (sum2*100)/TRAIN_SIZE;
        System.out.println(maths2 + "% correct, output2");
    }
}