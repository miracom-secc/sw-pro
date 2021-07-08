package swPro.source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;
import java.util.StringTokenizer;

/* �̺�Ž�� Ǯ�� 
- �޸� : 53564KB
- �ð� : 696ms

* ��������
- wire[N][2] : A������� B�����븦 ������ ������ ������ ����
 			   wire[n][0] : n��° ������ A������� ����Ǵ� ��ġ�� ��ȣ
 			   wire[n][1] : n��° ������ B������� ����Ǵ� ��ġ�� ��ȣ
- ArrayList<int[]> list : LIS ������ ����� ����Ʈ (= LIS�� �ִ���̴� list�� ũ�⸦ �ǹ�)
- checked[N] : LIS������ �ε����� �����ϴ� �迭, ������ ������ ã�Ƴ��µ� ��� 
- Stack<Integer> remove : �����ؾ��ϴ� �������� A������ ��ȣ�� ���������� ����ϱ� ���� �ڷᱸ��

* Ǯ�̹��
1) wire�� ������ ������ �Է¹޴´�.
2) A�������� ��ȣ�� �������� �������� �����Ѵ�.
3) list�� ����ó��(Null Exception �߻�����)�� ���� ���̰�(0)�� �־��ش�.
4) B�������� ��ȣ�� ���ϸ� LIS�� ���� & checked�迭�� �� ������ LIS������ �ε����� ����
  4-1) B�������� ��ȣ�� list�� ������ ��Һ��� ũ�� list�� �������� �߰� / checked[i] = list.size()-1 (���̰�(0))
  4-2) B�������� ��ȣ�� list�� ������ ��Һ��� ������ list(=LIS)���� �ش簪�� �� ��ġ�� ã�� �ٲ��ش�. / checked[i] = idx (�ش簪�� �� ��ġ)
  		-> list(=LIS)���� �ش簪�� �� ��ġ�� �̺�Ž������ �˻��Ѵ�(list�� ������������ ���ĵǾ� �ִ� ����)
5) �����ؾ��� ������ ���� ��� (list�� ũ��� ������ �� �ִ� ������ �ִ밳���̹Ƿ� N - (list.size()-1)�� ���. -1�� ���̰�(0))
6) checked�迭�� ���������� Ž���Ͽ� LIS�� �ε������� ���������� ������ Stack�� ���Ŵ������ �з�     
7) Stack���� �ϳ��� ���� ���� ��� 
 
* �׽�Ʈ���̽�
1) INPUT : N=8, (3,30), (6,10), (1,10), (4,5), (5,20), (8,40), (7,30), (2,20) 
2) A���� ���� : (1,10), (2,20), (3,30), (4,5), (5,20), (6,10), (7,30), (8,40)
3) list : {0}
4) LIS ����
------------------------------------------------
i=0 (10)  list | {0, 10} 
		checked| 1 | 0 | 0 | 0 | 0 | 0 | 0 | 0 |
------------------------------------------------		
i=1 (20)  list | {0, 10, 20} 
		checked| 1 | 2 | 0 | 0 | 0 | 0 | 0 | 0 |
------------------------------------------------
i=2 (30)  list | {0, 10, 20, 30} 
		checked| 1 | 2 | 3 | 0 | 0 | 0 | 0 | 0 |
------------------------------------------------
i=3 ( 5)  list | {0, 5, 20, 30} 
		checked| 1 | 2 | 3 | 1 | 0 | 0 | 0 | 0 |
------------------------------------------------
i=4 (20)  list | {0, 5, 20, 30} 
		checked| 1 | 2 | 3 | 1 | 2 | 0 | 0 | 0 |
------------------------------------------------
i=5 (10)  list | {0, 5, 10, 30} 
		checked| 1 | 2 | 3 | 1 | 2 | 2 | 0 | 0 |
------------------------------------------------
i=6 (30)  list | {0, 5, 10, 30} 
		checked| 1 | 2 | 3 | 1 | 2 | 2 | 3 | 0 |
------------------------------------------------
i=7 (40)  list | {0, 5, 10, 30, 40} 
		checked| 1 | 2 | 3 | 1 | 2 | 2 | 3 | 4 |
------------------------------------------------
5) �����ؾ��� ������ ���� : N - (list.size()-1) = 8-4 = 4
6) checked�迭�� ���������� Ž���Ͽ�
7) index = list.size()-1, checked�� �ڿ������� Ž��
	checked| 1 | 2 | 3 | 1 | 2 | 2 | 3 | 4 | 
	                                 	 O   index--, index:3, stack : {}
	                              	 O       index--, index:2, stack : {}
	                             O           index--, index:1, stack : {}
	                         X								   stack : {4} 
	                     O                   index--, index:0
	                 X										   stack : {4, 2} 
	             X											   stack : {4, 2, 1} 
	         X												   stack : {4, 2, 1, 0} 
8) stack.pop : wire[0][0]=>wire[1][0]=>wire[2][0]=>wire[4][0] 

	
* INPUT 
8
3 30
6 10
1 10
4 5
5 20
8 40
7 30
2 20

* OUTPUT
4
1
3
5
	           
*/
public class BJ_2568_Best{ // 2568 ������2
	public static void main(String[] args) throws Exception{
		BufferedReader br = new  BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st= null;
		StringBuilder sb= new StringBuilder();
		
		int N = Integer.parseInt(br.readLine().trim());
		
		// 1)
		int[][] wire = new int[N][2];
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine().trim());
			
			wire[i][0] = Integer.parseInt(st.nextToken());
			wire[i][1] = Integer.parseInt(st.nextToken());
		}

		// 2)
		Arrays.sort(wire, new Comparator<int[]>() {

			@Override
			public int compare(int[] o1, int[] o2) {
				// TODO Auto-generated method stub
				return Integer.compare(o1[0], o2[0]);
			}
		});
		
		// 3)
		ArrayList<int[]> list = new ArrayList<>();
		list.add(new int[] {0,0});
		
		int[] checked = new int[N];
		
		// 4)
		for(int i=0; i<N; i++) {
			if(list.get(list.size()-1)[1] < wire[i][1]) { // 4-1)
				list.add(wire[i]);
				checked[i] = list.size()-1;
			}
			else { // 4-2)
				int left = 1;
				int right = list.size()-1;
				
				while(left<right) {
					int mid = (left+right)/2;
					
					if(wire[i][1] > list.get(mid)[1]) {
						left = mid+1;
					}
					else {
						right = mid;
					}
				}
				checked[i] = right;
				list.set(right, wire[i]);
			}
		}
		
		int idx = list.size()-1;
		Stack<Integer> remove = new Stack<>();
		
		// 5)
		sb.append(N - (list.size()-1)).append("\n");
		
		// 6)
		for(int i=N-1; i>=0; i--) {
			if(checked[i] == idx) idx--;
			else remove.push(wire[i][0]); // A ���
		}
		
		// 7)
		while(!remove.isEmpty()) {
			sb.append(remove.pop()).append("\n");
		}
		
		System.out.println(sb.toString());
	}
}

