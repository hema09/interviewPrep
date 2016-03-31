package javasrc.problems;

import java.util.ArrayList;

/**
 * Created by Hema on 3/6/2016.
 */
public class Recursion {
    public static int fib(int n) {
        if(n < 0) return -1;
        if(n == 0 || n == 1) return n;
        return fib(n-1) + fib(n-2);
    }

    public static int fibI(int n) {
        if(n < 0) return -1;
        if(n == 0 || n == 1) return n;
        int n1 = 0, n2 = 1, n3 = n2 + n1;
        for(int i=2;i<=n;i++) {
            n3 = n2 + n1;
            n1 = n2;
            n2 = n3;
        }
        return n3;
    }

    public void print(ArrayList<Point> path) {
        for(int i=0;i<path.size();i++)
            System.out.print("(" + path.get(i).x + "," + path.get(i).y + ") ");
        System.out.println();
    }

    // robot path from 0,0 to n,n
    public boolean checkPath(int x, int y, int n, ArrayList<Point> currentPath) {
        if(x >= n || y >=n) {
            return false;
        }
        Point p = new Point(x, y);
        currentPath.add(p);
        if(x == n-1 && y == n-1) {
            print(currentPath);
            return true;
        }
        boolean success = false;
        if(x+1 < n)
            success = checkPath(x+1, y, n, currentPath);
        if(!success && y < n+1)
            success = checkPath(x, y+1, n, currentPath);
        if(!success)
            currentPath.remove(p);
        return success;
    }

    public void printPaths(int N) {
        checkPath(0, 0, N, new ArrayList<Point>());
    }

    class Point {
        int x, y;
        public Point(int x, int y) {
            this.x=x; this.y=y;
        }
    }

    public ArrayList<ArrayList<Integer>> getAllSubsets(ArrayList<Integer> input) {
        ArrayList<ArrayList<Integer>> subsets = new ArrayList<ArrayList<Integer>>();
        if(input.size() <= 0) {
            return subsets;
        }
        if(input.size() == 1){
            subsets.add(input);
            return subsets;
        }
        Integer first = input.get(0);
        input.remove(0);
        ArrayList<Integer> temp = new ArrayList<Integer>();
        temp.add(first);
        subsets.add(temp);
        ArrayList<ArrayList<Integer>> remaining = getAllSubsets(input);
        subsets.addAll(remaining);
        for(ArrayList<Integer> rem : remaining) {
            ArrayList<Integer> nlist = new ArrayList<Integer>();
            nlist.add(first);
            nlist.addAll(rem);
            subsets.add(nlist);
        }
        return subsets;
    }

    public void printArrayList(ArrayList<Integer> list) {
        for(Integer i : list)
            System.out.print(i + " ");
        System.out.println();
    }

    public ArrayList<ArrayList<Integer>> getAllSubsets2(ArrayList<Integer> input) {
        ArrayList<ArrayList<Integer>> subsets = new ArrayList<ArrayList<Integer>>();
        if(input == null) {
            return subsets;
        }
        if(input.size() == 1){
            subsets.add(input);
            return subsets;
        }
        int nums = 1 << input.size();
        int k = 0;
        for(int i = 0;i<nums;i++) {
            k = i;
            ArrayList<Integer> subset = new ArrayList<Integer>();
            int index = 0;
            while(k > 0) {
                if((k & 1) > 0) {
                    subset.add(input.get(index));
                }
                index++;
                k = k >> 1;
            }
            subsets.add(subset);
        }
        return subsets;
    }

    public void printParenthesis(char[] s, int l, int r, int count) {
        if(l < 0 || l < r) return;
        if(l == 0 && r == 0) {
            System.out.println(s);
            return;
        }
        if(l > 0) {
            s[count]= '(';
            printParenthesis(s, l - 1, r, count + 1);
        }
        if(r < l) {
            s[count]= ')';
            printParenthesis(s, l, r - 1, count + 1);
        }
    }

    public int getCount(int cents, int denom) {
        int count = 0;
        int nextDenom = 0;
        switch(denom) {
            case 25 : nextDenom = 10; break;
            case 10 : nextDenom = 5; break;
            case 5 : nextDenom = 1; break;
            case 1 : return 1;
        }
        for(int i=0;i*denom <= cents;i++) {
            int usedCents = denom * i;
            int leftCents = cents - usedCents;
            count += getCount(leftCents, nextDenom);
        }
        return count;
    }

    // given denominations in S array, count all possible ways change can be made
    public int count(int[] S, int m, int change) {
        if(change < 0) return 0;
        if(change == 0) return 1; // not include anything is one solution
        if(m <= 0 && change >= 1)
            return 0;
        return count(S, m-1, change) + count(S, m, change-S[m-1]); // don't include denomination m or include it and continue
    }

    // recursive
    // C(n,k) = C(n-1,k-1) + C(n-1,k)
    //C(n,n) = C(n,0) = 1
    public int binomialCoefficient(int n, int k) {
        if(k==0 || k==n) return 1;
        return binomialCoefficient(n-1,k-1) + binomialCoefficient(n-1, k);
    }

