public class ConnectRopes {

    // MinPriorityQueue implemented as binary heap using a dynamic array. 
    public static class MinPriorityQueue {
        private int[] heap;    // array for the binary heap
        private int size;      // no. elements in the heap
        private int capacity;  // current capacity of the array

        // Constructor initializes the array with a given capacity.
        public MinPriorityQueue(int capacity) {
            this.capacity = capacity;
            this.heap = new int[capacity];
            this.size = 0;
        }

    
        // When  array is full, double size when its one-quarter full make smaller
        private void resize(int newCapacity) {
            int[] newHeap = new int[newCapacity];

            for (int i = 0; i < size; i++) {
                newHeap[i] = heap[i];
            }
            heap = newHeap;
            capacity = newCapacity;
        }

        // Swap two elements in  heap.
        private void swap(int i, int j) {
            int temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }

        // Insert a new key into the priority queue.
        public void insert(int x) {
            // If the heap is full, double its size.
            if (size == capacity) {
                resize(2 * capacity);
            }
            heap[size] = x;
            int current = size;
            size++;

            // loop to maintain the minheap property.
            while (current > 0) {
                int parent = (current - 1) / 2;
                if (heap[current] < heap[parent]) {
                    swap(current, parent);
                    current = parent;
                } else {
                    break;
                }
            }
        }

        // returns the smallest element without removing it
        public int min() {
            if (size == 0) {
                throw new IllegalStateException("priority queue is empty");
            }
            return heap[0];
        }

        
        // returns the smallest element from the heap.
        public int extractMin() {
            if (size == 0) {
                throw new IllegalStateException("priority queue is empty");
            }
            int min = heap[0];
            // Replace root with the last element
            heap[0] = heap[size - 1];
            size--;
            // Restore the heap property
            heapify(0);
            // If number of elements is one-quarter of the capacity, smake smaller
            if (size > 0 && size == capacity / 4) {
                resize(capacity / 2);
            }
            return min;
        }

        // HEAPIFY restores the min-heap property starting at index i.
        private void heapify(int i) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int smallest = i;
            if (left < size && heap[left] < heap[smallest]) {
                smallest = left;
            }
            if (right < size && heap[right] < heap[smallest]) {
                smallest = right;
            }
            if (smallest != i) {
                swap(i, smallest);
                heapify(smallest);
            }
        }

        // Returns no. of elements in the queue
        public int size() {
            return size;
        }
    }

    // Main method to run the problem
    public static void main(String[] args) {
        int[] ropes = {4, 8, 3, 1, 6, 9, 12, 7, 2};

        // Create  MinPriorityQueue with size of no. ropes
        MinPriorityQueue pq = new MinPriorityQueue(ropes.length);

        // enqueue each rope length into the priority queue.
        for (int rope : ropes) {
            pq.insert(rope);
        }

        int totalCost = 0;
        System.out.println("Rope connection operations:");

        // iterate unitl there is only one rope left in queue
        // every iteration, extract the two smallest ropes, connect , and insert the resulting rope back.
        while (pq.size() > 1) {
            int rope1 = pq.extractMin();
            int rope2 = pq.extractMin();
            int cost = rope1 + rope2;
            System.out.println("Connect ropes " + rope1 + " and " + rope2 + " which costs: " + cost);
            totalCost += cost;
            pq.insert(cost);
        }

        System.out.println("Total cost: " + totalCost);
    }
}
