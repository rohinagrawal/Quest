# Implement Heap

## Problem Description

Implement a generic heap data structure that supports both **min-heap** and **max-heap** variants. The heap type should be configurable at initialization.

Use the most suitable underlying data structure (typically a dynamic array/list) to implement the heap efficiently.

---

## Required Operations

Implement the following operations with their specified time complexities:

| Operation | Signature | Time Complexity | Description |
|-----------|-----------|-----------------|-------------|
| **create** | `Heap(arr, heapType)` | O(n) optimized, O(n log n) basic | Build a heap from an array. `heapType` can be `MIN` or `MAX` |
| **insert** | `void insert(value)` | O(log n) | Insert a new element into the heap |
| **getTop** | `int getTop()` | O(1) | Return the top element (min or max) without removing it |
| **removeTop** | `int removeTop()` | O(log n) | Remove and return the top element |
| **removeNode** | `void removeNode(value)` | O(n + log n) | Remove a specific element from the heap |

---

## Implementation Notes

- Use **heapify-up** for insertion
- Use **heapify-down** for removal operations
- For optimal `create` operation, implement **bottom-up heapify** in O(n) time
- The heap should maintain the heap property after every operation:
  - **Min-heap**: Parent ≤ Children
  - **Max-heap**: Parent ≥ Children

---

## Example Usage

```java
// Create a min-heap from array
Heap minHeap = new Heap([4, 10, 3, 5, 1], HeapType.MIN);
minHeap.getTop();      // returns 1

// Insert element
minHeap.insert(2);     // heap maintains min property

// Remove top
minHeap.removeTop();   // removes and returns 1

// Create a max-heap
Heap maxHeap = new Heap([4, 10, 3, 5, 1], HeapType.MAX);
maxHeap.getTop();      // returns 10
```

---

## Constraints

- The heap should dynamically resize as elements are added
- All operations must maintain the heap invariant
- Support for generic data types (integers, custom objects with comparators)
