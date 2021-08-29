package Floyd;

import java.io.*;
import java.util.*;

/*����ġ ���� ���� �׷��� G�� �־����� ��, ��� ���� (i, j)�� ���ؼ�, i���� j�� ���� ��ΰ� �ִ��� ������ ���ϴ� ���α׷��� �ۼ��Ͻÿ�.

�Է�
ù° �ٿ� ������ ���� N (1 �� N �� 100)�� �־�����. ��° �ٺ��� N�� �ٿ��� �׷����� ���� ����� �־�����. 
i��° ���� j��° ���ڰ� 1�� ��쿡�� i���� j�� ���� ������ �����Ѵٴ� ���̰�, 0�� ���� ���ٴ� ���̴�. i��° ���� i��° ���ڴ� �׻� 0�̴�.

���
�� N���� �ٿ� ���ļ� ������ ������ ������� �������� ����Ѵ�. ���� i���� j�� ���� ��ΰ� ������ i��° ���� j��° ���ڸ� 1��, ������ 0���� ����ؾ� �Ѵ�.
*/

public class O_BOJ_11403_���ã�� {
	
	static int n;
	static int map[][];
	//static int fw[][];
	//static int INF = Integer.MAX_VALUE;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		
		map = new int[n+1][n+1];
		
		// �÷��̵� �ͼ� ���̺� �Է�
		for(int i=1; i<=n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=1; j<=n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}			
		}
		
		// �÷��̵� �ͼ� ����
		floyd();
		
		// ���
		for(int i=1; i<=n; i++) {
			for(int j=1; j<=n; j++) {
				System.out.print(map[i][j]+" ");
			}
			System.out.println();
		}

	}

	
	static void floyd() {
		for(int mid =1; mid <=n ; mid++) { // ������.
			for(int i=1; i<=n; i++) {  // �����
				for(int j=1; j<=n; j++) { // ������
					if(map[i][mid] ==1 && map[mid][j] ==1) {  //i-mid, mid-j �� 1�̶�°� mid �� ���ؼ� �ѻ��� ������ �ȴٴ� ��
						map[i][j] = 1;
					}	
				}			
			}
		}
		
	}

}
