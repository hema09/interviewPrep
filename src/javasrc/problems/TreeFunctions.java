package javasrc.problems;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Hema on 3/4/2016.
 */
public class TreeFunctions {
    class TreeNode<T> {
        T val;
        TreeNode left;
        TreeNode right;
        TreeNode() {
            val = null;
            left = null;
            right = null;
        }
        TreeNode(T v) {
            val = v;
            left = null;
            right = null;
        }
    }
    public static boolean isBalanced(TreeNode root) {
        if(root == null) return true;
        return maxDepth(root)-minDepth(root) <= 1;
    }

    public static int maxDepth(TreeNode root) {
        if(root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    public static int minDepth(TreeNode root) {
        if(root == null) return 0;
        return 1 + Math.min(minDepth(root.left), minDepth(root.right));
    }

    public TreeNode createBinary(int[] arr, int start, int end) {
        if(arr == null || start > end)
               return null;
        int mid = start + (end-start)/2;
        int midElem = arr[mid];
        TreeNode root = new TreeNode(midElem);
        root.left = createBinary(arr, start, mid - 1);
        root.right = createBinary(arr, mid+1, end);
        return root;
    }

    public void inOrder(TreeNode n) {
        if(n == null)
            return;
        inOrder(n.left);
        System.out.print(n.val + " ");
        inOrder(n.right);
    }

    public boolean exist(TreeNode<Integer> n, TreeNode<Integer> root) {
        if(root == null) return false;
        if(root.val == n.val) return true;
        return exist(n, root.left) || exist(n, root.right);
    }

    public boolean exist(TreeNode<Integer> root, int val) {
        if(root == null) return false;
        if(root.val == val) return true;
        return exist(root.left, val) || exist(root.right, val);
    }

    public void inOrderIterative(TreeNode root) {
        TreeNode temp = root;
        while(temp != null) {
            if(temp.left == null) {
                System.out.print(temp.val + " ");
                temp = temp.right;
            } else {
                TreeNode extR = temp.left;
                while(extR.right != null && extR.right != temp) {
                    extR = extR.right;
                }
                // either right of extR is temp, or null
                if(extR.right == null) {
                    extR.right = temp;
                    temp = temp.left;
                } else {
                    System.out.print(temp.val + " ");
                    extR.right = null;
                    temp = temp.right;
                }
            }
        }
    }

    public void printTree(TreeNode root) {
        inOrder(root);
    }

    // O(mn) in worst case, O(n) solution is to return true if preOrder(small) is substring of preOrder(big),
    // AND inOrder(small) is substring of inOrder(big)
    public boolean treeIsSubTree(TreeNode big, TreeNode sub) {
        if(big == null) return false;
        if(sub == null) return true;
        TreeNode start = getStartNode(big, sub);
        if(start == null) return false;
        if(matches(start, sub)) return true;
        return treeIsSubTree(big.left, sub) || treeIsSubTree(big.right, sub);
    }

    TreeNode getStartNode(TreeNode<Integer> T1, TreeNode<Integer> T2) {
        if(T1 == null || T2 == null) return null;
        if(T1.val == T2.val) return T1;
        if(T2.val < T1.val)
            return getStartNode(T1.left, T2);
        return getStartNode(T1.right, T2);
    }

    public boolean matches(TreeNode<Integer> N1, TreeNode<Integer> N2) {
        if(N1 == null && N2 == null) return true;
        if(N1 == null || N2 == null) return false;
        if(N1.val == N2.val)
            return matches(N1.left, N2.left) && matches(N1.right, N2.right);
        return false;
    }

    // common ancestor of nodes in BST
    public TreeNode getCommonAncestorMain(TreeNode<Integer> n1, TreeNode<Integer> n2, TreeNode<Integer> n) {
        if(!exist(n1, n) || !exist(n2, n))
            return null;
        return getCommonAncestor(n1, n2, n);
    }
    // O(log n)
    public TreeNode getCommonAncestor(TreeNode<Integer> n1, TreeNode<Integer> n2, TreeNode<Integer> n) {
        if(n == null || n1 == null || n2 == null)
            return null;
        if(n1.val <= n.val && n2.val >= n.val)
            return n;
        if(n1.val < n.val && n2.val < n.val)
            return getCommonAncestor(n1, n2, n.left);
        return getCommonAncestor(n1, n2, n.right);
    }

    // return true if path exists from node n with sum = sum
    // O(log n)
    public boolean pathExists(TreeNode<Integer> n, int sum) {
        if(n == null)
            return sum==0;
        return pathExists(n.left, sum-n.val) || pathExists(n.right, sum-n.val);
    }

    public void getSubArray(ArrayList<Integer> path, int i) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int j = i; j< path.size(); j++) {
            System.out.print(path.get(j) + " ");
        }
        System.out.println();
    }

