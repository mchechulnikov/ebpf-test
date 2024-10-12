#include <stdio.h>
#include <unistd.h>

void target_function() {
    printf("Выполняется целевая функция\n");
}

int main() {
    while (1) {
        target_function();
        sleep(1);
    }
    return 0;
}
