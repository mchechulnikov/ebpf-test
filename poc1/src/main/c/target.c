#include <stdio.h>
#include <unistd.h>

int my_function(int param) {
    printf("Function called with param: %d\n", param);
    return 42;
}

int main() {
    int i = 0;
    while(i < 10) {
        my_function(i);
        i++;
        sleep(1);
    }
    return 0;
}
