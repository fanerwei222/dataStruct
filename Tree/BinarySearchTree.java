package dataStruct;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉查找树/二叉搜索树
 */
public class BinarySearchTree {
    private Node root;
    /**
     * 遍历结果集合
     */
    List<Node> orderResult = new ArrayList<>();


    public BinarySearchTree(Node root){
        this.root = root;
    }

    public BinarySearchTree(){}

    public Node getRoot(){
        return this.root;
    }

    /**
     * 查找key
     * @param key
     * @return
     */
    public Node find(int key){
        /**
         * 从根节点开始找
         */
        Node currentNode = root;
        /**
         * 判断根节点是否符合要求
         */
        while (currentNode != null && key != currentNode.getKey()){
            if (key < currentNode.getKey()){
                //to left
                currentNode = currentNode.getLeftChildren();
            }else {
                //to right
                currentNode = currentNode.getRightChildren();
            }
        }

        return currentNode;
    }

    /**
     * 插入
     */
    public void insert(int key, String value){
        Node newNode = new Node(key, value);
        if (null == root){
            this.root = newNode;
            return ;
        }
        /**
         * 当前搜索到的树
         */
        Node currentNode = root;
        Node parentNode = root;
        /**
         * 默认左子树
         */
        boolean isLeftChild = true;
        while (null != currentNode){
            parentNode = currentNode;
            if (key < parentNode.getKey()){
                //父节点左侧
                currentNode = parentNode.getLeftChildren();
                isLeftChild = true;
            }else {
                //父节点右侧
                currentNode = parentNode.getRightChildren();
                isLeftChild = false;
            }
        }

        /**
         * 循环结束之后的parentNode就是最终需要插入子节点的节点，根据isLeftChild判断插入左儿子还是右儿子
         */
        Node childNode = new Node(key, value);
        if (isLeftChild){
            parentNode.setLeftChildren(childNode);
            childNode.setParent(parentNode);
        }else {
            parentNode.setRightChildren(childNode);
            childNode.setParent(parentNode);
        }
        return ;
    }

    /**
     * 前序遍历
     * @param root
     * @return
     */
    public List<Node> preOrder(Node root){
        if (null == root){
            return null;
        }
        orderResult.add(root);
        preOrder(root.getLeftChildren());
        preOrder(root.getRightChildren());
        return orderResult;
    }

    /**
     * 中序遍历
     * @param root
     * @return
     */
    public List<Node> midOrder(Node root){
        if (null == root){
            return null;
        }
        midOrder(root.getLeftChildren());
        orderResult.add(root);
        midOrder(root.getRightChildren());

        return orderResult;
    }

    /**
     * 后序遍历
     * @param root
     * @return
     */
    public List<Node> backOrder(Node root){
        if (null == root){
            return null;
        }
        backOrder(root.getLeftChildren());
        backOrder(root.getRightChildren());
        orderResult.add(root);

        return orderResult;
    }

