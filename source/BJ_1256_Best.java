package swPro.source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.StringCharacterIterator;
import java.util.StringTokenizer;

public class BJ_1256_Best{
	
	static int skip;
	static int N, M, K, bino[][];
	static final int INF = 1000000000 + 100; //K��  �ִ밪 + 100, �����÷θ� ���� ���� �̺��� ū ���� ������ �ʴ´�.
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //BufferedReader br = new BufferedReader(new FileReader("src/algorithm/sample_input.txt"));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        bino = new int[201][201];
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        
        skip = K-1; //�ǳ� �� ����
        calcBino(); //���װ�� ���ϱ�
        
        if (bino[N+M][N] <= skip) System.out.println("-1");
        else generate(N, M, ""); //1�� Ǯ��
        //else System.out.println(kth(N, M, skip)); //2�� Ǯ��
	}
	
	//�ʿ��� ���װ���� �̸� ����� �д�.
	static void calcBino() {
		for (int n=0; n<=200; n++) {
			bino[n][0] = bino[n][n] = 1;
			for (int r=1; r<n; r++)
				bino[n][r] = Math.min(INF, bino[n-1][r-1]+bino[n-1][r]);
		}
	}
	
	//n���� a, m���� z�� ������ ��ȣ �� skip���� �ǳʶٰ� ��������� ��ȣ�� ��ȯ�Ѵ�.
	static String kth(int n, int m, int skip2) {
		//n == 0�� ��� ������ �κ��� ���� 'z'�� �� �ۿ� ����
		if (n == 0) {
			String s = "";
			for(int i=0; i<m; i++)
				s += "z";

			return s;
		}
		
		if (skip2 < bino[n+m-1][n-1]) return "a" + kth(n-1, m, skip2);
		
		return "z" + kth(n, m-1, skip2 - bino[n+m-1][n-1]);
	}
	
	// n : �� �ʿ��� a�� ����
	// m : �� �ʿ��� z�� ����
	// s : ���ݱ��� ���� ���ڿ�
	static void generate(int n, int m, String s) {
		//������� skip<0
		if (skip < 0) return;
		
		//������� n==m==0
		if (n==0 && m==0) {
			// k-1�� �ǳ� �پ����� ���
			if (skip == 0) System.out.println(s);
			
			--skip; //�ǳʶٱ�
			return;
		}
		
		if (bino[n+m][n] <= skip) {
			skip -= bino[n+m][n];
			return;
		}
		
		//���� ������ ���ڿ� �����
		if(n > 0) generate(n-1, m, s+"a"); 
		if(m > 0) generate(n, m-1, s+"z");
	}
}
