#!/bin/bash

# Запускаем программу для отслеживания в фоновом режиме
./traced_program &

# Сохраняем PID запущенного процесса
PID=$!

# Запускаем bpftime с eBPF-программой
bpftime -b ./bpftime_program.o &

# Ждем некоторое время для сбора данных
sleep 10

# Останавливаем bpftime и отслеживаемую программу
pkill bpftime
kill $PID
