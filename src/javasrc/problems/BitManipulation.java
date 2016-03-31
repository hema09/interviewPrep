package javasrc.problems;

/**
 * Created by Hema on 3/7/2016.
 */
public class BitManipulation {

    public static Integer replaceBits(int N, int M, int i, int j) {
        if(i<j)
            return N;
        int k=j;
        while(k<=i) {
            N = N & ~(1<<k);
            k++;
        }
        M = M<<j;
        N = N | M;
        return N;
    }

    public static String toBinary(String input) {
        int intPart = Integer.valueOf(Integer.parseInt(input.substring(0, input.indexOf('.'))));
        double decPart = Double.valueOf(Double.parseDouble(input.substring(input.indexOf('.'))));
        String str = "";
        while(intPart>0) {
            int digit = intPart%2;
            str = digit + str;
            intPart >>= 1;
        }
        if(decPart>0)
            str += '.';
        while(decPart>0) {
            double temp = decPart*2;
            if(temp>=1) {
                str += '1';
                decPart -= 1;
            }
            else {
                str += '0';
                decPart = temp;
            }
        }
        return str;
    }

    public int swapsRequired(int a, int b) {
        int c = a ^ b;
        int i=0;
        while(c > 0) {
            if(c % 2 > 0) // or c & 1 == 1
                i++;
            c >>= 1;
        }
        return i;
    }

    /*public int findMissing(ArrayList<BitInteger> input) {
        return findMissing(input, Integer.INTEGER_SIZE-1);
    }

    public int findMissing(ArrayList<BitInteger> input, int column) {
        if(column < 0)
            return -1;
        ArrayList<BitInteger> even = new ArrayList<BitInteger>();
        ArrayList<BitInteger> odd = new ArrayList<BitInteger>();
        for(BitInteger in : input) {
            if(in%2 == 0)
                even.add(in);
            else
                odd.add(in);
        }
        if(even.size() > odd.size()) {
            return findMissing(odd, column-1) << 1 | 0;
        } else
            return findMissing(even, column-1) << 1 | 1;
    }
*/

    public int swapEvenOdd(int n) {
        return ((n & 0x55555555) << 1) | ((n&0xaaaaaaaa)>>1);
    }




    public static void main(String[] args) {
        //System.out.println(replaceBits(Integer.valueOf("1011001",2), Integer.valueOf("1100",2), 5 , 2));
        //System.out.println(String.valueOf(Integer.valueOf("1110001",2)));
        System.out.println(toBinary("3.45"));
    }
}
