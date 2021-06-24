package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
	�Ƽ��� : �Ƽ����� ���� �Ƶ������ �� ������ �ִܰŸ��� x������ ���̴�.
	������ : �������� ���� ��Ÿ������ �� ������ �ִܰŸ��� y������ ���̴�.
	�Ƽ��ǰ� �������� �����ϴ� �� �� �ִܰŸ��� ���� �ּ��� ��
*/
/*
 ù��: ������ ���� V(3 �� V �� 10,000)�� ������ ���� E(0 �� E �� 300,000) 
 �� ���� 1~1+E�� ���� �� ���θ� ��Ÿ���� �� ���� ���� (u,v,w)�� ������� �־�����. 
 		- ���� u�� v(1 �� u,v �� V), ����ġ�� w(1 �� w < 10,000)�� ����
 		- u�� v�� ���� �ٸ��� �ٸ� �� ���� ���̿��� ���� ���� ������ ������ ���� ����
 E+2��° ��: �Ƶ������� �� M(1 �� M �� V-2) �Ƽ����� ���� x(1 �� x �� 100,000,000)
 E+3:M���� �Ƶ����� ���� ��ȣ�� �־�����. 
 E+4:��Ÿ������ �� S(1 �� S �� V-2), �������� ���� y(1 �� y �� 100,000,000)
 E+5: S���� ��Ÿ���� ���� ��ȣ�� �־�����. 

 .�Ƶ����峪 ��Ÿ������ ��ġ�� �������� ���� ����.
 .�� ������ �Ƶ������ ��Ÿ������ ���� ��ġ�� �� �ִ�.
 .���� �ִ�(= �Ƶ����峪 ��Ÿ������ ��ġ���� ����) ������ �ϳ� �̻� �����Ѵ�.
*/

class Ed implements Comparable<Ed> {
    int v;
    int w;

    public Ed(int v, int w) {
        this.v = v;
        this.w = w;
    }

    @Override
    public int compareTo(Ed o) {
        // TODO Auto-generated method stub
        return Integer.compare(this.w, o.w); // x==y : 0 ,  x < y : -1, x > y :  1
        //return this.w < o.w ? -1 : 1;
    }
}

public class BJ_113911_Best {
	static ArrayList<Ed>[] arr;
	static boolean visit[];
	static int V,E;
	static int M, Mx, S ,Sy;
	static int[] mdist;
	static int[] sdist;
	static PriorityQueue<Ed> queue = new PriorityQueue<>();


	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st = new StringTokenizer(br.readLine());		
		
		V = Integer.parseInt(st.nextToken()); // ������ ����
		E = Integer.parseInt(st.nextToken()); // ������ ����
		
		arr = new ArrayList[V+1];
		for(int i=1; i<=V; i++) { //�Ƽ���, ������ �θ��� �����ϱ����� +2 
			arr[i] = new ArrayList<>();
		}
		mdist = new int[V+1]; //�Ƶ������
		sdist = new int[V+1]; //��Ÿ������
		
		for(int i=0; i<E; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()); //��1
			int v = Integer.parseInt(st.nextToken()); //��2
			int w = Integer.parseInt(st.nextToken()); //����ġ
			arr[u].add(new Ed(v,w));	
			arr[v].add(new Ed(u,w));	
		}		
		
		// �Ƽ���
		Arrays.fill(mdist,Integer.MAX_VALUE);
		st = new StringTokenizer(br.readLine());
		
		M = Integer.parseInt(st.nextToken()); // �Ƶ������ 
		Mx = Integer.parseInt(st.nextToken()); // �Ƽ��� ����		

		st = new StringTokenizer(br.readLine());
		for(int i=0; i<M; i++) {
			int mm =  Integer.parseInt(st.nextToken()); // �Ƶ����� ��ġ
			mdist[mm] = 0;
			queue.add(new Ed(mm, 0));	// �Ƶ������� ��ġ�� ������. ����ġ 0
			
		}
		dijkstra(mdist);

		// ������
		Arrays.fill(sdist,Integer.MAX_VALUE);
		st = new StringTokenizer(br.readLine());
		
		S = Integer.parseInt(st.nextToken()); // ��Ÿ���� �� 
		Sy = Integer.parseInt(st.nextToken()); // ������ ����
		
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<S; i++) {
			int ss =  Integer.parseInt(st.nextToken()); // ��Ÿ���� ��ġ
			sdist[ss] = 0;
			queue.add(new Ed(ss, 0)); // ��Ÿ������ ��ġ�� ������. ����ġ 0
		}
		dijkstra(sdist);
		
		int ans = Integer.MAX_VALUE;
        for(int i=1; i<=V; i++) {
            if((mdist[i]>0 && mdist[i]<=Mx) && (sdist[i]>0 && sdist[i]<=Sy)) // dist������ ����� ������, �Ƽ��� ���Ǻ��ٴ� �۾ƾ���.
                ans = Math.min(ans, mdist[i]+sdist[i]);
        }

      

        if(ans==Integer.MAX_VALUE)
        	ans = -1;

        bw.write(String.valueOf(ans));
        bw.flush();
		bw.close();
		br.close();

	}
	

	public static void dijkstra(int[] dist) {

        while(!queue.isEmpty()) {
            Ed eg = queue.poll();
            
            int des = eg.v;
            for (Ed next : arr[des]) {
                if (dist[next.v] >= dist[des] + next.w) {
                    dist[next.v] = dist[des] + next.w;
                    queue.add(new Ed(next.v,dist[next.v]));
                }
            }
        }
    }		
}
