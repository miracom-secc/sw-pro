package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * ���� �ڵ�
 * 1) �� N���� ������ �̷���� ���� X1, X2, ... XN �Է�
 * 2) ������ �� K�� ����, �� ���帶�� ��� ����
 *    2-1) ���� : ������ Ư���� �� ����
 *         ��,������ ��� ������ ���� ������ �� ���� ����(diff)�� �̿��Ͽ� Ʈ���� �������鼭 �ش� ������ ���� ������Ʈ ������
 *         ������ ��� �߰����� 0�� ��� �̷��� ����� ������ �� ���� �ش� ��忡������ �ö���鼭 ���� ������Ʈ���ش�.
 *    2-2) ���� : ������ i~j������ ���� ��� ���� ����� ���/����/0���� ���
 *         ��������� ������ ��ġ�� �ʱ� ����  ��,������ 0�� ���������� ������ ��� 1�� ����
 * 
 *  �̽� �߻�
 * 1) EOF ó���� �� 'br.readLine().trim() != null'�� ó���ϸ� ��Ÿ�� ���� (NullPointer) �߻�
 *    --> trim()�����ϰ� 'br.readLine() != null'�� ó���ϴ� �ذ�
 * 2) Overflow �߻�
 *    - �ڷ��� long�̸� ����ϴٰ� ���������� overflow �߻� (ū ���� �׽�Ʈ���̽����� +,- ������� ���� �ڹٲ�� ��� �߰�)
 *    - ���� ��ɿ��� i=1,j=10^5(1��i��j��10^5),������ ��� ���� 100(-100��Xi��100)�� ��� ����� 100^10^5(=100^100000)
 *    --> �Է¹��� ���� �������� �ʰ� ��ȣ�� ����(����� 1, ������ -1, 0�� 0)
 *    
 * # �׽�Ʈ���̽� 
 *   IN) http://maratona.ime.usp.br/hist/2012/resultados12/data/contest/I.in
 *   OUT) http://maratona.ime.usp.br/hist/2012/resultados12/data/contest/I.sol
 */

public class BJ_5676_Best { // ���� 5676
	static int[] arr, tree;

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		while (true) { 
			String input = br.readLine();
			if( input == null) break;
			st = new StringTokenizer(input);

			int N = Integer.parseInt(st.nextToken()); // ������ ũ��
			int K = Integer.parseInt(st.nextToken()); // ���� ����� 

			arr = new int[N+1];
			tree = new int[N*4];
			Arrays.fill(tree, 1);

			st = new StringTokenizer(br.readLine().trim());
			for(int i=0; i<N; i++) {
				int num = Integer.parseInt(st.nextToken());
				// overflow�� �����ϱ� ���� ��ȣ�� ����
				arr[i] = num > 0 ? 1 : num < 0 ? -1 : 0;
			}

			init(0, N-1, 1);

			for(int i=0; i<K; i++) {
				st = new StringTokenizer(br.readLine().trim());
				// C(����) i, V -> Xi�� V�� ���� (1 �� i �� N, -100 �� V �� 100)
				// P(����) i, j -> Xi~Xj ������ �� (1 �� i �� j �� N)
				char cmd = st.nextToken().charAt(0);
				int val1 = Integer.parseInt(st.nextToken());
				int val2 = Integer.parseInt(st.nextToken());

				if(cmd=='C') {
					val2 = val2 > 0 ? 1 : val2 < 0 ? -1 : 0;
					cmdC_update(0, N-1, 1, val1-1, val2); // start, end, node, position, value
					arr[val1-1] = val2;
				}
				else if(cmd == 'P') {
					int rst = cmdP_mulp(0, N-1, 1, val1-1, val2-1);
					sb.append(rst > 0 ? '+' : rst < 0 ? '-' : '0');
				}
			}
			sb.append("\n");
		}
		bw.write(sb.toString());
		bw.close();
		br.close();
	}

	static int init(int start, int end, int node ) {
		if(start == end) {
			return tree[node] = arr[start];
		}

		int mid = (start+end)/2;  

		return tree[node] = init(start, mid, node*2) * init(mid+1, end, node*2+1);
	}

	static int cmdC_update(int start, int end, int node, int idx, int newVal) {
		// ������ ���� ���� ������ �״�� ����
		if(idx < start  || idx > end)  {
			return tree[node];
		}
		// �߰� ��尡 0�� ��� ���� ����� ������ �� ���� ������ ���������� ���� ������Ʈ
		if(start == end ) return tree[node] = newVal; 

		int mid = (start+end)/2;
		return tree[node] = cmdC_update(start, mid, node*2, idx, newVal) * cmdC_update(mid+1, end, node*2+1, idx, newVal);

	}

	static int cmdP_mulp(int start, int end, int node, int left, int right) {
		// �������� ������ ��ġ�� �ʱ� ���� ��,������  0�� ���������� ������ 1�� ����
		if(left>end || right < start) return 1; 

		if(left <= start && end <= right)
			return tree[node];

		int mid = (start+end)/2;
		return cmdP_mulp(start, mid, node*2, left, right) * cmdP_mulp(mid+1, end, node*2+1, left, right);
	}
}