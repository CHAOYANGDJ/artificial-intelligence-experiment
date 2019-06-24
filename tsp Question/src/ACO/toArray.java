package ACO;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class toArray {

	public String[][] myToArray() {
		List<String[]> data = new ArrayList<>();
		 
        try {
            Scanner in = new Scanner(new File("G:\\人工智能实验\\TSP数据\\TSP100cities.txt"));
 
            while (in.hasNextLine()) {
                String str = in.nextLine();
 
                data.add(str.split("[\\t \\n]+"));
                //System.out.print(data.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        // list转化为数组
        String[][] result = data.toArray(new String[][] {});
        
// debug:
//       for (int i=0 ; i<3 ; i++ ) {
//        	for(int j=0 ;j<3 ;j++) {
//       		System.out.print(result[i][j]+" ");
//        	}
//        	System.out.print("\n");
//        }
        return result;
	}
	public double[][] toMatrix(String[][] result1) {
		String[][] r = result1;
		int x=0 , y=0 , num = 100;
		double[][] distance = new double[num][num]; 	 
		for (int i = 0; i < r.length; i++) {
            for (int j = i ; j < r.length; j++) {
            	x = Integer.parseInt(r[j][1]) - Integer.parseInt(r[i][1]);
            	y = Integer.parseInt(r[j][2]) - Integer.parseInt(r[i][2]);
                distance[i][j] = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) ;
                distance[j][i] = distance[i][j];
            }
            distance[i][i] = 0;
        }
		 for (int i=0 ; i<num ; i++ ) {
	        	for(int j=0 ;j<num ;j++) {
	       		System.out.print(distance[i][j]+" ");
	        	}
	        	System.out.print("\n");
	        }
		 return distance;
	}
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		 toArray to= new toArray();
		 to.toMatrix(to.myToArray());
    }
}
