#include "Code.cpp"
#include <cassert>
#include <iostream>

int main() {
    // Example 1: min-heap
    Code::Heap minHeap({4, 10, 3, 5, 1}, Code::HeapType::MIN);
    assert(minHeap.getTop() == 1);
    minHeap.insert(2);
    assert(minHeap.removeTop() == 1);
    assert(minHeap.getTop() == 2);

    // Example 2: max-heap
    Code::Heap maxHeap({4, 10, 3, 5, 1}, Code::HeapType::MAX);
    assert(maxHeap.getTop() == 10);
    assert(maxHeap.removeTop() == 10);
    assert(maxHeap.getTop() == 5);

    std::cout << "All tests passed." << std::endl;
    return 0;
}
