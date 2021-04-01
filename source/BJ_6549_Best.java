package swPro.source;

import java.io.*;
import java.util.*;
 
/*

�ð� ����	�޸� ����	����	����	���� ���	���� ����
1 ��	256 MB	25440	6326	4062	25.456%

�� ����
������׷��� ���簢�� ���� ���� �Ʒ������� ���ĵǾ� �ִ� �����̴�. �� ���簢���� ���� �ʺ� ������ ������, ���̴� ���� �ٸ� ���� �ִ�. 
���� ���, ���� �׸��� ���̰� 2, 1, 4, 5, 1, 3, 3�̰� �ʺ� 1�� ���簢������ �̷���� ������׷��̴�.
������׷����� ���� ���̰� ū ���簢���� ���ϴ� ���α׷��� �ۼ��Ͻÿ�.

�� �Է�
�Է��� �׽�Ʈ ���̽� ���� ���� �̷���� �ִ�. �� �׽�Ʈ ���̽��� �� �ٷ� �̷���� �ְ�, ���簢���� �� n�� ���� ó������ �־�����. (1 �� n �� 100,000) �� ���� n���� ���� h1, ..., hn (0 �� hi �� 1,000,000,000)�� �־�����. 
�� ���ڵ��� ������׷��� �ִ� ���簢���� �����̸�, ���ʺ��� �����ʱ��� ������� �־�����. ��� ���簢���� �ʺ�� 1�̰�, �Է��� ������ �ٿ��� 0�� �ϳ� �־�����.

�� ���
�� �׽�Ʈ ���̽��� ���ؼ�, ������׷����� ���� ���̰� ū ���簢���� ���̸� ����Ѵ�.

*/

public class BJ_6549_Best {
	static int N;
	static long his[], sgtree[];
	static long maxArea = 0L;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("src/algorithm/sample_input.txt"));
        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        
        while(true) {
        	String readLine = br.readLine();
        	if ("0".equals(readLine)) break;
        	
        	StringTokenizer st = new StringTokenizer(readLine);
        	N = Integer.parseInt(st.nextToken());
        	
        	his = new long[N];
            sgtree = new long[N*4];
            sgtree[0] = Integer.MAX_VALUE;
            
            for (int i=0; i<N; i++) his[i] = Integer.parseInt(st.nextToken());
            
            // ������ �ּ� ������ index�� �����ϴ� ���׸�Ʈ Ʈ�� ����
            maxArea = 0L;
            init(his, 0, N-1, 1);
            largestHis(0, N-1);
            
            bw.write(String.valueOf(maxArea));
            bw.newLine();
        }
        
        br.close();
        bw.close();
    }
    
    /* ���׸�Ʈ Ʈ�� �ʱ� ���� */
    static long init(long[] arr, long left, long right, long node) {
        // ���۰� ���� ������ �迭 �ڽ��� ����
    	if (left == right) return sgtree[(int) node] = left;
        	
    	long mid = (left + right) / 2;
    	long leftMinIdx = init(arr, left, mid, node * 2);
    	long rightMinIdx = init(arr, mid + 1, right, node * 2 + 1);
    	
        return sgtree[(int) node] = arr[(int) leftMinIdx] > arr[(int) rightMinIdx] ? rightMinIdx : leftMinIdx;
    }
    
    /* ���׸�Ʈ Ʈ�� ������ �ּ� index ���ǿ���*/
    static long query(long left, long right, long node, long nodeLeft, long nodeRight) {
        //�� ������ ��ġ�� ������ ���� ���� ���� ��ȯ�Ѵ�. ���õ�.
    	if (right < nodeLeft || nodeRight < left) return -1L;
        
    	// sgtree�� ǥ���ϴ� ������ arr[left...right]�� ������ ���ԵǴ� ��� , �̹� ������ ���� ��� �ִ� ��� ����
        if (left <= nodeLeft && nodeRight <= right)  return sgtree[(int) node];
        
        long mid = (nodeLeft + nodeRight) / 2;
        long leftMinIdx = query(left, right, node * 2, nodeLeft, mid);
        long rightMinIdx = query(left, right, node * 2 + 1, mid+1, nodeRight);
        
        if (leftMinIdx < 0) return rightMinIdx;
        if (rightMinIdx < 0) return leftMinIdx;
        
        return his[(int) leftMinIdx] > his[(int) rightMinIdx] ? rightMinIdx : leftMinIdx;
    }
    
    /* �κ��� �������鼭 �ִ� ���� ã�� */
    static void largestHis(long start, long end) {
    	//������ ���缺 üũ (start�� end ���� ũ�� �ȵȴ�)
    	if (start > end) return;
    	
    	long size = end - start + 1;
    	long minIdx = query(start, end, 1, 0, N-1);
    	
    	// ������ �ִ� ���� ����
    	maxArea = Math.max(maxArea, his[(int) minIdx] * size);
    	
    	largestHis(start, minIdx-1);
    	largestHis(minIdx+1, end);
    }
    
}

