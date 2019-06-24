package GA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class GAEntity {

    private static Integer[] initRoad = new Integer[100];
    private int num;
    private List<Integer> roadlist;
    private Integer[] road;
    private double adaptability = 0.0;
    private double p_lucky = 0.0;

    GAEntity(int n,String s) {
        num = n;
        roadlist = new ArrayList<Integer>();
        road = new Integer[num];
        InitRoad();
    }

    GAEntity(int n) {
        num = n;
        roadlist = new ArrayList<Integer>();
        road = new Integer[num];
    }
   
    private void InitRoad() {
    	for(int i=0; i<100; i++) {
    		initRoad[i] = i;
    	}
        roadlist = Arrays.asList(initRoad);
        Collections.shuffle(roadlist);
        road = (Integer[]) roadlist.toArray();
    }

    public void setRoad(int i, int j) {
        roadlist.set(i, j);
        road[i] = j;
    }

    public int getRoad(int i) {
        return road[i];
    }

    public double getAdaptability() {
        return adaptability;
    }

    public void setAdaptability(double adaptability) {
        this.adaptability = adaptability;
    }

    public double getP_lucky() {
        return p_lucky;
    }

    public void setP_lucky(double p_lucky) {
        this.p_lucky = p_lucky;
    }

    public String printRoad() {
        String p = "";
        for (int i = 0; i < num; i++) {
            p += "  " + road[i] + ";";
        }
        p+="幸存概率"+p_lucky;
        return p; 
    }

    public double cal_Adaptability() {
        adaptability = 0.0;
        for (int i = 0; i < num - 1; i++) {
            adaptability += Distance.getDistance(road[i], road[i + 1]);
        }
        adaptability +=Distance.getDistance(road[num-1], road[0]);
        return adaptability;
    }

    public double cal_preLucky(double all_ability) {
        p_lucky = 1 - adaptability / all_ability;
        return p_lucky;
    }

    public void cal_Lucky(double all_lucky) {
        p_lucky = p_lucky / all_lucky;
    }

    public void setRoad(GAEntity parent, int position1, int position2) {
        roadlist.clear();
        for (; position1 <= position2; position1++) {
            road[position1] = parent.getRoad(position1);
            roadlist.add(road[position1]);
        }
    }

    public void modifyRoad(GAEntity parent, int position1, int position2, MatchTable matchTable, boolean ifChild1) {
        int roadnum;
        boolean ifModify = false;
        if (ifChild1) {
            for (int i = 0; i < num; i++) {
                if (i >= position1&&i<=position2) {
                    i = position2;
                    continue;
                }
                roadnum = parent.getRoad(i);
                ifModify = checkRoad(roadnum);

                while (ifModify) {
           
                    roadnum = matchTable.getRoadNum(false, roadnum);

                    ifModify = checkRoad(roadnum);
                }
                road[i] = roadnum;
                roadlist.add(roadnum);
            }

        } else {

            for (int i = 0; i < num; i++) {
                if (i >= position1&& i<=position2) {
                    i = position2;
                    continue;
                }
                roadnum = parent.getRoad(i);
                ifModify = checkRoad(roadnum);

                while (ifModify) {

                    roadnum = matchTable.getRoadNum(true, roadnum);

                    ifModify = checkRoad(roadnum);
                }

                road[i] = roadnum;
                roadlist.add(roadnum);

            }
        }

    }

    private boolean checkRoad(int roadnum) {

        if (roadlist.contains(roadnum)) {
            return true;
        }
        return false;
    }

    public void exchange(int p1, int p2) {
        int t = road[p1];
        road[p1] = road[p2];
        road[p2] = t;
    }
    
    public boolean checkdifference(GAEntity g){
for(int i=0;i<num;i++){
    if(road[i]==g.getRoad(i)){
        continue;
    }else{
        return true;
    }
}
        return false;
    }
}
