#!/bin/bash

# Запустим отслеживаемую программу в фоновом режиме
./traced_program &

# Запустим bpftrace программу
bpftrace -e 'uprobe:./traced_program:target_function { printf("Целевая функция вызвана\n"); }'

# Остановим отслеживаемую программу после завершения bpftrace
kill %1
