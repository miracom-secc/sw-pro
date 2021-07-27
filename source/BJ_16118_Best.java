package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


/*
ù �ٿ� ���� �׷��ͱ��� ������ ���ֱ��� ������ �ǹ��ϴ� ���� N, M(2 �� N �� 4,000, 1 �� M �� 100,000)�� �־�����.

�� ��° �ٺ��� M���� �ٿ� ���� �� �ٿ� �� ���� ���� a, b, d(1 �� a, b �� N, a �� b, 1 �� d �� 100,000)�� �־�����. 
�̴� a�� �׷��ͱ�� b�� �׷��ͱ� ���̿� ���̰� d�� ���ֱ��� �� ������ �ǹ��Ѵ�.

���
ù �ٿ� �޺� ���찡 �޺� ���뺸�� ���� ������ �� �ִ� ���� �׷��ͱ��� ������ ����Ѵ�.


5 6
1 2 3
1 3 2
2 3 2
2 4 4
3 5 4
4 5 3


1

ù �ٿ� �޺� ���찡 �޺� ���뺸�� ���� ������ �� �ִ� ���� �׷��ͱ��� ������ ����Ѵ�.
*/
public class BJ_16118_Best {
	
	static class Node implements Comparable<Node>{
		int v;
		long w;
		int st;
		
		Node(int v, long w){ // �䳢��
			this.v = v;
			this.w = w;
		}
		Node(int v, long w,int st){ // ����� ��ġ, ����ġ, ����
			this.v = v;
			this.w = w;
			this.st = st;
		}
		@Override
		public int compareTo(Node o) {
			return this.w < o.w ? -1 :1;
		}
	}
	
	static int N, M ;
	static ArrayList<Node>[] arr ;
	static long dist_f[] ; // ���첨
	static long dist_w[][]; // ���벨
	static long INF = Long.MAX_VALUE;
		

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		dist_f = new long[N+1];
		dist_w = new long[2][N+1]; // 0 : �ش� ������ ������ �޷����� ���� �ּ� �ð�. 1 : �ش� ������ ������ �޷����� ���� �ּ� �ð�. ���� ���� 0�̸� �̹����� /2 �ӵ��� ������ �� ����
		
		Arrays.fill(dist_f, INF);
		for(int i=0; i<N+1; i++){
			dist_w[0][i] = INF;
			dist_w[1][i] = INF;
		}
		arr = new ArrayList[N+1];
		
		for(int i=0; i<N+1; i++) {
			arr[i] = new ArrayList<Node>();
		}
		
		for(int i=0; i< M; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			long w = Long.parseLong(st.nextToken());
			
			// �����
			arr[u].add(new Node(v,2*w));  // /2 �� ������ ����ϱ� ���ϰ� ó������ *2 �ϰ� ����
			arr[v].add(new Node(u,2*w));
		}
		
		// �䳢�� ���ͽ�Ʈ��
		dist_f[1] =0;
		dijsktra_f(1);

		dijsktra_w(1);

		int ans =0; // �׷��ͱ� ī��Ʈ��
		for(int i=2; i<N+1; i++) {
			if(dist_f[i] <  Math.min(dist_w[0][i], dist_w[1][i]) )  // �� ������ ������ �ִܰŸ� < �� ������ *2 �� �ӵ��� �������� ����� �ִܰŸ�, /2 �� �ӵ��� �������� ����� �ִܰŸ� �� �ּҰ�
				ans++;
		}
		
		bw.write(ans +"\n");
		bw.flush();
		bw.close();
		br.close();
		
	}


	static void dijsktra_w(int start) {
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		
		pq.offer(new Node(1,0,0)); // ������ ������ �Ҳ��ϱ� 
		// ����� ���ͽ�Ʈ��
		dist_w[0][1] =0;  // ���ͽ�Ʈ�� �������� �ʱ�ȭ (ó���� ������ ������ ����(/2 �ӵ�)�ϴ� [0][1] ������ �������� ����. ���� ������ ������ �����ߴٰ� ����)
		//boolean speed = true;
		
		while(!pq.isEmpty()) {
			
			Node now = pq.poll();
			int now_v= now.v;			
			//System.out.println("dist_w["+now.st+"]["+now_v+"] :"+dist_w[now.st][now_v]+" | now.w: "+ now.w);
			if(dist_w[now.st][now_v] < now.w) {	// �ش� ������ �ּҽð����� ���� �ش� ������ ������� �ɸ� �ð��� ũ�� ��� X
				continue;
			}
			
			for(Node next: arr[now_v]) {
				//long next_w =0;
				int st  = 1 - now.st;	// 0 �̸� 1��, 1�̸� 0���� ����ġ ��ȯ ���� 
				long cost = dist_w[now.st][now_v] + ((now.st == 0) ? next.w / 2 : next.w * 2);	// ���°� 0�̸� /2 �� �ӵ�, 1�̸� *2 �Ǽӵ� 

				if(dist_w[st][next.v] > cost) {
					dist_w[st][next.v] = cost;
					pq.offer(new Node(next.v, cost, st));
				}
			}	
		}
		
	}
	



	static void dijsktra_f(int start) {
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		pq.add(new Node(start,0));
		
		
		while(!pq.isEmpty()) {
			Node now = pq.poll();
			int now_v= now.v;
			
			if(dist_f[now_v] < now.w) continue; 	// �ش� ������ �ּҽð����� ���� �ش� ������ ������� �ɸ� �ð��� ũ�� ��� X
			
			for(Node next: arr[now_v]) {
				if(dist_f[next.v] > dist_f[now_v]+ next.w) {
					dist_f[next.v] = dist_f[now_v]+ next.w;
					pq.add(new Node(next.v, dist_f[next.v]));
					
				}
			}				
		}
		
	}

}