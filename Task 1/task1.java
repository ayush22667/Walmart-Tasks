import java.util.ArrayList;

public class Heap<T extends Comparable<T>> {
    private final int childCount;
    private final ArrayList<T> data;

    // Constructor to initialize the heap with a specified child count
    public Heap(int childCount) {
        // Validate and set the child count
        this.validateChildCount(childCount);
        this.childCount = childCount;
        // Initialize the data ArrayList to store heap elements
        this.data = new ArrayList<T>();
    }

    // Validate that the child count is greater than zero and a power of 2
    private void validateChildCount(int childCount) {
        if (childCount <= 0) {
            throw new IllegalArgumentException("childCount must be greater than zero");
        }

        double logChildCount = Math.log(childCount) / Math.log(2);
        if (Math.ceil(logChildCount) != Math.floor(logChildCount)) {
            throw new IllegalArgumentException("childCount must be a power of 2");
        }
    }

    // Insert an item into the heap and maintain the heap property
    public void insert(T item) {
        // Add the item to the end of the data list
        data.add(item);
        int itemIndex = data.size() - 1;

        // Move the item up in the heap as needed
        while (itemIndex > 0) {
            itemIndex = this.swapUp(itemIndex);
        }
    }

    // Swap a child with its parent as needed to satisfy the heap property
    private int swapUp(int childIndex) {
        T childValue = data.get(childIndex);
        int parentIndex = (int) Math.floor((float) (childIndex - 1) / childCount);

        // Check if the parent exists and swap if necessary
        if (parentIndex >= 0) {
            T parentValue = data.get(parentIndex);
            if (childValue.compareTo(parentValue) > 0) {
                data.set(parentIndex, childValue);
                data.set(childIndex, parentValue);
                return parentIndex;
            }
        }
        return -1;
    }

    // Remove and return the maximum value from the heap
    public T popMax() {
        // Return null if the heap is empty
        if (data.size() > 0) {
            // Store the maximum item (root of the heap)
            T maxItem = data.get(0);

            // If there is more than one element, replace the root with the last element and maintain the heap property
            if (data.size() > 1) {
                T lastItem = data.remove(data.size() - 1);
                data.set(0, lastItem);

                int itemIndex = 0;
                while (itemIndex >= 0) {
                    itemIndex = this.swapDown(itemIndex);
                }
            }

            return maxItem;
        } else {
            return null;
        }
    }

    // Swap a parent with its largest child as needed to satisfy the heap property
    private int swapDown(int parentIndex) {
        T parentValue = data.get(parentIndex);
        int largestChildIndex = 0;
        T largestChildValue = null;

        // Find the largest child
        for (int i = 0; i < childCount; i++) {
            int childIndex = childCount * parentIndex + i + 1;
            if (childIndex < data.size()) {
                T childValue = data.get(childIndex);
                if (largestChildValue == null || childValue.compareTo(largestChildValue) > 0) {
                    largestChildIndex = childIndex;
                    largestChildValue = childValue;
                }
            }
        }

        // Swap if a larger child exists
        if (largestChildValue != null && parentValue.compareTo(largestChildValue) < 0) {
            data.set(parentIndex, largestChildValue);
            data.set(largestChildIndex, parentValue);
            return largestChildIndex;
        }

        return -1;
    }
}
