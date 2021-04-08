package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/*

�ð� ����	�޸� ����	����	����	���� ���	���� ����
2 ��	128 MB	39615	12471	7657	29.115%

�� ����
�ʱ⿡ {0}, {1}, {2}, ... {n} �� ���� n+1���� ������ �̷�� �ִ�. ���⿡ ������ �����, �� ���Ұ� ���� ���տ� ���ԵǾ� �ִ����� Ȯ���ϴ� ������ �����Ϸ��� �Ѵ�.
������ ǥ���ϴ� ���α׷��� �ۼ��Ͻÿ�.

�� �Է�
ù° �ٿ� n(1 �� n �� 1,000,000), m(1 �� m �� 100,000)�� �־�����. m�� �Է����� �־����� ������ �����̴�. 
���� m���� �ٿ��� ������ ������ �־�����. �������� 0 a b�� ���·� �Է��� �־�����. �̴� a�� ���ԵǾ� �ִ� ���հ�, b�� ���ԵǾ� �ִ� ������ ��ģ�ٴ� �ǹ��̴�. 
�� ���Ұ� ���� ���տ� ���ԵǾ� �ִ����� Ȯ���ϴ� ������ 1 a b�� ���·� �Է��� �־�����. �̴� a�� b�� ���� ���տ� ���ԵǾ� �ִ����� Ȯ���ϴ� �����̴�. a�� b�� n ������ �ڿ��� �Ǵ� 0�̸� ���� ���� �ִ�.

�� ���
1�� �����ϴ� �Է¿� ���ؼ� �� �ٿ� �ϳ��� YES/NO�� ����� ����Ѵ�. (yes/no �� ����ص� �ȴ�)

*/

public class BJ_1717_Best {

	static int[] parent; // �θ� �迭
	
	public static void main(String[] args) throws Exception {
		
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        
        int N = Integer.parseInt(st.nextToken()); // ����
        int M = Integer.parseInt(st.nextToken()); // ���갹��
 
        parent = new int[N+1]; 
        
        for (int i = 1; i <= N; i++) {
            parent[i] = i; 	// ó�� �ڱ� �ڽ����� �ʱ�ȭ
        }
        
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int chk = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
 
            if (chk == 0) {
                union(a, b);
            } else if (chk == 1) {
            	a = find(a);
                b = find(b);
                if(a == b){
                    bw.write("YES\n");
                } else {
                    bw.write("NO\n");
                }
            }
        }
 
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
 
   
    public static int find(int num) {
        if (num == parent[num]) {
            return num;
        } 
        return parent[num] = find(parent[num]);
    }
 
    public static void union(int a, int b) {
        a = find(a);
        b = find(b);
 
        if(a > b) {
            parent[b] = a;
        }else{
        	parent[a] = b;
        } 
    }
}
