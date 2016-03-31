package javasrc.problems;

import java.util.*;

/**
 * Created by Hema on 3/5/2016.
 */
public class Sort {

    public static void swap(ArrayList<Integer> arr, int i, int j) {
        Integer temp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, temp);
    }

    public static ArrayList<Integer> bubbleSort(ArrayList<Integer> input) {
        if(input == null || input.size() == 1) return input;
        int n = input.size();
        boolean change = true;
        for(int i=0;i<n;i++) { // number of passes
            change = false;
            for(int j=0;j < n-1; j++) {
                if(input.get(j) > input.get(j+1)) {
                    swap(input, j, j+1);
                    change = true;
                }
            }
            if(!change) break;
        }
        return input;
    }

    public static ArrayList<Integer> selectionSort(ArrayList<Integer> arr) {
        if (arr == null || arr.size() == 1) return arr;
        int n = arr.size();
        for (int i = 0; i < n - 1; i++) {
            int min = arr.get(i);
            int loc = i;
            for (int j = i + 1; j < n; j++) {
                if (arr.get(j) < min) {
                    min = arr.get(j);
                    loc = j;
                }
            }
            swap(arr, i, loc);
        }
        return arr;
    }

    public static ArrayList<Integer> insertionSort(ArrayList<Integer> input) {
        if(input == null || input.size() == 1) return input;
        int n = input.size();
        int temp;
        for(int i=1;i<n;i++) {
            temp = input.get(i);
            int j=i-1;
            while(j >= 0 && input.get(j)>temp) {
                input.set(j, input.get(j-1));
                j--;
            }
            input.set(j+1, temp);
        }
        return input;
    }

    int [] countSort(int[] in) {
        int max = in[0];
        for(int i=0;i<in.length;i++)
            if(in[i] > max)
                max = in[i];
        int[] count = new int[max];
        for(int i=0;i<in.length;i++) {
            count[in[i]]++;
        }
        for(int i=1;i<count.length;i++)
            count[i] += count[i-1];
        int[] output = new int[in.length];
        for(int i=in.length-1;i>0;i--) {
            output[in[i]] = count[in[i]]-1;
            count[in[i]]--;
        }
        for(int i=0;i<in.length;i++)
            in[i] = output[i];
        return in;
    }

    public static ArrayList<Integer> mergeSort(ArrayList<Integer> list) {
        if(list == null || list.size() == 1) return list;
        int mid = list.size()/2;
        ArrayList<Integer> lhalf = new ArrayList<Integer>();
        ArrayList<Integer> rhalf = new ArrayList<Integer>();
        for(int i=0;i<list.size();i++) {
            if(i < mid)
                lhalf.add(list.get(i));
            else
                rhalf.add(list.get(i));
        }
        return merge(mergeSort(lhalf), mergeSort(rhalf));
    }

    public static ArrayList<Integer> merge(ArrayList<Integer> list1, ArrayList<Integer> list2) {
        int i = 0;
        int j = 0;
        ArrayList<Integer> mlist = new ArrayList<Integer>();
        while(i<list1.size() && j < list2.size()) {
            if(list1.get(i) <= list2.get(j)) {
                mlist.add(list1.get(i++));
            } else
                mlist.add(list2.get(j++));
        }
        if(i<list1.size())
            while(i < list1.size())
                mlist.add(list1.get(i++));
        if(j < list2.size())
            while(j < list2.size())
                mlist.add(list2.get(j++));
        return mlist;
    }

    public static ArrayList<Integer> concatenate(ArrayList<Integer> list1, int mid, ArrayList<Integer> list2) {
        ArrayList<Integer> newList = new ArrayList<Integer>();
        int i= 0;
        int j=0;
        int l = list1.size();
        int r = list2.size();
        while(i< l)
        {
            newList.add(list1.get(i));
            i++;
        }
        newList.add(mid);
        while(j<r) {
            newList.add(list2.get(j));
            j++;
        }
        return newList;
    }

    public static ArrayList<Integer> quickSort(ArrayList<Integer> arr) {
        if(arr.size() <= 1)
            return arr;
        int last = arr.size();
        int mid = arr.get(last/2);
        ArrayList<Integer> list1 = new ArrayList<Integer>();
        ArrayList<Integer> list2 = new ArrayList<Integer>();

        for(int i=0;i<arr.size();i++) {
            if(i != arr.size()/2) {
                if(arr.get(i) <= mid){
                    list1.add(arr.get(i));
                } else {
                    list2.add(arr.get(i));
                }
            }
        }
        arr = concatenate(quickSort(list1),mid,quickSort(list2));
        return arr;
    }

    public static void quickSortInPlace(int[] arr, int l, int h) {
        if(l >= h) return;
        int pivot = arr[l];
        int start = l+1;
        int end = h;
        while(start < end) {
            while(start < end && arr[start] < pivot) start++;
            while(end > start && arr[end] > pivot) end--;
            if(start < end && arr[start] > arr[end]) {
                int temp = arr[start];
                arr[start] = arr[end];
                arr[end] = temp;
            }
        }
        //swap start and pivot
        if(pivot > arr[start]) {
            int temp = arr[start];
            arr[start] = pivot;
            arr[l] = temp;
        }
        quickSortInPlace(arr, l, start-1);
        quickSortInPlace(arr, start+1, h);
    }

    // count sort for exponent exp, radix=10. Change radix=n for numbers between 1-n^2
    void countSort(ArrayList<Integer> arr, int exp) {
        int n = arr.size();
        int[] output = new int[n];
        int[] count = new int[10]; // for radix 10
        // initialize count and output arrays
        for(int i=0 ;i <n; i++) {
            output[i] = 0;
            count[i] = 0;
        }
        // increment count based on digit at exp location
        for(int i=0; i< n; i++) {
            count[(arr.get(i)/exp)%10]++;
        }
        // update count based on actual location in output array
        for(int i=1; i< n; i++) {
            count[i] += count[i-1];
        }
        // populate output array
        for(int i=0; i<n; i++) {
            output[count[(arr.get(i)/exp)%10]-1] = arr.get(i);
            count[(arr.get(i)/exp)%10]--;
        }
        // copy output to arr
        for(int i=0;i<n; i++)
            arr.set(i, output[i]);
    }

    /**
     * Counting sort: buckets hold only a single value
     * Bucket sort: buckets hold a range of values
     * Radix sort: buckets hold values based on digits within their values
     */
    public ArrayList<Integer> radixSort(ArrayList<Integer> input) {
        int m = Integer.MAX_VALUE;
        for(int i=0;i<input.size();i++)
            m = Math.min(input.get(i), m);
        for(int exp=1; m/exp > 0; exp*=10)
            countSort(input, exp);
        return input;
    }

    // also called bin sort
    public static int[] bucketSort(int[] input, int bucketCount) {
        if(input.length < 2) return input;
        int min = 0, max = 0, n = input.length;
        for(int i=0; i< input.length; i++) {
            if(input[i] < min) min = input[i];
            if(input[i] > max) max = input[i];
        }
        int interval = (max - min + 1) / bucketCount;
        ArrayList<Integer>[] buckets = new ArrayList[bucketCount];
        for(int i = 0; i< bucketCount ; i++)
            buckets[i] = new ArrayList<Integer>();
        for(int i = 0;i <n;i++) {
            int bucketIndex = (int)(input[i]-min)/interval;
            buckets[bucketIndex].add(input[i]);
        }
        int ptr=0;
        for(int i=0;i<bucketCount;i++){
            Collections.sort(buckets[i]);
            for(int j=0;j < buckets[i].size();j++)
                input[ptr++] = buckets[i].get(j);
        }
        return input;
    }

    public static void swap(int[] arr, int ind, int with) {
        int temp = arr[ind];
        arr[ind] = arr[with];
        arr[with] = temp;
    }

    public static int[] heapSort(int[] arr) {
        if(arr == null || arr.length < 2) return arr;
        int size = arr.length;
        // create heap
        for(int i=size/2-1;i>=0;i--) { // n * log(n) = nlog(n)
            heapify(arr, i, size);
        }
        printArray(arr);
        System.out.println();
        // swap 0th element(top of heap) with jth(last) element of arr, and heapify the 0th element
        for(int j=size-1; j >= 0;j--) { // n * log(n) = nlog(n)
            swap(arr, 0, j);
            size = size-1;
            heapify(arr, 0, size);
        }
        return arr; // total = 2 * nlog(n) ~ nlog(n)
    }

    // max heapification
    public static void heapify(int[] arr, int index, int size) {
        int lindex = index*2 + 1;
        int rindex = index*2 + 2;
        int swapBy = index;
        if(lindex < size && arr[lindex] > arr[index])
            swapBy=lindex;
        if(rindex < size && arr[rindex] > arr[swapBy])
            swapBy=rindex;
        if(swapBy != index) {
            swap(arr, index, swapBy);
            heapify(arr, swapBy, size);
        }
    }


    public static void printArray(ArrayList<Integer> arr) {
        for(int i=0;i<arr.size();i++)
            System.out.print(arr.get(i) + " ");
    }

    public static void printArray(int[] arr) {
        for(int i=0;i<arr.length;i++)
            System.out.print(arr[i] + " ");
    }

    public static void printArray(String[] arr) {
        for(int i=0;i<arr.length;i++)
            System.out.print(arr[i] + " ");
    }

    public class AnagramComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return sortChars(a).compareTo(sortChars(b));
        }
        public String sortChars(String a) {
            char[] ch = a.toCharArray();
            Arrays.sort(ch);
            return new String(ch);
        }
    }

    // cannot handle duplicates
    public static int modifiedBS(int[] arr, int v, int s, int e) {
        if(s>e) return -1;
        int m = s + (e-s)/2;
        if(arr[m] == v) return m;
        if(arr[s] < arr[m]) {
            if(v >= arr[s] && v < arr[m])
                return modifiedBS(arr, v, s, m-1);
            else
                return modifiedBS(arr, v, m+1, e);
        } else {
            if(v>arr[m] && v <= arr[e])
                return modifiedBS(arr, v, m+1, e);
            else
                return modifiedBS(arr, v, s, m-1);
        }
    }

    // cannot handle duplicates
    public static int modifiedBS_iterative(int[] arr, int v, int s, int e) {
        if(s>e) return -1;
        int m;
        while(s <= e) {
            m = s + (e-s)/2;
            if(arr[m] == v) return m;
            if(arr[s] < arr[m]) {
                if(v >= arr[s] && v < arr[m])
                    e = m - 1;
                else
                    s = m + 1;
            } else {
                if(v > arr[m] && v <= arr[e])
                    s = m + 1;
                else
                    e = m - 1;
            }
        }
        return -1;
    }

    // sorted string array contains lots of "", search string
    public static int search(String[] input, String s, int start, int end) {
        while(end > start && input[end].equals(""))
            end--;
        while(start < end && input[start].equals(""))
            start++;
        if(start>end) return -1;
        int mid = start + (end-start)/2;
        while(input[mid].equals(""))mid++;
        if(input[mid].equals(s))
            return mid;
        int r = s.compareTo(input[mid]);
        if(r > 0)
            return search(input, s, mid+1, end);
        return search(input, s, start, mid-1);
    }

    // complexity = n+n/2+n/4+...+1=O(log n)
    // same can be used to fetch median (special case with k=n/2)
    public static Integer findKthSmallest(ArrayList<Integer> array, int start, int end, int k) {
        if(start>end) return null;
        int pivot = start + (end-start)/2;
        ArrayList<Integer> smaller = new ArrayList<Integer>();
        ArrayList<Integer> larger = new ArrayList<Integer>();
        for(int i=start ;i<=end;i++) {
            if(array.get(i) < array.get(pivot))
                smaller.add(array.get(i));
            if(array.get(i) > array.get(pivot))
                larger.add(array.get(i));
        }
        if(smaller.size()==k)
            return array.get(pivot); // k=pivot index
        if(smaller.size() > k) // k is somewhere in smaller
            return findKthSmallest(array, start, pivot-1, k);
        return findKthSmallest(array, pivot+1, end, k-pivot); // k is somewhere in larger array
    }

    public class PersonComparator implements Comparator<Person> {
        @Override
        public int compare(Person p1, Person p2) {
            return p1.number.compareTo(p2.number);
        }
    }

    class Person {
        String name;
        Integer number;

    }
    public ArrayList<Person> getNeighbors(Person person, ArrayList<Person> input) {
        ArrayList<Person> friends = new  ArrayList<Person>();
        int start = (person.number-1)-2;
        int end = (person.number-1)+2;
        int added = 0;
        while(added < 3) {
            if(start >=0 && end <= input.size()) {
                friends.add(input.get(start));
                added++;
            }
            start++;
        }
        if(person.number==1)
            if(friends.size()>3)
                friends.add(input.get(3));
        if(person.number==input.size())
            if(friends.size()>3)
                friends.add(input.get(input.size()-3));
        return friends;
    }

    public HashMap<Person, ArrayList<Person>> friendsList(ArrayList<Person> input) {
        Collections.sort(input, new PersonComparator());
        HashMap<Person, ArrayList<Person>> personFriends = new HashMap<Person, ArrayList<Person>>();
        for(Person person : input) {
            ArrayList<Person> friends = getNeighbors(person, input);
            personFriends.put(person, friends);
        }
        return personFriends;
    }



    // given 2 sorted arrays, find pair(one element from EACH array) with sum closest to 'sum'
    public void closest(int[] A, int[] B, int m, int n, int sum) {
        int diff=Integer.MAX_VALUE;
        int point1 = -1, point2 = -1;
        int l=0, r=n-1;
        while(l<m && r >=0) {
            if(A[l]+B[r] == sum) {
                System.out.println("Pair = " + A[l] + ", " + B[r]);
                return;
            }
            if(Math.abs(A[l] + B[r] - sum) < diff) {
                point1 = A[l];
                point2 = B[r];
                diff = Math.abs(A[l]+B[r]-sum);
            }
        }
        System.out.println("Pair = " + point1 + ", " + point2);
    }
    public static void main(String[] args) {
        int[] arr = {1,7,4,0,7,3,9,6,1,2,4};
        int[] arr2 = {4,5,6,7,0,1,2,3};
        quickSortInPlace(arr, 0, arr.length-1);
        printArray(arr);

        //System.out.println(modifiedBS_iterative(arr2, 0, 0, arr2.length - 1));
        //arr = heapSort(arr);
        //printArray(arr);
        //String[] strs = {"baa", "aba", "ds", "sda", "sd"};
        //Arrays.sort(strs, new Sort().new AnagramComparator());
        //printArray(strs);
        //String[] strs = {"at", "", "", "ball", "", "", "cat", "per", "", "", "", "ter"};
        //System.out.println(search(strs, "ball", 0, strs.length-1));

    }

}
