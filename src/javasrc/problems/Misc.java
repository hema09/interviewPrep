package javasrc.problems;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Hema on 3/9/2016.
 */
public class Misc {

    public void patternMatcherExample() {
        Pattern pattern = Pattern.compile("(\\d+)");
        String s = "Hello007!Iam001!Bye*";
        Matcher m = pattern.matcher(s);
        while(m.find()) {
            System.out.println(s.substring(m.start(), m.end()));
        }
    }

    /*public Integer getMissingInteger() {
        Byte[] bitField = new Byte[0xFFFFFFF/8];
        Scanner in = new Scanner(new FileReader("input.txt"));
        while(in.hasNextInt()) {
            Integer intVal = in.nextInt();
            int index = n/8;
            bitField[index] = bitField[index] | (1 << n%8);
        }
        for(int i=0 ;i<bitField.length;i++) {
            for(int j=0;j<8;j++) {
                if(i & 1<<j == 0)
                    return i*8+j;
            }
        }
    }*/

    //TODO : https://www.careercup.com/question?id=5696520106016768


    /*
    * Given two words of equal length that are in a dictionary,
    * write a method to transform one word into another word by
    * changing only one letter at a time. The new word you get
    * in each step must be in the dictionary.
        EXAMPLE: Input: DAMP, LIKE
        Output: DAMP -> LAMP -> LIMP -> LIME -> LIKE
    */
    public LinkedList<String> transformWord(String start, String end,
                                            Set<String> dictionary) {

        Queue<String> actionQueue = new LinkedList<String>();
        TreeMap<String, String> backtrack = new TreeMap<String, String>();
        Set<String> visited = new HashSet<String>();
        LinkedList<String> transformList = new LinkedList<String>();
        actionQueue.add(start);
        visited.add(start);
        while(!actionQueue.isEmpty()) {
            String str = actionQueue.remove();
            if(str.equals(end)) {
                // get backtrack list and return
                transformList.add(end);
                while(end != start) {
                    end = backtrack.get(end); transformList.add(end);
                }
                return transformList;
            } else {
                ArrayList<String> words = getOneEditWords(str);
                for(String s : words) {
                    if(dictionary.contains(s) && !visited.contains(s)) {
                        visited.add(s);
                        actionQueue.add(s);
                        backtrack.put(s, str);
                    }
                }
            }
        }
        return null;
    }
    ArrayList<String> getOneEditWords(String str) {
        ArrayList<String> oneEdits = new ArrayList<String>();
        for(int i=0; i<str.length();i++) {
            for(int j=0; j < 26; j++) {
                char c = (char) ('a' + j);
                String newStr = str.substring(0,i) + c + str.substring(i+1);
                oneEdits.add(newStr);
            }
        }
        return oneEdits;
    }

    // sample little dictionary
    public Set<String> getDictionary() {
        Set<String> strings = new HashSet<String>();
        strings.addAll(Arrays.asList("damp", "lamp", "like", "bike", "dice", "mice", "lice", "limp",
                "lime", "dime", "chime", "like", "beak", "leak", "leaf", "deaf", "dead", "lead", "load"));
        return strings;
    }

    // O(n^4)
    public int subMatrixWithLargestSum(int[][] input) {
        int[][] precompute = precomputeMatrix(input);
        int rows = input.length;
        int cols = input[0].length;
        int maxArea = Integer.MIN_VALUE;
        for(int r1=0; r1< rows; r1++)
            for(int c1=0; c1<cols; c1++)
                for(int r2=0; r2< rows; r2++)
                    for(int c2=0; c2<cols; c2++) {
                        maxArea = Math.max(maxArea, compute(precompute, r1, c1, r2, c2));
                    }
        return maxArea;
    }

    public int[][] precomputeMatrix(int[][] matrix) {
        int[][] precompute = new int[matrix.length][matrix[0].length];
        for(int r1=0; r1< matrix.length; r1++)
            for(int c1=0; c1<matrix[0].length; c1++){
                if(r1 == 0 && c1 == 0)
                    precompute[r1][c1] = precompute[r1][c1];
                else if(r1==0)
                    precompute[r1][c1] = precompute[r1][c1-1]+matrix[r1][c1];
                else if(c1 == 0)
                    precompute[r1][c1] = precompute[r1-1][c1]+matrix[r1][c1];
                else
                    precompute[r1][c1]=precompute[r1-1][c1]+precompute[r1][c1-1] -  precompute[r1-1][c1-1];
            }
        return precompute;
    }
    public int compute(int[][] precompute, int r1, int c1, int r2, int c2) {
        if(r1==0 && c1 == 0)
            return precompute[r1][c1];
        else if(r1 == 0)
            return precompute[r2][c2] - precompute[r1][c1];
        else if(c1 == 0)
            return precompute[r2][c2] - precompute[r1][c1];
        else
            return precompute[r2][c2] - precompute[r1][c2] - precompute[r2][c1] + precompute[r1][c1];
    }

