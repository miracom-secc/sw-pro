package tree;

import java.io.*;
import java.util.*;
/*
3
10 3
175 182 178 179 170 179 171 185 185 181
3 7 175
1 10 180
1 10 179
7 5
183 176 175 183 174 182 186
1 4 176 
2 6 177
1 7 180
1 7 160
5 7 180
2 2
161 168
1 2 175
1 2 188 
* */


/* a ~ b ��° ���̿� x ���� ū����� �� ��? => ������
 * 
 * Ǯ�� ����
 *  => ��������� ���� ������ ������ �Է� ���� �� �����Ͽ� ���� ���� ���
 * 
 * 1. �Է�
 *  1) ���ִ� ����� ������ ������ ������ Ÿ���� ������ �Է¹���
 *     - �������: Type 1
 *     - ��������: Type 2
 *  2) ��ü ����
 *     - Ÿ��, Ű, �ε���(�������/������), Ž�����۱���, Ž�����ᱸ��
 * 2. ����
 *  1) Ű�� �������� ���� 
 * 3. �Է¹��� ������ ���� ����
 *  1) ����� ��� Ʈ���� insert
 *  2) ������ ��� ������
 * 4. ���� ���
 * 
 * */

public class P_Ű������ {
	
	static class Info implements Comparable<Info>{
		int type;	//Ÿ�� - 1: ���, 2: ����
		long h;		//Ű
		int idx;	//�ε���
		int start; // Ž�����۱���
		int end;	// Ž�����ᱸ��
		
		// ���������
		Info(int type, long h, int idx){
			this.type = type;
			this.h = h;
			this.idx = idx;
		}
		// ����������
		Info(int type, long h, int idx, int start, int end){
			this.type = type;
			this.h = h;
			this.idx = idx;
			this.start = start;
			this.end= end;
		}
		
		@Override
		public int compareTo(Info o) {
			if(this.h == o.h) {
				if(this.type > o.type) return -1;
				else return 1;
			}else {
				return (this.h >o.h)? -1: 1;
			}
		}
	}
	
	static int n,q; // ����� ��, ������ ��
	static Info input[];
	static long tree[]; 
	static int leaf;
	static long ans[];
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;
		int t = Integer.parseInt(br.readLine().trim());
		int de;
		for(int tc=1; tc <=t ; tc++) {
			
			st= new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken());
			q = Integer.parseInt(st.nextToken());
			
			input = new Info[n+q+1];	// ���� �Է¿�
			leaf=1;
			while(leaf <n+q+1){
				leaf *=2;
			}
			tree = new long[leaf*2]; 	// Ʈ�� ������
			ans = new long[q+1]; 			//���������						
			
			/****************** 1. �Է� ******************/
			// 1) ���ִ� ����� ����
			st= new StringTokenizer(br.readLine());
			for(int i=1; i<=n; i++) {
				long h = Long.parseLong(st.nextToken());
				input[i] = new Info(1,h,i); // Ÿ��1, Ű
			}
			input[0] = new Info(1,0,0); 
			
			// 2) ���� ����
			for(int i=1; i<= q; i++) {
				st= new StringTokenizer(br.readLine());
				int left = Integer.parseInt(st.nextToken());
				int right = Integer.parseInt(st.nextToken());
				long h = Long.parseLong(st.nextToken());
				input[i+n] = new Info(2, h, i, left, right); // Ÿ��2, Ű, �����ε���, Ž�����۱���, Ž�� ���ᱸ��		
			}
			
			/****************** 2. ���� ******************/
			Arrays.sort(input); // 1. Ư�� Ű���� ū ����� ���� ���ϴ� �����̹Ƿ� Ű�� �������� sort, 2. ū ����� ���̹Ƿ� ���� ���� ���ܵǾ�� �ϴ� Ű�� ������쿡�� ������ ���������� sort
			
			de=1;
			/****************** 3. ���� ******************/			
			for(int i=0; i<= n+q; i++ ) { 
				Info info = input[i];
				
				// 1) ��������� ���
				if(info.type == 1) {
					update(info.idx,1);									
				}
				// 2) ���� ������ ���
				else {
					ans[info.idx] = sum(info.start, info.end); // �Է¹��� �� ������ �ε����� �ش� ������ ���� ����.
					
				}				
			}
			StringBuilder sb = new StringBuilder();
			sb.append("#"+tc);
			for(int i=1; i<=q; i++) {
				sb.append(" "+ans[i]);
			}
			bw.write(sb.toString() +"\n");
			bw.flush();
		}
		
		bw.close();
		br.close();
	}
	
	static long sum(int start, int end) {
		long ret=0; 
		
		start = start+leaf-1;
		end= end+leaf-1;
		
		while(start < end) {
			if(start % 2 ==1) {
				ret += tree[start];
				start++;
			} 
			if(end % 2 == 0) {
				ret += tree[end];
				end--;
			}
			
			start /= 2;
			end /= 2;
		}
		if(start== end)
			ret += tree[end];
		
		return ret;
	}
	
	static void update(int idx, int val) {
		idx = idx+leaf-1;
		tree[idx] += val;
		
		while(idx > 1) {
			idx /=2 ;
			tree[idx] = tree[idx*2]+ tree[idx*2+1];
		}
		
	}

}
