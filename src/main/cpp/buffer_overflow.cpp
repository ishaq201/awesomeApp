#include <iostream>
#include <cstring>

void vulnerable_function(char* input) {
    char buffer[10];
    strcpy(buffer, input);
    std::cout << "Buffer: " << buffer << std::endl;
}

void stack_overflow(char* data) {
    char local_buffer[8];
    strcpy(local_buffer, data);
    std::cout << "Local: " << local_buffer << std::endl;
}

void heap_overflow() {
    char* heap_buffer = new char[5];
    strcpy(heap_buffer, "This string is way too long");
    std::cout << "Heap: " << heap_buffer << std::endl;
    delete[] heap_buffer;
}

int main() {
    vulnerable_function("This input is too long for buffer");
    stack_overflow("Overflow!");
    heap_overflow();
    return 0;
}