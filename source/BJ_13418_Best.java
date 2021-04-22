package swPro.source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/*
 * �ذ� ���� 
 * 1. �ǹ��� ��� �湮�ϴ� �̵����
 * 2. ������ �Ա�(0��)�� 1�� �ǹ��� ����
 * 3. �������� K�� -> K^2 �Ƿε� �߻� 
 * 
 * - �־��� �ڽ� : �Ƿε��� �ִ� = �������� �ִ� Ƚ�� �߻�
 * - ������ �ڽ� : �Ƿε��� �ּ� = �������� �ּ� Ƚ�� �߻� 
 * => ������ ��� ã��
 * 
 *  �ʿ� ���� 
 *  minP, maxP : �̵���ΰ� ����Ŭ�� �����ϴ��� �Ǵ��� �迭
 *  cntMin, cntMax : �Ƿε��� ���� �ִ�, �ּ��� �� �������� Ƚ������ 
 */

public class BJ_13418_Best {
	static int N,M;
	
	static class Node implements Comparable<Node>{
		int from;
		int to;
		int dir;
		
		public Node(int from, int to, int dir) {
			this.from = from;
			this.to = to;
			this.dir = dir;
		}

		@Override
		public int compareTo(Node o) {
			// TODO Auto-generated method stub
			return Integer.compare(this.dir, o.dir);
		}
	}
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine().trim());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		int[] minP = new int[N+1];
		int[] maxP = new int[N+1];
		
		for(int i=0; i<N+1; i++) {
			minP[i] = maxP[i] = i;
		}
		
		int cntMin = 0; // �������� ī��Ʈ ����
		int cntMax = 0;
		
		ArrayList<Node> list = new ArrayList<>();
		
		for(int i=0; i<M+1; i++) {
			st = new StringTokenizer(br.readLine().trim());
			
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			int dir = (Integer.parseInt(st.nextToken())+1)%2; // 0(��������) �Ǵ� 1(��������) -> 1(��������), 0(��������)�� ����
			
			list.add(new Node(A,B,dir));
		}
		
		Collections.sort(list);
		
		for(int i=0; i<list.size(); i++) {
			// �ּ� �Ƿε� ���
			Node cur = list.get(i);
			int pA = findParents(minP, cur.from);
			int pB = findParents(minP, cur.to);
			
			if(pA != pB) {
				union(minP, pA, pB);
				cntMin += cur.dir;
			}
			
			// �ִ� �Ƿε� ��� 
			cur = list.get(list.size()-1-i);
			pA = findParents(maxP, cur.from);
			pB = findParents(maxP, cur.to);
			
			if(pA != pB) {
				union(maxP, pA, pB);
				cntMax += cur.dir;
			}
		}
		
		// �ִ�, �ּ� �Ƿε��� ���� ���
		System.out.println((int)(Math.pow(cntMax, 2)- Math.pow(cntMin, 2)));
	}
	
	static int findParents(int[] parents, int x) {
		if(parents[x] == x) return x;
		return parents[x] = findParents(parents, parents[x]);
	}
	
	static void union(int[] parents, int x, int y) {
		parents[y] = x;
	}
}

