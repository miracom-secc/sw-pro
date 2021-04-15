package swPro.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

public class BJ_5052_Best1 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		for (int i = 0; i < T; i++) {
			int n = Integer.parseInt(br.readLine());
			Trie trie = new Trie();
			for (int j = 0; j < n; j++) {
				String word = br.readLine();
				trie.insert(word);
			}
			if (Trie.isConsistency)
				System.out.println("YES");
			else
				System.out.println("NO");
			Trie.isConsistency = true;
		}
	}

}

// Ʈ����
class Trie { 
 
	static boolean isConsistency = true;
	boolean isWord = false;
	boolean existChild = false;

	// ����Ʈ �Ӽ�
	Trie[] childTrie = new Trie[10];

	void insert(String word) {
		int len = word.length();
		Trie nowTrie = this;
		
		for (int i = 0; i < len; i++) {
			int nextNum = word.charAt(i) - '0';
			if (nowTrie.childTrie[nextNum] == null) {
				nowTrie.childTrie[nextNum] = new Trie();
			}

			nowTrie = nowTrie.childTrie[nextNum];

			// �ڽ� �׷����� �̵�
			if (i == len - 1) {
				nowTrie.isWord = true;
			} else {
				nowTrie.existChild = true;
			}

			if (nowTrie.isWord && nowTrie.existChild)
				isConsistency = false;
		}
	}
}