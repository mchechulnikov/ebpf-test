FROM golang:1.19-buster

# Устанавливаем необходимые пакеты
RUN apt-get update && apt-get install -y \
    clang \
    llvm \
    libelf-dev \
    zlib1g-dev \
    make \
    git

# Клонируем репозиторий bpftime и собираем bpftime
RUN git clone https://github.com/eunomia-bpf/bpftime.git /bpftime \
    && cd /bpftime \
    && go mod tidy \
    && go build -o /usr/local/bin/bpftime ./cmd/bpftime

# Копируем исходный код в контейнер
WORKDIR /app
COPY . .

# Собираем eBPF-программу
RUN clang -O2 -target bpf -c bpftime_program.c -o bpftime_program.o

# Компилируем программу, которую будем отслеживать
RUN gcc -o traced_program traced_program.c

# Делаем скрипт run.sh исполняемым
RUN chmod +x run.sh

CMD ["./run.sh"]
