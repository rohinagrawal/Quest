#include "../../../../main/data_struct_algo/linked_list/reverse_k_nodes_group/Code.cpp"
#include <cassert>
#include <iostream>
#include <vector>

namespace {

ListNode* buildList(const std::vector<int>& vals) {
    ListNode dummy(0);
    ListNode* tail = &dummy;
    for (int v : vals) {
        tail->next = new ListNode(v);
        tail = tail->next;
    }
    return dummy.next;
}

std::vector<int> toVector(ListNode* head) {
    std::vector<int> out;
    while (head != nullptr) {
        out.push_back(head->val);
        head = head->next;
    }
    return out;
}

}  // namespace

int main() {
    Code sol;

    // Example 1: head = [1,2,3,4,5], k = 2 -> [2,1,4,3,5]
    {
        ListNode* head = buildList({1, 2, 3, 4, 5});
        ListNode* result = sol.reverseKGroup(head, 2);
        assert((toVector(result) == std::vector<int>{2, 1, 4, 3, 5}));
    }

    // Example 2: head = [1,2,3,4,5], k = 3 -> [3,2,1,4,5]
    {
        ListNode* head = buildList({1, 2, 3, 4, 5});
        ListNode* result = sol.reverseKGroup(head, 3);
        assert((toVector(result) == std::vector<int>{3, 2, 1, 4, 5}));
    }

    // Example 3: head = [], k = 1 -> []
    {
        ListNode* head = buildList({});
        ListNode* result = sol.reverseKGroup(head, 1);
        assert(result == nullptr);
    }

    // Example 4: head = [1], k = 1 -> [1]
    {
        ListNode* head = buildList({1});
        ListNode* result = sol.reverseKGroup(head, 1);
        assert((toVector(result) == std::vector<int>{1}));
    }

    std::cout << "All tests passed." << std::endl;
    return 0;
}
