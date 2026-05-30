#include <bits/stdc++.h>

using namespace std;

class Code {
public:
    enum HeapType {
        MIN,
        MAX
    };
    struct Heap {
        // Child[x] -> 2x+1 , 2x+2
        // Parent[x] -> Floor((x-1)/2)
        vector<int> heap;
        HeapType heapType;

        Heap(vector<int> arr, HeapType heapType) {
            this->heapType = heapType;
            this->heap = arr;
            create();
        }

        void create() {
            int lastNonLeaf_idx = ((this->heap.size()-1)-1)/2;
            while (lastNonLeaf_idx >= 0 ) {
                downHeapify(lastNonLeaf_idx);
                lastNonLeaf_idx--;
            }
        }

        int getTop() {
            return this->heap[0];
        }

        void insert(int num) {
            this->heap.push_back(num);
            upHeapify(this->heap.size()-1);
        }

        int removeTop() {
            int ans = this->getTop();
            swapInHeap(0,heap.size()-1);
            this->heap.pop_back();
            downHeapify(0);
            return ans;
        }

        int removeNode(int val) {

        }

        void upHeapify(int index) {
            int child_idx = index;
            while (child_idx>0) {
                int parent_idx = (child_idx-1)/2;
                if (HeapType::MAX == this->heapType && this->heap[parent_idx] < this->heap[child_idx]) {
                    swapInHeap(parent_idx, child_idx);
                } else if (HeapType::MIN == this->heapType && this->heap[parent_idx] > this->heap[child_idx]) {
                    swapInHeap(parent_idx, child_idx);
                } else {
                    break;
                }
                child_idx = parent_idx;
            }
        }

        void downHeapify(int index) {
            int parent_idx = index;
            // left must be present since heap is a complete binary tree
            while (2*parent_idx+1 < this->heap.size()) {
                int childA_idx = 2*parent_idx + 1;
                int childB_idx = 2*parent_idx + 2;

                int extremeType_idx = parent_idx;

                if (HeapType::MAX == this->heapType) {
                    if (childA_idx < this->heap.size() && this->heap[extremeType_idx] < this->heap[childA_idx]) {
                        extremeType_idx = childA_idx;
                    }
                    if (childB_idx < this->heap.size() && this->heap[extremeType_idx] < this->heap[childB_idx]) {
                        extremeType_idx = childB_idx;
                    }
                } else if (HeapType::MIN == this->heapType) {
                    if (childA_idx < this->heap.size() && this->heap[extremeType_idx] > this->heap[childA_idx]) {
                        extremeType_idx = childA_idx;
                    }
                    if (childB_idx < this->heap.size() && this->heap[extremeType_idx] > this->heap[childB_idx]) {
                        extremeType_idx = childB_idx;
                    }
                }

                if (extremeType_idx == parent_idx) {
                    break;
                }

                swapInHeap(parent_idx, extremeType_idx);
                parent_idx = extremeType_idx;
            }
        }

        void swapInHeap(int indexA, int indexB) {
            int temp = this->heap[indexA];
            this->heap[indexA] = this->heap[indexB];
            this->heap[indexB] = temp;
        }
    };

    void implementHeap() {
        // Create a min-heap from array
        Heap minHeap = Heap({4, 10, 3, 5, 1}, HeapType::MIN);
        minHeap.getTop();      // returns 1

        // Insert element
        minHeap.insert(2);     // heap maintains min property

        // Remove top
        minHeap.removeTop();   // removes and returns 1

        // Create a max-heap
        Heap maxHeap = Heap({4, 10, 3, 5, 1}, HeapType::MAX);
        maxHeap.getTop();      // returns 10
    }
};