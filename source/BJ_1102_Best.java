package swPro.source;

import java.io.*;
import java.util.*;


/*
 * ��Ʈ����ŷ ����
 * 1) num | (1<<i)
 * 		num�� �� �߰�
 * 		ex) 8 | (1<<2) = 1000 | 0100 = 1100(4��, 3�� ������ On ����)
 * 
 * 2) num & (1<<i)
 * 		num�� ���� ���� ��ȣ�� ��ȯ
 * 		ex) 8 & (1<<3) = 1000 & 1000 = 1000(4�� �����Ҹ� ��������)
 * 			12 & (1<<3) = 1100 & 1000 = 1000 ��ȯ
 * 			����, 12 & (1<<i) == (1<<i) -> i�� ���� ���¿� ��ġ�ϴ� ���̶�� ��(4��,3�� ������ on)
 * 
 * 
 * Ǯ��
 * 1) �ʱ⿡ �������� ���¸� ��Ʈ������ ǥ��
 *    ex) 3���� �����Ұ� ���峪 ���� ���� ����
 *    		10011
 *    		01110
 *    		00111
 * 2) �۵��ϴ� �������� ������ P�� ��ġ�Ҷ����� ���ȣ�� Ž��
 * 3) �ּҰ� memorization
 */

public class BJ_1102_Best {
	static int[] dp;
	static int[][] cost;
	static int N;
	static int P;
	static int init = 123456789;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());	//������ ����
		cost = new int[N][N];					//����� ���(��� ���·� �Է��� �־����Ƿ� 2���� �迭)			
		dp = new int[1<<N];						//��Ʈ������ ǥ���ϱ� ���� ������ ����^N
		
		for(int i=0; i<N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				cost[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		Arrays.fill(dp, -1);	//��ġ�°��� �Ұ����Ҷ� -1����ؾ� �ϹǷ� -1�� �ʱ�ȭ

		
		String[] status = br.readLine().split("");	
		P = Integer.parseInt(br.readLine());	//��� P�� �̻��� �����Ҵ� ������ ���� �ȵ�.
		
		int powerStatus = 0;	//���� �����ִ� ������ ����(��Ʈ����ũ) : YNN -> 001(2) = 1 
		int cnt = 0;	//���� �����ִ� ������ ����
		for(int i=0; i<status.length; i++) {
			if(status[i].equals("Y")) {
				powerStatus = powerStatus | (1<<i);
				cnt++;
			}
		}
		int anwser = powerplant(cnt,powerStatus);

		System.out.println(anwser == init? -1 : anwser);		
	}
	
	static int powerplant(int cnt, int powerStatus) {
		if(cnt >= P ) return 0;		//����� 0�϶�, ������ ���� NYN, P=1�̸�, ����� 0
		if(dp[powerStatus] != -1) return dp[powerStatus];
		
		dp[powerStatus] = init; 
		
		for(int i=0; i<N; i++) {
			// pNum�� �����Ұ� �������� ��
			if((powerStatus &(1<<i)) == (1<<i)) {
				for(int j=0; j<N; j++) {
					// ���� ��ȣ�� �������� ��� || j�� �����ִ� ��� ��ŵ 
					if((i==j) || (powerStatus&(1<<j)) == (1<<j)) continue;
					
					//�ּҰ� ���ϱ�
					dp[powerStatus] = Math.min(dp[powerStatus], powerplant(cnt+1, powerStatus|(1<<j)) + cost[i][j]);	// pNum|1<<���峭 ������ + cost[�����߹�����][���峭������]
				}
				
			}
		}
		return dp[powerStatus];
	}
}
