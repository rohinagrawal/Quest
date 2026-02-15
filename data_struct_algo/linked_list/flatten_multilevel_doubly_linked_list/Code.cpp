import<bits/stdc++.h>

using namespace std;


// Definition for a Node.
class Node {
public:
int val;
Node* prev;
Node* next;
Node* child;
};


class Code {
public:
    Node* flatten(Node* head) {
        flattenLL(head);
        return head;
    }

    void flattenLL(Node* node){
        if(node == NULL){
            return;
        }

        while(node!= NULL && node->child== NULL){
            node = node->next;
        }

        if(node!= NULL && node->child!= NULL){
            flattenLL(node->next);
            flattenLL(node->child);
            merge(node, node->next, node->child);
            node->child = NULL;
        }
        return;
    }

    void merge (Node* head, Node* masterLL, Node* childLL){
        head->next = childLL;
        childLL->prev = head;
        Node* itr = childLL;
        while(itr->next!= NULL){
            itr = itr->next;
        }
        itr->next = masterLL;
        if(masterLL!= NULL){
            masterLL->prev = itr;
        }
        return;
    }
};