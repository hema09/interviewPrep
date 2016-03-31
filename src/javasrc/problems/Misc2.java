package javasrc.problems;

/**
 * Created by Hema on 3/17/2016.
 */
public class Misc2 {

    /*Question 1) Given a string, write a routine that converts the string to a long, without using the
    built in functions that would do this. Describe what (if any) limitations the code has. For
    example:*/
    long stringToLong(String s)
    {
        if(s == null || s.isEmpty())
            throw new IllegalArgumentException("Invalid input");
        boolean negative = false;
        if(s.charAt(0)=='-') {
            negative = true;
            s = s.substring(1);
            if(s.length() == 0)
                throw new IllegalArgumentException("Invalid input");
        }
        long val = 0L;
        int length = s.length();
        for(int i=0; i< length; i++) {
            char ch = s.charAt(i);
            if(ch >= '0' && ch <= '9')
                val = val*10 + (int)ch-'0'; // move MSB, add next digit in LSB
            else
                throw new IllegalArgumentException("Invalid input");
        }
        val = negative ? val * -1 : val;
        return val;
    }

    /* problems with this test :
        1. There are no edge test cases
        2. It cannot handle strings that are invalid and cannot be converted
        3. It does no check on negative numbers
        4. It does not check min and max long values
    */
    void test()
    {
        long i = stringToLong("123");
        if (i == 123) {
            // success
            System.out.println("success");
        } else {
        // failure
            System.out.println("failure");
        }
    }


    /*
    * Question 2) Implement insert and delete in a tri-nary tree. A tri-nary tree is much like a binary
    * tree but with three child nodes for each parent instead of two -- with the left node being values
    * less than the parent, the right node values greater than the parent, and the middle nodes values
    * equal to the parent.
    */
    class TrinaryTree{

        Node root;

        class Node {
            int value;
            Node left;
            Node middle;
            Node right;
            // constructor for Node
            Node(int val) {
                value = val;
                left = null;
                middle = null;
                right = null;
            }
        }

        // METHOD 1 : INSERT ON TRI-NARY TREE
        public void insert(int val) {
            if(root == null) {
                root = new Node(val);
                return;
            }
            Node n = root;
            while(n != null) {
                if(n.value == val) {
                    // if values are equal, add new node to middle if middle is null, or move to middle node
                    if(n.middle == null) {
                        n.middle = new Node(val);
                        break;
                    } else
                        n = n.middle;
                } else if(val < n.value) {
                    // if val is less than current node, add new node to left if left is null, or move to left node
                    if(n.left == null) {
                        n.left = new Node(val);
                        break;
                    } else {
                        n = n.left;
                    }
                } else {
                    // remaining case, add new node to right if right is null, or move to right node
                    if(n.right == null) {
                        n.right = new Node(val);
                        break;
                    } else
                        n = n.right;
                }
            }
        }

        // METHOD 2 : DELETE ON TRI-NARY TREE
        // since it is not clear whether we want to delete by Node instance or value, my assumption is value
        public void delete(int val) {
            Node node = find(root, val);
            deleteNode(node);
        }

        // supporting method, returns node with first matching value
        public Node find(Node node, int val) {
            if(node == null)
                return null;
            if(node.value == val)           // value equal
                return node;
            if(val < node.value)            // value less than node.val
                return find(node.left, val);
            return find(node.right, val);   // remaining case : vaue greater than node.val

        }
        // supporting method
        void deleteNode(Node node) {
            if(node == null)
                return;
            // first of all, if there is middle node, we make it null as it will have same value
            if(node.middle != null)
                node.middle = null;
            if(node.left == null && node.right == null) { // all 3 children null
                node = null; return;
            }
            if(node.left == null) {      // left null, point current to right
                node = node.right; return;
            }
            if(node.right == null) {     // right null, point current to left
                node = node.left; return;
            }
            // last case, both left and right are not null
            // swap current node's data with its predecessor node
            // and do recursive delete on that node
            Node nodeBeforeCurrent = findNodeBefore(node);
            swapData(node, nodeBeforeCurrent);
            deleteNode(nodeBeforeCurrent);
        }
        Node findNodeBefore(Node node) {
            Node n = node.left;
            while(n.right != null)
                n = n.right;
            return n;
        }
        void swapData(Node n1, Node n2) {
            int val = n1.value;
            n1.value = n2.value;
            n2.value = val;
        }
    }


    public static void main(String[] args) {
        Misc2 misc2 = new Misc2();
        misc2.test();
    }
}
