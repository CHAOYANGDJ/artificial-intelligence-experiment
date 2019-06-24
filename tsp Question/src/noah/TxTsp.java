package noah;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import BAB.Timer;
public class TxTsp {
	private int cityNum; // ��������
	private int[][] distance; // �������
 
	private int[] colable;//�����У�Ҳ��ʾ�Ƿ��߹����߹���0
	private int[] row;//�����У�ѡ����0
 
	public TxTsp(int n) {
		cityNum = n;
	}
 
	private void init(String filename) throws IOException {
		// ��ȡ����
		int[] x;
		int[] y;
		String strbuff;
		BufferedReader data = new BufferedReader(new InputStreamReader(
				new FileInputStream(filename)));
		distance = new int[cityNum][cityNum];
		x = new int[cityNum];
		y = new int[cityNum];
		for (int i = 0; i < cityNum; i++) {
			// ��ȡһ�����ݣ����ݸ�ʽ1 6734 1453
			strbuff = data.readLine();
			// �ַ��ָ�
			String[] strcol = strbuff.split(" ");
			x[i] = Integer.valueOf(strcol[1]);// x����
			y[i] = Integer.valueOf(strcol[2]);// y����
		}
		data.close();
 
		// ����������
		
		for (int i = 0; i < cityNum - 1; i++) {
			distance[i][i] = 0; // �Խ���Ϊ0
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
			i = j;//��ǰ�ڵ�ָ����һ�ڵ�
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
		//�ӿ��ýڵ�J��ʼ����ɨ�裬�ҳ�������С�ڵ�
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
		ts.init("G:\\�˹�����ʵ��\\TSP����\\TSP100cities.txt");
		ts.solve();
		timer.stop();
		System.out.println("time: "+ timer.getFormattedTime());
	}
}