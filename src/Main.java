import DoubleLinkedLists.DoubleLinkedList;

public class Main {
    public static void main(String[] args) {
        DoubleLinkedList myDoubleLinkedList = new DoubleLinkedList(40);
        myDoubleLinkedList.prepend(50);

        myDoubleLinkedList.printList();
        System.out.println(myDoubleLinkedList.isPalindrome());

    }

}