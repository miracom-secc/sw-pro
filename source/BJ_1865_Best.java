package swPro.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BJ_1865_Best {
	static final int INF = 1000000000;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		int TC = Integer.parseInt(st.nextToken());

		for(int t=0; t<TC; t++) {
			st = new StringTokenizer(br.readLine().trim());
			int N = Integer.parseInt(st.nextToken());	//������ ��
			int M = Integer.parseInt(st.nextToken());	//������ ����
			int W = Integer.parseInt(st.nextToken());	//��Ȧ�� ����

			ArrayList<Edge> edgeList = new ArrayList<>();
			/* ����� ��� ����ġ */
			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine().trim());
				int S = Integer.parseInt(st.nextToken());	//S,E ����� ����
				int E = Integer.parseInt(st.nextToken());
				int T = Integer.parseInt(st.nextToken());	//�ɸ��� �ð�
				edgeList.add(new Edge(S, E, T));
				edgeList.add(new Edge(E, S, T));
			}
			
			/* �ܹ��� ���� ����ġ */
			for (int i = 0; i < W; i++) {
				st = new StringTokenizer(br.readLine());
				int S = Integer.parseInt(st.nextToken());	//��������
				int E = Integer.parseInt(st.nextToken());	//��������
				int T = Integer.parseInt(st.nextToken());	//�پ��� �ð�
				edgeList.add(new Edge(S, E, (-1) * T));
			}

			int[] nodes = new int[N+1];
			Arrays.fill(nodes, INF);
			nodes[1] = 0;
			boolean isUpdated = false;

			// N-1 Edge Relaxation(N-1�� ������������ �ִ� ���)
			for(int i = 1; i <= N; i++) {
				isUpdated = false;
				for(Edge edge : edgeList) {
					if(nodes[edge.end] > nodes[edge.start] + edge.time) {
						nodes[edge.end] = nodes[edge.start] + edge.time;
						isUpdated = true;
						// N�� ����ÿ��� ���ŵǴ� ���� ������ ����Ŭ ����
						if (i == N) {
							isUpdated = true;
						}
					}
				}
				if(!isUpdated) break;
			}
			sb.append((isUpdated ? "YES" : "NO") + "\n");
		}
		System.out.println(sb);
	}
}

class Edge {
	int start, end, time;

	Edge(int start, int end, int time) {
		this.start = start;
		this.end = end;
		this.time = time;
	}
}