package javasrc.problems;

import java.util.ArrayList;

/**
 * Created by Hema on 3/6/2016.
 */
public class DP {

    // O(mn)
    // longest commom subsequence
    // ABCRDEOF and ALCKDME should return ACDE
    public int lcs(String s1, String s2) {
        if(s1 == null || s1.length() == 0) return 0;
        if(s2 == null || s2.length() == 0) return 0;
        int m = s1.length();
        int n = s2.length();
        int[][] LCS = new int[m+1][n+1];
        for(int i=1; i<= m; i++) {
            for(int j=1; j<= n;j++) {
                if(i==0 || j==0)
                    LCS[i][j] = 0;
                else if(s1.charAt(i-1) == s2.charAt(j-1))
                    LCS[i][j] = 1 + LCS[i-1][j-1];
                else
                    LCS[i][j] = Math.max(LCS[i-1][j], LCS[i][j-1]);
            }
        }
        return LCS[m][n];
    }

    // ABCDEF and GHCDEL should return CDE
    public int longestCommonSubstring(String s1, String s2) {
        if(s1 == null || s1.length() == 0) return 0;
        if(s2 == null || s2.length() == 0) return 0;
        int m = s1.length();
        int n = s2.length();
        int[][] LCS = new int[m+1][n+1];
        int maxLen = 0;
        for(int i=1; i<= m; i++) {
            for(int j=1; j<= n;j++) {
                if(i==0 || j==0)
                    LCS[i][j] = 0;
                else if(s1.charAt(i-1) == s2.charAt(j-1)) {
                    LCS[i][j] = 1 + LCS[i - 1][j - 1];
                    maxLen = Math.max(maxLen, LCS[i][j]);
                }
                else
                    LCS[i][j] = 0; // in sub sequence calculation, we put the max of left and above subentries, but since its sub string, we make it 0;
            }
        }
        return maxLen;
    }

    public String longestPalindromeSubstringLength(String str) {
        // modify for returning substring
        int start = 0;
        if(str == null) return null; // 0 for length
        if(str.length() <= 1) return str; //return str.length(); for length
        int n = str.length();
        boolean[][] palin = new boolean[n][n];
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
                palin[i][j] = false;
        for(int i=0;i<n;i++)
            palin[i][i]=true;
        int maxlen = 1;
        for(int i=0;i<n-1;i++) {
            if(str.charAt(i)==str.charAt(i + 1)) {
                palin[i][i+1]=true;
                maxlen = 2;
                start = i;
            }
        }
        // sl = substring length
        for(int sl=3;sl<=n;sl++){
            for(int i=0;i<n-sl+1;i++) {
                int j = i+sl-1;
                if(str.charAt(i) == str.charAt(j) && palin[i+1][j-1]) {
                    palin[i][j] = true;
                    if(sl > maxlen) {
                        maxlen = sl;
                        start = i;
                    }
                }
            }
        }
        //return palin[0][n-1]; // for length or return maxlen
        return str.substring(start, start+maxlen-1); // to return actual string
    }

    public int LIS(ArrayList<Integer> input) {
        if(input == null) return 0;
        if(input.size() <= 1) return 1;
        int n = input.size(), maxlis = 1;
        int[] lis = new int[n];
        for(int i=n-1;i>=0;i--) {
            lis[i] = 1;
            for (int j = i + 1; j < n; j++) {
                if (input.get(i) < input.get(j) && lis[i] < lis[j]+1) {
                    lis[i] = lis[j] + 1;
                    maxlis = Math.max(maxlis, lis[i]);
                }
            }
        }
        return maxlis;
    }

    // recursive solution for LIS
    static int maxlis = 0;
    public int LIS(ArrayList<Integer> input, int n) {
        if(input == null) return 0;
        int lis = 0;
        for(int j = n+1;j<input.size();j++) {
            int lisj = LIS(input, j);
            if(input.get(n) < input.get(j) && lis < lisj+1)
                lis = lisj+1;
        }
        maxlis = Math.max(maxlis, lis);
        return lis;
    }

    int min(int a, int b, int c) {
        return (a <= b) && (a <= c)? a : ((b <= c) ? b : c);
    }

