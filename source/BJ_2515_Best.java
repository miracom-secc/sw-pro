package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/*
6 4
15 80
8 230
10 100
17 200
20 75
26 80

9 3
8 30
5 10
14 50
12 80
8 20
16 50
11 60
15 40
10 50
 */

public class BJ_2515_Best {
	
	static int N, S;
	static int dp[];
	static List<Picture> pictures;

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());

		pictures = new ArrayList<Picture>();
		dp = new int[N]; 
		
		for (int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			int H = Integer.parseInt(st.nextToken());
			int C = Integer.parseInt(st.nextToken());
			
			pictures.add(new Picture(H, C));
		}
		
		Collections.sort(pictures);
		
		//======== �׸��� �����ϰų� ���� �ʰų� �ϸ鼭 i��° �׸������� �ְ� �ݾ� ã��
		dp[0] = pictures.get(0).cost;
		for (int i=1; i<N; i++) {
			int idx = bs(i, pictures.get(i).hight-S); // S���̺��� ���� �׸� ã��
//			System.out.println("idx= "+idx);
			if (idx != -1) {
				// i���� �׸��� �������� ������ �����׸������� ���(dp[i-1])
				// �����ϸ� ���� ����ġ + ���Ű����� �׸��� �ִ밪 (dp[idx] + cost)
				dp[i] = Math.max(dp[i-1], dp[idx]+pictures.get(i).cost); 
			} else {
				dp[i] = Math.max(dp[i-1], pictures.get(i).cost);
			}
		} // N
		
		
		// �� �ð� ���⵵ : NlogN
		bw.write(dp[N-1]+"");
		
		br.close();
		bw.close();
	}
	
	static int bs(int e, int findnum) {
		int s = 0;

		int ret = -1;
		while(s <= e) {
			int mid = (s+e) / 2;
			if (pictures.get(mid).hight > findnum) {
				e = mid-1;
				ret = e;
			} else {
				s = mid+1;
			}
		}
		
		return ret;
	} // logN

}

class Picture implements Comparable<Picture>{
	int hight;
	int cost;
	
	Picture( int hight, int cost) {
		this.hight = hight;
		this.cost = cost;
	}

	@Override
	public int compareTo(Picture right) {
		if(hight < right.hight) return -1;
		if(hight > right.hight) return 1;
		if(cost < right.cost) return 1;
		if(cost > right.cost) return -1;
		return 0;
	}
	
}
