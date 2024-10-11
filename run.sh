#!/bin/bash

# Запустим отслеживаемую программу в фоновом режиме
./traced_program &

# Сохраним PID запущенного процесса
PID=$!

# Запустим bpftrace для отслеживания параметров функции
bpftrace -e '
uprobe:./traced_program:target_function
{
    printf("Целевая функция вызвана с параметрами: %d, %s\n", arg0, str(arg1));
}
'

# Остановим отслеживаемую программу после завершения bpftrace
kill $PID
