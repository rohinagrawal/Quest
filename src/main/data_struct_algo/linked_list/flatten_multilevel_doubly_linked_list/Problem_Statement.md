# Flatten a Multilevel Doubly Linked List

## Problem Description

You are given a doubly linked list, which contains nodes that have a next pointer, a previous pointer, and an additional **child pointer**. This child pointer may or may not point to a separate doubly linked list, also containing these special nodes. These child lists may have one or more children of their own, and so on, to produce a **multilevel data structure**.

Given the `head` of the first level of the list, **flatten the list** so that all the nodes appear in a single-level, doubly linked list. Let `curr` be a node with a child list. The nodes in the child list should appear **after** `curr` and **before** `curr.next` in the flattened list.

Return the `head` of the flattened list. The nodes in the list must have **all** of their child pointers set to `null`.

## Node Structure

```cpp
class Node {
public:
    int val;
    Node* prev;
    Node* next;
    Node* child;
};
```

## Examples

### Example 1

**Input:**
```
head = [1,2,3,4,5,6,null,null,null,7,8,9,10,null,null,11,12]
```

**Visual Representation:**
```
1---2---3---4---5---6--NULL
        |
        7---8---9---10--NULL
            |
            11--12--NULL
```

**Output:**
```
[1,2,3,7,8,11,12,9,10,4,5,6]
```

**Flattened Structure:**
```
1--2--3--7--8--11--12--9--10--4--5--6--NULL
```

**Explanation:**
- Start at node 1, proceed to node 2, then node 3
- Node 3 has a child (7), so we flatten the child list before continuing
- In the child list starting at 7, we reach node 8
- Node 8 has a child (11), so we flatten that child list (11->12)
- After flattening all children, we get the sequence: 1,2,3,7,8,11,12,9,10,4,5,6

### Example 2

**Input:**
```
head = [1,2,null,3]
```

**Visual Representation:**
```
1---2---NULL
|
3---NULL
```

**Output:**
```
[1,3,2]
```

**Explanation:**
- Node 1 has a child (3)
- Insert the child list after node 1 and before node 2
- Result: 1->3->2

### Example 3

**Input:**
```
head = []
```

**Output:**
```
[]
```

**Explanation:**
- Empty list remains empty

## Constraints

- The number of nodes in the list is in the range `[0, 1000]`
- `1 ≤ Node.val ≤ 10^5`
- The depth of the multilevel linked list is at most `1000`

## How Multilevel Linked List is Represented in Test Cases

We use the multilevel linked list from **Example 1** above:

```
 1---2---3---4---5---6--NULL
         |
         7---8---9---10--NULL
             |
             11--12--NULL
```

### Step-by-Step Serialization Process

**Step 1:** The serialization of each level is as follows:
```
[1,2,3,4,5,6,null]
[7,8,9,10,null]
[11,12,null]
```

**Step 2:** To serialize all levels together, we add `null`s in each level to signify no node connects to the upper node of the previous level. The serialization becomes:
```
[1,    2,    3, 4, 5, 6, null]
             |
[null, null, 7,    8, 9, 10, null]
                   |
[            null, 11, 12, null]
```

**Step 3:** Merging the serialization of each level and removing trailing nulls we obtain:
```
[1,2,3,4,5,6,null,null,null,7,8,9,10,null,null,11,12]
```

This shows:
- Node at index 2 (value 3) has a child starting at index 6 (value 7)
- Node at index 7 (value 8) has a child starting at index 10 (value 11)

## Approach

### Recursive Approach (Depth-First Search)

1. **Traverse** the linked list node by node
2. When encountering a node with a **child**:
   - Recursively flatten the child list
   - Recursively flatten the remaining main list (after current node)
   - **Merge** the child list between the current node and the next node
   - Set the child pointer to `null`
3. Continue until all nodes are processed

**Time Complexity:** O(n) where n is the total number of nodes  
**Space Complexity:** O(d) where d is the maximum depth (recursion stack)

### Iterative Approach (Using Stack)

1. Use a **stack** to track nodes with children
2. Traverse the list:
   - If a node has a child, push the next node onto the stack
   - Move to the child and set child pointer to `null`
   - When reaching the end of a level, pop from stack to continue
3. Properly maintain `prev` and `next` pointers

**Time Complexity:** O(n)  
**Space Complexity:** O(d) where d is the maximum depth

## Key Points

- After flattening, all `child` pointers must be set to `null`
- The `prev` and `next` pointers must be correctly maintained in the flattened list
- The flattening order is depth-first: process child lists before continuing with the main list
- This is a **doubly linked list**, so both forward and backward links must be properly connected

