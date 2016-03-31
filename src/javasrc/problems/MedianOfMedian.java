package javasrc.problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Hema on 3/18/2016.
 */
public class MedianOfMedian {

    // todo: this method has errors, AIOOB
    int medianPer5Elements(ArrayList<Integer> input) {
        if(input.size() == 1) return input.get(0);
        if (input.size() <= 5) {
            Collections.sort(input);
            return input.get(input.size()/2);
        }
        ArrayList<Integer> medians = new ArrayList<Integer>();
        int[] temp;
        for(int i=0; i<input.size();i+=5) {
            int min = Math.min(5, input.size()-i);
            if(min == 0) break;
            temp = new int[min];
            for(int j=0; j<min; j++) {
                temp[j] = input.get(i+j);
            }
            Arrays.sort(temp);
            medians.add(temp[min/2]);
        }
        return medianPer5Elements(medians);
    }

    // k was passed as median, but same code can be used for kth smallest element
    public int median(ArrayList<Integer> input, int k) {
        if(input.size() == 1)
            return input.get(0);
        int pivot = medianPer5Elements(input);
        ArrayList<Integer> less = new ArrayList<Integer>();
        ArrayList<Integer> more = new ArrayList<Integer>();
        for(int i=0; i<input.size(); i++) {
            if(input.get(i) <= pivot) {
                less.add(input.get(i));
            } else
                more.add(input.get(i));
        }
        if(less.size() == k)
            return pivot;
        if(less.size() > k)
            return median(less, k);
        return median(more, k-more.size());
    }

    public static void main(String[] args) {
        ArrayList<Integer> input = randomList(100, 100);
        MedianOfMedian med = new MedianOfMedian();
        System.out.println(med.median(input, input.size()/2));
    }

    static ArrayList<Integer> randomList(int size, int max) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i=0; i<size; i++) {
            list.add((int)(Math.random()*max));
        }
        return list;
    }
}
