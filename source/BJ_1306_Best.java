package swPro.source;

import java.io.*;
import java.util.*;
 
/*

�ð� ����	�޸� ����	����	����	���� ���	���� ����
2 ��	256 MB	1056	455	350	44.473%

�� ����
ȫ���̴� �����̴�. �׷��� ��¼�� ���� �ƹ��� �پ �پ �ӵ��� ������ �ʴ´�. 1�ʿ� �� 1ĭ�� �����δ�.
�׷��� ȫ���̰� �ٴ� �ڽ��� ���������� �����ϴ�. �������� ���� ���Ⱑ �ٸ���, ȫ���̴� �ڽ��� �� �� �ִ� �����ǵ� �߿��� ���� ������ ���� �����Ǹ��� ���� ���´�.
ȫ���̴� ���� ������ ������ ���� ���Ⱑ ���� ���������� �Ȱ��� ���� �ٷ��Ѵ�. �׷��� �˾ƾ� �� ����, �پ�鼭 ���̴� �������� ���� ���⸦ �˰� �ʹ�.
ȫ���̰� �پ ��, 1�ʸ��� ���̴� �������� ���� ���⸦ ����Ͽ���.

�� �Է�
ù° �ٿ��� �ٴ� �ڽ��� ����, �� ĭ�� N�� ȫ������ �þ��� ���� M�� �־�����. �þ߰� M�̶�� �ϸ� ���� ��ġ���� �յڷ� M-1ĭ���� �������� ���̴� ���̴�. (1 �� M �� N �� 1,000,000) 
�� ��° �ٿ��� ���� ĭ�� �ִ� �����ǵ��� ���� ���Ⱑ �־�����. ���� ����� 1,000,000�� ���� �ʴ� �ڿ����̴�.
ȫ���̴� ������ �������� 2M-1�� ���鼭 �ٰ� �ͱ� ������(�߽�����) M��° ĭ���� �ٱ� �����ؼ� N-M+1��° ĭ���� ����ٰ� ��������.

�� ���
�ٸ鼭 ���̴� �������� ���⸦ ����Ѵ�.

 */

public class BJ_1306_Best {
	
	static void init(int[] a, int[] tree, int node, int start, int end) {
        if (start == end) {
            tree[node] = a[start];
        } else {
            int mid = (start + end) / 2;
            int nextNode = node * 2;

            init(a, tree, nextNode, start, mid);
            init(a, tree, nextNode + 1, mid + 1, end);
            tree[node] = Math.max(tree[nextNode], tree[nextNode + 1]);
        }
    }

    static int query(int[] tree, int node, int start, int end, int i, int j) {
        if (i > end || j < start) return -1;
        if (i <= start && end <= j) return tree[node];
        int mid = (start + end) / 2;
        int nextNode = node * 2;
        int m1 = query(tree, nextNode, start, mid, i, j);
        int m2 = query(tree, nextNode+ 1, mid + 1, end, i, j);
        return Math.max(m1, m2);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String[] line = bf.readLine().split(" ");
        int n = Integer.parseInt(line[0]);
        int m = Integer.parseInt(line[1]);
        int[] a = new int[n];
        String temp = bf.readLine();
        String[] tempArr = temp.split(" ");
        for (int i = 0; i < tempArr.length; i++) {
            a[i] = Integer.parseInt(tempArr[i]);
        }


        int h = (int) Math.ceil(Math.log(n) / Math.log(2));
        int tree_size = (1 << (h + 1));
        int[] tree = new int[tree_size];
        init(a, tree, 1, 0, n - 1);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int i = m - 1; i < n - m + 1; i++) {
            int start = i - m + 1;
            int end = i + m - 1;
            bw.write(query(tree, 1, 0, n - 1, start, end) + " ");
        }
        bw.flush();
    }
    
}

