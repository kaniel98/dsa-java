import LinkedList.LinkedList;

public class Main {
    public static void main(String[] args) {
        LinkedList newLinkedList = new LinkedList(40);
        newLinkedList.append(50);
        newLinkedList.append(60);
        newLinkedList.append(70);
        newLinkedList.reverse();

        newLinkedList.printList();
    }
}