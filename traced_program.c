#include <stdio.h>
#include <unistd.h>

void target_function(int param1, const char *param2) {
    printf("Выполняется целевая функция с параметрами: %d, %s\n", param1, param2);
}

int main() {
    int counter = 0;
    while (1) {
        target_function(counter, "тестовая строка");
        counter++;
        sleep(1);
    }
    return 0;
}
