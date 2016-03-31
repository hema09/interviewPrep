package javasrc.problems;

import java.util.*;

/**
 * Created by Hema on 3/8/2016.
 */
public class Hash {
    public static HashMap<Character, Integer> getLettersMap(){
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        Character ch = 'A';
        while(ch <= 'Z') {
            switch(ch) {
                case 'A' : case 'B' : case 'C' : map.put(ch, 2); break;
                case 'D' : case 'E' : case 'F' : map.put(ch, 3); break;
                case 'G' : case 'H' : case 'I' : map.put(ch, 4); break;
                case 'J' : case 'K' : case 'L' : map.put(ch, 5); break;
                case 'M' : case 'N' : case 'O' : map.put(ch, 6); break;
                case 'P' : case 'Q' : case 'R' : map.put(ch, 7); break;
                case 'S' : case 'T' : case 'U' : case 'V' : map.put(ch, 8); break;
                case 'W' : case 'X' : case 'Y' : case 'Z' : map.put(ch, 9); break;
            }
            ch++;
        }
        return map;
    }

    // convert 1-800-COM-CAST to 18002662288
    public String getPhoneNumber(String input) {
        Map<Character, Integer> map = getLettersMap();
        input = input.toUpperCase();
        String output = "";
        for(int i=0; i<input.length();i++) {
            Character ch = input.charAt(i);
            if(ch >= '0' && ch <= '9')
                output += ch;
            if(ch >= 'A' && ch <='Z') {
                output += map.get(ch);
            }
        }
        return output;
    }

    public long getRandomNumberBetween(Integer n1, Integer n2) {
        return new Random().nextInt(n2) - n1;
    }

    /** randomly order deck of cards (integers) such that each reordering is equally likely

    for each digit :
    randomly swap it with any other digit in integers array
    return this new array */
    public ArrayList<Integer> shuffle(ArrayList<Integer> input) {
        Random random = new Random();
        for(Integer i=input.size()-1; i >= 0;i--) {
            Integer newPos = random.nextInt(i);
            Integer temp = input.get(newPos);
            input.add(i,temp);
            input.add(newPos, input.get(i));
        }
        return input;
    }

    // given array of integers and number called sum, check if 2 integers add upto sum (other way to use bst - see TreeFunctions class)
    public boolean hasSum(ArrayList<Integer> integers, int sum) {
        HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
        for(Integer i : integers) {
            if(map.containsKey(sum - i))
                return true;
            map.put(i, true);
        }
        return false;
    }

    // find element present only once, given array where all elements are repeated twice exept one
    public static int getNonTwice(ArrayList<Integer> input) {
        HashSet<Integer> set = new HashSet<Integer>();
        int sum = 0;
        for(Integer i : input) {
            if(set.contains(i)) {
                sum -= i;
            } else {
                set.add(i);
                sum += i;
            }
        }
        return sum;
    }

    // identify subarray with maximum sum
    public int[] maxSumSubArray(int[]  A) {
        int maxSum = 0;
        int begin = 0, end = 0, maxBegin=0, maxEnd=0;
        int[] sum = new int[A.length];
        sum[0] = A[0];
        for(int i=1;i < A.length;i++) {
            if(sum[i-1] + A[i] < A[i]) {
                sum[i] = A[i];
                begin = i;
                end = i;
            } else {
                sum[i] = sum[i] + A[i]; end = i;
            }
            if(sum[i] > maxSum) {
                maxSum = sum[i];
                maxBegin = begin;
                maxEnd=end;
            }
        }
        return Arrays.copyOfRange(A, maxBegin, maxEnd);
    }


    public static void main(String[] args) {
        ArrayList<Integer> input = new ArrayList<Integer>();
        SortedSet<Integer> sortedSet = new TreeSet<Integer>();
        input.add(2);
        input.add(3);
        input.add(2);
        input.add(3);
        input.add(4);
        input.add(1);
        input.add(4);
        System.out.println(getNonTwice(input));
    }



}
