package problems2;

import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class LinkedListUtility {


    static void removeDups(LinkedListNode n) {
        LinkedListNode a = n;
        LinkedListNode b = n.next;
        HashSet<Integer> set = new HashSet<Integer>();
        set.add(a.val);
        while(b != null) {
            if(!set.contains(b.val)) {
                set.add(b.val);
                a=a.next;
                b=b.next;
            } else {
                a.next = b.next;
                b = b.next;
            }
        }
    }

    static void removeDups2(LinkedListNode n) {
        LinkedListNode a;
        LinkedListNode b;
        int val;
        while (n != null) {
            val = n.val;
            a = n;
            b = n.next;
            while(b != null) {
                if(b.val == val) {
                    a.next = b.next;
                    b = b.next;
                } else {
                    a = a.next;
                    b = b.next;
                }
            }
            n = n.next;
        }
    }

    static LinkedListNode createLinkedList() {
        LinkedListNode n1 = new LinkedListNode(1);
        LinkedListNode n2 = new LinkedListNode(5);
        LinkedListNode n3 = new LinkedListNode(6);
        LinkedListNode n4 = new LinkedListNode(2);
        LinkedListNode n5 = new LinkedListNode(3);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        return n1;
    }

    static LinkedListNode createCircularLinkedList() {
        LinkedListNode n1 = new LinkedListNode(1);
        LinkedListNode n2 = new LinkedListNode(2);
        LinkedListNode n3 = new LinkedListNode(3);
        LinkedListNode n4 = new LinkedListNode(4);
        LinkedListNode n5 = new LinkedListNode(5);
        LinkedListNode n6 = new LinkedListNode(6);
        LinkedListNode n7 = new LinkedListNode(7);
        LinkedListNode n8 = new LinkedListNode(8);
        LinkedListNode n9 = new LinkedListNode(9);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = n6;
        n6.next = n7;
        n7.next = n8;
        n8.next = n9;
        n9.next = n4; // this node creates circulation
        return n1;
    }

    static LinkedListNode createLinkedList(int[] nums) {
        LinkedListNode head = new LinkedListNode();
        LinkedListNode p = head;
        for(int i=0; i< nums.length; i++) {
            LinkedListNode n = new LinkedListNode(nums[i]);
            p.next = n;
            p = p.next;
        }

        return head.next;
    }

    static void printLL(LinkedListNode node) {
        System.out.println();
        while(node != null) {
            System.out.print(node.val + " ");
            node = node.next;
        }
        System.out.println();
    }

    static LinkedListNode kthToLast(LinkedListNode n, int k) {
        if(k <= 0) return null;
        LinkedListNode p1 = n;
        LinkedListNode p2 = n;
        int steps = k-1;
        while(steps>0 && p2 != null) {
            p2 = p2.next;
            steps--;
        }
        if(p2 == null) return null;
        while(p2.next != null) {
            p1 = p1.next;
            p2 = p2.next;
        }
        return p1;
    }

    // cannot delete last node
    static void deleteNode(LinkedListNode del) {
        if(del == null) return;
        LinkedListNode curr = del;
        LinkedListNode prev = curr;
        while(curr.next != null) {
            curr.val = curr.next.val;
            prev = curr;
            curr = curr.next;
        }
         prev.next = null;
    }

    static LinkedListNode partitionList(LinkedListNode start, int x) {
        if(start == null || start.next == null)
            return start;
        LinkedListNode beforeStart = null;
        LinkedListNode beforeEnd = null;

        LinkedListNode afterStart = null;
        LinkedListNode afterEnd = null;
        LinkedListNode curr = start;
        while(curr != null) {
            if(curr.val < x) {
                if(beforeStart == null) {
                    beforeStart = new LinkedListNode(curr.val);
                    beforeEnd = beforeStart;
                } else {
                    beforeEnd.next = new LinkedListNode(curr.val);
                    beforeEnd = beforeEnd.next;
                }
            } else {
                if(afterStart == null) {
                    afterStart = new LinkedListNode(curr.val);
                    afterEnd = afterStart;
                } else {
                    afterEnd.next = new LinkedListNode(curr.val);
                    afterEnd = afterEnd.next;
                }
            }
            curr = curr.next;
        }
        beforeEnd.next = afterStart;
        return beforeStart;
    }

    static LinkedListNode getSum(LinkedListNode n1, LinkedListNode n2) {
        if(n1 == null) return n2;
        if(n2 == null) return n1;
        LinkedListNode n3 = new LinkedListNode();
        LinkedListNode head = n3;
        int sum = 0, carry = 0;
        while(n1 != null || n2 != null || carry!= 0) {
            sum = ((n1!=null)? n1.val:0) + ((n2!=null)? n2.val:0) + carry;
            carry = sum/10;
            sum = sum%10;
            n3.next = new LinkedListNode(sum);
            n3 = n3.next;
            if(n1 != null)
                n1 = n1.next;
            if(n2 != null)
                n2 = n2.next;
        }
        return head.next;
    }

    static LinkedListNode addLists(LinkedListNode n1, LinkedListNode n2, int carry) {
        if(n1 == null && n2 == null && carry == 0)
            return null;
        LinkedListNode node = new LinkedListNode();
        int data = (n1 != null? n1.val:0) + (n2 != null? n2.val:0) + carry;
        node.val = data%10;
        carry = data/10;
        node.next = addLists(n1 != null? n1.next:null, n2!= null? n2.next: null, carry);
        return node;
    }

    static boolean isPalindrome(LinkedListNode n1) {
        if(n1 == null) return false;
        if(n1.next == null) return true;
        LinkedListNode reverse = reverseList(n1);
        return isEqual(n1, reverse);
    }

    static LinkedListNode reverseList(LinkedListNode node) {
        if(node == null || node.next == null) return node;
        LinkedListNode head = null;
        while (node != null) {
            LinkedListNode n = new LinkedListNode(node.val);
            n.next = head;
            head = n;
            node = node.next;
        }
        return head;
    }

    static boolean isEqual(LinkedListNode n1, LinkedListNode n2) {
        if(n1 == null && n2 != null || n1 != null && n2 == null)
            return false;
        if(n1 == null && n2 == null)
            return true;
        if(n1.val != n2.val)
            return false;
        return isEqual(n1.next, n2.next);
    }

    static boolean isPalindromeIterative(LinkedListNode n1) {
        if(n1 == null) return false;
        if(n1.next == null) return true;
        Stack<Integer> stack = new Stack<Integer>();
        LinkedListNode node = n1;
        while(node != null) {
            stack.add(node.val);
            node = node.next;
        }
        int j = stack.size() - 1;
        LinkedListNode n = n1;
        while(n != null) {
            int pop = stack.pop();
            if(pop != n.val)
                return false;
            n = n.next;
        }
        return true;
    }

    static LinkedListNode findIntersection(LinkedListNode l1, LinkedListNode l2) {
        if(l1 == null || l2 == null) return null;
        Result result1 = getTailAndSize(l1);
        Result result2 = getTailAndSize(l2);
        System.out.println(result1.length + " " + result1.tail.val);
        System.out.println(result2.length + " " + result2.tail.val);

        if(result1.tail != result2.tail)
            return null;
        int len1 = result1.length;
        int len2 = result2.length;
        int steps = Math.abs(len2-len1);
        LinkedListNode s = len1 < len2 ? l1 : l2;
        LinkedListNode t = len1 < len2 ? l2 : l1;

        // increment longer node by steps
        for(int i=0;i<steps;i++) {
            t=t.next;
        }
        while (s != t && s.next != null){
            s = s.next;
            t = t.next;
        }
        return s;
    }

    static LinkedListNode getCircularListCollisionSpot(LinkedListNode head) {
        if(head == null || head.next == null)
            return null;
        LinkedListNode slow = head;
        LinkedListNode fast = head;
        while(fast != null || fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast)
                break;
        }
        if(fast == null || fast.next == null)
            return null;
        slow = head;
        while(slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return fast;
    }

    static Result getTailAndSize(LinkedListNode list) {
        LinkedListNode n = list;
        int size = 1;
        while(n.next != null) {
            size++;
            n = n.next;
        }
        return new Result(size,n);
    }

    static void printNode(LinkedListNode node) {
        if(node != null)
            System.out.println(node.val);
    }

    public static void main(String[] args) {
        //LinkedListNode start = createLinkedList();
        //printLL(start);
        //removeDups2(start);
        //LinkedListNode kthToLast = kthToLast(start, 1);
        //printNode(kthToLast);
        //LinkedListNode toDelete = kthToLast;
        //deleteNode(toDelete);
        //LinkedListNode newStart = partitionList(start, 3);

        /*LinkedListNode list1 = createLinkedList(new int[]{5,5,5});
        printLL(list1);
        LinkedListNode list2 = createLinkedList(new int[]{5,5,5,5});
        printLL(list2);
        LinkedListNode sum = getSum(list1,list2);
        printLL(sum);*/

        /*LinkedListNode start = createLinkedList(new int[]{1,2,3,2,1});
        System.out.println(isPalindromeIterative(start));*/

        /*LinkedListNode l1 =  createLinkedList(new int[]{1,2,3});
        LinkedListNode l2 =  createLinkedList(new int[]{6,7});
        LinkedListNode l3 =  createLinkedList(new int[]{4,5});
        l2.next = l3;
        l1.next = l3;
        LinkedListNode inter = findIntersection(l1,l2);
        printNode(inter);*/


        LinkedListNode circularList = createCircularLinkedList();
        LinkedListNode circularStart = getCircularListCollisionSpot(circularList);
        printNode(circularStart);

    }

}
class Result {
    int length;
    LinkedListNode tail;
    Result(int len, LinkedListNode t) {
        this.length = len;
        this.tail = t;
    }

}

class LinkedListNode {

    int val;
    LinkedListNode next;

    LinkedListNode(int val) {
        this.val = val;
        this.next = null;
    }

    LinkedListNode() {
        this.val = 0;
        this.next = null;
    }

    LinkedListNode(int val, LinkedListNode n) {
        this.val = val;
        this.next = n;
    }

}
