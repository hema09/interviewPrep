package problems2;

public class BitProblems {
    public static int replaceBits(int N, int M, int i, int j) {
        if(i < j)
            return N;
        int k = j;
        while(k <= i) {
            N = N & ~(1 << k);
            k++;
        }
        M = M << j;
        N = N | M;
        return N;
    }

    public static int replace(int N, int M, int i, int j) {
        if(i<j)
            return N;
        int k=i;
        while(k <=j) {
            N = N & ~(1<<k);
            k++;
        }
        M = M << j;
        N = N | M;
        return N;
    }

    public static String decimalToBinary(int n) {
        StringBuilder s = new StringBuilder("");
        while(n > 0) {
            s.append(n%2);
            n = n/2;
        }
        return s.reverse().toString();
    }

    public static void printBinaryOfRealNumberBetween0And1(double n) {
        if (n >= 1 || n <= 0)
            System.out.println("ERROR");
        StringBuilder s = new StringBuilder(".");
        while(n > 0) {
            if(s.length() >= 32) {
                System.out.println("ERROR");
                break;
            }
            double r = n *2;
            if(r >= 1) {
                s.append(1);
                n=r-1;
            } else {
                s.append(0);
                n = r;
            }
        }
        if(s.length() < 32)
            System.out.println(s.toString());
    }

    // return max length of sequence of 1s by flipping one 0 to a 1
    // 11011011101111 should return 8
    public static int flipBits(int a) {
        if(~a == 0)
            return Integer.SIZE;
        int maxlen=0, currlen=0, prevlen=0;

        while(a != 0) {
            if((a&1) == 1) {
                currlen++;
            } else if((a&1) == 0) {
                prevlen = (a&2) == 0 ? 0 : currlen;
                currlen=0;
            }
            maxlen = Math.max(currlen + prevlen + 1, maxlen);
            a >>>= 1;
        }
        return maxlen;
    }

    public static int bitsToFlipAtoB(int a, int b) {
        int c = a^b;
        int bits = 0;
        while(c != 0) {
            if((c & 1) == 1)
                bits++;
            c = c >>1;
        }
        return bits;
    }

    public static void main(String[] args) {
        /*int N = Integer.parseInt("1010101010",2);
        int M = Integer.parseInt("11011",2);
        int X = replaceBits(N, M, 6, 2);
        System.out.println(Integer.toBinaryString(X));
        X = replace(N, M, 6, 2);
        System.out.println(Integer.toBinaryString(X));*/

        //System.out.println(decimalToBinary(10));
        //printBinaryOfRealNumberBetween0And1(0.625);

        //System.out.println(flipBits(Integer.valueOf("11011011101111",2)));
        System.out.println(bitsToFlipAtoB(29,15));
    }
}
