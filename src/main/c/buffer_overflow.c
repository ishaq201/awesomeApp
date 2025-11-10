#include <stdio.h>
#include <string.h>
#include <stdlib.h>

void vulnerable_gets() {
    char buffer[10];
    printf("Enter input: ");
    gets(buffer);
    printf("You entered: %s\n", buffer);
}

void strcpy_overflow(char* input) {
    char buffer[8];
    strcpy(buffer, input);
    printf("Buffer: %s\n", buffer);
}

void sprintf_overflow() {
    char buffer[10];
    char* user_input = "This is a very long string that will overflow";
    sprintf(buffer, "%s", user_input);
    printf("Formatted: %s\n", buffer);
}

void strcat_overflow() {
    char buffer[10] = "Hello";
    char* append = " World and more text";
    strcat(buffer, append);
    printf("Concatenated: %s\n", buffer);
}

int main() {
    vulnerable_gets();
    strcpy_overflow("Overflow data here");
    sprintf_overflow();
    strcat_overflow();
    return 0;
}