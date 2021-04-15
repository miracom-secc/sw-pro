package swPro.source;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

/*

�ð� ����	�޸� ����	����	����	���� ���	���� ����
1 ��	256 MB	22132	6844	3948	29.447%

�� ����
��ȭ��ȣ ����� �־�����. �̶�, �� ����� �ϰ����� �ִ��� �������� ���ϴ� ���α׷��� �ۼ��Ͻÿ�.
��ȭ��ȣ ����� �ϰ����� �����Ϸ���, �� ��ȣ�� �ٸ� ��ȣ�� ���ξ��� ��찡 ����� �Ѵ�.
���� ���, ��ȭ��ȣ ����� �Ʒ��� ���� ��츦 �����غ���

�����ȭ: 911
���: 97 625 999
����: 91 12 54 26
�� ��쿡 �����̿��� ��ȭ�� �� �� �ִ� ����� ����. ��ȭ�⸦ ��� ������ ��ȣ�� ó�� �� �ڸ��� ������ ���� �ٷ� �����ȭ�� �ɸ��� �����̴�. ����, �� ����� �ϰ����� ���� ����̴�. 

�� �Է�
ù° �ٿ� �׽�Ʈ ���̽��� ���� t�� �־�����. (1 �� t �� 50) �� �׽�Ʈ ���̽��� ù° �ٿ��� ��ȭ��ȣ�� �� n�� �־�����. (1 �� n �� 10000) ���� n���� �ٿ��� ��Ͽ� ���ԵǾ� �ִ� ��ȭ��ȣ�� �ϳ��� �־�����. ��ȭ��ȣ�� ���̴� ���� 10�ڸ��̸�, ��Ͽ� �ִ� �� ��ȭ��ȣ�� ���� ���� ����.

�� ���
�� �׽�Ʈ ���̽��� ���ؼ�, �ϰ��� �ִ� ����� ��쿡�� YES, �ƴ� ��쿡�� NO�� ����Ѵ�.

 */

public class BJ_5052_Best2 {
	
	public static int T, N;
	public static TrieNode rootNode; 
	public static String phoneNumber[];

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new FileReader("src/algorithm/sample_input.txt"));
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		T = Integer.parseInt(br.readLine());
		
		
		while(T-- > 0) {
			N = Integer.parseInt(br.readLine());
			rootNode = new TrieNode(); 
			phoneNumber = new String[N];
			
			int count = 0;
			for (int n=0; n<N; n++) {
				phoneNumber[n] = br.readLine();
				insert(phoneNumber[n]);
			}
			
			for (int n=0; n<N; n++) {
				//System.out.println(phoneNumber[n]);
				if (!contains(phoneNumber[n])) count++;
			}
			
			System.out.println(count > 0 ? "NO" : "YES");
		}
		
	}
	
	static void insert(String word) {
		TrieNode thisNode = rootNode;
		for (int i=0; i<word.length(); i++) {
			thisNode = thisNode.childNodes.computeIfAbsent(word.charAt(i), c -> new TrieNode());
		}
		thisNode.isLastChar = true;
	}
	
	static boolean contains(String word) {
		TrieNode thisNode = rootNode;
		for (int i=0; i<word.length(); i++) { 
			char character = word.charAt(i); 
			TrieNode node = thisNode.childNodes.get(character); 
			//if (node == null) return false; 
			if (node != null) {
				if (i != (word.length()-1) && node.isLastChar)
					return false;
			}
			
			thisNode = node; 
		} 
		
		return thisNode.isLastChar; 
	}

}

class TrieNode {
	HashMap <Character, TrieNode> childNodes = new HashMap<>();
	boolean isLastChar;
}
