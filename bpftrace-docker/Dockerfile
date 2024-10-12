FROM ubuntu:22.04

# Устанавливаем необходимые пакеты
RUN apt-get update && apt-get install -y \
    build-essential \
    bpftrace \
    git

# Скопируем исходный код в контейнер
COPY . /usr/src/app

WORKDIR /usr/src/app

# Собираем отслеживаемую программу без оптимизаций
RUN gcc -O0 -o traced_program traced_program.c

# Делаем скрипт исполняемым
RUN chmod +x run.sh

CMD ["./run.sh"]
