package javasrc.problems;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Hema on 3/1/2016.
 */
public class StringUtility {

    // time O(n) , n is input string
    // space : O(n)
    public static boolean hasAllUniqueChars(String inputStr) {
        boolean[] charUnique = new boolean[256];
        for(int i=0; i<inputStr.length(); i++) {

            if(charUnique[inputStr.charAt(i)]) return false;
            charUnique[inputStr.charAt(i)] = true;
        }
        return true;
    }

    // assuming string has only 'a' through 'z' 1-26 chars
    // time O(n), space O(n) : but little better than above solution
    public static boolean isUniqueCharsUsingBitVector(String str) {
        int holder = 0;
        for(int i=0;i<str.length();i++) {
            int val = str.charAt(i) - 'a';
            if((holder & (1 << val)) > 0) return false;
            holder = holder | (1 << val);
        }
        return true;
    }

    public static String reverse(String str) {
        if(null == str || str.length() < 2)
            return str;
        char[] strChar = str.toCharArray();
        char c;
        int len = strChar.length;
        for(int i=0;i<strChar.length/2; i++) {
            c = strChar[i];
            strChar[i] = strChar[len-i-1];
            strChar[len-i-1] = c;
        }
        return new String(strChar);
    }

    public static String removeDups(String str) {
        if(null == str || str.length() < 2)
            return str;
        char[] ch = str.toCharArray();
        int last = 0;
        boolean[] hit = new boolean[256];
        for(int i=0; i< 256; i++)
            hit[i] = false;
        for(int i=0;i<ch.length;i++) {
            if(!hit[ch[i]]) {
                ch[last++] = ch[i];
                hit[ch[i]] = true;
            }
        }
        return new String(ch).substring(0, last);
    }

    public static String removeDups2(String s) {
        if(null == s || s.length() < 2)
            return s;
        HashMap<Character, Boolean> map = new HashMap<Character, Boolean>();
        StringBuffer b = new StringBuffer("");
        for(int i=0;i<s.length();i++) {
            if(!map.containsKey(s.charAt(i))) {
                map.put(s.charAt(i), true);
                b.append(s.charAt(i));
            }
        }
        return b.toString();
    }

    public static ArrayList<String> perms(String s) {
        if(s == null) return null;
        ArrayList<String> perms = new ArrayList<String>();
        if(s.length() == 1) {
            perms.add(s);
            return perms;
        }
        char c = s.charAt(0);
        ArrayList<String> rem = perms(s.substring(1));
        for(String p : rem) {
            for(int i=0;i<=p.length();i++) {
                String n = p.substring(0,i) + c + p.substring(i);
                perms.add(n);
            }
        }
        return perms;
    }

    public static String reverseRec(String s) {
        if(s == null || s.length() <= 1)
            return s;
        return
                s.charAt(s.length()-1) + reverse(s.substring(0,s.length()-1));
    }

    public static String reverseSentence(String sent) {
        sent = reverseRec(sent);
        String[] words = sent.split(" ");
        StringBuilder sb = new StringBuilder();
        for(String w : words) {
            sb.append(reverse(w) + " ");
        }
        return sb.substring(0, sb.length()-1); // removes last space
    }

    // CTCI 1.7
    public static int[][] setZeroes(int[][] mat) {
        if(null == mat || mat.length==1 && mat[0].length==1)
            return mat;
        int[] rows = new int[mat.length];
        int[] columns = new int[mat[0].length];
        for(int i=0; i< mat.length; i++) {
            for(int j=0; j< mat.length; j++) {
                if(mat[i][j] == 0) {
                    rows[i] = 1;
                    columns[j] = 1;
                }
            }
        }
        for(int i=0; i< mat.length; i++) {
            for(int j=0; j< mat.length; j++) {
                if(rows[i] == 1 || columns[j] == 1) {
                    mat[i][j] = 0;
                }
            }
        }
        return mat;
    }

    public static boolean isSubString(String a, String b) {
        return a.contains(b);
    }

    public static boolean isRotation(String s1, String s2) {
        return isSubString(s1+s1, s2);
    }

    public static boolean isSubstring(String full, String sub) {
        if(full == null && sub == null) return true;
        if(full == null) return false;
        int j = 0;
        for(int i=0;i<full.length();i++) {
            if(j == sub.length()) return true;
            if(full.charAt(i) == sub.charAt(j))
                j++;
            else
                j=0;
        }
        if(j == sub.length()) return true;
        return false;
    }

    public static String convertFromBase4ToBase2(String a) {
        StringBuilder sb = new StringBuilder();
        for(Character c : a.toCharArray()) {
            int v = Integer.valueOf(c.toString());
            if(v > 3)
                System.out.println("incorrect number");
            sb.append(v/2);
            sb.append(v%2);
        }
        return sb.toString();
    }

    public static boolean isPalindrome(String s) {
        if(s == null || s.length()<2)
            return true;
        int len = s.length();
        for(int i=0;i<len/2;i++) {
            if(s.charAt(i) != s.charAt(len-i-1))
                return false;
        }
        return true;
    }

    // with dynamic programming
    public static String longestPalindromeSubstring(String str) {
        if(str == null || str.length() < 2)
            return str;
        if(str.length() == 2)
            return str.charAt(0) == str.charAt(1) ? str : str.substring(0,1);
        int len = str.length();
        boolean[][] table = new boolean[len][len];
        int maxlen = 0;
        String maxsub = str.substring(0,1);
        // fill table for length=1
        for(int i=0;i<len;i++)
            table[i][i] = true;
        // edit table for lengths = 2
        for(int i=0;i<len-1;i++) {
            if(str.charAt(i) == str.charAt(i+1)) {
                table[i][i+1] = true;
                maxlen = 2;
                maxsub = str.substring(i, i+2);
            }
        }
        // edit table for lengths >= 3 up til len
        for(int l=3;l<=len;l++) {
            for(int i=0;i<=len-l;i++) {
                int j = i+l-1;
                if(str.charAt(i) == str.charAt(j)
                        && table[i+1][j-1]) {
                    table[i][j] = true;
                    if(l > maxlen) {
                        maxlen = l;
                        maxsub = str.substring(i,j+1);
                    }
                }
            }
        }
        return maxsub;
    }


    public static void main(String[] args) {
        //System.out.println(isRotation("hema", "emah"));
        //ArrayList<String> perms = perms("abc");
        //for(String s : perms)
         //   System.out.println(s);
        //System.out.println(reverseSentence("abc is a great company"));
        //System.out.println(isSubstring("alabama", ""));
        //System.out.println(convertFromBase4ToBase2("32"));
        //System.out.println(isPalindrome("abcba"));
        System.out.println(longestPalindromeSubstring("nabbghk"));

    }

}
