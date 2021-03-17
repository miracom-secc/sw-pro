package swPro.source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/*
 
�ð� ����	�޸� ����	����	����	���� ���	���� ����
2 ��	128 MB	11090	2743	2087	25.845%

�� ����
����翡�� ��� �ִ� �رԴ� �� ���� ��Ƽ���� �̿��ϰ� �ִ�. �رԴ� Ű����, ������̱�, �ڵ��� ������, ������ ī�޶� ������ �� ���� ���� �����ǰ�� ����ϸ鼭 ��¿ �� ���� ���� �����ǰ�� �÷��׸� ���� �ȾҴ� �ϴ� �������� �ް� �ִ�. �׷��� �رԴ� �ڽ��� ��Ȱ ������ �м��Ͽ�, �ڱⰡ ����ϰ� �ִ� �����ǰ�� �������� �˾Ƴ�����, �̸� ������� �÷��׸� ���� Ƚ���� �ּ�ȭ�ϴ� ����� ����Ͽ� ���� ������ ��Ȱȯ���� ������� �Ѵ�.
���� ��� 3 ��(������ �� �� �޸�) ��Ƽ���� �� ��, �����ǰ�� ��� ������ �Ʒ��� ���� �־����ٸ�, 
Ű����
������̱�
�ڵ��� ������
������ ī�޶� ������
Ű����
������̱�
Ű����, ������̱�, �ڵ��� �������� �÷��׸� ������� ��Ƽ�ǿ� ���� ���� ������ ī�޶� ������ �÷��׸� �ȱ� ���� �ڵ��������� �÷��׸� ���� ���� ������ ���̹Ƿ� �÷��״� �� ���� ���� �ȴ�. 

�� �Է�
ù �ٿ��� ��Ƽ�� ������ ���� N (1 �� N �� 100)�� ���� ��ǰ�� �� ���Ƚ�� K (1 �� K �� 100)�� ������ �־�����. 
�� ��° �ٿ��� �����ǰ�� �̸��� K ������ �ڿ����� ��� ������� �־�����. �� ���� ��� ���� ���̴� ���鹮�ڷ� ���еǾ� �ִ�. 

�� ���
�ϳ��� �÷��׸� ���� �ּ��� Ƚ���� ����Ͻÿ�.

 */

public class BJ_1700_Best {

	static int N, K;
	static int[] usethings, multitab;

	public static void main(String[] args) throws Exception {
		BJ_1700_Best t = new BJ_1700_Best();
		long startTime = System.currentTimeMillis();
		/* ========================================= */
		// �˰����� �ش� ���� �ȿ� �־� �ۼ��� �˰����� ������ ����� �� �ִ�.

		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    BufferedReader br = new BufferedReader(new FileReader("src/algorithm/sample_input.txt"));
	    StringTokenizer st = new StringTokenizer(br.readLine());
	    
    	N = Integer.parseInt(st.nextToken());
    	K = Integer.parseInt(st.nextToken());
    	
    	usethings = new int[101];
    	multitab = new int[101];
    	
    	st = new StringTokenizer(br.readLine());
    	for (int i=1; i<=K; i++) {
    		usethings[i] = Integer.parseInt(st.nextToken());
    	}
    	
    	/** 
    	 * 1. ��Ƽ�ǿ� ��������� �ȱ�
    	 * 2. �����ִ� ��ǰ�� �������� ��ߵǸ� ���α�
    	 * 3. �� ������ ��� ���� ���߿� �� ��ǰ �̱�
    	 */
    	int unplugCnt = 0; //�÷��� ���� Ƚ��
    	boolean fullPlug = false;
    	for (int k=1; k<=K; k++) {
    		for (int n=1; n<=N; n++) {
    			// 1. ��Ƽ�ǿ� ��������� �ȱ�
    			if (multitab[n] == 0) {
    				multitab[n] = usethings[k];
    				fullPlug = false;
    				break;
    			}
    			
    			// 2. �̹� �����ִ� ��ǰ�̸� ���α�
    			if (multitab[n] == usethings[k]) {
    				fullPlug = false;
    				break;
    			}
    			
    			fullPlug = true;
    		}
    		
    		// 3. �� ������ ��� ���� ���߿� �� ��ǰ �̱�
    		if (fullPlug) {
    			int lastOrder = 0; //���� �������� ���� ��ǰ
    	    	int unplugIdx = 0;
    	    	
    			for (int n=1; n<=N; n++) {
    				boolean use = false;
    				for (int a=k+1; a<=K; a++) {
    					
    					// ���� �������� ���Ǵ� ��ǰ ���� ���
    					if (multitab[n] == usethings[a]) {
    						if (lastOrder < a) {
    							lastOrder = a;
    							unplugIdx = n;
    						}
    						
    						use = true;
    						break;
    					}
    				}
    				
    				// ������ �����ϴ� ��ǰ�̸� �̱�
    				if (!use) {
    					unplugIdx = n;
    					break;
    				}
    			}
    			
    			multitab[unplugIdx] = usethings[k];
    			unplugCnt++;
    		}
    	}
    	
	    System.out.println(unplugCnt);
		
		/* ========================================= */
		long endTime = System.currentTimeMillis();
		//System.out.println("����ӵ� : "+(endTime - startTime));
	    
	}
}