    // DP
    // O(nk) time and space
    public int binomialCoefficientDP(int n, int k) {
        int[][] C = new int[n+1][k+1];
        for(int i=0; i< n; i++) {
            for(int j=0; j< Math.min(k,i); j++) {
                if(j==0 || j==i)
                    C[i][j] = 1;
                else {
                    C[i][j] = C[i-1][j-1] + C[i-1][j];
                }
            }
        }
        return C[n][k];
    }

    int[] colForRow = new int[8];

    public void printBoard() {
        System.out.print("(");
        for(int i=0;i<8;i++) {
            System.out.print(colForRow[i]+1 + ",");
        }
        System.out.println(")");
    }

    public void placeQueen(int row) {
        if(row == 8) {
            printBoard(); return;
        }
        for(int i=0;i<8;i++) {
            colForRow[row] = i;
            if(check(row))
                placeQueen(row+1);
        }
    }

    public boolean check(int row) {
        for(int i=0;i<row;i++) {
            int abs = Math.abs(colForRow[i] - colForRow[row]);
            if(abs == 0 || abs == (row-i))
                return false;
        }
        return true;
    }

    public int longestPalindromeLength(String str) {
        if(str == null) return 0;
        if(str.length() <= 1) return str.length();
        if(str.charAt(0) == str.charAt(str.length()-1))
            return 2 + longestPalindromeLength(str.substring(1, str.length()-1));

        int max1 = longestPalindromeLength(str.substring(0, str.length()-1));
        int max2 = longestPalindromeLength(str.substring(1, str.length()));
        return Math.max(max1, max2);
    }

    public String longestPalindromeSubstring(String str) {
        if(str == null) return null;
        if(str.length() <= 1) return str;
        if(str.charAt(0) == str.charAt(str.length()-1))
            return String.valueOf(str.charAt(0)) + longestPalindromeSubstring(str.substring(1, str.length() - 1)) + str.charAt(str.length()-1);

        String maxStr1 = longestPalindromeSubstring(str.substring(0, str.length() - 1));
        String maxStr2 = longestPalindromeSubstring(str.substring(1, str.length()));
        return maxStr1.length() > maxStr2.length() ? maxStr1 : maxStr2;
    }

    public int edit(String x, String y) {
        if(x == null || x.length() == 0) return y != null ? y.length() : 0;
        if(y == null || y.length() == 0) return x.length();
        if(x.charAt(x.length()-1) == y.charAt(y.length()-1))
            return edit(x.substring(0, x.length()-1), y.substring(0, y.length()-1));
        else
            return 1 + Math.min(
                    Math.min(edit(x.substring(0, x.length() - 1), y),
                            edit(x, y.substring(0, y.length() - 1))),
                    edit(x.substring(0, x.length()-1), y.substring(0, y.length()-1))
            );
    }

    //O(2n).
    public boolean isInterleave(String s1, String s2, String s3) {
        if(s1 == null || s1.isEmpty()) return s2.equals(s3);
        if(s2 == null || s2.isEmpty()) return s1.equals(s3);
        if(s1.charAt(0) == s3.charAt(0))
            return isInterleave(s1.substring(1), s2, s3.substring(1));
        if(s2.charAt(0) == s3.charAt(0))
            return isInterleave(s1, s2.substring(1), s3.substring(1));
        return false;
    }

    // longest common subsequence of 2 strings
    public String lcs(String s1, String s2) {
        if(s1 == null || s1.length() == 0) return "";
        if(s2 == null || s2.length() == 0) return "";
        if(s1.charAt(0) == s2.charAt(0))
            return s1.charAt(0) + lcs(s1.substring(1), s2.substring(1));
        String lcs1 = lcs(s1.substring(1), s2);
        String lcs2 = lcs(s1, s2.substring(1));
        return lcs1.length() >= lcs2.length() ? lcs1 : lcs2;
    }

    // return true list can list be divided into 2 lists with sum of lists = sum
    public boolean canDivide(ArrayList<Integer> list, int i, int s1, int s2, int sum) {
        if(s1 + s2 == sum && i == list.size())
            return true;
        if(i == list.size())
            return false;
        return canDivide(list, i+1, s1+list.get(i), s2, sum) || canDivide(list, i+1, s1, s2+list.get(i), sum);
    }

    public static void main(String[] args) {
       // System.out.println(fib(4));
       // System.out.println(fibI(4));
        Recursion recursion = new Recursion();
        System.out.println(recursion.lcs("AGGTAB", "GXTXAYB"));
        //recursion.printPaths(3);
        /*ArrayList<ArrayList<Integer>> sets = recursion.getAllSubsets2(new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
        }});
        for(ArrayList a : sets)
            recursion.printArrayList(a);*/
        //char[] str = new char[6];
        //recursion.printParenthesis(str, 3, 3, 0);
        //System.out.println(recursion.getCount(5, 25));
        //recursion.placeQueen(0);
        //System.out.println(recursion.longestPalindromeLength("abccbada"));
        //System.out.println(recursion.longestPalindromeSubstring("abccbada"));
        //System.out.println(recursion.edit("abcd", ""));
        //System.out.println(recursion.isInterleave("abc", "ab", "aabcb"));
    }
}
