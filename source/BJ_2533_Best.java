package swPro.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/*
 1. �ڽ��� �󸮾���� X : ģ�� ��� �󸮾����
 2. �ڽ��� �󸮾���� O : ģ�� ���X
 
 input[v][0] : ���� v�� �� �ƴ��Ͱ� �ƴ� ���, ģ������ ���� ��
 input[v][1] : ���� v�� �� �ƴ����� ���, �� �ڽĵ��� �� �ƴ����̰ų� �ƴ� ���� �ּҰ����� ��
 */
public class BJ_2533_Best {
	public static int input[][];
	//public static LinkedList<Integer>[] list;
	public static List<Integer>[] list;
	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine().trim());
		
		int N = Integer.parseInt(st.nextToken());
		list = new ArrayList[N+1];
		input= new int[N+1][2];
		
		// ���� N���� ���� ����Ʈ ���� 
		for (int i = 1; i <= N; i++) 
			list[i] = new ArrayList<Integer>();
		
		for (int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			list[a].add(b);
			list[b].add(a);
		}
		
		ea(1, -1); // 1���� ����, �θ�� ���� ���� -1 

		//System.out.println(Math.min(input[1][0], input[1][1]));
		bw.write(String.valueOf(Math.min(input[1][0], input[1][1])));
		bw.flush();
		bw.close();
		br.close();
	}
	
	public static void ea(int v, int parent) {
		
		
		input[v][0] = 0; 
		input[v][1] = 1;
		
		for(int next : list[v]) {
			if(next != parent) {  // �θ��尡 ���� ���� �̹� Ȯ���� �� ��� 
				// parent�� ���� ��� ���� �־�, ���� �б⿡�� �Ǵ� 
				ea(next, v); 
				input[v][0] += input[next][1];	//�θ� ����X -> �ڽ� ����
				input[v][1] += Math.min(input[next][0], input[next][1]); //�θ� ���� -> �ڽ��� ���Ե� ��,���� �ȵ� �� �߿� ���� ��
			}
		}
	}

}