    // O(nlogn)
    public void printPathIfExists(TreeNode<Integer> n, int sum, ArrayList<Integer> pathTillNow) {
        if(n == null)
            return;
        pathTillNow.add(n.val);
        int temp = sum;
        for(int i=pathTillNow.size()-1;i>=0;i--) {
            temp = temp-pathTillNow.get(i);
            if(temp == 0)
                getSubArray(pathTillNow, i);
        }
        ArrayList<Integer> tempArr = (ArrayList<Integer>)pathTillNow.clone();
        ArrayList<Integer> tempArr2 = (ArrayList<Integer>)pathTillNow.clone();
        printPathIfExists(n.left, sum, tempArr);
        printPathIfExists(n.right, sum, tempArr2);
    }

    // O(log n)
    public TreeNode getNode(Integer val, TreeNode<Integer> root) {
        if(root == null)
            return null;
        if(root.val == val)
            return root;
        if(val < root.val)
            return getNode(val, root.left);
        return getNode(val, root.right);
    }

    public TreeNode getSampleTree() {
        TreeNode root = new TreeNode(20);
        TreeNode n;
        root.left = new TreeNode(10);
        root.right = new TreeNode(30);
        TreeNode tempLeft = root.left;
        TreeNode tempRight = root.right;
        tempLeft.left = new TreeNode(5);
        tempLeft.right = new TreeNode(15);
        tempLeft.left.left = new TreeNode(2);
        tempRight.left = new TreeNode(25);
        tempRight.right = new TreeNode(35);
        return root;

    }

    /*public TreeNode inOrderSuccessor(TreeNode n) {
        if(n == null)
            return null;
        TreeNode temp = n;
        if(temp.right != null) {
            temp = temp.right;
            while(temp.left != null)
                temp = temp.left;
            return temp;
        } else {
            TreeNode p = temp.parent;
            while(p != null) {
                if(p.left == temp)
                    return p;
                p = p.parent;
            }
        }
        return null;
    }*/

    public void printLevelWise(TreeNode root) {
        if(root==null)
            return;
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        TreeNode n;
        while(!queue.isEmpty()) {
            int size = queue.size();
            while(size>0) {
                n = queue.remove();
                System.out.print(n.val + " ");
                if(n.left != null)
                    queue.add(n.left);
                if(n.right != null)
                    queue.add(n.right);
                size--;
            }
            System.out.println();
        }
    }

    public ArrayList<LinkedList<TreeNode>> levelWiseLists(TreeNode root) {
        if(root == null)
            return null;
        ArrayList<LinkedList<TreeNode>> lists = new   ArrayList<LinkedList<TreeNode>>();

        Queue<TreeNode> que = new LinkedList<TreeNode>();
        que.add(root);
        while(!que.isEmpty()) {
            LinkedList<TreeNode> list = new LinkedList<TreeNode>();
            int size = que.size();
            while(size > 0) {
                TreeNode t = que.remove();
                list.add(t);
                size--;
                if(t.left != null) que.add(t.left);
                if(t.right != null) que.add(t.right);
                //Optional.valueOf(t.left).ifPresent(que::add);
                //Optional.valueOf(t.right).ifPresent(que::add);
            }
            lists.add(list);
        }
        return lists;
    }

