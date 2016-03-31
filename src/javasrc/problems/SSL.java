package javasrc.problems;

import java.util.HashMap;

/**
 * Created by Hema on 3/2/2016.
 */
public class SSL {
    class LinkedListNode {
        Integer val;
        LinkedListNode next;
    }
    LinkedListNode first;
    LinkedListNode last;

    public LinkedListNode getNode(int val) {
        LinkedListNode node = new LinkedListNode();
        node.val = val;
        node.next = null;
        return node;
    }
    public LinkedListNode addNode(Integer data) {
        if(first == null) {
            first = getNode(data);
            last = first;
            return first;
        }
        last.next = getNode(data);
        last = last.next;
        return last;
    }

    public static void removeDups(LinkedListNode n) {
        if(n == null || n.next == null)
            return;
        LinkedListNode previous = n;
        HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
        map.put(n.val, true);
        n = n.next;
        while(n != null) {
            if(map.containsKey(n.val)) {
                previous.next = n.next;
            }
            else
                map.put(n.val, true);
            previous = n;
            n = n.next;

        }
    }

    public static void removeDups2(LinkedListNode n) {
        if(n == null || n.next == null)
            return;
        LinkedListNode previous = n;
        LinkedListNode current = n.next;
        LinkedListNode runner;
        while(current != null) {
            runner = n;
            while(runner != null || runner != current) {
                if(runner.val == current.val) {
                    previous.next = current.next;
                    break;
                }
                runner = runner.next;
            }
            previous = previous.next;
            current = previous.next;
        }
    }

    public static LinkedListNode nthToLast(LinkedListNode head, int n) {
        if(head == null || n < 1)
            return null;
        LinkedListNode p1 = head;
        LinkedListNode p2 = head;
        int i = 0;
        while(i < n) {
            if(p2 == null)
                return null;
            p2 = p2.next;
            i++;
        }
        while(p2 != null) {
            p2 = p2.next;
            p1 = p1.next;
        }
        return p1;
    }

    public static void printSSL(LinkedListNode n) {
        while(n != null) {
            System.out.print(n.val + " ");
            n = n.next;
        }
        System.out.println();
    }

    public LinkedListNode getSum(LinkedListNode head1, LinkedListNode head2) {
        if(head1 == null && head2 == null)
            return null;
        if(head2 == null) return head1;
        if(head1 == null) return head2;
        LinkedListNode p3 = new LinkedListNode();
        LinkedListNode head3 = p3;
        int carry = 0;
        while(head1 != null || head2 != null || carry != 0) {
            int val = (head1 != null ? head1.val : 0) + (head2 != null ? head2.val : 0) + carry;
            LinkedListNode n = new LinkedListNode();
            n.val = val%10;
            carry = val/10;
            head1 = head1 != null ? head1.next : null;
            head2 = head2 != null ? head2.next : null;
            p3.next = n;
            p3 = p3.next;
        }
        return head3;
    }

    public static LinkedListNode getCircularStart(LinkedListNode head) {
        LinkedListNode p1 = head;
        LinkedListNode p2 = head;
        while(p2.next != null) {
            p1 = p1.next;
            p2 = p2.next.next;
            if(p1 == p2)
                break;
        }
        if(p2 == null || p2.next == null)
            return null;
        p1 = head;
        while(p1 != p2) {
            p1 = p1.next;
            p2 = p2.next;
        }
        return p1; // or p2
    }

    public static LinkedListNode getMiddle(LinkedListNode head) {
        if(head == null || head.next == null) return head;
        LinkedListNode p = head;
        LinkedListNode q = head.next;
        while(q != null && q.next != null) {
            p = p.next;
            q = q.next.next;
        }
        return p;
    }

    public LinkedListNode sort(LinkedListNode h1) {
        if(h1 == null || h1.next == null)
            return h1;
        LinkedListNode mid = getMiddle(h1);
        LinkedListNode rhalf = mid.next;
        mid.next = null;
        LinkedListNode lhalf = h1;
        return merge(sort(lhalf), sort(rhalf));
    }

    public LinkedListNode merge(LinkedListNode h1, LinkedListNode h2) {
        if(h1 == null) return h2;
        if(h2 == null) return h1;
        LinkedListNode head = new LinkedListNode();
        LinkedListNode n = head;
        LinkedListNode p;
        while(h1 != null && h2 != null) {
            p = new LinkedListNode();
            if(h1.val <= h2.val) {
                p.val = h1.val; h1 = h1.next;
            } else {
                p.val = h2.val; h2 = h2.next;
            }
            n.next = p;
            n = n.next;
        }
        n.next = h1 == null ? h2 : h1;
        return head.next;
    }



    public LinkedListNode addLists(LinkedListNode n1, LinkedListNode n2, int carry) {
        if(n1 == null && n2 == null && carry == 0)
            return null;
        LinkedListNode n3 = new LinkedListNode();
        int sum = (n1 != null ? n1.val : 0) + (n2 != null ? n2.val : 0) + carry;
        n3.val = sum%10;
        carry = sum/10;
        n3.next = addLists(n1 != null ? n1.next : null,
                n2 != null ? n2.next : null,
                carry);
        return n3;
    }


    public LinkedListNode reverse(LinkedListNode head) {
        if(head == null || head.next == null)
            return head;
        LinkedListNode head2 = null;
        LinkedListNode n = head;
        while(n != null) {
            head2 = addInFront(head2, n.val);
            n = n.next;
        }
        return head2;
    }
    public LinkedListNode addInFront(LinkedListNode p, int val) {
        LinkedListNode n = getNode(val);
        if(p == null)
            return n;
        n.next = p;
        return n;
    }

    public static LinkedListNode reverseInPlace(LinkedListNode head) {
        LinkedListNode p = null;
        LinkedListNode c = head;
        LinkedListNode n = c.next;
        while(c != null) {
            c.next = p;
            p = c;
            c = n;
            if(n != null)
                n = n.next;
        }
        return p;
    }

    public static int length(LinkedListNode head) {
        int len = 0;
        LinkedListNode t = head;
        while(t != null) {
            len++;
            t = t.next;
        }
        return len;
    }

    public static LinkedListNode rotateByK(LinkedListNode head, int k) {
        int len = length(head);
        LinkedListNode s = head;
        LinkedListNode t = head;
        int i = 0;
        while(i < k) {
            i++;
            t = t.next;
        }
        while(t != null && t.next != null) {
            s = s.next;
            t = t.next;
        }
        LinkedListNode hh = s.next;
        t.next = head;
        s.next = null;
        return hh;
    }

    public static void main(String[] args) {

        SSL ssl = new SSL();
        ssl.addNode(2);
        ssl.addNode(6);
        ssl.addNode(4);
        ssl.addNode(2);
        ssl.addNode(4);
        printSSL(ssl.first);
        //printSSL(ssl.sort(ssl.first));
        //printSSL(ssl.reverseInPlace(ssl.first));
        printSSL(ssl.rotateByK(ssl.first, 4));
        SSL ssl2 = new SSL();
        ssl2.addNode(2);
        ssl2.addNode(4);
        LinkedListNode circularStartNode = ssl2.addNode(3);
        ssl2.addNode(6);

        //ssl2.addNode(7).next = circularStartNode;
        //printSSL(ssl2.first);
        //printSSL(ssl.getSum(ssl.first, ssl2.first));
        //LinkedListNode circularStart = getCircularStart(ssl2.first);
        //System.out.println(circularStart.val);
        //removeDups2(ssl.first);
        //printSSL(ssl.first);
        //System.out.println(nthToLast(ssl.first, 2).val);

    }
}
