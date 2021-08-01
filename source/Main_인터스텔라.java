package swPro.source;

import java.io.*;
import java.util.*;

/*
 * dist[i][j] : i��° �༺���� j���� ������Ŷ�� ������ �������� ���� �ð�
 *  	- dist[4][0] : 4��° �༺���� 0���� ������Ŷ�� ������ ������ �ð��� �ǹ�
 * adj : �༺���� �������� ����
 * MAX_VALUE : �����༺���� �����༺���� �����ϴµ� �ɸ��� �ð��� �ִ�+1
 * 		- �ִ� : �༺ �ִ� 10����, �༺ �� ����ð� �ִ� 10�� = 10��*10��
 * 
 */
public class Main_���ͽ��ڶ� {
	static int N,M,K,FROM,TO; // N: [2:100,000] , M:[1:200,000], K:[0:2]
	static long ANS;	// ����� ���� 32��Ʈ ���� ������ ǥ���� �� �ִ� ������ ���� �� �����Ƿ� long���
	static long[][] dist;
	static ArrayList<Node>[] adj;
	static long MAX_VALUE = 100000*100000+1;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = null;
		
		int TC = Integer.parseInt(br.readLine().trim());
		
		for(int tc=1; tc<=TC; tc++) {
			st = new StringTokenizer(br.readLine().trim());
			
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			// ���� �ʱ�ȭ 
			adj = new ArrayList[N+1];
			dist = new long[N+1][K+1];
			ANS = MAX_VALUE;
			
			for(int i=1; i<N+1; i++) {
				adj[i] = new ArrayList<>();	
				Arrays.fill(dist[i], MAX_VALUE); 
			}
			
			// �༺�� �������� �Է�
			for(int i=0; i<M; i++) {
				st = new StringTokenizer(br.readLine().trim());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				int cost = Integer.parseInt(st.nextToken()); // [1:100,000]
				
				adj[a].add(new Node(b,cost,0));
				adj[b].add(new Node(a,cost,0));
			}
			
			// ������ġ, ������ġ �Է�
			st = new StringTokenizer(br.readLine().trim());
			FROM = Integer.parseInt(st.nextToken());
			TO = Integer.parseInt(st.nextToken());
			
			// �ɸ��� �ð� ���
			dijkstra();
			
			sb.append("#").append(tc).append(" ").append(ANS).append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
	
	static void dijkstra() {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		
		pq.add(new Node(FROM, 0, K)); // �����༺(FROM)���� K�� ������Ŷ�� ������ ���
		Arrays.fill(dist[FROM], 0);
		
		while(!pq.isEmpty()) {
			Node n = pq.poll();
			
			if(n.planet == TO) { // �����ϸ� �ּڰ� ���
				ANS = Math.min(ANS, dist[TO][n.k]);
				break;
			}
			
			if(dist[n.planet][n.k] < n.cost) continue;
			
			for(Node next: adj[n.planet]) {
				// �ϴ� �̵�
				if(dist[next.planet][n.k] > dist[n.planet][n.k]+next.cost) {
					dist[next.planet][n.k] = dist[n.planet][n.k]+next.cost;
					pq.add(new Node(next.planet, dist[next.planet][n.k], n.k));
				}
				
				// ������Ŷ ����ؼ� �̵�
				if(n.k>0 && dist[next.planet][n.k-1] > dist[n.planet][n.k]+1) {
					dist[next.planet][n.k-1] = dist[n.planet][n.k]+1;
					pq.add(new Node(next.planet, dist[next.planet][n.k-1], n.k-1));
				}
			}
		}
	}
	
	static class Node implements Comparable<Node>{
		int planet;
		long cost;
		int k;
		
		public Node(int planet, long cost, int k) {
			this.planet = planet;
			this.cost = cost;
			this.k = k;
		}

		@Override
		public int compareTo(Node o) {
			// �ð��� �������� �������� ����
			return Long.compare(this.cost, o.cost);
		}
	}
}
