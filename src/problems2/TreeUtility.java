package problems2;

import apple.laf.JRSUIUtils;
import javafx.beans.binding.When;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class TreeUtility {

    // runtime O(N)
    public static ArrayList<LinkedList<TreeNode>> levelwiseLinkedList(TreeNode root) {
        ArrayList<LinkedList<TreeNode>> lists = new ArrayList<LinkedList<TreeNode>>();
        LinkedList<TreeNode> q = new LinkedList<TreeNode>();
        q.add(root);
        int length;
        while(!q.isEmpty()) {
            length = q.size();
            LinkedList<TreeNode> linkedList = new LinkedList<TreeNode>();
            while(length > 0) {
                TreeNode node = q.poll();
                linkedList.add(node);
                if(node.left != null) q.add(node.left);
                if(node.right != null) q.add(node.right);
                length--;
            }
            lists.add(linkedList);
        }
        return lists;
    }

    public static Integer checkHeight(TreeNode root) {
        if(root == null) return -1;

        int leftHeight = checkHeight(root.left);
        if(leftHeight == Integer.MIN_VALUE) return Integer.MIN_VALUE;

        int rightHeight = checkHeight(root.right);
        if(rightHeight == Integer.MIN_VALUE) return Integer.MIN_VALUE;

        if(Math.abs(leftHeight - rightHeight) > 1) return Integer.MIN_VALUE;

        return Math.max(leftHeight, rightHeight) + 1;

    }

    // O(N) time, O(H) space
    public static boolean isBalanced(TreeNode root) {
        return checkHeight(root) != Integer.MIN_VALUE;
    }

    public boolean isBST(TreeNode root) {
        return isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static boolean isBST(TreeNode root, int min, int max) {
        if(root == null)
            return true;
        if(root.val < min || root.val > max)
            return false;
        if(root.left != null && root.left.val > root.val)
            return false;
        if(root.right != null && root.right.val < root.val)
            return false;
        return isBST(root.left, min, root.val) && isBST(root.right, root.val, max);
    }

    public TreeNode inorderSuccessor(TreeNode n) {
        if(n == null)
            return null;
        // if n has right subtree, return leftmost child of right sub tree
        if(n.right != null) {
            TreeNode x = n.right;
            while(x.left != null) {
                x = x.left;
            }
            return x;
        }
        else
        {
            // while n is right child if parent, n = n.parent
            // return parent
            TreeNode p = n.parent;
            TreeNode q = n;
            while(p != null && p.left != q) {
                q = p;
                p = p.parent;
            }
            return p;
        }
    }

    public static void BSTSequence(TreeNode root) {
        ArrayList<TreeNode> arr = new ArrayList<TreeNode>();
        arr.add(root);
        bstSequenceHelper(arr, root);
    }

    public static void bstSequenceHelper(ArrayList<TreeNode> arr, TreeNode node) {
        printArr(arr);
        if(node == null) return;
        if(node.left != null) {
            ArrayList<TreeNode> leftArr = (ArrayList<TreeNode>)arr.clone();
            leftArr.add(node.left);
            bstSequenceHelper(leftArr, node.left);
        }
        if(node.right != null) {
            ArrayList<TreeNode> rightArr = (ArrayList<TreeNode>)arr.clone();
            rightArr.add(node.right);
            bstSequenceHelper(rightArr, node.right);
        }
    }

    public static void printArr(ArrayList<TreeNode> arr) {
        for(TreeNode n : arr)
            System.out.print(n.val + " ");
        System.out.println();
    }

    public static TreeNode createSampleBT() {
        TreeNode root = new TreeNode(5);
        TreeNode n1 = new TreeNode(1);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(3);
        TreeNode n4 = new TreeNode(4);
        TreeNode n6 = new TreeNode(6);
        TreeNode n7 = new TreeNode(7);
        TreeNode n8 = new TreeNode(8);
        TreeNode n9 = new TreeNode(9);
        TreeNode n10 = new TreeNode(10);
        root.left = n1;
        root.right = n2;
        n1.left = n3;
        n1.right = n4;
        n2.left = n6;
        n2.right = n7;
        n3.left = n8;
        n4.left = n9;
        n9.left = n10;
        return root;
    }

    public static void printLists(ArrayList<LinkedList<TreeNode>> lists) {
        for(LinkedList<TreeNode> list : lists) {
            System.out.println();
            for(TreeNode node : list) {
                System.out.print(node.val + " ");
            }
        }
    }

    // time O(n+m), space O(n+m)
    public static boolean containsTree(TreeNode t1, TreeNode t2) {
        if (t2 == null)
            return true;
        if(t1 == null)
            return false;
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();
        getPreOrderTraversal(t1, s1);
        getPreOrderTraversal(t2, s2);
        return s1.toString().contains(s2.toString());
    }

    public static void getPreOrderTraversal(TreeNode t, StringBuilder b) {
        if(t == null) {
            b.append('X');
            return;
        }
        b.append(t.val + " ");
        getPreOrderTraversal(t.left, b);
        getPreOrderTraversal(t.right,b);
    }

    // O(mn) time worst case, O(log(m) + log(n)) space
    public static boolean containsTree2(TreeNode t1, TreeNode t2) {
        if(t2 == null) return true;
        return subTree(t1, t2);
    }

    // go to match tree only if t2 root is found in t1
    public static boolean subTree(TreeNode t1, TreeNode t2) {
        if(t1 == null)
            return false;
        if(t1.val == t2.val && matchTree(t1, t2))
            return true;
        return subTree(t1.left, t2) || subTree(t1.right, t2);
    }

    public static boolean matchTree(TreeNode t1, TreeNode t2) {
        if(t1 == null && t2 == null)
            return true;
        if(t1 == null || t2 == null)
            return false;
        if(t1.val != t2.val)
            return false;
        return matchTree(t1.left, t2.left) && matchTree(t1.right, t2.right);
    }

    // O(log(n))
    public TreeNode getNthNodeOfInOrderTraversal(TreeNode t, int n) {
        int index = 0;
        return doInorderTraversal(t,index, n);
    }

    public TreeNode doInorderTraversal(TreeNode t, int index, int n) {
        if(t.left != null)
            doInorderTraversal(t.left, index, n);
        if(index == n)
            return t;
        index++;
        if(t.right != null)
            return doInorderTraversal(t.right, index, n);
        return null;
    }

    public boolean pathExists(TreeNode n, int sum) {
        if(n == null)
            return sum==0;
        return pathExists(n.left, sum-n.val) || pathExists(n.right, sum-n.val);
    }

    int countPathsWithSum(TreeNode root, int targetsum) {
        return countPathsWithSum(root, targetsum, 0, new HashMap<Integer, Integer>());
    }

    int countPathsWithSum(TreeNode node, int targetSum, int runningSum, HashMap<Integer, Integer> map) {
        if(node == null)
            return 0;
        runningSum += node.val;
        int sum = runningSum - targetSum;
        int count = map.getOrDefault(sum, 0);
        if(targetSum == sum)
            count++;
        updateMap(map, runningSum, 1);
        count += countPathsWithSum(node.left, targetSum, runningSum, map);
        count += countPathsWithSum(node.right, targetSum, runningSum, map);
        updateMap(map, runningSum, -1);
        return count;
    }

    void updateMap(HashMap<Integer, Integer> map, int runningSum, int delta) {
        int count = map.getOrDefault(runningSum, 0) + delta;
        if(count == 0)
            map.remove(count);
        map.put(runningSum, count);
    }

    public static void main(String[] args) {
        TreeNode root = createSampleBT();

        /*ArrayList<LinkedList<TreeNode>> lists = levelwiseLinkedList(root);
        printLists(lists);
        System.out.println();
        System.out.println();
        BSTSequence(root);*/
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode parent;
    int size;

    TreeNode root = null;

    public TreeNode(int v) {
        val = v;
        left = null;
        right = null;
        parent = null;
        size = 1;
    }

    public TreeNode getRandomNode() {
        Random random = new Random();
        int i = random.nextInt(size);
        return root.getIthNode(i);
    }

    public TreeNode getIthNode(int i) {
        int leftSize = left == null ? 0 : left.size;
        if(i < leftSize)
            return left.getIthNode(i);
        if(size == leftSize)
            return this;
        else
            return right.getIthNode(i - (leftSize + 1));
    }
}
