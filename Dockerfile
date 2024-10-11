FROM ubuntu:22.04

# Установим необходимые пакеты
RUN apt-get update && apt-get install -y \
    build-essential \
    bpftrace \
    git

# Скопируем исходный код в контейнер
COPY . /usr/src/app

WORKDIR /usr/src/app

# Соберем отслеживаемую программу
RUN gcc -o traced_program traced_program.c

CMD ["./run.sh"]
