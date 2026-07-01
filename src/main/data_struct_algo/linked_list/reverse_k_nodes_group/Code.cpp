import<bits/stdc++.h>

using namespace std;

// Definition for a Node.
struct ListNode {
    int val;
    ListNode *next;
    ListNode() : val(0), next(nullptr) {}
    ListNode(int x) : val(x), next(nullptr) {}
    ListNode(int x, ListNode *next) : val(x), next(next) {}
};


class Code {
public:
    /**
     * Reverse nodes in a linked list in groups of k
     *
     * Algorithm:
     * 1. Identify k-sized groups in the list
     * 2. Reverse only complete groups (groups with exactly k nodes)
     * 3. Leave incomplete groups at the end unchanged
     * 4. Connect all groups together
     *
     * Time Complexity: O(n) - single pass through the list
     * Space Complexity: O(1) - only uses constant extra space
     *
     * @param head - head of the linked list
     * @param k - size of each group to reverse
     * @return head of the modified list
     */
    ListNode* reverseKGroup(ListNode* head, int k) {
        // Create a dummy node pointing to head to simplify edge cases
        // (especially when reversing the first k nodes)
        ListNode start(0, head);

        // itr: traverses the list to identify k-sized groups
        ListNode* itr = head;

        // gPrev: points to the node before the current group
        // Used to connect the previous group to the reversed current group
        ListNode* gPrev = &start;

        // gCurr: marks the start of the current group
        ListNode* gCurr = NULL;

        // gNext: marks the start of the next group (for later connection)
        ListNode* gNext = NULL;

        // count: tracks how many nodes are in the current group
        int count = 0;

        // Process groups until we reach the end of the list
        while(itr != NULL) {
            count = 1;           // Start counting from 1
            gCurr = itr;         // Mark the beginning of current group

            // ===== PHASE 1: IDENTIFY K-SIZED GROUP =====
            // Count k nodes starting from gCurr
            // Loop terminates when either:
            // - We've counted k nodes (itr points to kth node)
            // - We reach end of list (fewer than k nodes remaining)
            while(itr != NULL && count < k) {
                itr = itr->next;
                count++;
            }

            // ===== PHASE 2: CHECK IF GROUP IS COMPLETE =====
            // Only reverse if:
            // - itr != NULL: we have at least k nodes
            // - count == k: we found exactly k nodes (not fewer)
            if(itr != NULL && count == k) {
                // kth node of the current group
                ListNode* kth = itr;

                // Save the starting node of the next group
                // (we'll need this to continue after reversing current group)
                gNext = kth->next;

                // ===== PHASE 3: REVERSE K NODES =====
                // Three-pointer technique: prev, curr, next
                ListNode* prev = gNext;     // Start from node after kth node
                ListNode* curr = gCurr;     // Start from first node of group
                ListNode* next = NULL;      // Temporary pointer for traversal

                // Reverse the k nodes in the current group
                // Example: 1->2->3 becomes 3->2->1
                while(count-- > 0) {
                    // Save the next node before we change curr->next
                    next = curr->next;

                    // Reverse the pointer: make curr point backwards
                    curr->next = prev;

                    // Move prev to current node (will be the "previous" for next iteration)
                    prev = curr;

                    // Move curr to the next node in the original sequence
                    curr = next;
                }

                // ===== PHASE 4: CONNECT GROUPS =====
                // After reversal:
                // - kth node is now the head of the reversed group
                // - gCurr node is now the tail of the reversed group

                // Connect previous group to the head of reversed group
                gPrev->next = kth;

                // Update gPrev to point to the tail of current group
                // (this tail will be connected to the next group)
                gPrev = gCurr;

                // Move to the next group for processing
                itr = gNext;
            }
            // If group is incomplete (fewer than k nodes), we exit the loop
            // and return. The incomplete group remains unchanged.
        }

        // Return the head of the modified list
        // start.next skips the dummy node and returns the actual head
        return start.next;
    }
};