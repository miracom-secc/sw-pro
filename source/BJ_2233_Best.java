package swPro.source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * �������
 * 1) ������ ������ �����̵���θ� �Է¹޴´�.
 * 2) 3���� �迭�� �����Ѵ�.
 * 	  depths  : Ʈ���� ����(depth)�� �����Ѵ�.
 * 	  parents : ������ ��� ��ġ�κ��� �θ� ����(���)�� ������ �����Ѵ�.
 *    visits  : ������ ����� �湮�� ��θ� �����Ѵ�.
 * 3) �Է¹��� ���� �̵� ��θ� ���� Ʈ���� �����ϰ�, ������ �湮�� ������� visits�迭�� �������ش�.   
			|	0 	|	0	|	0	|	1	|	0	|	1	|	1	|	0	|	1	|	1	|
 	----------------------------------------------------------------------------------------
  	prev	|	0	|	1	|	2	|	1	|	2	|	1	|	0	|	1	|	0	|	0	|
   	cur		|	1	|	2	|	3	|	2	|	4	|	2	|	1	|	5	|	1	|	0	|
 	apple	|	2	|	3	|	4	|	5	|	5	|	5	|	6	|	6	|	6	|	6	|
 	----------------------------------------------------------------------------------------
 	visit	|	1	|	2	|	3	|	3	|	4	|	4	|	2	|	5	|	5	|	1	|
 * 4) ���� ��� ������ �Է¹޾� visits�迭���� ���� ��� ��ȣ�� �˾Ƴ���.
 * 5) �� ��ȣ�� ���� �ʴٸ� �ּ� ���� ������ ã���ش�.
*/
public class BJ_2233_Best {  // ���� 2233
	static int N;
	static String visit;
	static int[] depths, visits;
	static int[][] parents;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine().trim());
		StringBuilder sb = new StringBuilder();
		
		// ������ ����
		N = Integer.parseInt(st.nextToken());
		
		depths = new int[N+1];
		parents = new int[N+1][12];  // ��� ���� ����(�ڽ��� �ִ� ����)�� �ּ� �� ���� �ڽ��� ���� ����
		
		// ���� �̵� ���
		visit = br.readLine().trim();
		visits = new int[visit.length()];
		
		/*********depth, parent ���� START ********/
		int cur = 1;
		int apple = 2;
		depths[cur] = 1;
		visits[0] = 1;
		
		for(int i=1; i<visit.length(); i++) {
			if(visit.charAt(i) == '0' ) { // ���ο� ��� �湮
				parents[apple][0] = cur;
				depths[apple] = depths[cur]+1;
				cur = apple++;
				visits[i] = cur;
			}
			else if(visit.charAt(i) == '1') { // �ǵ��ư���
				visits[i] = cur;
				cur = parents[cur][0];
			}
		}
		
		// ��Ʈ������ �������� ����
		for(int j=1; j<12; j++ ) {
			for(int i=1; i<N+1; i++) {
				parents[i][j] = parents[parents[i][j-1]][j-1];
			}
		}
		/*********depth, parent ����  END ********/
		
		
		// ���� ��� ����
		st = new StringTokenizer(br.readLine().trim());
		int X = visits[Integer.parseInt(st.nextToken())-1]; 
		int Y = visits[Integer.parseInt(st.nextToken())-1];
		
		int pos = X;
		
		if( X != Y )  {
			// �ּ� ���� ����
			pos = LCA(X,Y);
		}
		
		for(int i=0; i<visit.length(); i++) {
			if(visits[i] == pos) {
				sb.append(i+1).append(" ");
			}
		}
		
		System.out.println(sb.toString().trim());
	}

	static int LCA(int vtx1, int vtx2) {
		if(depths[vtx1] > depths[vtx2]) { // ���̰� �� ���� ��带 vtx2�� ����
			int tmp = vtx1;
			vtx1 = vtx2;
			vtx2 = tmp;
		}
		
		for(int i=11; i>=0; i--) { // ���̸� ���߱�
			int jump = 1 << i;
			
			if(depths[vtx2] - depths[vtx1] >= jump) {
				vtx2 = parents[vtx2][i];
			}
		}
		if(vtx1 == vtx2) return vtx1;
		
		for(int i=11; i>=0; i--) {
			if(parents[vtx1][i]==parents[vtx2][i]) continue;
			
			vtx1 = parents[vtx1][i];
			vtx2 = parents[vtx2][i];
		}
		
		return parents[vtx1][0];
	}
}
