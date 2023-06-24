package cn.structure;

import java.util.HashMap;

public class LeeCode {
    public static void main(String[] args) {
//        vl1 =[2,4,3];
//        vl1 =[2,4,3];
//        addTwoNumbers();
        String a = "abcaefghajiklf";
        int i = lengthOfLongestSubstring(a);
        System.out.println(i);
    }


    public static int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) return 0;
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        int max = 0;
        int left = 0;
        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                left = Math.max(left, map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i), i);
            max = Math.max(max, i - left + 1);
        }
        return max;
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode temp1 = l1;
        ListNode temp2 = l2;
        ListNode result = null;
        int ten = 0;
        while (temp1 != null || temp2 != null) {
            ListNode nn = new ListNode();
            if (temp1 != null && temp2 != null) {
                int v = temp1.val + temp2.val + ten;
                if (v > 9) {
                    nn.val = v % 10;
                    ten = 1;
                } else {
                    nn.val = v;
                    ten = 0;
                }
                temp1 = temp1.next;
                temp2 = temp2.next;
            } else if (temp1 == null) {
                nn.val = temp2.val + ten;
                temp2 = temp2.next;
            } else if (temp2 == null) {
                nn.val = temp1.val + ten;
                temp1 = temp1.next;
            }
            if (result == null) {
                result = nn;
            } else {
                result.next = nn;
            }
            result = result.next;

        }
        return result;
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

}
