package javasrc.problems;

import java.util.LinkedList;

/**
 * Created by Hema on 3/9/2016.
 */
public class Trie {
    Node root = new Node('\0');
    class Node {
        char ch;
        LinkedList<Node> childNodes;
        boolean isEnd;
        int count;
        public Node(char c) {
            ch = c;
            childNodes = new LinkedList<Node>();
            isEnd = false;
            count = 0;
        }
        // check if child with c present and return
        Node getNode(char c) {
            for(Node child : childNodes) {
                if(child.ch == c)
                    return child;
            }
            return null;
        }
    }

    public void addWord(String word) {
        if(word == null || word.isEmpty())
            return;
        Node n = root;
        char[] letters = word.toCharArray();
        for(char letter : letters) {
            Node t = n.getNode(letter);
            if(t == null) {
                t = new Node(letter);
                n.childNodes.add(t);
            }
            n.count++;
            n = t;
        }
        n.isEnd = true;
    }

    boolean removeWord(String word) {
        if(word == null || word.isEmpty()) return false;
        if(!search(word))
            return false;
        Node n = root;
        char[] letters = word.toCharArray();
        for(char c : letters) {
            Node t = n.getNode(c);
            if(t.count == 1) {
                n.childNodes.remove(t);
                n.count--;
                return true;
            } else {
                n.count--;
                n = t;
            }
        }
        return false;
    }

    boolean search(String word) {
        if(word == null || word.isEmpty()) return false;
        Node n = root;
        char[] letters = word.toCharArray();
        for(char c : letters) {
            Node t = n.getNode(c);
            if(t == null)
                return false;
            n = t;
        }
        return true;
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.addWord("to");
        trie.addWord("tea");
        System.out.println(trie.search("to"));
        System.out.println(trie.search("tea"));
        System.out.println(trie.search("ted"));
        System.out.println(trie.removeWord("ted"));
        System.out.println(trie.removeWord("tea"));
        trie.addWord("purse");
        System.out.println(trie.search("tea"));
        System.out.println(trie.search("purse"));
    }
}

