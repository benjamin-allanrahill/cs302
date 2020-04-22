// from https://www.geeksforgeeks.org/binary-tree-set-1-introduction/

class Node {
    Double key;
    Letter letter;

    public Node(Double item, Letter letter) {
        this.key = item;
        this.letter = letter;
    }
    public Node(Double item) {
        this.key = item;
        this.letter = null;
    }

}
public class BinaryTree {

    Node root;
    BinaryTree left, right;

    BinaryTree(Double key, Letter letter) {
        root = new Node(key, letter);
        this.right = null;
        this.left = null;
    }
    BinaryTree(Double key, BinaryTree l, BinaryTree r) {
        root = new Node(key);
        this.left = l;
        this.right = r;
    }

    BinaryTree() {
        root = null;
    }

    public Double getWeight() {
        return this.root.key;
    }

    public Boolean isLeaf(){
        if(this.right == null && this.left==null){
            return true;
        } else {
            return false;
        }
    }
}