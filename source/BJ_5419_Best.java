package swPro.source;

import java.io.*;
import java.util.*;

/*
 * list : ���� ������ ����(x,y��ǥ : [-10^9 : 10^9])
 * 		- Ž���� ���� ArrayList���
 * xPos : ��ǥ������ ���� ����Ʈ, x��ǥ�� ����
 * 		- ������ ������ ���� LinkedList���
 * map : ��ǥ������ ���� hashmap, �ߺ����� �� Ű������ Ž��
 * 
 * ���� Ǯ��
 * 1. ���� ��ǥ�� �Է¹ް�, x��ǥ�� �������� ��ǥ���� (���� �ִ� 75000��)
 * 2. ���� y��ǥ ���� ������������, y������ x���� �������� ����
 * 		- y��ǥ�� �����ϴ� ������ ����, x��ǥ�� �����ϴ� ������ ����
 * y
 * |-------------------------------
 * | (-10,10)		(10,10)         
 * | 
 * | (-10,-10)		(10,-10)
 * |-------------------------------x
 * ���� ��: (-10,10) (10,10) (-10,-10) (10,-10)
 * 	���� ��ǥ:   1          2          1           2 
 * 3. �ε���Ʈ���� �̿��Ͽ� x��ǥ �������� �� �۰ų� ���� �� Ž�� (�ڽſ��� �� �� �ִ� ������ ī��Ʈ)
 * 		(-10,10) (10,10) (-10,-10) (10,-10)
 * 		0 -> 1 -> 1 -> 3 = �� 5���� ��
 */
public class BJ_5419_Best {
	static int leaf;
	static int[] tree;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st  = null;
		
		int TC = Integer.parseInt(br.readLine().trim());
		for(int tc = 1; tc <= TC ; tc++) {
			int N = Integer.parseInt(br.readLine().trim());
			
			ArrayList<Node> list = new ArrayList<>();
			LinkedList<Integer> xPos = new LinkedList<>();
			
			for(int i=0; i<N; i++) {
				st = new StringTokenizer(br.readLine().trim());
				
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				
				list.add(new Node (x,y));
				xPos.add(x);
			}
			// x��ǥ ���� ��ǥ����
			Collections.sort(xPos);
			
			HashMap<Integer, Integer> map = new HashMap<>();
			int idx = 0;
			
			while(!xPos.isEmpty()) {
				int getX = xPos.poll();
				if(!map.containsKey(getX)) map.put(getX, ++idx);
			}
			
			// ���� ��ǥ ����
			Collections.sort(list);
			
			init(idx);
			
			long cnt = 0;
			for(Node island: list) {
				int key = map.get(island.x);
				cnt += query(key);
				update(key);
			}
			
			bw.write(cnt+"\n");
			
		}
		bw.flush();
	}
	
	static void init(int idx) {
		leaf = 1;

		while(leaf < idx) {
			leaf *= 2;
		}
		
		tree = new int[(leaf--)*2];
	}
	
	static int query(int pos) {
		int start = leaf+1;
		int end = leaf+pos;
		int sum = 0;
		
		while(start<=end) {
			if(start%2 == 1) {
				sum+=tree[start];
			}
			if(end%2 == 0) {
				sum+=tree[end];
			}
			
			start = (start+1)/2;
			end = (end-1)/2;
		}
		return sum;
	}
	
	static void update(int pos) {
		int idx = leaf+pos;
		
		while(idx>=1) {
			tree[idx] += 1;
			idx /= 2;
		}
	}
	
	static class Node implements Comparable<Node> {
		int x;
		int y;
		
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Node o) {
			// y���� ������������, y������ x���� �������� ����
			if(this.y == o.y) {  
				return Integer.compare(this.x, o.x); 
			}
			return Integer.compare(o.y, this.y);
		}
	}
}