    // print all duplicate numbers in a array of 32000 numbers with memory of 4KB
    // 4KB = 4*8*1024  bits
    public void checkDuplicates(int[] array) {
        BitSet bs = new BitSet(32000);
        for(int i=0;i<32000;i++) {
            if(bs.get(array[i]))
                System.out.println(array[i]);
            else
                bs.set(array[i]);
        }
    }
    class BitSet {
        int[] bits;
        BitSet(int s) {
            bits = new int[s >> 32];
        }
        public boolean get(int pos) {
            int wordNum = pos >> 32;
            int bitNum = pos%32; // same as pos & 0x1F
            return (bits[wordNum] & 1<<bitNum) != 0;
        }
        public void set(int num) {
            int wordNum = num >> 32;
            int bitNum = num % 32;
            bits[wordNum] |= 1 << bitNum;
        }
    }

    // longest substring with all distinct characters
    public static String longestDistinctSubstring(String s) {
        if(s == null || s.length() < 2) return s;
        if(s.length() == 2) return s.charAt(0) != s.charAt(1) ? s : s.substring(0,1);
        HashMap<Character, Boolean> hmap = new HashMap<Character, Boolean>();
        int maxLen = -1;
        int currentMax;
        int start=0, end=0;
        for(int i=0;i<s.length();i++) {
            hmap.clear();
            currentMax = 0;
            for(int j=i; j< s.length(); j++) {
                if(!hmap.containsKey(s.charAt(j))) {
                    hmap.put(s.charAt(j), true);
                    currentMax++;
                    if(maxLen < currentMax) {
                        maxLen = currentMax; start=i; end=j;
                    }
                }
                else
                    break;
            }
        }
        return s.substring(start,end+1);
    }

    // longest substring with distinct m chars
    public static int longestSubstringMChars(String s, int m) {
        if (s == null || m > s.length()) return -1;
        int[] count = new int[256];
        for(int i=0;i<256;i++)count[i]=0;
        int currStart = 0, currEnd = -1;
        int maxStart=0, maxEnd=0, maxWindowSize=0;
        int k = 0; // to store distinct chars till now
        char[] charr = s.toCharArray();
        for (int i = 0; i < charr.length; i++) {
            count[charr[i]-'a']++;
            currEnd++;
            if (count[charr[i]-'a'] == 1) // means this char was added now
                k++;
            if (k > m) {
                while (k > m) {
                    count[charr[currStart] - 'a']--;
                    if (count[charr[currStart] - 'a'] == 0)
                        k--;
                    currStart++;
                }
            }
            if(currEnd-currStart+1 > maxWindowSize) {
                maxWindowSize = currEnd-currStart+1;
            }
        }
        return maxWindowSize;
    }

    // get path from root to leaf
    public ArrayList<TreeFunctions.TreeNode> pathFromRootToLeaf(TreeFunctions.TreeNode root, TreeFunctions.TreeNode leaf,
                                                                ArrayList<TreeFunctions.TreeNode> path) {
        if(leaf == root)
            return path;
        if(root.left != null) {
            ArrayList<TreeFunctions.TreeNode> path1 = (ArrayList<TreeFunctions.TreeNode>)path.clone();
            path1.add(root.left);
            pathFromRootToLeaf(root.left, leaf, path1);
            if(path1 != null) return path1;
        } else if(root.right != null){
            ArrayList<TreeFunctions.TreeNode> path2 = (ArrayList<TreeFunctions.TreeNode>)path.clone();
            path2.add(root.right);
            pathFromRootToLeaf(root.right, leaf, path2);
            if(path2 != null) return path2;
        }
        return null;
    }

    // 1’s and 2’s complement of a Binary Number
    public void printComplements(Integer in) {
        if(in == null) return;
        Integer onesComplement = ~in;
        StringBuffer str = new StringBuffer("");
        Integer twosComplement = onesComplement + 1;
        System.out.println(Integer.toBinaryString(onesComplement));
        System.out.println(Integer.toBinaryString(twosComplement));
    }

    // http://www.geeksforgeeks.org/print-concatenation-of-zig-zag-string-form-in-n-rows/
    public void printStringZigZag(String str, int n) {
        if(str == null || str.length() < 3)
            return;
        int len = str.length();
        char[][] mat = new char[n][len];
        int row=0, i=0;
        while(i < len) {
            while(row < n)
                mat[row++][i] = str.charAt(i++);
            row--;
            while(row >= 0)
                mat[row--][i] = str.charAt(i++);
            row++;
        }
        StringBuffer sb = new StringBuffer("");
        for(int k=0;k<n;k++)
            for(int j=0; j<str.length();j++)
                if(mat[k][j] != '\0')
                    sb.append(mat[k][j]);
        System.out.println(sb.toString());
    }

