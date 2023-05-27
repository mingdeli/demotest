package cn.itcast;

import java.util.PriorityQueue;

public class Test {
    public static void main(String[] args) {

        Test test = new Test();
        String s = "1(234)567-890";
        test.maskPII(s);
        //[  1->4->5,
        //  1->3->4,
        //  2->6 ]
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(4);
        ListNode l3 = new ListNode(5);
        l1.next = l2;
        l2.next = l3;
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(3);
        ListNode n3 = new ListNode(4);
        n1.next = n2;
        n2.next = n3;
        ListNode t1 = new ListNode(2);
        ListNode t2 = new ListNode(6);
        t1.next = t2;
        ListNode[] lls = new ListNode[3];
        lls[0] = l1;
        lls[1] = n1;
        lls[2] = t1;
        ListNode listNode = test.mergeKLists(lls);
        System.out.println(listNode);

        //到达的最远建筑
        int[] heights = new int[]{4, 2, 7, 6, 9, 14, 12}; //bricks = 5, ladders = 1
        int i = test.furthestBuilding(heights, 5, 1);
        System.out.println(i);
    }
    String[] country = {"", "+*-", "+**-", "+***-"};

    public String maskPII(String s) {
        int at = s.indexOf("@");
        if (at > 0) {
            s = s.toLowerCase();
            return (s.charAt(0) + "*****" + s.substring(at - 1)).toLowerCase();
        }
        s = s.replaceAll("[^0-9]", "");
        return country[s.length() - 10] + "***-***-" + s.substring(s.length() - 4);
    }

////[  1->4->5,
//       1->3->4,
//        //  2->6 ]
    //链表，只看当前节点；如1->4->5 在数组里面只看1；处理完1后，把4放回数组去，直到为null；
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        ListNode listNode = new ListNode(0);
        ListNode p = listNode;
        PriorityQueue<ListNode> queue = new PriorityQueue<ListNode>(lists.length, (o1, o2) -> {
            if (o1.val < o2.val) return -1;
            else if (o1.val == o2.val) return 0;
            else return 1;
        });
        for (ListNode node : lists) {
            if (node != null) {
                queue.add(node);
            }
        }
        while (!queue.isEmpty()) {
            p.next = queue.poll(); //取出来的都是最小的值；接在listNode后面；如 1->4->5的 1；
            p = p.next; // 将p指向 1；
            if (p.next != null) // 1的后面不为空，即：4，重新放回队列。
                queue.add(p.next);
        }
        return listNode.next;

    }

    //贪心算法：+ 优选队列 ：实现：LeetCode 5556. 可以到达的最远建筑： 有一堆楼房，有梯子 和砖头
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int n = heights.length;
        for (int i = 0; i < n - 1; i++) {
            int a = heights[i + 1] - heights[i];
            if (a > 0) {
                pq.offer(a);
                if (pq.size() > ladders) {
                    bricks -= pq.poll();
                }
                if (bricks < 0) {
                    return i;
                }
            }
        }
        return n - 1;
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public String toString() {
        return   val + " --> " + next ;
    }
}