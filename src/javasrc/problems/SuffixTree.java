package javasrc.problems;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Hema on 3/12/2016.
 */
public class SuffixTree {
    SuffixTreeNode root;

    SuffixTree() {
        root = new SuffixTreeNode('\0');
    }

    public void insertWord(String str) {
        for (int i = 0; i < str.length(); i++) {
            root.insertWord(str.substring(i), i);
        }
    }

    public ArrayList<Integer> getIndexes(String s) {
        return root.getIndexes(s);
    }

    class SuffixTreeNode {
        char ch;
        private ArrayList<Integer> indexes;
        HashMap<Character, SuffixTreeNode> children;

        SuffixTreeNode(char c) {
            ch = c;
            indexes = new ArrayList<Integer>();
            children = new HashMap<Character, SuffixTreeNode>();
        }

        public void insertWord(String str, int i) {
            indexes.add(i);
            if (str == null || str.length() == 0)
                return;
            char ch = str.charAt(0);
            if (!children.containsKey(ch)) {
                SuffixTreeNode node = new SuffixTreeNode(ch);
                children.put(ch, node);
            }
            SuffixTreeNode n = children.get(ch);
            n.insertWord(str.substring(1), i);
        }

        public ArrayList<Integer> getIndexes(String str) {
            if (str == null || str.isEmpty())
                return indexes;
            if (children.containsKey(str.charAt(0))) {
                return children.get(str.charAt(0)).getIndexes(str.substring(1));
            }
            return null;
        }
    }

    /*
    * CTCI 20.8
    * Given a string s and an array of smaller strings T, design a method to search s for each small string in T.
    * */
    public static void main(String args[]) {
        SuffixTree suffixTree = new SuffixTree();
        suffixTree.insertWord("bibs");
        ArrayList<Integer> indexes = suffixTree.getIndexes("by");
        if(indexes != null)
            System.out.println(indexes.toString());
        else
            System.out.println("no indexes found!");

    }

}

