package swPro.source;

import java.io.*;
import java.util.*;

/*

�ð� ����	�޸� ����	����	����	���� ���	���� ����
2 ��	256 MB	46923	9166	4718	23.498%

�� ����
� N���� ���� �־��� �ִ�. �׷��� �߰��� ���� ������ ����� �Ͼ�� �� �߰��� � �κ��� ���� ���Ϸ� �Ѵ�. 
���࿡ 1,2,3,4,5 ��� ���� �ְ�, 3��° ���� 6���� �ٲٰ� 2��°���� 5��°���� ���� ���϶�� �Ѵٸ� 17�� ����ϸ� �Ǵ� ���̴�. 
�׸��� �� ���¿��� �ټ� ��° ���� 2�� �ٲٰ� 3��°���� 5��°���� ���� ���϶�� �Ѵٸ� 12�� �� ���̴�.

�� �Է�
ù° �ٿ� ���� ���� N(1 �� N �� 1,000,000)�� M(1 �� M �� 10,000), K(1 �� K �� 10,000) �� �־�����. 
M�� ���� ������ �Ͼ�� Ƚ���̰�, K�� ������ ���� ���ϴ� Ƚ���̴�. �׸��� ��° �ٺ��� N+1��° �ٱ��� N���� ���� �־�����. 
�׸��� N+2��° �ٺ��� N+M+K+1��° �ٱ��� �� ���� ���� a, b, c�� �־����µ�, a�� 1�� ��� b(1 �� b �� N)��° ���� c�� �ٲٰ� a�� 2�� ��쿡�� b(1 �� b �� N)��° ������ c(b �� c �� N)��° �������� ���� ���Ͽ� ����ϸ� �ȴ�.

�Է����� �־����� ��� ���� -263���� ũ�ų� ����, 263-1���� �۰ų� ���� �����̴�.

�� ���
ù° �ٺ��� K�ٿ� ���� ���� ������ ���� ����Ѵ�. ��, ������ -263���� ũ�ų� ����, 263-1���� �۰ų� ���� �����̴�.

*/

public class BJ_2042_Best {
	
	static int N, M, K;
	static long fwTree[], arr[];
	
    public static void main(String[] args) throws IOException {
        // StringTokenizer �̿�
        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader br = new BufferedReader(new FileReader("src/algorithm/sample_input.txt"));
        StringBuffer sb = new StringBuffer();
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        
        fwTree = new long[N+1];
        arr = new long[N+1];
        
        M += K;
        for (int i=1; i<=N; i++) {
        	arr[i] = Long.parseLong(br.readLine());
        	update(i, arr[i]);
        }
        
        while (M-- > 0) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());

            if (a == 1) {
                int b = Integer.parseInt(st.nextToken());
                long c = Long.parseLong(st.nextToken());
                
                long diff = c - arr[b];
                arr[b] = c;
                update(b, diff);
                
            } else {
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                long sum = sum(c) - sum(b-1);
                System.out.println(sum);
            }
        }
    }
    
	// 111,  110, 100 ..
	public static long sum(int pos) {
		// �ε����� 1���� �����Ѵٰ� ��������.
		//++pos;
		
		long ret = 0;
		while(pos > 0) {
			ret += fwTree[pos];
			pos &= (pos - 1); // ������ �� ��ġ�� ���������� ������ ��Ʈ�� ����.
//			pos -= (pos & -pos);
		}
		
		return ret;
	}

	// 101, 110, 1000, 10000 ..
	public static void update(int pos, long val) {
		//++pos;
		while (pos < fwTree.length) { 
			fwTree[pos] += val;
			pos += (pos & -pos); // �� ������ ��Ʈ�� �����ο��� ���� �ش�.
		}
	}
}
