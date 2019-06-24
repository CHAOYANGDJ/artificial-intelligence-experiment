package ACO;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class ANT {
	private List<Integer> Tabu;
    private List<Integer> Allowed;
    private double[][] Delta;
    private int currentCity;
    private int citynum;
    private double roadLength;
    private Random random;
    private double alpha;
    private double beta;

    ANT(int citynum, double alpha, double beta) {
        this.citynum = citynum;
        this.alpha = alpha;
        this.beta = beta;
        roadLength = 0.0;
        Tabu = new ArrayList<Integer>();
        Allowed = new ArrayList<Integer>();
        random = new Random();
        for (int i = 0; i < citynum; i++) {
            Allowed.add(i);
        }
        Delta = new double[citynum][citynum];
        InitCurrentCity();
    }

    private void InitCurrentCity() {
        currentCity = random.nextInt(citynum);
        Tabu.add(Allowed.get(currentCity));
        Allowed.removeAll(Tabu);

    }

   
    public void chooseNextCity() {
        while (Allowed.size() > 0) {

            double[] nextcities = CityGraph.getCities(currentCity);
           
            int tempcity = nextcities.length - 1;
            double all_p = 0.0;
            for (int i = 0; i < nextcities.length; i++) {
                all_p += nextcities[i];
            }
            while (true) {
                double p = all_p * random.nextDouble();
                double temp = 0.0;
                for (int i = 0; i < nextcities.length; i++) {
                    temp += nextcities[i];
                    if (p < temp) {
                        tempcity = i;
                        break;
                    }
                }
                if (!(tempcity == currentCity) && !Tabu.contains(tempcity)) {
                    break;
                }
            }
            roadLength += CityGraph.getDistance(currentCity, tempcity);           
            currentCity = tempcity;
            Tabu.add(currentCity);
      
          Allowed.removeAll(Tabu);
        }
         roadLength += CityGraph.getDistance(Tabu.get(currentCity), Tabu.get(0));          
    }

    public double getRoadLength() {
        return roadLength;
    }

    public String getRoad() {
        String p = "";
        for (int i = 0; i < citynum; i++) {
            p += Tabu.get(i) + ";";
        }
        return p;
    }
    
        public void  getAllowed() {
        String p = "";
        for (int i = 0; i < Allowed.size(); i++) {
            p += Allowed.get(i) + ";";
        }
        System.out.println(p);
    }
        
    public void updatePheromone(double Q,long t){
        Q = Q/roadLength;
        for(int i=0;i<Tabu.size()-1;i++){
            CityGraph.setPhero(Tabu.get(i), Tabu.get(i+1), Q,t);
        }
         CityGraph.setPhero(Tabu.get(Tabu.size()-1), Tabu.get(0), Q,t);
    }
}
