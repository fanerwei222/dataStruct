package dataStruct;

import java.util.LinkedList;
import java.util.List;

/**
 * 双向链表实现
 */
public class LinkList <T>{
    /**
     * 链表的大小
     */
    private int size;
    /**
     * 头节点
     */
    private LinkNode<T> head;

    public LinkList(){
        /**
         * 头节点刚开始都是null
         */
        head = new LinkNode<>(null, null, null);
        /**
         * 大小默认为0,头节点不算
         */
        size = 0;
        /**
         * 头节点的前后节点都是它自己
         */
        head.prev = head;
        head.next = head;
    }

    /**
     * 链表大小
     * @return
     */
    public int size(){
        return this.size;
    }

    /**
     * 是否空链表
     * @return
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * 检查下标索引是否在链表范围内
     * @param index
     * @return
     */
    public void checkIndexIsIn(int index){
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException("请输入正确的下标！");
        }
    }

    /**
     * 插入节点
     * @param index
     * @param value
     */
    public void insert(int index, T value){
        /**
         * 空链表的情况
         */
        LinkNode currNode;
        if (size == 0){
            currNode = new LinkNode(head, value, head.prev);
            head.prev = currNode;
            head.next = currNode;
            size++;
            return ;
        }

        /**
         * 先找到原index的节点
         */
        LinkNode<T> oriNode = getNode(index);
        /**
         * 设置新节点的前后节点
         */
        currNode = new LinkNode(oriNode.prev, value, oriNode);
        /**
         * 设置新节点的前节点的后节点
         */
        oriNode.prev.next = currNode;
        /**
         * 设置新节点的后节点的前节点
         */
        oriNode.prev = currNode;
        size++;
    }

    /**
     * 向表头插入数据
     * @param value
     */
    public void insertValueIntoHead(T value){
        insert(0, value);
    }

    /**
     * 将元素追加到尾部
     * @param value
     */
    public void addToTail(T value){
        /**
         * head.prev指的是原来的尾部节点
         */
        LinkNode<T> tailNode = new LinkNode<>(head.prev, value, head);
        /**
         * 先更改新增节点的前一个节点
         */
        head.prev.next = tailNode;
        /**
         * 再修改新增节点的后一个节点
         */
        head.prev = tailNode;
        size++;
    }

    /**
     * 根据下标获取节点
     * @param index
     * @return
     */
    public LinkNode<T> getNode(int index){
        /**
         * 下标判断是否在范围内
         */
        checkIndexIsIn(index);
        /**
         * 从最近的一端查找是最快的
         */
        LinkNode<T> curNode;
        if (index < size/2){
            curNode = head.next;
            for (int i = 0 ; i < index; i++){
                curNode = curNode.next;
            }
            return curNode;
        }
        /**
         * 从链表后端开始找
         */
        curNode = head.prev;
        /**
         * 设置新循环次数
         */
        int nIndex = size - (index + 1);
        for (int i = 0; i < nIndex; i++){
            curNode = curNode.prev;
        }
        return curNode;
    }

    /**
     * 获取节点的值
     * @param node
     * @return
     */
    public T getValue(LinkNode<T> node){
        return node.value;
    }

    /**
     * 获取第一个节点的值
     * @return
     */
    public T getFirstValue(){
        return getValue(getNode(0));
    }

    /**
     * 获取最后一个节点的值
     * @return
     */
    public T getLastValue(){
        return getValue(getNode(size - 1));
    }

    /**
     * 删除节点
     * @param index
     * @return
     */
    public boolean delete(int index){
        checkIndexIsIn(index);

        LinkNode<T> delNode = getNode(index);
        /**
         * 先将待删除节点的前节点的后节点指向待删除节点的后节点
         */
        delNode.prev.next = delNode.next;
        /**
         * 再将待删除节点的后节点的前节点指向待删除节点的前节点
         */
        delNode.next.prev = delNode.prev;
        size--;
        delNode = null;

        return true;
    }

    /**
     * 删除第一个节点
     * @return
     */
    public boolean deleteFirst(){
        return delete(0);
    }

    /**
     * 删除最后一个节点
     * @return
     */
    public boolean deleteLast(){
        return delete(size - 1);
    }

    /**
     * 链表节点
     * @param <T>
     */
     class LinkNode <T> {
        /**
         * 节点的值
         */
        private T value;
        /**
         * 后一个节点
         */
        private LinkNode<T> next;
        /**
         * 前一个节点
         */
        private LinkNode<T> prev;

        public LinkNode(LinkNode<T> prev ,T value , LinkNode<T> next){
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }
}


