package GA;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GA {

   
    private int entitysize = 100;
    private int p = 5000;//迭代次数
    private double p_bianyi = 0.05;
    private double p_jiaopei = 0.8;
    private int citynum = 100;//城市数目
    private Distance weight_distance;
    private GAEntity[] gaEntity;
    private GAEntity[] tempEntity;
    private double all_ability;
    private MatchTable matchTable;
    private GAEntity bestEntity;
    private double shortestRoad;
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
        weight_distance = new Distance(citynum,true);
    }

    private void Init_GAEntity() {
        gaEntity = new GAEntity[entitysize];
        tempEntity = new GAEntity[entitysize];
        for (int i = 0; i < entitysize; i++) {
            gaEntity[i] = new GAEntity(citynum, "");
            System.out.println("初始种群" + i + ":" + gaEntity[i].printRoad());
        }
    }

  
    private void Cal_AdaptabilityAndLucky() {
        all_ability = 0.0;
        double all_lucky = 0.0;
        for (int i = 0; i < entitysize; i++) {
            all_ability += gaEntity[i].cal_Adaptability();
        }

        for (int i = 0; i < entitysize; i++) {
            all_lucky += gaEntity[i].cal_preLucky(all_ability);
        }
         
        for (int i = 0; i < entitysize; i++) {
            gaEntity[i].cal_Lucky(all_lucky);
        }

    }

    
    private void chooseSample() {
        double p = 0.0;
        double all_prelucky = 0.0;
        for (int i = 0; i < entitysize; i++) {
            p = Math.random();
            all_prelucky = 0.0;
            tempEntity[i] = gaEntity[entitysize - 1];
            for (int j = 0; j < entitysize; j++) {
                all_prelucky += gaEntity[j].getP_lucky();         
                if (p <= all_prelucky) {
                    tempEntity[i] = gaEntity[j];
                    break;
                }
            }
        }    
        for (int i = 0; i < entitysize; i++) {
            gaEntity[i] = null;
            gaEntity[i] = tempEntity[i];
        }

    }

    private void Mating() {
        double mating[] = new double[entitysize];
        boolean matingFlag[] = new boolean[100];
        boolean findMating1 = false;
        Random random = new Random();
        matchTable = new MatchTable(citynum);
        int mating1 = 0;
        int mating2 = -1;
        int position1, position2;
        int matingnum = 0;
        
        for (int i = 0; i < entitysize; i++) {
            mating[i] = Math.random();
            if (mating[i] < p_jiaopei) {
                matingFlag[i] = true;
                matingnum++;
            } else {
                matingFlag[i] = false;
            }
        }

        matingnum = matingnum / 2 * 2;
        for (int i = 0; i < matingnum / 2; i++) {
            findMating1 = false;
            position1 = random.nextInt(citynum);
            position2 = random.nextInt(citynum);
            if (position1 <= position2) {

            } else {
                int t = position1;
                position1 = position2;
                position2 = t;
            }
         
            for (mating2++; mating2 < entitysize; mating2++) {

                if (matingFlag[mating2]) {
                    if (findMating1) {
                        break;
                    } else {
                        mating1 = mating2;
                        findMating1 = true;
                    }
                }
            }
        
            matchTable.setTable(gaEntity[mating1], gaEntity[mating2], position1, position2);
          
            GAEntity tempGaEntity1 = new GAEntity(citynum);
            GAEntity tempGaEntity2 = new GAEntity(citynum);
            

            if (!gaEntity[mating1].checkdifference(gaEntity[mating2])) {
                tempGaEntity1 = gaEntity[mating1];
                tempGaEntity2 = gaEntity[mating2];
            } else {

                tempGaEntity1.setRoad(gaEntity[mating2], position1, position2);
                tempGaEntity2.setRoad(gaEntity[mating1], position1, position2);
                tempGaEntity1.modifyRoad(gaEntity[mating1], position1, position2, matchTable, true);
                tempGaEntity2.modifyRoad(gaEntity[mating2], position1, position2, matchTable, false);
            }

            gaEntity[mating1] = tempGaEntity1;
            gaEntity[mating2] = tempGaEntity2;
        }

    }


    private void Variating() {

        double rating[] = new double[entitysize];
        boolean ratingFlag[] = new boolean[entitysize];
        Random random = new Random();
        int position1, position2;
       
        for (int i = 0; i < entitysize; i++) {
            rating[i] = Math.random();
            if (rating[i] < p_bianyi) {
                ratingFlag[i] = true;
            } else {
                ratingFlag[i] = false;
            }
        }
        for (int i = 0; i < entitysize; i++) {
            if (ratingFlag[i]) {
                position1 = 0;
                position2 = 0;
                while (position1 == position2) {
                    position1 = random.nextInt(citynum);
                    position2 = random.nextInt(citynum);
                }
                gaEntity[i].exchange(position1, position2);
            }
        }
    }

   
    private void ChooseBestSolution(Boolean initBest) {

        Double roadLength = Double.MAX_VALUE;
        int bestRoad = 0;
        for (int i = 0; i < entitysize; i++) {
            if (roadLength > gaEntity[i].getAdaptability()) {
                roadLength = gaEntity[i].getAdaptability();
                bestRoad = i;
            }
        }
        System.out.println("该次迭代最短路径：" + gaEntity[bestRoad].printRoad());
        System.out.println("该次迭代最低消耗：" + roadLength);
        saveFile(Integer.toString(roadLength.intValue()));
        if (initBest) {
            shortestRoad = roadLength;
            bestEntity = gaEntity[bestRoad];
        } else if (shortestRoad > roadLength) {
            shortestRoad = roadLength;
            bestEntity = gaEntity[bestRoad];
        }
    }

    private void Iterator() {
        Init_Distance();
        Init_GAEntity();
        boolean initBest = true;
        for (int i = 0; i < p; i++) {
            System.out.println("第" + i + "次迭代：");
            Cal_AdaptabilityAndLucky();
            ChooseBestSolution(initBest);
            initBest = false;
            chooseSample();
            Mating();
            Variating();
        }
        Cal_AdaptabilityAndLucky();
        ChooseBestSolution(false);
        System.out.println("最好路径：" + bestEntity.printRoad());
        System.out.println("最低消耗：" + shortestRoad);
    }

 
    public static void main(String[] args) {
    	long startTime = System.currentTimeMillis();
        GA ga = new GA();
        ga.Iterator();
        long endTime = System.currentTimeMillis();
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
    }
}
