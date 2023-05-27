package cn.unable;

import lombok.Data;

public class erchashu {

    public static void main(String[] args) {
        Integer a[] = {5, 4, 8, 6, 3, 9,1,12};

        //构建顺序二叉树
        TwoTree twoTree = new TwoTree();
        for (int i = 0; i < a.length; i++) {
            Node node = new Node(a[i]);
            twoTree.addOrder(node);
        }
        System.out.println(twoTree.height());
        twoTree.midOrder();
        System.out.println("===");
        twoTree.preOrder();
        // 构建完全二叉树
        TwoTree fullTree = new TwoTree();
        fullTree.buildFull(a);
        System.out.println(fullTree.height());
        fullTree.midOrder();
        System.out.println("-------");
        fullTree.preOrder();

    }

}

class TwoTree {

    private Node root;

    /**
     * 构建二叉排序树
     *
     * @param n
     */
    public void addOrder(Node n) {
        if (root == null)
            root = n;
        else
            root.addOrder(n);
    }

    public void preOrder(){
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("~~树为空");
        }
    }
    public void midOrder() {
        if (root != null) {
            root.midOrder();
        } else {
            System.out.println("~树为空~");
        }
    }
    public void postAfter(){
        if(root != null) {
            root.postAfter();
        } else {
            System.out.println("树为空~~");
        }
    }

    public int height() {
        if (root == null) {
            return 0;
        }
        return root.height();
    }

    //构建完全二叉树
    public void buildFull(Integer[] a) {
        root = buildFull(a, 0);
    }

    private Node buildFull(Integer[] a, int i) {
        Node node = null;
        if (i < a.length) {
            node = new Node(a[i]);
            node.left = buildFull(a, 2 * i + 1);
            node.right = buildFull(a, 2 * i + 2);
            return node;
        }
        return node;
    }
}

@Data
class Node {
    int data;
    Node left;
    Node right;

    public Node() {
    }

    public Node(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    public void addOrder(Node node) {
        if (node == null) {
            return;
        }
        if (node.data < this.data) {
            if (this.left == null)
                this.left = node;
            else
                left.addOrder(node);
        } else {
            if (this.right == null)
                this.right = node;
            else
                right.addOrder(node);
        }
    }

    //中序遍历
    public void midOrder() {
        if (this.left != null) {
            this.left.midOrder();
        }
        System.out.println(this);
        if (this.right != null) {
            this.right.midOrder();
        }
    }
    //前序遍历
    public void preOrder(){
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    public void postAfter(){
        if(this.left != null){
            this.left.postAfter();
        }
        if(this.right != null){
            this.right.postAfter();
        }
        System.out.println(this);
    }
    public int height() {
        return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                '}';
    }
}
