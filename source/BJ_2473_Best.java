package swPro.source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * �Է�
 * 	N : ��ü ����� ��(3 ~ 5000)
 *  ����� Ư���� : -1,000,000,000 ~ 1,000,000,000
 * 
 * ���
 * ù° �ٿ� Ư������ 0�� ���� ����� ����� ������ �� ����� Ư������ ���
 * ����ؾ��ϴ� �� ����� Ư������ ������������ ���
 * Ư������ 0�� ����� ����� ������ ��찡 �� �� �̻��� ��쿡�� �� �� �ƹ��ų� �ϳ� ���
 * 
 * 
 * Ǯ�̹�
 * �ϳ��� ����� �����ϰ�, ������ ��׿��� left, right�� �����Ͽ� ������� ���� 0�� ����� ��츦 ���
 * �� ����� ���� 3,000,000,000 �ϼ��� �����Ƿ� longŸ������ Ǯ��
 */

public class BJ_2473_Best {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		
		long[] solutions = new long[N];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		for(int i = 0; i < N; i++) {
			solutions[i] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.sort(solutions);	//�������� ����
		
		long[] res = new long[3];
		
		long diff = Long.MAX_VALUE;

		// ���� ���ʺ��� �����ؼ� ���� ������ ������
		for(int i = 0; i < N; i++) {
			int left = i+1; // ���� ������ �����ϰ� �� �������� ��ȸ
			int right = N-1;
			
			while(left < right) {
				// �� ����� �� ���
				long sum = solutions[i] + solutions[left] + solutions[right];
				
				// ���� ���
				long cur_diff = Math.abs(sum);
				
				// ���̰� �� �۴ٸ� ���� ����
				if(cur_diff < diff) {
					diff = cur_diff;
					res[0] = solutions[i];
					res[1] = solutions[left];
					res[2] = solutions[right];
				}
				
				if(sum > 0) { // ������� ���� 0���� ũ�� right�� ����� �� ĭ ������
					right--;
				} else { // ������� ���� 0���� ������ ���� ����� �� ĭ ������
					left++;
				}
			}
		}
		System.out.println(res[0] + " " + res[1] + " " + res[2]);
	}
}
