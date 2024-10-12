#include <stdio.h>
#include <unistd.h>

void target_function(int param1, const char *param2) {
    printf("Начало выполнения target_function\n");
    printf("Параметры: %d, %s\n", param1, param2);
    sleep(1); // Симуляция некоторой работы
    printf("Завершение выполнения target_function\n");
}

int main() {
    int counter = 0;
    while (1) {
        target_function(counter, "тестовая строка");
        counter++;
        sleep(2);
    }
    return 0;
}
