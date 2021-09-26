 package union;

import java.io.*;
import java.util.*;

public class P_�Ǽ��ڵ� {

	// ������  k��, ġ��� 3�븸 ����.
	// ���ġ�ᰡ �������� �������� ���� ����� �ִ���� ���ϴ� ����
	// 1. �Է�
	//		1) �������� �ϳ��� �Է� �����鼭, union-find ����
	//		2) ���� ��� ���� �Է¹����鼭 �ش� �θ� ++ ���ֱ�
	// 2. ��� �Է��� ������ ���� �׷��� ũ������� ����
	// 3. �ش�׷쿡 ���̷����� ������ ������ �������� ġ��Ǵ� �ִ������ ����  
	// 		����Ǽ� 
	//		0) �������� > 3	: X  -
	//		1) �ѱ׷쿡�� ���������� 3	: child�� �ִ��� ��
	//		2) �ѱ׷� ������� 2+1 		: child�� �ִ��� ��
	//		3) ������� 1+1+1			: child�� �ִ��� �� ����
	// 1~3) �� ����� �ִ밪�� ����
	// 4. avail_cnt = 1~3�� �ִ밪 + �Ѵ뵵 �����ȵ� �׷��� ����
	// 5. avail_cnt ���
	
	static class Node implements Comparable<Node> {
		int idx;   // ���� �ε���
		int t_cnt; // ��ü �ڽļ�
		int v_cnt; // ������ �ڽļ�

		Node(int idx, int t_cnt, int v_cnt){
			this.idx = idx;
			this.t_cnt = t_cnt;
			this.v_cnt = v_cnt;
		}		
			@Override
		public int compareTo(Node o) {
			// TODO Auto-generated method stub
			return Integer.compare(o.t_cnt, this.t_cnt);
		}	
	}
	
	
	static int n, m, k;
	static int parent[]; //parent �θ�迭
	static Node child[]; //��ü�ڽļ�, ������ �ڽļ�	
	static int INF = (int) (Math.pow(10, 6)+1);
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;
		
		int d1=1;
		int t = Integer.parseInt(br.readLine().trim());
		
		for(int tc=1; tc<=t ; tc++) {
		//	System.out.println("INF:"+ INF);
			// 0. �ʱ�ȭ
			st = new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			k = Integer.parseInt(st.nextToken());
			
			parent = new int[n+1];
			child = new Node[n+1];
			
			for(int i=1; i<=n; i++) {
				parent[i] = i;
				child[i] = new Node(i, 1, 0);//�ε���, �ش� ������ �ڱ��ڽ� 1, ������ 0
			}
			child[0] = new Node(0,10000001,0);
			// 1-1). ���� ���� �ϳ��� �Է� �����鼭, union-find ����
			for(int i=0; i<m; i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				
				union(a,b);// �θ�����
			}
			
			// 1-2) ������ ����� �θ��忡 ++ ���ֱ�
			st = new StringTokenizer(br.readLine());
			for(int i=0; i<k; i++) {
				int temp = Integer.parseInt(st.nextToken());
				// ������ ����� �θ���ã�Ƽ� ++ ���ֱ�
				int p_num = find(temp); 
				child[p_num].v_cnt +=1;
			}
			d1=1;
			Arrays.sort(child); // ũ������� ��Ʈ�ϱ�
			//d1=1;
			long ans=0; // ��ü ��
			long z_cnt=0; // ���� ���̷����� �Ѱ��� ���� ���++
			long max_cnt3 = 0; 	// 3�� ¥�� �ƽ��� ���ϱ�
			long max_cnt2 = 0; 	// 2�� ¥��
			long max_cnt1[] = new long[3];	// 1�� ¥��
			int cnt1_idx=0;
			
			//n��1: 3��¥�� �ִ밪, 2��¥�� �ִ밪, 1��¥�� �ִ밪, 0���ΰ� �������ֱ�
			for(int i=1; i<=n; i++){
				
				// 3��¥���� �ִ�
				if(child[i].v_cnt ==3) 
					max_cnt3 = Math.max(child[i].t_cnt, max_cnt3);
				// 2��¥���� �ִ�
				if(child[i].v_cnt ==2) 
					max_cnt2 = Math.max(child[i].t_cnt, max_cnt2);
				
				// 1��¥��
				if(cnt1_idx<3 && child[i].v_cnt ==1) {
					max_cnt1[cnt1_idx] = child[i].t_cnt;
					cnt1_idx++;
				} 
				// ���̷��� �����ȵ� ���鳢�� ��ģ��
				if(child[i].v_cnt ==0 &&  child[i].t_cnt!= 0) {
					z_cnt+= child[i].t_cnt;
				}
				
			}
			ans = Math.max(max_cnt3, max_cnt2+max_cnt1[0]);
			ans = Math.max(ans, max_cnt1[0]+max_cnt1[1]+max_cnt1[2]);
			
			ans+= z_cnt;
			System.out.println("#"+tc+" "+ans);								
		}
	}
	
	
	static void union(int a, int b) {
		a= find(a);
		b= find(b);
		if(a>b) {
			parent[a] = b;
			child[b].t_cnt += child[a].t_cnt; // �ڽ��� �ڽļ��� �θ� �����ֱ�
			child[a].t_cnt = 0;		
		}
		else if(a<b){
			parent[b] = a;
			child[a].t_cnt += child[b].t_cnt; // �ڽ��� �ڽļ��� �θ� �����ֱ�
			child[b].t_cnt = 0;				
		}
	}
	
	static int find(int a) {
		if(parent[a] == a) return a;
		return parent[a] = find(parent[a]);
	}

}
