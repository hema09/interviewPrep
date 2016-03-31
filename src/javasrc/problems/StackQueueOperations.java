package javasrc.problems;

import java.util.Stack;

/**
 * Created by Hema on 3/3/2016.
 */
public class StackQueueOperations {
    class StackWithMin extends Stack<NodeWithMin> {
        public void pushNew(int val) {
            int min = Math.min(peek().min, val);
            NodeWithMin n = new NodeWithMin(val, min);
            push(n);
        }
        public NodeWithMin pop() {
            NodeWithMin node = pop();
            return node;
        }
        public int getMin() {
            return peek().min;
        }
    }

    class StackWithMin2 extends Stack<Integer> {
        Stack<Integer> minStack;
        public void pushNew(int val) {
            if(val <= minStack.peek()) // multiple occurrences on min element should be stored so that we remove only related during pop
                minStack.push(val);
            push(val);
        }
        public Integer pop2() {
            Integer node = pop();
            if(node == minStack.peek())
                minStack.pop();
            return node;
        }
        public int getMin() {
            return minStack.peek();
        }
    }

    class NodeWithMin {
        int val;
        int min;
        public NodeWithMin(int val, int min) {
            val = val;
            min = min;
        }
    }

    public static void main(String[] args) {


    }
}
