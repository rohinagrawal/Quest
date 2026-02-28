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
    ListNode* reverseKGroup(ListNode* head, int k) {
        ListNode start (0,head);
        ListNode* itr = head;
        ListNode* gPrev = &start;
        ListNode* gCurr = NULL;
        ListNode* gNext = NULL;
        int count = 0;
        while(itr != NULL){
            count = 1;
            gCurr = itr;
            while(itr != NULL && count<k){
                itr = itr->next;
                count++;
            }
            if(itr != NULL && count == k){
                ListNode* kth = itr;
                gNext = kth->next;
                ListNode* prev = gNext;
                ListNode* curr = gCurr;
                ListNode* next = NULL;
                while(count-- > 0){
                    next = curr->next;
                    curr->next = prev;
                    prev = curr;
                    curr = next;
                }
                gPrev->next = kth;
                gPrev = gCurr;
                itr = gNext;
            }
        }
        return start.next;
    }
};