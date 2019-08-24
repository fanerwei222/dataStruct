package dataStruct;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTreeTest {
    public static void main(String[] args){
        Node root = new Node(5, "five");
        BinarySearchTree tree = new BinarySearchTree(root);
        Node one = new Node(1, "one");
        Node two = new Node(2, "two");
        Node three = new Node(3, "three");
        Node four = new Node(4, "four");
        Node six = new Node(6, "six");
        Node seven = new Node(7, "seven");
        Node eight = new Node(8, "eight");
        Node nine = new Node(9, "nine");
        List<Node> list = new ArrayList<>();
        list.add(one);
        list.add(two);
        list.add(three);
        list.add(four);
        list.add(six);
        list.add(seven);
        list.add(eight);
        list.add(nine);
        for (Node node : list) {
            tree.insert(node.getKey(), node.getValue());
        }
        tree.delete(1);
        tree.insert(1, "1");
        tree.insert(2, "2");
        tree.delete(3);
        tree.delete(6);
        tree.delete(7);
        tree.delete(8);
        tree.delete(9);
        tree.insert(10, "10");
        tree.insert(8, "8");
        tree.insert(9, "9");
        tree.insert(7, "7");
        tree.insert(11, "11");
        tree.insert(12, "12");
        List<Node> result = new ArrayList<>();
        //前序遍历
        //result = tree.preOrder(root);
        //中序遍历
        result = tree.midOrder(tree.getRoot());
        //后序遍历
        //result = tree.backOrder(root);
        for (Node node : result) {
            System.out.println(node.getKey());
        }



    }
}