    // O(n)
    public boolean isBinaryTreeSearchTree(TreeNode root) {
        return isBSTUtil(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public boolean isBSTUtil(TreeNode<Integer> n, Integer min, Integer max) {
        if (n == null) return true;
        if(n.val < min || n.val > max) return false;
        return isBSTUtil(n.left, min, n.val) && isBSTUtil(n.right, n.val, max);
    }

    // iterative O(n) for time, O(1) for space, and O(n) if stack size considered
    public boolean isBinaryTreeSearchTree_Iterative(TreeNode n) {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        ArrayList<Integer> inOrder = inOrder(n, arr);
        return isSorted(inOrder);
    }

    public ArrayList<Integer> inOrder(TreeNode<Integer> n, ArrayList<Integer> arr) {
        if(n == null) return arr;
        inOrder(n.left, arr);
        arr.add(n.val);
        inOrder(n.right, arr);
        return arr;
    }

    public boolean isSorted(ArrayList<Integer> arr) {
        for(int i=0; i<arr.size()-1;i++) {
            if(arr.get(i) > arr.get(i+1))
                return false;
        }
        return true;
    }

    // LCA in BT - 2, if parent node given
    /*public Node lca(Node n1, Node n2, Node root){
        if(root == null || n1 == null || n2 == null) return null;
        ArrayList<Node> pathN1 = pathToRoot(n1);
        ArrayList<Node> pathN2 = pathToRoot(n2);
        int min = Math.min(pathN1.size(), pathN2.size());
        int i = 0;
        for( i=0;i<min;i++) {
            while(pathN1.get(i).val == pathN2.get(i).val)
                continue;
        }
        if(i == pathN1.size())
            return pathN2.get(i).parent;
        if(i == pathN2.size())
            return pathN1.get(i).parent;
        return pathN1.get(i).parent //or N2 parent, same thing
    }*/


    public void deleteTreeNode(TreeNode<Integer> n) {
        if(n == null) return;
        if(n.left == null && n.right == null) {
            n = null;
            return;
        }
        if(n.left == null) {
            n = n.right; return;
        }
        if(n.right == null) {
            n = n.left; return;
        }
        TreeNode max = getMaxChild(n);
        swapData(max, n);
        deleteTreeNode(max);
    }
    public void swapData(TreeNode<Integer> max, TreeNode<Integer> n) {
        int temp = max.val;
        max.val = n.val;
        n.val = temp;
    }
    public TreeNode getMaxChild(TreeNode n) {
        TreeNode ch = n.left;
        while(ch.right != null)
            ch = ch.right;
        return ch;
    }

    // uncomment and write lca and reverse functions to compile
    // path between 2 nodes in BST
    /*public ArrayList<TreeNode> pathBetweenTreeNodes(TreeNode a, TreeNode b, TreeNode root) {
        TreeNode lca = lca(a, b, root);
        ArrayList<TreeNode> path1 = new ArrayList<>();
        pathFrom(lca, a, path1);
        ArrayList<TreeNode> path2 = new ArrayList<>();
        pathFrom(lca, a, path2);
        return reverse(path1).remove();
        path1.addAll(path2);
        return path1;
    }
    public void pathFrom(TreeNode from, TreeNode to, ArrayList<TreeNode> path) {
        if(from == null || to == null)
            return;
        path.add(from);
        if(to.val < from.val)
            pathFrom(from.left, to, path);
        else
            pathFrom(from.right, to, path);
    }*/

    // LCA - O(n) method
    public TreeNode lca(TreeNode<Integer> root, TreeNode<Integer> p,TreeNode<Integer> q) {
        if(root == null)
            return null;
        if(root == p || root == q)
            return root;
        TreeNode<Integer> leftLca = lca(root.left, p, q);
        TreeNode<Integer> rightlca = lca(root.right, p, q);
        if(rightlca != null && leftLca != null)
            return root;
        return leftLca != null ? lca(leftLca, p, q) : lca(rightlca, p, q);
    }

    // given array of integers and number called sum, check if 2 integers add upto sum (other way to use hashmap - see Hash class)
    public boolean hasSum(ArrayList<Integer> integers, int sum) {
        TreeNode<Integer> root = null;
        for(Integer i : integers) {
            boolean found = searchOrAdd(sum-i, root);
            if(found)
                return true;
        }
        return false;
    }
    public boolean searchOrAdd(Integer i, TreeNode<Integer> root) {
        if(root == null) {
            root = new TreeNode(i); return false;
        }
        if(root.val == i)
            return true;
        if(i < root.val) {
            if(root.left == null){
                root.left = new TreeNode(i);
                return false;
            } else
                return searchOrAdd(i, root.left);
        } else {
            if(root.right == null){
                root.right = new TreeNode(i);
                return false;
            } else
                return searchOrAdd(i, root.right);
        }
    }

    public TreeNode BST_to_Vine(TreeNode node) {
        TreeNode p = node;
        TreeNode q = node;
        TreeNode root = null;
        while(p != null) {
            if(p.right == null) {
                if(root == null) {
                    root = new TreeNode(q.val); // root will be set only once when
                                                // first time right node is absent and we move down
                }
                q = p;
                p = p.left;
            } else {
                TreeNode temp = p.right; // left rotate the left sub tree
                q.left = temp;
                p.right = temp.left;
                temp.left = p;
                p = temp;
            }
        }
        return root;
    }

    public TreeNode Vine_To_BST(TreeNode node) {
        return null;
    }

    // balance a BST
    // explanation : http://adtinfo.org/libavl.html/Balancing-a-BST.html
    public TreeNode<Integer> balanceBST(TreeNode<Integer> root) {
        root = BST_to_Vine(root);
        TreeNode node2 = Vine_To_BST(root);
        return node2;
    }

    public String serializeBinaryTree(TreeNode n, String data, String MARKER) {
        if(n == null) {
            data += " " + MARKER + " ";
            return data;
        }
        data += " " + n.val + " ";
        data = serializeBinaryTree(n.left, data, MARKER);
        data = serializeBinaryTree(n.right, data, MARKER);
        return data;
    }
    public TreeNode deserialize(String data, String MARKER) {
        String[] vals = data.split(" ");
        return deserializeUtil(vals, MARKER, vals.length-1, 0);
    }
    public TreeNode deserializeUtil(String[] data, String MARKER, int last, int index) {
        if(data == null || index > last || data[index] == MARKER) {
            return null;
        }
        TreeNode node = new TreeNode(data[index]);
        node.left = deserializeUtil(data, MARKER, last, index++);
        node.right = deserializeUtil(data, MARKER, last, index++);
        return node;
    }

    public static void main(String[] args) {
        TreeFunctions functions = new TreeFunctions();
        TreeNode root = functions.getSampleTree();
       // System.out.println(isBalanced(root));
        //functions.printTree(root);
        /*int[] arr = {1,2,3,4,5,6,7,8,9,10};
        TreeNode root2 = functions.createBinary(arr, 0, 9);
        functions.printTree(root2);*/
        functions.inOrder(root);
        System.out.println();
        functions.printLevelWise(root);
        //functions.levelWiseLists(root);
        System.out.println();
        //System.out.println("Is BT BST = " + functions.isBinaryTreeSearchTree_Iterative(root));
        System.out.println("path exists = " + functions.pathExists(root, 32));
        functions.printPathIfExists(root, 15, new ArrayList<Integer>());
        
        //functions.inOrderIterative(root);
        //System.out.println(functions.getCommonAncestor(root.left.left, root.right, root).val);
        //System.out.println(functions.getNode(30, root).val);
    }


}
