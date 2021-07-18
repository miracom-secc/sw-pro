package swPro.source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
 

/*
 * �Է�
 * 	N : ������ ���� �ܾ��(1 �� N �� 105)
 *      �ܾ�� ���� �ҹ��ڷθ� �̷���� 1~80����
 * 
 * ����
 *  1. ù��° ���ڴ� ������ ����ڰ� ��ư �Է�
 *  2. ���̰� 1 �̻��� ���ڿ� c1c2...cn�� ���ݱ��� �ԷµǾ��� ��,
 *     ���� ���� ��� c1c2...cn���� �����ϴ� �ܾ c1c2...cnc�ε� �����ϴ� ���� c�� �����Ѵٸ� ����� ������� ��ư �Է� ���̵� �ڵ����� c�� �Է�
 *     ex) "hello", "hell", "heaven", "goodbye"
 *        -> ����ڰ� ù���� h�� �Է��ϸ� �������� h�� �����ϴ� �ܾ��� ���� ���ڴ� ��� e �̹Ƿ� e�� �ڵ� �Է�
 * 
 */


public class BJ_5670_Best {
	
	/* Ʈ���� ��� ǥ�� */
    static class TrieNode{
        HashMap<Character, TrieNode> childNodes = new HashMap<>();
        boolean last;
    }
    
    /* Ʈ���� ���� ��ü */
    static class Trie{
        TrieNode rootNode;
        Trie(){
            rootNode=new TrieNode();
        }
 
        /* ��忡 �ܾ� �Է� */
        void insert(String word) {
            TrieNode currentNode = rootNode;
            
            for(int i=0; i<word.length(); i++) {
            	currentNode = currentNode.childNodes.computeIfAbsent(word.charAt(i), c-> new TrieNode());
            }
            currentNode.last = true;
        }
 
        /*��ư Ŭ�� Ƚ�� üũ */
        double buttonClinkCount(String word) {
            TrieNode currentNode = rootNode.childNodes.get(word.charAt(0));
            int answer = 1;	//ù���ڴ� ������ �Է��ϹǷ� ��ưŬ�� 1ȸ
            
            for (int i = 1; i < word.length(); i++) {            	
                if (currentNode.childNodes.size() != 1||currentNode.last) {
                	// �ڽĳ�尡 1�� �̻��̰ų�(�������ڰ� �ٸ����ڰ� �ִ°��̹Ƿ� �ڵ��ϼ��� �ȵ�) �����������̸� ���� �Է�
                    answer++;
                }
                currentNode = currentNode.childNodes.get(word.charAt(i));
            }
            return answer;
        }
    }
    
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input="";
        while ((input = br.readLine()) != null) {
            int N = Integer.parseInt(input);	//������ ���� �ܾ� ��
            Trie trie = new Trie();
            String[] words = new String[N];
            double answer = 0;
 
            for (int i = 0; i < N; i++) {
                words[i] = br.readLine();
                trie.insert(words[i]);	//�Է� �ܾ� trie��� �Է�
            }
 
            for (int i = 0; i < N; i++) {
                answer += trie.buttonClinkCount(words[i]);
            }
 
            System.out.println(String.format("%.2f", answer / N));
        }
    }
}