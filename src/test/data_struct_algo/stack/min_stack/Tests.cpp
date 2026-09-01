#include "../../../../main/data_struct_algo/stack/min_stack/Code.cpp"
#include <cassert>
#include <iostream>

int main() {
    Code minStack;
    minStack.push(-2);
    minStack.push(0);
    minStack.push(-3);
    assert(minStack.getMin() == -3);
    minStack.pop();
    assert(minStack.top() == 0);
    assert(minStack.getMin() == -2);

    std::cout << "All tests passed." << std::endl;
    return 0;
}
