package javasrc.problems;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by Hema on 3/12/2016.
 */
// get median of all numbers till now, where numbers are a stream
public class MedianUsingHeap {

    PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(10, new Comparator<Integer>() {
        public int compare(Integer in1, Integer in2) {
            return in2 - in1;
        }
    });
    PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>(10, new Comparator<Integer>() {
        public int compare(Integer in1, Integer in2) {
            return in1 - in2;
        }
    });

    // maxHeap has values below the median with max value of top,
    // minHeap has values above the median with min value on top
    public void addNewNumber(int n) {
        if(maxHeap.size() == minHeap.size()) {
            if(minHeap.peek() != null && n > minHeap.peek()) {
                maxHeap.offer(minHeap.poll());
                minHeap.offer(n);
            }else {
                maxHeap.offer(n);
            }
        } else {
            if(n < maxHeap.peek()) {
                minHeap.offer(maxHeap.poll());
                maxHeap.offer(n);
            } else
                minHeap.offer(n);
        }
    }

    public int getMedian() {
        if(maxHeap.isEmpty()) return minHeap.peek();
        if(minHeap.isEmpty()) return maxHeap.peek();
        if(maxHeap.size() == minHeap.size()) return minHeap.poll()+maxHeap.poll()/2;
        if(minHeap.size() > maxHeap.size()) return minHeap.peek();
        else return maxHeap.peek();
    }

    public static void main(String[] args) {
        MedianUsingHeap test = new MedianUsingHeap();
        test.addNewNumber(2);
        test.addNewNumber(12);
        test.addNewNumber(20);
        test.addNewNumber(1);
        test.addNewNumber(5);
        System.out.println(test.getMedian());
    }


}
