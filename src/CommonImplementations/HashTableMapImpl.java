package CommonImplementations;

import java.util.List;

public class HashTableMapImpl {
    // Bucket array is used to store array of chains / values
    private List<Node> bucketArray;

    // Refers to the current capacity of array list
    private int numBuckets;

    // Refers to the current size of the array list
    private int size;


}

class Node<K, V> {
    K key;
    V value;
    final int hashCode;

    // Points to the next node - In the event of collision
    Node<K, V> next;

    public Node(K key, V value, int hashCode) {
        this.key = key;
        this.value = value;
        this.hashCode = hashCode;
    }
}