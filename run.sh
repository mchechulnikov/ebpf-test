#!/bin/bash

# Запускаем отслеживаемую программу в фоновом режиме
./traced_program &
PID=$!

# Даем программе время запуститься
sleep 1

# Определяем номер системного вызова write для текущей архитектуры
if [[ "$(uname -m)" == "x86_64" ]]; then
    WRITE_SYSCALL=1
elif [[ "$(uname -m)" == "aarch64" ]]; then
    WRITE_SYSCALL=64
else
    echo "Неизвестная архитектура: $(uname -m)"
    kill $PID
    exit 1
fi

# Создаем временный файл для скрипта bpftrace
cat <<EOF > script.bt
BEGIN
{
    printf("Начинаем отслеживание процесса с PID %d\\n", $PID);
}

uprobe:./traced_program:target_function
{
    @in_target_function[tid] = 1;
    printf("target_function вызвана\\n");
}

uretprobe:./traced_program:target_function
{
    printf("target_function завершила выполнение\\n");
    delete(@in_target_function[tid]);
}

tracepoint:raw_syscalls:sys_enter
/@in_target_function[tid] && pid == $PID && args->id == $WRITE_SYSCALL/
{
    if (args->args[0] == 1) {
        printf("Вывод из target_function: %s\\n", str(args->args[1], args->args[2]));
    }
}
EOF

# Запускаем bpftrace с созданным скриптом
bpftrace script.bt

# Удаляем временный файл
rm script.bt

# Останавливаем отслеживаемую программу после завершения bpftrace
kill $PID