    // count valid bracket pairs in string
    public int lengthValidBrackets(char[] brackets) {
        Stack<Character> stk = new Stack<Character>();
        int valids = 0;
        for(char c : brackets) {
            if(c == ')' && !stk.isEmpty() && stk.peek() == '(') {
                valids++;
                stk.pop();
            }
            stk.push(c);
        }
        return valids;
    }

    public boolean isPalin(String s) {
        for(int i=0; i<s.length()/2;i++)
            if(s.charAt(i) != s.charAt(s.length()-i-1))
                return false;
        return true;
    }

    int[][] sumCache;
    public void preComputeSumCache(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        sumCache = new int[rows][cols];
        for(int i=0; i< rows; i++) {
            for(int j=0; j< cols; j++) {
                if(i==0 && j==0) {
                    sumCache[i][j] = matrix[i][j];
                } else if(i==0)
                    sumCache[i][j] = matrix[i][j] + sumCache[i][j-1];
                else if(j==0)
                    sumCache[i][j] = matrix[i][j] + sumCache[i-1][j-1];
                else
                    sumCache[i][j] = matrix[i][j] + sumCache[i][j-1] + sumCache[i-1][j] - sumCache[i-1][j-1];
            }
        }
    }

    // assuming they are points of diagonal like \ and not /
    public int matrixRegionSum(int[][] matrix, int r1, int r2, int c1, int c2) {
        if(r1 > r2 || c1 > c2)
            return 0;
        if(sumCache == null)
            preComputeSumCache(matrix);
        return sumCache[r2][c2] - sumCache[r1][c2] - sumCache[r2][c1] + sumCache[r1][c1];
    }

    // example : 1091 -> 1111, 102300 -> 103301, 10289 -> 10301
    public static int getNextPalindromeNumber(int number) {
        String input = String.valueOf(number);
        char[] charr = input.toCharArray();
        for(int i=0; i<charr.length/2; i++) {
                charr[charr.length-i-1] = charr[i]; // make ending characters same as starting characters
        }
        if(Integer.valueOf(new String(charr)) > number)
            return Integer.valueOf(new String(charr));
        if(charr.length%2!=0) { // odd length strings
            charr[charr.length/2] = (char)(charr[charr.length/2]+1);
        } else { // even length strings
            charr[charr.length/2-1] = (char)(charr[charr.length/2-1]+1);
            charr[charr.length/2] = charr[charr.length/2-1];
        }
        return Integer.valueOf(new String(charr));
    }

    // example 1099 -> 1909, 12345 -> 12354, 90010->90100
    public static int nextHigherNumberWithSameDigits(int number) {
        // if number is power of 2 number cannot be created, example 1000
        if((number & number-1) == 0)
            return -1;
        if(number/10 == 0)
            return number;
        String str = String.valueOf(number);
        char[] charr = str.toCharArray();
        for(int i=charr.length-1; i>0;i--) {
            boolean done=false;
            for(int j=i-1;j>=0;j--) {
                if(charr[j] < charr[i]) {
                    char temp = charr[j];
                    charr[j] = charr[i];
                    charr[i] = temp;
                    Arrays.sort(charr, j+1, charr.length);
                    done = true;
                    break;
                }
            }
            if(done) break;
        }
        return Integer.valueOf(new String(charr));
    }

    public static void main(String[] args) {
        Misc misc = new Misc();
        System.out.println("1091   -> " + nextHigherNumberWithSameDigits(1091));
        System.out.println("102300 -> " + nextHigherNumberWithSameDigits(102300));
        System.out.println("91990  -> " + nextHigherNumberWithSameDigits(91990));
        /*System.out.println("1091   -> " + getNextPalindromeNumber(1091));
        System.out.println("102300 -> " + getNextPalindromeNumber(102300));
        System.out.println("10289  -> " + getNextPalindromeNumber(10289));*/
        //misc.printStringZigZag("abcdefgh", 2);
        //misc.printComplements(5);
        //misc.patternMatcherExample();
        /*LinkedList<String> route = misc.transformWord("damp", "like", misc.getDictionary());
        for(String s : route) {
            System.out.print(s + " :: ");
        }
        System.out.println();
        route = misc.transformWord("beak", "load", misc.getDictionary());
        for(String s : route) {
            System.out.print(s + " :: ");
        }*/
        //System.out.println(longestDistinctSubstring("abcdefacdefgbd"));
        //System.out.println(longestSubstringMChars("aabababcdef", 2));
    }
}
