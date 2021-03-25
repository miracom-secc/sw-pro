package swPro.source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 �ð� ����	�޸� ����	����	����	���� ���	���� ����
3 ��	256 MB	10048	4547	2634	43.783%

�� ����
N(2 �� N �� 50,000)���� �������� �̷���� Ʈ���� �־�����. Ʈ���� �� ������ 1������ N������ ��ȣ�� �Ű��� ������, ��Ʈ�� 1���̴�.
�� ����� �� M(1 �� M �� 10,000)���� �־����� ��, �� ����� ���� ����� ���� ������ �� ������ ����Ѵ�.

�� �Է�
ù° �ٿ� ����� ���� N�� �־�����, ���� N-1�� �ٿ��� Ʈ�� �󿡼� ����� �� ������ �־�����. 
�� ���� �ٿ��� ���� ����� ���� ������ �˰���� ���� ���� M�� �־�����, ���� M�� �ٿ��� ���� ���� �־�����.

�� ���
M���� �ٿ� ���ʴ�� �Է¹��� �� ������ ���� ����� ���� ������ ����Ѵ�.

*/

public class BJ_11437_Best {

	static int N, M, depths[], parent[][];
	static boolean[] visited;
	static ArrayList<Integer>[] adjList;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader br = new BufferedReader(new FileReader("src/algorithm/sample_input.txt"));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		
		visited = new boolean[N+1];
		depths = new int[N+1];
		parent = new int[N+1][21];
		adjList = new ArrayList[N+1];
		
		for(int a=1; a<=N; a++) adjList[a] = new ArrayList<Integer>();
		
		for (int n=1; n<N; n++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			
			//�����
			adjList[start].add(end);
			adjList[end].add(start);
		}
		
		dfs(1,0);
		fillParent();
		
		M = Integer.parseInt(br.readLine());
		for (int m=0; m<M; m++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			
			System.out.println(lca(x,y));
		}
		
	}
	
	/* ��ó��  1 */
	static void dfs(int here, int depth) {
	    visited[here] = true;
	    depths[here] = depth;
	    
	    for (int a=0; a<adjList[here].size(); a++) {
	    	int there = adjList[here].get(a);
	        if (visited[there]) continue;
	        
	        parent[there][0] = here; //�Ѹ��� �޷��ִ� ����� �θ�� ȣ���� ����
	        dfs(there, depth + 1);
	    }
	}
	
	/* ��ó��  2 */
	static void fillParent() {
	    for (int j = 1; j < 21; j++) {
	        for (int i = 1; i <= N; i++) {
	        	// 2^j ��� = 2^(J-1)����� �θ�(j-1)
	        	parent[i][j] = parent[parent[i][j - 1]][j - 1];
	        }
	    }
	}

	/* LCA */
	static int lca(int x, int y) {
	    // y�� �� ���� depth�� �ִ� ������ ����
		if (depths[x] > depths[y]) {
	    	// y�� �� ū������ ġȯ
	    	int temp = y;
	    	y = x;
	    	x = temp;
	    }
	    
	    // ���� depth���� �����ϱ� ���� ���� depth�� �÷��ش�
	    for (int i = 20; i >= 0; i--) {
	        // (1 << i) => ��Ʈ����ũ : 1�� �������� ����Ʈ 2�� N�� ǥ��
	    	if (depths[y] - depths[x] >= (1 << i)) 
	            y = parent[y][i];
	    }
	    
	    // �θ� ���� ��� x���� �θ�
	    if (x == y) return x;
	    
	    //����θ� ã��
	    for (int i = 20; i >= 0; i--) {
	        if (parent[x][i] != parent[y][i]) {
	            x = parent[x][i];
	            y = parent[y][i];
	        }
	    }
	    
	    return parent[x][0];
	}

}
