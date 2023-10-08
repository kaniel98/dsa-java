import DoubleLinkedLists.DoubleLinkedList;

public class Main {
    public static void main(String[] args) {
        DoubleLinkedList myDoubleLinkedList = new DoubleLinkedList(40);
        myDoubleLinkedList.prepend(50);
        myDoubleLinkedList.prepend(60);
        myDoubleLinkedList.prepend(70);

        myDoubleLinkedList.swapFirstLast();
        myDoubleLinkedList.printList();
    }
    
}