package problems2;

import java.util.ArrayList;
import java.util.HashMap;

public class StringAndArrays {

    public static String sort(String s) {
        char[] sArr = s.toCharArray();
        java.util.Arrays.sort(sArr);
        return new String(sArr);
    }
    static boolean isPerm1(String s, String t) {
        if(s.length() != t.length())
            return false;
        return sort(s).equals(sort(t));
    }

    public static boolean isPerm2(String s, String t) {
        if(s.length() != t.length())
            return false;
        int[] countChar = new int[128];
        for(char c : s.toCharArray())
            countChar[c]++;
        for (char c: t.toCharArray()) {
            countChar[c]--;
            if(countChar[c] < 0)
                return false;
        }
        return true;
    }

    public static String replaceSpaceWithChars(String s, int truelength) {
        if(s == null || s == "")
            return s;
        char[] str = s.toCharArray();
        if(truelength < str.length)
            str[truelength]='\0';
        // count spaces
        // for each space, replace with %20 starting from index truelength

        int spaces = 0;
        for(int i=0; i<truelength; i++) {
            if (str[i] == ' ')
                spaces++;
        }
        int index = truelength + spaces * 2;
        for(int i = truelength; i>0; i--) {
            if(str[i-1] == ' ') {
                str[index-1] = '0';
                str[index-2] = '2';
                str[index-3] = '%';
                index = index - 3;
            } else {
                str[index - 1] = str[i-1];
                index--;
            }
        }
        return new String(str);
    }

    public static boolean palindromePermutation(String s) {
        if(s == null || s.length() ==0)
            return false;
        HashMap<Character, Integer> countMap = new HashMap();
        for(char c : s.toCharArray()) {
            if(countMap.containsKey(c)) {
                countMap.put(c, countMap.get(c)+1);
            } else
                countMap.put(c,1);
        }
        boolean oddEncountered = false;
        for(char c : countMap.keySet()) {
            if(countMap.get(c)%2 == 1) {
                if (oddEncountered)
                    return false;
                oddEncountered = true;
            }
        }
        return true;
    }

    // using bit vector
    public static boolean palindromePermutation2(String s) {
        int bitVector = createBitVector(s);
        return bitVector == 0 || isExactlyOneBitSet(bitVector);
    }

    static int createBitVector(String s) {
        int bitVector = 0;
        for(char c : s.toCharArray()) {
            int index = c - 'a';
            bitVector = toggleBit(bitVector, index);
        }
        return bitVector;
    }

    static int toggleBit(int bitVector, int index) {
        int mask = 1 << index;
        if((bitVector & mask) == 0)
            bitVector = bitVector | mask; // not set, so set
        else
            bitVector = bitVector &  ~mask; // already set, so unset
        return bitVector;
    }

    static boolean isOneEdit(String s1, String s2) {
        if(s1.length() + 1 == s2.length())
            return isOneEditInsert(s2,s1);
        if(s2.length() + 1 == s1.length())
            return isOneEditInsert(s1,s2);
        if(s1.length() == s2.length())
            return isOneEditReplacement(s1,s2);
        return false;
    }

    // if characters are same, increment both indexes
    // if characters are different, then increment second index only if until now indexes were same
    // else means indexes were already different, now return false as this is second mismatch
    static boolean isOneEditInsert(String s1, String s2) {
        int i =0, j=0;
        while(i < s1.length() && j < s2.length()) {
            if(s1.charAt(i) == s2.charAt(j)) {
                i++; j++;
            } else {
                if(i == j)
                    j++;
                else
                    return false;
            }
        }
        return true;
    }

    static boolean isOneEditReplacement(String s1, String s2) {
        int i=0, j=0;
        boolean foundEdit = false;
        while(i< s1.length() && j< s2.length()) {
            if(s1.charAt(i) == s2.charAt(j)) {
                i++; j++;
            } else {
                if(foundEdit == false) {
                    foundEdit = true;
                    i++;
                    j++;
                } else
                    return false;
            }

        }
        return true;
    }

    // above 2 methods merged into one
    static boolean isOneEditAway(String first, String second) {
        if(Math.abs(first.length()-second.length()) > 1)
            return false;
        String s1 = first.length() > second.length() ? second : first;
        String s2 = first.length() > second.length() ? first : second;
        boolean foundEdit = false;
        int i = 0;
        int j = 0;
        while(i < s1.length() && j < s2.length()) {
            if(s1.charAt(i) == s2.charAt(j)) {
                i++;
                j++;
            } else {
                if(foundEdit)
                    return false;
                foundEdit = true;
                if(s1.length() == s2.length()) {
                    i++;j++;
                } else
                    j++;
            }
        }
        return true;
    }

    static boolean isExactlyOneBitSet(int bitVector) {
        return (bitVector & bitVector -1) == 0;
    }

    //aaaabbcddddd should return a4b2c1d5, and aaaabc should return aaaabc not a4b1c1
    static String compress(String s) {
        StringBuilder finalStr = new StringBuilder("");
        Character letter;
        int count = 0;
        for(int i=0; i<s.length(); i++) {
            letter = s.charAt(i);
            count = 1;
            while(i+1 < s.length() && letter == s.charAt(i+1)) {
                i++; count++;
            }
            finalStr.append(letter).append(count);
        }
        return finalStr.length() < s.length() ? finalStr.toString() : s;
    }

    static void setZero(int[][] matrix){
        ArrayList<Integer> rowsWithZero = new ArrayList<Integer>();
        ArrayList<Integer> colsWithZero = new ArrayList<Integer>();
        int m = matrix.length;
        int n = matrix[0].length;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++) {
                if(matrix[i][j] == 0) {
                    rowsWithZero.add(i);
                    colsWithZero.add(j);
                }
            }
        }
        for(int row : rowsWithZero) {
            for(int j=0;j<n;j++)
                matrix[row][j] = 0;
        }
        for(int col : colsWithZero) {
            for(int i=0;i<m;i++)
                matrix[i][col] = 0;
        }

        for(int i=0;i<m;i++){
            System.out.println();
            for(int j=0;j<n;j++) {
                System.out.print(matrix[i][j] + " ");
            }
        }
    }

    static boolean isRotation(String s1, String s2) {
        if(s1.length() != s2.length() || s1.length() == 0 || s2.length() == 0)
            return false;
        String joined = s1 + s1;
        return isSubString(joined, s2);
    }

    static boolean isSubString(String s1, String s2) {
        return s1.contains(s2);
    }

    public static void main(String[] args) {
        //System.out.println(isPerm1("abc","bca"));
        //System.out.println(isPerm2("abc","bca"));
        //System.out.println(replaceSpaceWithChars("ab bc     ",5));
        //System.out.println(palindromePermutation2("abcbacde"));
        //System.out.println(isOneEditAway("bcdaef","bcda"));
        //System.out.println(compress("aaaabbcddddd"));

        int[][] matrix = new int[3][4];
        for(int i=0;i<3;i++){
            for(int j=0;j<4;j++) {
                matrix[i][j] = 1;
            }
        }
        matrix[0][0] = 0;
        matrix[1][3] = 0;
        //setZero(matrix);

        System.out.println(isRotation("hema","emah"));
    }
}
