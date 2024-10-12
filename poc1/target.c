#include <stdio.h>
#include <unistd.h>

void my_function(int param) {
    printf("Function called with param: %d\n", param);
}

int main() {
    int i = 0;
    while(i < 5) {
        my_function(i);
        i++;
        sleep(1);
    }
    return 0;
}
