package javasrc.problems;

import java.util.*;

/**
 * Created by Hema on 3/21/2016.
 */
public class Huffman {

    class HuffmanNode {
        char ch;
        int freq;
        HuffmanNode left;
        HuffmanNode right;
        public HuffmanNode(Character ch, Integer freq, HuffmanNode left, HuffmanNode right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }
    }

    public static class HuffmanComparator implements Comparator<HuffmanNode> {
        public int compare(HuffmanNode n1, HuffmanNode n2) {
            return n2.freq - n1.freq;
        }
    }

    public HashMap<Character,Integer> frequencyMap(char[] input) {
        HashMap<Character, Integer> freqMap = new HashMap<Character, Integer>();
        for(char ch : input) {
            if(!freqMap.containsKey(ch)) {
                freqMap.put(ch, 1);
            } else
                freqMap.put(ch, freqMap.get(ch));
        }
        return freqMap;
    }

    public HuffmanNode buildTree(HashMap<Character, Integer> freqMap) {
        ArrayList<HuffmanNode> nodes = new ArrayList<HuffmanNode>();
        for(Character ch : freqMap.keySet()) {
            nodes.add(new HuffmanNode(ch,freqMap.get(ch), null, null));
        }
        Queue<HuffmanNode> queue = new PriorityQueue<HuffmanNode>(freqMap.size(), new HuffmanComparator());
        queue.addAll(nodes);
        while(queue.size() > 1) {
            HuffmanNode n1 = queue.remove();
            HuffmanNode n2 = queue.remove();
            queue.add(new HuffmanNode('\0', n1.freq+n2.freq, n1, n2));
        }
        return queue.remove();
    }

    public void generateCodes(HuffmanNode node, HashMap<Character, String> codeMap, String code) {
        if(node.left == null && node.right == null) {
            codeMap.put(node.ch, code);
            return;
        }
        generateCodes(node.left, codeMap, code+'0');
        generateCodes(node.right, codeMap, code+'1');
    }

    HashMap<Character, String> charToCode;
    HashMap<String, Character> codeToChar;
    public String encode(char[] input) {
        // 1. count char frequency
        HashMap<Character, Integer> freqMap = frequencyMap(input);
        // 2. create HuffmanTree of nodes
        HuffmanNode tree = buildTree(freqMap);
        charToCode = new HashMap<Character, String>();
        generateCodes(tree, charToCode, "");
        // encode the array
        StringBuffer output = new StringBuffer("");
        for(Character ch : input) {
            output.append(charToCode.get(ch));
        }
        codeToCharMap(); // for decode use
        return output.toString();
    }

    public String decoded(String encoded) {
        int end=0;
        char[] arr = encoded.toCharArray();
        StringBuffer result = new StringBuffer("");
        StringBuffer tempCode = new StringBuffer("");
        while (end < arr.length) {
            tempCode.append(arr[end]);
            if(codeToChar.containsKey(tempCode.toString())) {
                result.append(codeToChar.get(tempCode.toString()));
                tempCode = new StringBuffer("");
            }
            end++;
        }
        return result.toString();
    }

    public HashMap<String, Character> codeToCharMap() {
        codeToChar = new HashMap<String, Character>();
        for(Character ch : charToCode.keySet()) {
            codeToChar.put(charToCode.get(ch), ch);
        }
        return codeToChar;
    }

    public static void main(String[] args) {
        Huffman huffman = new Huffman();
        String input = "aaaabbbccde";
        String encoded = huffman.encode(input.toCharArray());
        System.out.println("Actual string : " + input);
        System.out.println(encoded);
        System.out.println(huffman.decoded(encoded));
    }
}
