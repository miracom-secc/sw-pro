package swPro.source;

import java.io.*;
import java.util.*;

// �����˰��� ���
/* Ǯ�� ���
 * 
 * 1. �ѹ��� ��� �Է��� ����
 * 2. �Է� ���� ������ ������������ ����
 * 3. Ʈ�� ����
 *  1) ���� �������� ���� �Ǿ����Ƿ� �̹� �湮�� ���� ����
 *  2) �̹� �湮���� �� ����� ������ ū ���� ����
 *  3) Ʈ���� ����
 *    3-1) ���纸�� ū��쿡�� �̵�.
 *    3-2) �̹� �湮�� ���� ����
 *    3-3) ���� ������ ����ġ�� answer ������ �߰� 
 *        , ����ġ �迭�� ����Ȱ��� �ƴ϶�� ������ answer �迭���� �ش簪�� �����ϰ� ���� ����ġ�� ����.
 *    3-4) ���� ������ ť�� �߰��� 
 * 
 * */
public class P0102_�ּ��������д�Ʈ�� {

	static class Node implements Comparable<Node>{
	//	int u;
		int v; 
		int w;
		
		Node( int v, int w){
			//this.u = u;
			this.v = v;
			this.w = w;
		} 
		@Override
		public int compareTo(Node o) {
			// TODO Auto-generated method stub
			return this.w < o.w ? -1:1;
		}
	}
	static int n,m;
	static ArrayList<Node> arr[]; // �������� �����
	static long cost[];
	static boolean visit[];
	static long INF = Long.MAX_VALUE;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		PriorityQueue<Node> pq = new PriorityQueue<>();
		StringTokenizer st;
		int t= Integer.parseInt(br.readLine());
		
		for(int tc =1; tc <= t; tc++) {
			st = new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			cost = new long[n+1];
			visit = new boolean[n+1];
			arr = new ArrayList[n+1];
			
			for(int i=0; i<n+1; i++) {
				cost[i]=INF;
				arr[i] = new ArrayList<Node>();
			}	
			for(int i=0; i<m; i++) {
				st = new StringTokenizer(br.readLine());
				int u = Integer.parseInt(st.nextToken());
				int v = Integer.parseInt(st.nextToken());
				int w = Integer.parseInt(st.nextToken());

				arr[u].add(new Node(v, w));
				arr[v].add(new Node( u, w));
				
			}

			// ������ ����
			pq.add(new Node(1,0));
			cost[1] =0; 

			long ans=0;
			int cnt=0;
			while(!pq.isEmpty()) {
				Node now = pq.poll();
				
				// ������������ ���ĵǹǷ� �̹� �湮�� ��� pass
				if(visit[now.v]) continue;
				// �̹� ����� ������ ū��� ���ʿ� �����Ƿ� X
				if(cost[now.v]  < now.w) continue;
				visit[now.v] = true;
				cnt++;
				for(Node next: arr[now.v]) {
					
					// ���纸�� ū��쿡�� �̵��ؾ��ϹǷ�.
					if(now.w  > next.w) continue;
					// ������������ ���ĵǹǷ� �̹� �湮�� ��� pass
					if(visit[next.v]) continue;

					if(cost[next.v] > next.w) 
					{
						if(cost[next.v] != INF) {
							ans -= cost[next.v];
						}
						ans +=  next.w;
						cost[next.v] = next.w;
						pq.add(new Node(next.v, next.w));
					}
				}
			}
			bw.write(cnt==n? "#"+tc+" "+ ans+"\n":"#"+tc+" -1\n");
		}
		bw.flush();
		bw.close();
		br.close();
		
	}

}
