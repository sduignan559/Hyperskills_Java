package animals;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Node<T> {


    private String value;
    private Node<T> left;
    private Node<T> right;

    public Node() {
    }

    public Node(String value) {
        this.value = value;
    }
    public Node(String value, Node<T> left, Node<T> right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Node<T> getLeft() {
        return left;
    }

    //json
    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    //json
    public void setRight(Node<T> right) {
        this.right = right;
    }
    @JsonIgnore
    public boolean isLeaf() {
        return left == null && right == null;
    }






}
