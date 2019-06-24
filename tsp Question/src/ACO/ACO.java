package ACO;
import GA.Distance;

import java.io.*;
import java.util.ArrayList;
public class ACO {
    private CityGraph weight_distance;
    private int citynum = 100;
    private int p = 10000;//迭代次数
    private double bestLength;
    private String bestTour;
    private int antNum = 100;
    private int bestAnt = Integer.MAX_VALUE;
    private ANT[] ants;
    private double alpha = 1.0;
    private double beta = 5.0;
    private double rho = 0.5;
    private double Q = 1000;
    private long startTime;
    private long endTime;
    private String filePath="E:\\1.txt";

  
    private void saveFile(String content ) {
    	FileWriter fwriter = null;
    	try {
    		fwriter = new FileWriter(filePath,true);
    		fwriter.write(content+"\n");
    	}catch(IOException ex) {
    		ex.printStackTrace();
    	}finally {
    		try {
    			fwriter.flush();
    			fwriter.close();
    		}catch(IOException ex) {
    			ex.printStackTrace();
    		}
    	}
    }
    
    private void Init_Distance() {
        weight_distance = new CityGraph(citynum, true);
    }

  
    private void Init_paras() {
        bestLength = Double.MAX_VALUE;
        bestTour = "";
    }

   
    private void Init_Ants() {
        ants = null;
        ants = new ANT[antNum];
        for (int i = 0; i < antNum; i++) {
            ants[i] = new ANT(citynum, alpha, beta);
        }
    }

    private void MovetoNextCity() {
        for (int i = 0; i < antNum; i++) {
            ants[i].chooseNextCity();
        }
    }

    private void findBestRoad() {

        for (int i = 0; i < antNum; i++) {

            if (bestLength > ants[i].getRoadLength()) {
                bestLength = ants[i].getRoadLength();
                bestTour = ants[i].getRoad();
                bestAnt = i;
            }
        }
        System.out.println("当前最优解：" + bestTour);
        System.out.println("该路径下的最低消耗：" + bestLength);
        saveFile(Integer.toString((int)bestLength));
    }

    private void updatePheromone() {
        for (int i = 0; i < antNum; i++) {
            ants[i].updatePheromone(Q, (endTime - startTime) / 1000);
        }

    }

    /**
    
     */
    private void iterator() {
        Init_Distance();
        Init_paras();
        startTime = System.currentTimeMillis();
        for (int i = 0; i < p; i++) {
            System.out.println("第" + i + "次迭代：");
            Init_Ants();
            MovetoNextCity();
            findBestRoad();
            endTime = System.currentTimeMillis();
            updatePheromone();
        }
    }

    public static void main(String[] args) {
    	long startTime = System.currentTimeMillis();
        ACO aco = new ACO();
        aco.iterator();
        long endTime = System.currentTimeMillis(); 
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
    }
}
