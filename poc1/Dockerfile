# Базовый образ
FROM ubuntu

# Установка необходимых пакетов
RUN apt-get update && apt-get install -y \
    build-essential \
    bpftrace \
    linux-headers-$(uname -r) \
    openjdk-11-jdk \
    wget \
    git \
    unzip \
    libelf-dev \
    && rm -rf /var/lib/apt/lists/*

# Установка Kotlin Compiler
RUN wget https://github.com/JetBrains/kotlin/releases/download/v1.8.0/kotlin-compiler-1.8.0.zip \
    && unzip kotlin-compiler-1.8.0.zip -d /opt \
    && rm kotlin-compiler-1.8.0.zip

ENV PATH="/opt/kotlinc/bin:${PATH}"

COPY . /app
WORKDIR /app

# Сборка программы на C
RUN gcc -g -O0 -o target target.c

# Компиляция Kotlin программы
RUN kotlinc tracer.kt -include-runtime -d tracer.jar

# Указываем команду запуска
CMD ["java", "-jar", "tracer.jar"]
