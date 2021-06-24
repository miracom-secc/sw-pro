package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
6
2 1 2 0 3 1
1 2 2 2 2 0
1 0 1 2 0 3
2
2 4


1
2 
1
1
2
2 3
*/

public class BJ_1273_Best {

    static int N, M, max;
    static int hight[];
    static int sum[];
    static int leafNode, tree[];

    static final int score[] = {1, 2, 5};

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    
    public static void main(String[] args) throws Exception{
        N = Integer.parseInt(br.readLine());

        sum = new int[3000001]; //�� �հ� ����
        hight = new int[1000000 * 3+1];
        max = 0;

        init();
        int totCnt = leafNode * 2;
        tree = new int[totCnt+1];

        for (int i=0; i<3; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            for (int j=0; j<N; j++) {
                int h = Integer.parseInt(st.nextToken());
                // ������ ������ ���� �� ����
                sum[hight[j]] += score[i]; //��������
                sum[hight[j] + h] -= score[i]; //������

                hight[j] += h;
                max = Math.max(max, hight[j]);
            }
        }

        // ==================== ������ ���ϱ�
        update(0, 1);
        for (int i=1; i<3000001; i++) {
            sum[i] += sum[i-1];
            update(i, 1);
        }

        // System.out.println(Arrays.toString(sum));
        // System.out.println(Arrays.toString(tree));

        // =================== �� ã��
        M = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int m=0; m<M; m++) {
            int p = Integer.parseInt(st.nextToken());
            if (max < p) {
                bw.write(0+"\n");
                continue;
            }

            int idx = find(p);
            // System.out.println("p= "+p+" idx= "+idx);
            bw.write(sum[idx]+"\n");
            update(idx, -1);
            max--;
        }

        br.close();
        bw.close();
    }

    /* leafNode ������ ã�� */
	static void init() {
		leafNode = 1;
		while(3000001 > leafNode) {
			leafNode *= 2; //leafNode�� ������
		}
	}

	/* �� ������Ʈ */
	static void update(int idx, int diff) {
		idx = leafNode + idx; //idx ������ 1�����Ͷ� 1���ֱ�
		
		tree[idx] += diff;
		while (idx > 1) {
			idx /= 2;
			tree[idx] += diff;
		}
	}

	/* x��° �� ã�� */
	static int find(int x) {
		int index = 1;

		while (index < leafNode) {
			if (tree[index*2] >= x) {
				index *= 2 ;
			} else {
				x -= tree[index*2];
				index = index *2 +1;
			}
		}

		return index-leafNode;
	}
}
