package labTestPreparation;

import java.util.Comparator;

public class ShapeAreaComparator implements Comparator<Object> {
    @Override
    public int compare(Object o1, Object o2) {
        // Compare the area of the two shapes
        // If o1 is larger, return 1
        // If o2 is larger, return -1

        // Arrange from smallest to largest
//        return (int) (o1.getArea() - o2.getArea());
        // Arrange from largest to smallest
        // return (int) (o2.getArea() - o1.getArea());
        return 0;
    }
}