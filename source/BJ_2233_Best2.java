package swPro.source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BJ_2233_Best2 {

	static int N, M, apples[], depths[], parent[][];
	
	public static void main(String[] args) throws Exception {
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("src/algorithm/sample_input.txt"));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		
		apples = new int[N*2+1];
		depths = new int[N+1];
		parent = new int[N+1][21];
		
		String node = br.readLine();
		int apple = 0, depth = 0, start = 0, end = 0;
		for (int n=1; n<=N*2; n++) {
			char move = node.charAt(n-1);
			
			if (move == '0') {
				start = end;
				apples[n] = ++apple; //���� ã�� ���
				depths[apples[n]] = ++depth; //�������� depth�� ���
				parent[apples[n]][0] = start; //�θ���� �ٷ� �� ���������
				end = apples[n];
			} else if (move == '1') {
				apples[n] = end; //
				end = parent[end][0]; //�ö󰡴� ������ �θ���
				--depth;
			}
		}
		
		//dfs(1,0);
		fillParent(); // �θ�ã��
		
		st = new StringTokenizer(br.readLine());
		int x = Integer.parseInt(st.nextToken());
		int y = Integer.parseInt(st.nextToken());
		int cut = lca(apples[x], apples[y]); //���� ���� ã��
		
		for (int i=1; i<=(N*2); i++) {
			if (apples[i] == cut)
				System.out.print(i+" ");
		}
		
	}
	
	/* ��ó��  1 */
//	static void dfs(int here, int depth) {
//	    visited[here] = true;
//	    depths[here] = depth;
//	    
//	    for (int a=0; a<adjList[here].size(); a++) {
//	    	int there = adjList[here].get(a);
//	        if (visited[there]) continue;
//	        
//	        parent[there][0] = here; //�Ѹ��� �޷��ִ� ����� �θ�� ȣ���� ����
//	        dfs(there, depth + 1);
//	    }
//	}
	
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
