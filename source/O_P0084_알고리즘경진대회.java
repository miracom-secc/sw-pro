package index_tree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;


/*
 *  =============Ǯ�� ����
 * 1. �������� �Է¹���
 *     - �Է¹��� ������, ���������� �迭�� ����
 *  2. �ִ�����(������ ������ �� �ִ� �ִ� �ο���) ��  Ʈ�� ����
 *  3. ������ ������ ��ȸ�� �����ϴ� ���� �� ���ϱ�
 *    0) ���� �ʴ� ������ ���� ���� �Է�
 *    1) ���� �ʴ� ���� ���� ���� �������� �����ϴ� ���� ������ ����
 *    2) �ش籸���� �ִ�����(������ ������ �� �ִ� �ִ� �ο���) �� ����
 *    3) 1������ ���ߴ� ���������� �迭�� �̿��ؼ� �ش� ������ �������� ���� ���ϰ�
 *        3-2) ���� ���� �ִ� ������� �����ָ� 
 *        �ش� ������ �����ϴ� ��ȸ�� �����ϴ� ���� ���� ����
 * */       

public class O_P0084_�˰��������ȸ {
	
	static int n ; // ����
	static int input[];	// �Է¹��� ������
	static long sum[];	// ������ �հ��
	static int tree[];  // �ּҰ����(������ ������ �� �ִ� �ִ� �ο���)�� Ʈ��
	static int leaf;
	

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int t = Integer.parseInt(br.readLine()); // �׽�Ʈ ���̽�
		 
		for(int tc = 1; tc <=t ; tc++) {
			n = Integer.parseInt(br.readLine()); // �ǹ��� �� ��
			
			leaf =1;
			while(leaf<n) {
				leaf*=2;
			}

			input  = new int[n+1];
			sum =  new long[n+1];
			tree = new int[leaf*2+1];

			//1. �������� �Է¹���
			st = new StringTokenizer(br.readLine());
			for(int i=1; i<=n; i++) {
				input[i] =Integer.parseInt(st.nextToken()); // ���� ���� ���� ��
				sum[i] = sum[i-1]+input[i]; // ���������� : �� �������� ������ �� + ���� ���� ������
			}
			
			// 2. �ִ�����(������ ������ �� �ִ� �ִ� �ο���) ��  Ʈ�� ����
			initTree(1, n, 1); // start , end , node						
			
			long ans=0;
			// 3. ������ ������ ��ȸ�� �����ϴ� ���� �� ���ϱ�
			st = new StringTokenizer(br.readLine());
			for(int i=1; i<=n; i++) {
				// 0) ���� �ʴ� ������ ���� ���� �Է�
				int game =Integer.parseInt(st.nextToken());
				
				// 1) ���� �ʴ� ���� ���� ���� �������� �����ϴ� ���� ������ ����
				int leftnode = (i-game) < 1 ? 1 : i-game; // �Ʒ���
				int rightnode = (i+game) > n ? n : i+game; // ����
				
				// 2) �ش籸���� �ִ�����(������ ������ �� �ִ� �ִ� �ο���) �� ����
				int gcd = findGcd(1, n, leftnode, rightnode,1); 
				
				// 3) 1������ ���ߴ� ���������� �迭�� �̿��ؼ� �ش� ������ �������� ��
				long count = sum[rightnode] - sum[leftnode-1]; 
				
				// 4) �ش����� �������� �� = ���� ������ / �ִ����� 
				ans += count/gcd;
				
			}
			sb.append("#"+tc+" "+ans +"\n");
			
		}
		bw.write(sb.toString());
		bw.flush();
		bw.close();
		br.close();
	}

	// ������ gcd ã��
	static int findGcd(int start, int end, int leftnode, int rightnode, int node) {
		// ������ ������ �������
		if(rightnode < start || end < leftnode) return 0;
		
		// ������ ������ ���Ե�����
		if(leftnode <= start && end <= rightnode) return tree[node];
		
		// ��������
		int mid = (start+end)/2;
		int num1 = findGcd(start, mid, leftnode,  rightnode, node*2);
		int num2 = findGcd(mid+1, end, leftnode,  rightnode, node*2+1);
		return initGcd(Math.max(num1, num2), Math.min(num1, num2));
	}

	// �ִ����� Ʈ�� �����.
	static int initTree(int start, int end, int node) {
		if(start == end) return tree[node] = input[start];
		
		int mid = (start+end)/2;
		int num1 = initTree(start, mid,node*2);
		int num2 = initTree(mid+1, end, node*2+1);
		
		return tree[node] = initGcd(Math.max(num1, num2), Math.min(num1, num2));
	}
	
	// �ִ� ����� ã��
	static int initGcd(int num1, int num2) {
		
		while(num2>0) {
			int rest = num1%num2;
			num1 = num2;
			num2 = rest;
		}
		
		return num1;
	}
	
	//** ����! �ּ� ����� ã��
	static int initLcm(int num1, int num2) {
		// �μ��� ��/�ִ낋���
		 return num1*num2/initGcd(Math.max(num1, num2), Math.min(num1, num2));
	 }

}
