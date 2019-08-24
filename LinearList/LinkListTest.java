package dataStruct;

import entity.Test;

/**
 * 双向链表测试
 */
public class LinkListTest {
    public static void main(String[] args){
        LinkList<String> linkList = new LinkList<String>();
        linkList.insert(1, "1");
        linkList.addToTail("9");
        linkList.addToTail("2");
        linkList.addToTail("4");
        linkList.addToTail("3");
        linkList.addToTail("90");
        linkList.addToTail("65");
        linkList.delete(5);
        for (int i = 0; i < linkList.size(); i++) {
            System.out.println(linkList.getNode(i).getValue());
        }
    }

}
