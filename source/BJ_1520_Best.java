package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/*
 * 
������ ���� ���� �� ���� -> ���� ������ �Ʒ� �������� �׻� ��������θ� �̵��ϴ� ����� ����

�Է�
ù° �ٿ��� ������ ������ ũ�� M�� ������ ũ�� N�� ��ĭ�� ���̿� �ΰ� �־�����. 
�̾� ���� M�� �ٿ� ���� �� �ٿ� N���� ���������� ���ʷ� �� ������ ���̰� �� ĭ�� ���̿� �ΰ� �־�����.
M�� N�� ���� 500������ �ڿ����̰�, �� ������ ���̴� 10000������ �ڿ����̴�.

���
ù° �ٿ� �̵� ������ ����� �� H�� ����Ѵ�. ��� �Է¿� ���Ͽ� H�� 10�� ������ ���� �ƴ� �����̴�.

4 5
50 45 37 32 30
35 50 40 20 25
30 30 25 17 28
27 24 22 15 10

3


DFS �θ� Ǯ�� �ð��ʰ� -> DFS + DP ������ �ش� ��ǥ���� ���� ���� ����صΰ�, �ش� ��ǥ ��湮�� �ش簪�� �������־� �ݺ��۾��� ����.
*/
public class BJ_1520_Best {

	static int map[][];
	static long chk[][]; // ���� ��ġ ~ �������� ���� ����� ���� ����
	
	static int m, n;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		m = Integer.parseInt(st.nextToken()); // row
		n = Integer.parseInt(st.nextToken()); // col
		
		map = new int[m+1][n+1];
		chk = new long[m+1][n+1];
		
		// �湮 ���� �� ��ΰ��� ����� �ʱ�ȭ
		for(int i=1; i<=m ; i++) {
			for(int j=1; j<=n; j++) {
				chk[i][j] = -1;
			}		
		}
		
		for(int i=1; i<=m ; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=1; j<=n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}		
		}
		
		long ans = find(1,1);	// 	������
/*
chk �迭
 3  2  2  2  1 
 1 -1 -1  1  1 
 1 -1 -1  1 -1 
 1  1  1  1 -1 
*/		
		bw.write(ans+"\n");
		
		bw.flush();
		bw.close();
		br.close();
		
	}

	static long find(int x, int y) {

		int de =1;
		
		int dx[] = {-1, 0, 1, 0}; // ��, ��, ��, ��
		int dy[] = { 0,-1, 0, 1}; // ��, ��, ��, ��
		
		// 1. ������ ������ ��� 
		if(x == m && y == n) return 1;
		
		// 2. �̹� ����� ���� ��� - ���� ����� ���� return
		if(chk[x][y] != -1) return chk[x][y]; 
		
		chk[x][y] = 0; // ������ Ž�� ������ ��ġ, 0���� �ʱ�ȭ. (�湮�ߴ�!)
		
		// 3. ��� Ž�� �� ���� ����
		for(int i=0; i< 4 ; i++) {
			
			int next_x = x + dx[i];
			int next_y = y + dy[i];
			
			// ������ ��� ���, continue;
			if(next_x <1 || next_x> m || next_y < 1 || next_y > n) continue;

			// �� ���� ������ ��쿡�� �̵�!
			if(map[x][y] > map[next_x][next_y]) 
				chk[x][y] += find(next_x, next_y); // ������ ��ǥ�� ���� ��ǥ~ m,n ������ ��� ����.			
			
		}
		return chk[x][y];
	}
	
}