    // O(n^2)
    public int edit(String x, String y) {
        if(x.length() == 0) return y.length();
        if(y.length() == 0) return x.length();
        int m = x.length();
        int n = y.length();
        int[][] edit = new int[m+1][n+1];
        for(int i=0;i<m;i++)
            edit[i][0] = i;
        for(int j=0;j<n;j++)
            edit[0][j] = j;
        for(int i=1;i<=m;i++) {
            for(int j=1;j<=n;j++){
                if(x.charAt(i-1)==y.charAt(j-1))
                    edit[i][j] = min(edit[i-1][j-1],edit[i-1][j]+1, edit[i][j-1]+1);
                else {
                    edit[i][j] = 1 + min(edit[i-1][j-1],edit[i-1][j], edit[i][j-1]);
                }
            }
        }
        return edit[m][n];
    }

    public boolean isInterleave(String t1, String t2, String t3) {
        if(t1 == null || t1.isEmpty()) return t2.equals(t3);
        if(t2 == null || t2.isEmpty()) return t1.equals(t3);
        int m = t1.length(), n = t2.length();
        if(t3.length() != m + n) return false;
        boolean[][] ileave = new boolean[m+1][n+1];
        for(int i=0; i<=m;i++)
            for(int j=0;j<=n;j++)
                ileave[i][j] = false;

        char[] s1 = t1.toCharArray();
        char[] s2 = t2.toCharArray();
        char[] s3 = t3.toCharArray();

        for(int i=0;i<=m;i++) {
            for(int j=0;j<=n;j++) {
                if(i==0 && j == 0)
                    ileave[i][j] = true;
                else if(i == 0 && s2[j-1]==s3[j-1])
                    ileave[i][j] = ileave[i][j-1];
                else if (j == 0 && s1[i-1]==s3[i-1])
                    ileave[i][j] = ileave[i-1][j];
                else if(i == 0 && s2[j-1]!=s3[j-1])
                    ileave[i][j] = ileave[i][j];
                else if (j == 0 && s1[i-1]!=s3[i-1])
                    ileave[i][j] = ileave[i][j];
                else if(s1[i-1]==s3[i+j-1] && s2[j-1]!=s3[i+j-1])
                    ileave[i][j]=ileave[i-1][j];
                else if(s1[i-1]!=s3[i+j-1] && s2[j-1]==s3[i+j-1])
                    ileave[i][j]=ileave[i][j-1];
                else if(s1[i-1]==s3[i+j-1] && s2[j-1]==s3[i+j-1])
                    ileave[i][j]=(ileave[i][j-1] || ileave[i-1][j]);
            }
        }
        return true;
    }

    // minimum coins for a change
    // O(mV)
    public int minCoins(int[] coins, int V) {
        int m = coins.length;
        if(V == 0) return 0;
        if(coins == null || coins.length == 0) return 0;
        int[] totalAmount = new int[V+1];

        totalAmount[0] = 0;
        for(int i=1; i<=V;i++)
            totalAmount[i] = Integer.MAX_VALUE;

        for(int amt=1; amt<=V;amt++) {
            for(int j=0;j<m;j++) {
                if(coins[j] <= amt) {
                    int temp_res = totalAmount[amt - coins[j]];
                    if (temp_res != Integer.MAX_VALUE && temp_res + 1 < totalAmount[amt])
                        totalAmount[amt] = temp_res + 1;
                }
            }
        }
        return totalAmount[V];
    }

    public static void main(String[] args) {
        DP dp = new DP();
        System.out.println(dp.longestPalindromeSubstringLength("abcaabaac"));
        /*ArrayList<Integer> arr = new ArrayList<Integer>();
        arr.add(0);arr.add(1);arr.add(2);arr.add(3);arr.add(4);arr.add(2);arr.add(4);arr.add(0);arr.add(0);
        //System.out.println(dp.LIS(arr));
        //System.out.println(dp.edit("nerd", "horse"));
        //System.out.println(dp.isInterleave("abec", "abd", "ababdecd"));
        int[] coins = {1,5,6,9};
        System.out.println(dp.minCoins(coins, 11));*/
    }
}
