package GA;

import java.util.Random;
import ACO.toArray;


public class Distance {

    private static double distance[][];
    private int num;
    Random random = new Random();
    

    public Distance(int num, boolean ifDuichen) {
        this.num = num;
        initDistance(ifDuichen);
    }

    private void initDistance(boolean ifDuichen) {
        distance = new double[num][num];
        toArray to= new toArray();
        distance = to.toMatrix(to.myToArray());
        printlndistance();
    }

    public static double getDistance(int i, int j) { 
        return distance[i][j];
    }

    private void printlndistance() {
        System.out.printf("%8s","");
        for (int i = 0; i < num; i++) {
            System.out.printf("%5s",i);
        }
        System.out.println();
        for (int i = 0; i < num; i++) {
            System.out.printf("%5s",i);
            for (int j = 0; j < num; j++) {
                System.out.printf("%5s",(int)distance[i][j]);
            }
            System.out.println();
        }
    }

}
