package ex;

import java.io.*;
import java.util.*;

public class P_�ɺ�_LCA {
	
	static class Node{
		int v;
		int dep;
		
		Node(int v, int dep) {
			this.v = v;
			this.dep = dep;
		}
	}
	
	static int n, q ;// ������, ���Ӽ�
	//static ArrayList<Node> arr[]; // ��������
	static ArrayList<Integer> arr[]; // ������ ��������
	static long score[]; // �� ������ ����
	static long sum[];	 // �� ���������� ������
	static int depth[];  // ����
	static int parent[][]; // �θ����� �Է¿�
	static int max_depth; // �ִ� ����
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st= null;

		int t = Integer.parseInt(br.readLine().trim());
		
		for(int tc =1; tc<=t ; tc++) {
			st = new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken()); // ������
			q = Integer.parseInt(st.nextToken()); // ���Ӽ�
			
			arr = new ArrayList[n+1];
			score = new long[n+1];
			sum = new long[n+1];
			depth = new int[n+1];
			int tmp =1;
			while(tmp<n) {
				tmp <<= 1;
				max_depth++;
			}
			parent = new int[n+1][max_depth];
			
			for(int i=0; i<=n; i++) 
				arr[i] = new ArrayList<Integer>();
			
			
			/*************** 1. �Է� ***************/
			// 1-1) �� ������ ����
			st = new StringTokenizer(br.readLine());
			for(int i=1; i<=n; i++)
				score[i] = Long.parseLong(st.nextToken());
			
			// 1-2) ������ ��������
			st = new StringTokenizer(br.readLine());
			for(int i=1; i<=n; i++) {
				int pp = Integer.parseInt(st.nextToken());
				
				if(pp == 0) // 0 �϶��� �������̴� �н�
					continue;
				
				arr[pp].add(i);  // ���� ��忡 ������� ���̰�,
				parent[i][0] = pp; //�ٷ� ���� �θ� ���� �Է�.
			}
			
			
			/********* 2. �������� �� ���� ��� *********/
			Queue<Node> qq = new LinkedList<>();
			qq.add(new Node(1,1)); // Ž������.(������, ����)
			
			while (!qq.isEmpty()){
                Node now = qq.poll();
                int now_depth = now.dep;
                int pp = parent[now.v][0]; // �ٷ����� �θ�.
                
                depth[now.v] = now_depth;	// �ش� ������ ����.
                sum[now.v] = score[now.v]+ sum[pp]; // ���� ���� ����
                
                for(int next : arr[now.v]){
                	qq.add(new Node(next, now_depth+1));                  
                }
            }
			
			
			/********* 3. ������ ������ Ʈ�� ���� *********/
			//===> �⺻�ҽ�: fillParent() �� �ش�, �� ������ ���������� ����		
		    for (int j = 1; j < max_depth; j++) { // depth �� ���� �ͺ��� ���������� ä�� 
		        for (int i = 1; i <= n; i++) {
		        	parent[i][j] = parent[parent[i][j - 1]][j - 1];
		        }
		    }
		    
		    /************* 4. ���������Է� *************/
		    long a_sum=0;
		    long b_sum=0;
		    for(int i = 0; i < q; i++) { 
		    	st = new StringTokenizer(br.readLine());
		    	int a = Integer.parseInt(st.nextToken());
		    	int b = Integer.parseInt(st.nextToken());
		    	// 4-1) �� ������ �ּҰ������� ã�� 
		    	int p_node = (depth[a]>depth[b]? findLCA(a,b):findLCA(b,a));
		    	
		    	// 4-2) �������
		    	long temp1=0;
		    	long temp2=0;
		    	
		    	// a) a�� ���̰� �� ���� ��� 
		    	if(depth[a]< depth[b]) {  
		    		temp1 = sum[a]; // a �� ������������ ��� ������ �� ȹ��
		    		temp2 = sum[b] - sum[p_node]; // b�� ���������������� ������ ȹ��
		    	} 
		    	// b) b�� ���̰� �� ���� ���  
		    	else if(depth[a]> depth[b]) {
		    		temp1 = sum[a] - sum[p_node]; // a�� ���������������� ������ ȹ��
		    		temp2 = sum[b]; // b�� ������������ ��� ������ �� ȹ��
		    	}
		    	// c) ���̰� �������
		    	else{
		    		// �������󿡼� ƨ��� ������ �Ѵ� ���������������� ������ ȹ��
		    		temp1 = sum[a] - sum[p_node];
		    		temp2 = sum[b] - sum[p_node];
		    	}
		    	a_sum += temp1;
		    	b_sum += temp2;
		    
		    
		    }
		    
		    /*************** 5. ��� ***************/
		    System.out.println("#"+tc+" "+ a_sum+" "+b_sum);	
		}
	}

	// 4-1) �� ������ �ּҰ������� ã�� 
	static int findLCA(int a, int b) { 
		//===> �⺻�ҽ�: lca() �� �ش�, �� ����� ���������� ã��
		//a) ���� ���� �����
		for(int j=max_depth-1; j>=0; j--) { // �ִ� ���̺��� ���������� Ž���ϸ� ���̸� ���簨
			if(depth[a] - depth[b] >= (1 << j))
				a = parent[a][j];
		}
		
		//b) ���� �θ� ã�� ��� 
		if(a == b)
			return a;
		
		//c) ����θ�ã��
		for(int j = max_depth-1; j>=0; j--) {
			
			if(parent[a][j] != parent[b][j]) {
				a = parent[a][j];
				b = parent[b][j];
			}				
		}				
		return parent[a][0];
	}


}
