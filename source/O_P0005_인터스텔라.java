package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
3
6 6 0
1 2 3
5 1 2
5 6 5
3 4 3
6 4 6
2 3 3
1 4
6 6 1
1 2 3
5 1 2
5 6 5
3 4 3
6 4 6
2 3 3
1 4
6 6 2
1 2 3
5 1 2
5 6 5
3 4 3
6 4 6
2 3 3
1 4
*/

public class O_P0005_���ͽ��ڶ� {
	
	static class Node implements Comparable<Node>{
		int v; 
		long w;
		int k;
		
		Node(int v, long w, int k){
			this.v = v;
			this.w = w;
			this.k = k;
		}
		@Override
		public int compareTo(Node o) {
			// TODO Auto-generated method stub
			return this.w < o.w ? -1:1;
		}
	}
	static int n,m,k; // �༺�� ����, �༺�� ���� ����, ������ ����
	static ArrayList<Node> arr[];
	static int start, end; // ���� ������, ���� ������ ==> �ѻ����� �ִܰŸ��� ���ϴ� ����.
	static long dist[][]; // 
	static long INF = 999999900;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st= new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		int tc = Integer.parseInt(st.nextToken()); // TC ����
		int de=1;
		for(int t=1; t<=tc ; t++) {
			st= new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken());  // �༺�� ����
			m = Integer.parseInt(st.nextToken());  // �༺�� ���� ����
			k = Integer.parseInt(st.nextToken());  // ������ ����
			
			// �ʱ�ȭ
			dist = new long [k+1][n+1]; // ������ ��, �༺�Ǽ�
			arr = new ArrayList[n+1];
			
			for(int i=0; i<k+1; i++) {
				Arrays.fill(dist[i], INF);
			}
			for(int i=0; i<n+1; i++) {
				arr[i] = new ArrayList<Node>();
			}			
			
			for(int i=0; i< m ; i++) {
				st= new StringTokenizer(br.readLine());
				int u = Integer.parseInt(st.nextToken());  // �༺ 1
				int v = Integer.parseInt(st.nextToken());  // �༺2
				long w = Long.parseLong(st.nextToken());  // �ѻ����� �Ÿ�
				
				arr[u].add(new Node(v,w,k)); // �������� ������ 2�� ������ ������������ ����.
				arr[v].add(new Node(u,w,k));				
			}
			de=1;
			st= new StringTokenizer(br.readLine());
			start = Integer.parseInt(st.nextToken());  // ���� ������
			end = Integer.parseInt(st.nextToken());  // ���� ������ 
			
			// ������ �ʱ�ȭ.
			for(int i=0; i<k+1; i++) {
				dist[i][start] =0;
			}
			//Ž��
			dijkstra(start, end, k);
			
			long ans= INF;
			for(int i=0; i<k+1; i++) {
				ans = Math.min(ans, dist[i][end]); // ���� ��밹���� ��� ���� ���� �����ߴ��� Ȯ�� 
			}
			//System.out.println(ans +"===================");
			sb.append("#"+t+" "+ans+"\n");
			de=1;
		}
		bw.write(sb.toString());
		bw.flush();
		bw.close();
		br.close();
		
	}
	
	static void dijkstra(int start, int end, int k) {
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		pq.add(new Node(start, 0, k)); // Ž�� ��������, 0, ���� ���� ���������� ���� �״��.
		
		while(!pq.isEmpty()) {
			Node now = pq.poll();
			int now_v = now.v; // ����ġ
			long now_w = now.w; // ������ ����ð�
			int now_k = now.k;//���� �����Ǽ�!
			
			if(now_v == end) break; //���ϴ� ������ ���������� break �ϰ� ����������
						
			for(Node next: arr[now_v]) {
				int next_v = next.v;
				long next_w = next.w;

				if(next_v == start) continue;
				
				long cost = now_w + next_w;
				
				//System.out.println("now_v:"+now_v+" |now_k: "+now_k+" |now_k:"+now_k+"|next_v:"+next_v+" |next_w:"+next_w);
				
				//System.out.println("1: dist["+now_k+"]["+next_v+"]="+dist[now_k][next_v]+" / cost:"+ cost);
				//1. ���� ������
				if(dist[now_k][next_v] > cost) { 
					dist[now_k][next_v] = cost;
					pq.add(new Node(next_v, cost, now_k)); // ���� ��� ��������  k�� ���粨 �״�� ���.
				}
				
				
				// 2. ���� ����
				if(now_k > 0 && dist[now_k-1][next_v] > now_w +1 ) { // ���� ���� �̵��ð��� 1�̱� ������ cost�� �ƴ� ������ ����ð��� +1����
				//	System.out.println("2: dist["+(now_k-1)+"]["+next_v+"]="+dist[now_k-1][next_v]+" /now_w +1: "+ (now_w +1));
					dist[now_k-1][next_v] = now_w +1;
					pq.add(new Node(next_v, now_w+1, now_k-1 )); //���� ��������� ���� --
				}
			}
				
					
		}

	}
	
}
