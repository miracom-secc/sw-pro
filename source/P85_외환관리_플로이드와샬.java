package Floyd;

import java.io.*;
import java.util.*;

/* n(��ȭ����)���� ���� m(��ȭ��ȯ����)���� �������� �̷���� �׷����� �ִܰ�θ� ���ϴ� ����
 * ������ k�� ��������� ���� ����� ���
 * 
 * Ǯ�� ����
 * 1. �÷��̵� ���� �ʱ�ȭ.=> �ִ밪
 * 	 1) K �� ������� �ʴ� ��� : dist[i][j][0] = INF
 *   2) K �� ����ϴ� ���	   : dist[i][j][1] = INF
 * 2. ��ȯ ��� ���� �Է�
 * 	 1) K �� ������� �ʴ� ��� : dist[i][j][0] = z (��ȯ���)  
 *   2) K �� ����ϴ� ���	   : dist[i][j][1] = k (������)  
 * 3. �÷��̵� ���� ����
 *   1) K �� ������� �ʴ� ��� : min(dist[x][y][0], dist[x][mid][0]+dist[mid][y][0])
 *   2) K �� ����ϴ� ���	   : min(dist[x][y][1], dist[x][mid][1]+dist[mid][y][0], dist[x][mid][0]+dist[mid][y][1])
 * 4. ��ȭ �߻� ��� ���
 *   1) min(dist[x][y][0], dist[x][y][1])
 */
/*
3
5 6 5 5
1 2 2
3 2 2
3 4 3
1 3 6
5 4 4
5 1 4
1 4
5 3
3 1
4 2
2 5
5 6 5 -5
1 2 2
3 2 2
3 4 3
1 3 6
5 4 4
5 1 4
1 4
5 3
3 1
4 2
2 5
5 4 3 1
1 2 1
2 3 2
3 4 1
4 5 2
1 2
1 3
3 5 
*/
// 

public class P85_��ȯ����_�÷��̵�ͼ� {
	
	static int n; // ��ȭ������ ��
	static int m; // ��ȯ��� ��������
	static int d; // �����ϼ�
	static int k; // ������
	
	static long dist[][][]; 
	static long INF = 1000000*10000000;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;
		
		int t = Integer.parseInt(br.readLine());
		for(int tc =1; tc <=t ; tc ++) {
			st = new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			d = Integer.parseInt(st.nextToken());
			k = Integer.parseInt(st.nextToken());
			
			/* 1. �÷��̵� ���� �ʱ�ȭ */
			dist = new long[n+1][n+1][2];
			for(int i=1; i<=n; i++) {
				for(int j=1; j<=n; j++) {
					// 1) dist[i][j][0] : K �� ������� �ʴ� ���
					dist[i][j][0] =INF;
					// 2) dist[i][j][1] : K �� ����ϴ� ���  
					dist[i][j][1] =INF;
				}
			}
			
			/* 2. ��ȯ ��� ���� �Է� */
			for(int i=0; i<m ; i++) {
				st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken()); // ��ȭ x
				int y = Integer.parseInt(st.nextToken()); // ��ȭ y
				int z = Integer.parseInt(st.nextToken()); // ��� z
				
				// 1)  K �� ������� �ʴ� ��� ����
				dist[x][y][0] = z;
				dist[y][x][0] = z;
				// 2)  K �� ����ϴ� ��� ����
				dist[x][y][1] = k;
				dist[y][x][1] = k;				
			}
			
			/*�÷��̵� ���� �� dist �迭�� ��
			 	 - 		2/5 	6/5		 -		4/5 
			 	2/5 	 -		2/5 	 - 		 - 
			 	6/5 	2/5 	 - 		3/5 	 - 
			 	 -		 - 		3/5 	 - 		4/5 
			 	4/5 	 - 		 - 		4/5 	 - 
			 */
			
			/* 3. �÷��̵���� ����*/
			for(int mid=1; mid<=n; mid++) { // �߰� ������
				for(int i=1; i<=n; i++) {
					for(int j=1; j<=n; j++) {
						// 1) K �� ������� �ʴ� ���
						// 	- min(������� ����� �ִ� �Ÿ�, ������ ������(mid)�� ������ ���� �Ÿ�)
						dist[i][j][0] = Math.min(dist[i][j][0], dist[i][mid][0]+dist[mid][j][0]); 
						// 2) K �� ����� ���
						// 	- k �� �Ϸ翡 �ѹ��� ��밡��  => min(i->������ �� k �� �����, ������->j �� k�� �� ���)
						long dmin = Math.min(dist[i][mid][1]+dist[mid][j][0], dist[i][mid][0]+dist[mid][j][1]);  
						dist[i][j][1] = Math.min(dist[i][j][1], dmin);
					}
				}
			}
			
			/* �÷��̵� ������ dist �迭�� ��
				4/7 	2/5 	4/5 	7/8 	4/5 
				2/5 	4/7 	2/5 	5/7 	6/7 
				4/5 	2/5 	4/7 	3/5 	7/8 
				7/8 	5/7 	3/5 	6/8 	4/5 
				4/5 	6/7 	7/8 	4/5 	8/9 
			*/

			/* 4. ��ȭ �߻� ��� ��� */
			long ans=0;
			for(int i=0; i<d ; i++) {
				st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken()); // ��ȭ x
				int y = Integer.parseInt(st.nextToken()); // ��ȭ y
				
				// x,y ��ȭ�� �ּ� ��ȯ���
				ans += Math.min(dist[x][y][0], dist[x][y][1]);
			}
			bw.write("#"+tc+" "+ans+"\n");
		}
		bw.flush();
		bw.close();
		br.close();
	}

}
