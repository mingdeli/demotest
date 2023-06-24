package cn.structure;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Huffman{


}

/**
 * 哈夫曼树
 */
class HuLinked{
    public static void main(String[] args) {
        Integer[] b = new Integer[]{2, 3, 5, 1, 4, 8};
        List<Integer> integers = Arrays.asList(b);
        Collections.sort(integers);
        System.out.println(integers);
    }
}

class HuNode implements Comparable<HuNode> {
    private int value;
    private HuNode left;
    private HuNode right;

    public HuNode(int value) {
        this.value = value;
    }

    @Override
    public int compareTo(HuNode o) {
        //从小到大
        return this.value - o.value;
    }
}
