package swPro.source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * ���� ���� 
 * - �ٴ� ���� �ִ� ��� ���� �ٸ��� ���� 
 * - ���� 1*1�� ���� �����¿�� �پ� �ִ� ��� 
 * - �ٸ��� �ٴٿ��� ���������� �Ǽ� ����, ���̴� 2�̻�
 * 
 * ���� Ǯ�� 
 * 1. �Է¹��� ������ �����κ��� ���� �ĺ� (0�� �ٴ�, 1�� ��) 
 * 2. �Ǽ������� �ٸ� ã�� (�������� ����) 
 * 3. ��� ���� �����ϴ� �ٸ� ������ �ּڰ� ���ϱ� 
 * 
 * �ʿ� ����
 * - map : ���� ���� �Է�
 * - island : �� ���� ��ȣ�� �ٿ� ���� (�� �ĺ��ϱ�)
 * - parents : �� ����� ����Ŭ�� �߻����� �ʵ��� �������� ���� 
 */

public class BJ_17472_Best{  // ���� 17472
	static int N,M;
	static int[][] map, island;
	static int[] dy = {-1,1,0,0}; // ��, ��, ��, �� 
	static int[] dx = {0,0,-1,1};
	static int[] parents;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine().trim());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		island = new int[N][M];
		
		Queue<int[]> conn = new LinkedList<>(); // �ٴٿ� ������ ���� ����(��ǥ �� �ٴٸ� �ٶ󺸴� ����)�� �����ϴ� ����Ʈ 
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine().trim());
			for(int j=0; j<M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken()); // 1:��, 0:�ٴ�
			}
		}
		
		// ���� ��ȣ�� �ٿ� �ĺ��ϱ� 
		int idx = 1;
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(map[i][j] > 0 && island[i][j]==0) {
					findIsland(conn, i, j, idx);
					idx++;
				}
			}
		}
		
		ArrayList<int[]> edges = new ArrayList<>(); // �ٸ� ������ �����ϴ� ����Ʈ 
		
		while(!conn.isEmpty()) {
			int[] n = conn.poll();
			
			int toY = n[0]+dy[n[2]];  
			int toX = n[1]+dx[n[2]];

			int dis = 1;
			
			while(true) {
				toY += dy[n[2]];
				toX += dx[n[2]];
				
				if(!isRange(toY,toX)) break;
				if(map[toY][toX] > 0 ) { // ���� �����ߴµ�
					if(island[toY][toX] != island[n[0]][n[1]] && dis>=2) { // �ٸ� ���� ������? + �Ÿ��� 2�̻�
						edges.add(new int[] {island[n[0]][n[1]], island[toY][toX], dis}); // ���� �߰�
					}
					break;
				}
				dis++;
			}
		}

		// �Ÿ� �������� ����
		Collections.sort(edges, new Comparator<int[]>() {

			@Override
			public int compare(int[] o1, int[] o2) {
				// TODO Auto-generated method stub
				return o1[2]-o2[2];
			}
		});
		
		// ��� ���� �ּұ����� �ٸ��� �����ϱ�
		int ans = 0;
		int cnt = 1;
		
		parents = new int[idx];
		for(int i=1; i<idx; i++) {
			parents[i] = i;
		}
		
		for(int i=0; i<edges.size(); i++) {
			int[] edge = edges.get(i);
			int left = findParents(edge[0]);
			int right = findParents(edge[1]);
			if(left != right) {
				parents[right] = left;
				ans += edge[2];
				cnt++;
			}
			if(cnt==idx-1) break;
		}
		if(cnt!=idx-1) ans=-1;
		
		System.out.println(ans==0? -1: ans);
	}
	
	
	static int findParents(int x) {
		if(parents[x] == x) return x;
		
		return parents[x] = findParents(parents[x]);
	}
	
	static void findIsland(Queue<int[]> conn, int y, int x, int idx) {
		Queue<int[]> land = new LinkedList<>();

		land.add(new int[] {y,x});
		island[y][x] = idx;
		
		while(!land.isEmpty()) {
			int[] n = land.poll();
			
			for(int k=0; k<4; k++) {
				int toY = n[0] + dy[k];
				int toX = n[1] + dx[k];
				
				if(!isRange(toY,toX) || island[toY][toX] > 0) continue;  // ���� ���̰ų� �̹� ������ �ľ��� �����̸� �ǳʶٱ�
				
				
				if(map[toY][toX] == 0) {  // �ٴٿ� ������ ������ ť�� �־��ְ� �ǳʶٱ�
					conn.add(new int[] {n[0], n[1], k}); // ���� ��ǥ, �ٴٸ� �ٶ󺸴� ����
					continue;
				}
				
				island[toY][toX] = idx;
				land.add(new int[] {toY, toX});
			}
		}
	}
	
	static boolean isRange(int y , int x ) {
		if(y<0 || y>=N || x<0 || x>=M) return false;
		return true;
	}
}
