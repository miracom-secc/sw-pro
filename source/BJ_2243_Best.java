package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/*
������ �� :  1���� 1,000,000 
ù° ��:  �����̰� �������ڿ� ���� �� Ƚ�� n(1��n��100,000)
���� n���� ��: �� ���� A, B, Ȥ�� �� ���� A, B, C�� �־�����. 
            > A�� 1�� ���: �������ڿ��� ������ ������ ���. B�� �־���. B�� ���� ������ ����, ���� �Ѱ� ����.
            > A�� 2�� ���: ������ �ִ� ���. B�� ���� ������ ��. C�� �׷��� ������ ����. C�� ����� ��쿡�� ������ �ִ� ����̰�, ������ ��쿡�� ��. 
            
�� ó������ �� �������ڿ��� �����Ѵٰ� �����ϸ�, ������ �� ������ 2,000,000,000�� ���� �ʴ´�. ���� ���� ������ ������ ���� ���� �߸��� �Է��� �־����� �ʴ´�.

���
A�� 1�� ��� �Է¿� ���ؼ�, ���� ������ ���� ��ȣ�� ����Ѵ�. ����, A=2 �̸鼭 C<0 �� ���� ������� �ʴ´�.
*/

public class BJ_2243_Best {
static int n, leafCnt; // ���� �� Ƚ��
	
	static int INF = 1000001; //������ �� :  1 ~ 1,000,000 
	//static long[] arr;
	static long[] tree;

	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		//arr = new long[INF];
		tree = new long[INF];
		
		// �Է�
		n = Integer.parseInt(st.nextToken()); // - ���� �� Ƚ��
		
		for(int i=0; i<n ; i++) {
			
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			
			// ������ ������ ���
			if(a==1) {
				int b = Integer.parseInt(st.nextToken()); // ������ ����
				int ans = find(b); // ���� ��ġ ã��
				bw.write(String.valueOf(ans) +"\n");
				update(ans,-1);
			}
			// ������ �ִ� ���
			else {
				int b = Integer.parseInt(st.nextToken()); // ������ ��
				long c = Long.parseLong(st.nextToken()); // c �� ���: �� ������ŭ �ְ� / ����: �� ������ŭ ��
				update(b, c);				
			}			
		}
		bw.flush();
		bw.close();
		br.close();
				
	}
	static void update(int idx, long val) {
		while (idx <tree.length) {
			tree[idx] += val;
			idx += (idx & -idx); // ���� ���� �ε����� ����, �� ������ ��Ʈ�� �����ο��� ������
		}
		
	}
	
	static int find(int num) {
		int idx=-1;
		int left =1, right= INF-1;
		
		while(left < right) {
			int mid = (left + right) /2 ;
			if (sum(mid) >= num) {				
				right = mid;
			}else {
				left = mid+1;
			}	
			int de=1;
		}	
		idx = right;
		return idx;
	}
	
	static long sum(int pos) {
		// �ε����� 1 ����
		long ret = 0;
		while(pos > 0) {
			ret += tree[pos];
			//pos &= (pos - 1); 
			pos -= (pos & -pos); // ���������� ������ ��Ʈ�� ����.
		}
		return ret;
	}
}
