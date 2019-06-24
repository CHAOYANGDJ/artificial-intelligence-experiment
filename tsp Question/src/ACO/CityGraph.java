package ACO;
import java.util.Random;
import ACO.toArray;

public class CityGraph {

    private static double distance[][];
    private static double pheromone[][];
    private static int num;
    Random random = new Random();

    public CityGraph(int num, boolean ifDuichen) {
        this.num = num;

        initDistance(ifDuichen);
        initPheronome();
    }

    private void initDistance(boolean ifDuichen) {
        distance = new double[num][num];
        toArray to= new toArray();
        distance = to.toMatrix(to.myToArray());
     
printlndistance();
    }

    private void initPheronome() {
        pheromone = new double[num][num];
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                pheromone[i][j] = 0.1;
            }
        }
    }

    public static void normalizePheromone() {
        double all_phero = 0.0;
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                all_phero += pheromone[i][j];
            }
        }
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                 pheromone[i][j]=pheromone[i][j]/all_phero;
            }
        }
         
    }

    public static double getDistance(int i, int j) {
        return distance[i][j];
    }

    public static double getPheronmone(int i, int j) {
        return pheromone[i][j];
    }
    
    public static double[] getCities(int i){
        return pheromone[i];
    }
    
    public static void setPhero(int i,int j ,double Q,long t){
        pheromone[i][j]+=Q-t/100* pheromone[i][j];
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
