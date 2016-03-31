package javasrc.problems;

import java.util.ArrayList;

/**
 * Created by Hema on 3/20/2016.
 */
public class Greedy {

    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    public void sortByFinishTime(int[] start, int[] end, int n) {
        for(int i=0;i<n;i++) {
            for(int j=0; j<n;j++) {
                if(end[j] < end[i]) {
                    swap(end,i,j);
                    swap(start,i,j);
                }
            }
        }
    }

    public ArrayList<Integer> activitySelectionProblem(int[] start, int[] end, int n) {
        sortByFinishTime(start, end, n);
        int k = 1;
        ArrayList<Integer> selected = new ArrayList<Integer>();
        selected.add(0);
        while(k < n) {
            if(start[k] >= selected.get(selected.size()-1)) {
                selected.add(k);
            }
            k++;
        }
        return selected;
    }

    public static void printArray(ArrayList<Integer> list) {
        for(Integer i : list) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public int nextDenom(int denom) {
        switch (denom) {
            case 100 : return 25; // dollar to quarter
            case 25 : return 10; // quarter to dime
            case 10 : return 5; // dime to nickel
            case 5 : return 1; // nickel to penny
            case 1 : return 0; // penny to nothing
        }
        return 0;
    }

    // get minimum coins required for change
    // doesnt work for certain results. if demoninations are 9,6,5,1 and change is 11, output will be 3 instead of 2
    // for this see DP solution
    public int count(int denom, int change) {
        if(change == 0) return 0;
        if(denom == 0) return 0;
        int nextDenom = nextDenom(denom);
        return change/denom + count(nextDenom, change - (change/denom)*denom);
    }

    public static void main(String[] args) {
        Greedy greedy = new Greedy();
        /*int[] start = {1,3,0,5,8,5};
        int[] end = {2,4,6,7,9,9};
        ArrayList<Integer> selected = greedy.activitySelectionProblem(start, end, start.length);
        printArray(selected);*/

        System.out.println(greedy.count(100, 141));
    }
}
