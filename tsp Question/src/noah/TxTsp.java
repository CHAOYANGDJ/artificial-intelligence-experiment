package noah;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import BAB.Timer;
public class TxTsp {
	private int cityNum; // 城市数量
	private int[][] distance; // 距离矩阵
 
	private int[] colable;//代表列，也表示是否走过，走过置0
	private int[] row;//代表行，选过置0
 
	public TxTsp(int n) {
		cityNum = n;
	}
 
	private void init(String filename) throws IOException {
		// 读取数据
		int[] x;
		int[] y;
		String strbuff;
		BufferedReader data = new BufferedReader(new InputStreamReader(
				new FileInputStream(filename)));
		distance = new int[cityNum][cityNum];
		x = new int[cityNum];
		y = new int[cityNum];
		for (int i = 0; i < cityNum; i++) {
			// 读取一行数据，数据格式1 6734 1453
			strbuff = data.readLine();
			// 字符分割
			String[] strcol = strbuff.split(" ");
			x[i] = Integer.valueOf(strcol[1]);// x坐标
			y[i] = Integer.valueOf(strcol[2]);// y坐标
		}
		data.close();
 
		// 计算距离矩阵
		
		for (int i = 0; i < cityNum - 1; i++) {
			distance[i][i] = 0; // 对角线为0
			for (int j = i + 1; j < cityNum; j++) {
				double rij = Math
						.sqrt(((x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j])
								* (y[i] - y[j])) / 10.0);
			
				int tij = (int) Math.round(rij);
				if (tij < rij) {
					distance[i][j] = tij + 1;
					distance[j][i] = distance[i][j];
				} else {
					distance[i][j] = tij;
					distance[j][i] = distance[i][j];
				}
			}
		}
 
		distance[cityNum - 1][cityNum - 1] = 0;
 
		colable = new int[cityNum];
		colable[0] = 0;
		for (int i = 1; i < cityNum; i++) {
			colable[i] = 1;
		}
 
		row = new int[cityNum];
		for (int i = 0; i < cityNum; i++) {
			row[i] = 1;
		}
 
	}
	
	public void solve(){
		
		int[] temp = new int[cityNum];
		String path="0";
		
		int s=0;
		int i=0;
		int j=0;
		
		while(row[i]==1){
			
			for (int k = 0; k < cityNum; k++) {
				temp[k] = distance[i][k];
				
			}
			
			j = selectmin(temp);
			
			row[i] = 0;
			colable[j] = 0;
			path+=" " + j;
			s = s + distance[i][j];
			i = j;//当前节点指向下一节点
		}
		System.out.println(path);
	}
	
	public int selectmin(int[] p){
		int j = 0, m = p[0], k = 0;
		while (colable[j] == 0) {
			j++;
			if(j>=cityNum){
				m = p[0];
				break;
			}
			else{
				m = p[j];
			}
		}
		//从可用节点J开始往后扫描，找出距离最小节点
		for (; j < cityNum; j++) {
			if (colable[j] == 1) {
				if (m >= p[j]) {
					m = p[j];
					k = j;
				}
			}
		}
		return k;
	}
 
 
	public void printinit() {
		System.out.println("print begin....");
		for (int i = 0; i < cityNum; i++) {
			for (int j = 0; j < cityNum; j++) {
				System.out.print(distance[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("print end....");
	}
 
	public static void main(String[] args) throws IOException {
		Timer timer = new Timer();
		timer.start();
		System.out.println("Start....");
		TxTsp ts = new TxTsp(100);
		ts.init("G:\\人工智能实验\\TSP数据\\TSP100cities.txt");
		ts.solve();
		timer.stop();
		System.out.println("time: "+ timer.getFormattedTime());
	}
}