package swPro.source;

/*
3
12 3 
0 3
3 4
5 5 
3 6
2 8
4 8
5 0
2 1
4 1
6 2
5 3
7 7
4 10 12
5 5
4 5
16 14
7 17
6 15
11 13
1 2 5 3 4
10 1
19 8
9 10
6 3
4 17
4 16
10 4
13 0
20 20
4 3
14 9
6
 * */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


/**
���� Ǯ�� ����
 1. �Է�
    - ������ ��ȿ������ ��������� ����� ��ü �ʿ� (===> City ��ü)
 2. �Է¹��� ��ǥ���� ���� �����Ű�°�ó�� �ؼ� �����ǥ���� ���� �Ÿ��� ����Ͽ� ������ 
    - �������� �Ÿ� ������ ������ ��ü �����ʿ�. (===>Edge ��ü)
    - �������� ����� (x1-x2)^2 _+ (y1-y2)^2
 3. ������ ������ pq �� �־��ְ�, dijkstra ����.
     => ** ��ģ�κ� : �������κ��� �ִܰŸ��� �ƴ϶� ���������� �ִܰŸ��� ����� ����������� �ִܰŸ��� ���ؾ� �ϹǷ�
                    ���� �ϳ��� �����ؼ� �̹� �ִܰŸ��� ������ ��� �ش� ������ ���� �߰��ϰ� �ű������ dist ���� 0���� �ʱ�ȭ.
 4. �� ��ǥ���� ���� ����
*/
public class O_P0079_���ð�ȹ {
	
	static class City{
		int idx; // ����
		int x; // x��ǥ
		int y; // y��ǥ
		
		City(int idx, int x, int y){
			this.idx = idx;
			this.x = x;
			this.y = y;
		}
	}
	
	static class Node implements Comparable<Node>{
		int v; //2�� ������ ��ȣ
		long w;  //�� ���ð��� �Ÿ� 
		
		Node(int v, long w){
			this.v = v;
			this.w = w; 
		}

		@Override
		public int compareTo(Node o) {
			// TODO Auto-generated method stub
			return this.w < o.w ?-1 : 1;
		}
	}
	
	static int n,m; // ���ð���, ������ ����
	static City city[];
	static ArrayList<Node>[] arr;
	static long dist[];
	static long dist_w[];
	static long INF =Long.MAX_VALUE;
	static int suwon[];
	static PriorityQueue<Node> pq;
	static long ans;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		//tc �Է�
		StringTokenizer st = new StringTokenizer(br.readLine());
		int tc = Integer.parseInt(st.nextToken());
		
		for(int t=1; t<=tc; t++) {
			//******* 1. �Է� *******//
			//  1) n (������ ��),m (�������� ��) 
			st = new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			
			pq = new PriorityQueue<Node>();
			city = new City[n+1];
			dist = new long[n+1];
			dist_w = new long[n+1];
			arr = new ArrayList[n+1];
			Arrays.fill(dist, INF);
			
			for(int i=1; i<n+1; i++) {
				arr[i]= new ArrayList<Node>();
			}
			
			// 2) ��ǥ�� �������
			for(int i=1; i<n+1; i++) {
				st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				city[i] = new City(i,x,y);			
			}
			
			// 3) ������ ������ ������� ť�� ����.
			st = new StringTokenizer(br.readLine());
			for(int i=0; i<m; i++) {
				int start = Integer.parseInt(st.nextToken());
				dist[start] =0;
				pq.add(new Node(start,0));
			}
			
			
			//******* 2. ��� ��ǥ�� �Ÿ��� ��� *******//
			calc_distance();

			//******* 3. ��� �������� �������� ���ͽ�Ʈ�� ���� *******//
			ans = dijkstra();
			bw.write(ans+"\n");
		}
		bw.flush();
		bw.close();
		br.close();

	}

	static long dijkstra() {
		long ret=0;
		while(!pq.isEmpty()) {
			Node now = pq.poll();
			int now_v= now.v;

			if(dist[now_v] < now.w) continue;

			//=============== ��ģ�κ� ================//
			ret += dist[now_v]; // �̹� �ּҷ� ����� �κ��̴ϱ� ���ϰ��� �߰� 
			dist[now_v]=0; // �̹� ����� �Ϸ�Ǿ���, ������ ������ �ƴ϶� �׳� ���� ����� ������ �����ؾ��ϴ� 0���� ����(�տ������� ����Ǿ� ������ ������ �� ����X)
			//==========================================//
			for(Node next : arr[now_v]) {
				if(dist[next.v] > next.w) {
					dist[next.v] = next.w;
					pq.add(new Node(next.v, dist[next.v]));	
				}
			}						
		}	
		return ret;
	}

	//2. ��� ��ǥ�� �Ÿ��� ���
	static void calc_distance() {
		
		for(int i=1; i<n+1; i++) {
			City idx1 = city[i];
			for(int j=i+1; j<n+1; j++) {
				City idx2 = city[j];
				long dis = (long) (Math.pow((idx1.x-idx2.x), 2) + Math.pow((idx1.y - idx2.y), 2));
				arr[idx1.idx].add(new Node(idx2.idx, dis));
				arr[idx2.idx].add(new Node(idx1.idx, dis));
				
			}
		}
		
	}

}