    /**
     * 删除节点(有三种情况)
     * 1.待删除节点没有子节点
     * 2.待删除节点只有一个子节点
     * 3.待删除节点有两个子节点
     * @param key
     * @return
     */
    public boolean delete(int key){
        /**
         * 被查找到的节点
         */
        Node findNode = find(key);

        if (null == findNode){
            return false;
        }
        /**
         * 待删除节点没有儿子节点
         */
        if (null == findNode.getLeftChildren() && null == findNode.getRightChildren()){
            if (null == findNode.getParent()){
                root = null;
                return true;
            }else {
                /**
                 * 是否和父节点的左儿子相同
                 */
                if (isParentLeftChildren(findNode, key)){
                    findNode.getParent().setLeftChildren(null);
                    return true;
                }else{
                    /**
                     * 右儿子
                     */
                    findNode.getParent().setRightChildren(null);
                    return true;
                }
            }
        }

        /**
         * 待删除节点左儿子存在，右儿子为空
         */
        if (null == findNode.getRightChildren() && null != findNode.getLeftChildren()){
            if (isParentLeftChildren(findNode, key)){
                findNode.getParent().setLeftChildren(findNode.getLeftChildren());
                findNode.getLeftChildren().setParent(findNode.getParent());
                findNode = null;
                return true;
            }else{
                findNode.getParent().setRightChildren(findNode.getLeftChildren());
                findNode.getLeftChildren().setParent(findNode.getParent());
                findNode = null;
                return true;
            }
        }
        /**
         * 待删除节点右儿子存在，左儿子为空
         */
        else if (null == findNode.getLeftChildren() && null != findNode.getRightChildren()){
            if (isParentLeftChildren(findNode, key)){
                findNode.getParent().setLeftChildren(findNode.getRightChildren());
                findNode.getRightChildren().setParent(findNode.getParent());
                findNode = null;
                return true;
            }else{
                findNode.getParent().setRightChildren(findNode.getRightChildren());
                findNode.getRightChildren().setParent(findNode.getParent());
                findNode = null;
                return true;
            }
        }
        /**
         * 待删除节点左右儿子都存在
         */
        else if (null != findNode.getLeftChildren() && null != findNode.getRightChildren()){
            if (isParentLeftChildren(findNode, key)){
                /**
                 * 设置一系列的引用
                 */
                Node succesor = findSuccessorNode(findNode);
                /**
                 * 判断后继者是否就是当前节点的左儿子，如果是，不需要再重复设置自己引用自己，会引发内存泄漏。
                 */
                if (succesor != findNode.getLeftChildren()){
                    findNode.getLeftChildren().setParent(succesor);
                    succesor.setLeftChildren(findNode.getLeftChildren());
                    findNode.getRightChildren().setParent(succesor);
                    succesor.setRightChildren(findNode.getRightChildren());
                    findNode.getParent().setLeftChildren(succesor);
                    succesor.setParent(findNode.getParent());
                    findNode = null;

                    return true;
                }else {
                    findNode.getRightChildren().setParent(succesor);
                    succesor.setRightChildren(findNode.getRightChildren());
                    findNode.getParent().setLeftChildren(succesor);
                    succesor.setParent(findNode.getParent());
                    findNode = null;

                    return true;
                }

            }else{
                Node succesor = findSuccessorNode(findNode);
                /**
                 * 判断后继者是否就是当前节点的左儿子，如果是，不需要再重复设置自己引用自己，会引发内存泄漏。
                 */
                if (succesor != findNode.getLeftChildren()){
                    findNode.getLeftChildren().setParent(succesor);
                    succesor.setLeftChildren(findNode.getLeftChildren());
                    findNode.getRightChildren().setParent(succesor);
                    succesor.setRightChildren(findNode.getRightChildren());
                    findNode.getParent().setRightChildren(succesor);
                    succesor.setParent(findNode.getParent());
                    findNode = null;

                    return true;
                }else {
                    findNode.getRightChildren().setParent(succesor);
                    succesor.setRightChildren(findNode.getRightChildren());
                    findNode.getParent().setRightChildren(succesor);
                    succesor.setParent(findNode.getParent());
                    findNode = null;

                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 查询该节点是否父节点的左节点
     * @param findNode
     * @param key
     */
    private boolean isParentLeftChildren(Node findNode,int key){
        /**
         * 是否和父节点的左儿子相同
         */
        if (null != findNode.getParent().getLeftChildren() && findNode.getParent().getLeftChildren().getKey() == key){
            return true;
        }else{
            /**
             * 是否和父节点的右儿子相同
             */
            return false;
        }
    }

    /**
     * 查找待删除节点的后继节点，一般是右儿子的左子树的最小值
     * @param node
     * @return
     */
    private Node findSuccessorNode(Node node){
        /**
         * 保存父节点
         */
        Node parentNode = node;
        /**
         * 当前节点
         */
        Node currentNode = node;
        while (null != currentNode){
            parentNode = currentNode;
            currentNode = currentNode.getLeftChildren();
        }
        /**
         * 判断父节点的右儿子是否为空，不为空的话需要把右儿子提到该父节点的父节点（没有重复打，就是两个父节点）的左儿子
         */
        if (null == parentNode.getRightChildren()){
            return parentNode;
        }else {
            parentNode.getParent().setLeftChildren(parentNode.getRightChildren());
            return parentNode;
        }
    }

}

/**
 * 节点
 */
class Node{
    private int key;
    private String value;
    private Node parent;
    private Node leftChildren;
    private Node rightChildren;

    public Node(){}

    public Node(int key, String value){
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getLeftChildren() {
        return leftChildren;
    }

    public void setLeftChildren(Node leftChildren) {
        this.leftChildren = leftChildren;
    }

    public Node getRightChildren() {
        return rightChildren;
    }

    public void setRightChildren(Node rightChildren) {
        this.rightChildren = rightChildren;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
