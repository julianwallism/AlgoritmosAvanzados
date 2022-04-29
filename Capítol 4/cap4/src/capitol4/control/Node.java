package capitol4.control;

import java.io.Serializable;

public class Node implements Comparable<Node>, Serializable {

    private Node left;
    private Node right;
    private byte b;
    private int freq;

    public Node(Node left, Node right) {
        this.left = left;
        this.right = right;
        this.freq = left.freq + right.freq;
    }

    public Node(byte b, int freq) {
        this.b = b;
        this.freq = freq;
    }

    public Node getLeft() {
        return this.left;
    }

    public Node getRight() {
        return this.right;
    }

    public byte getB() {
        return this.b;
    }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(this.freq, o.freq);
    }
}
