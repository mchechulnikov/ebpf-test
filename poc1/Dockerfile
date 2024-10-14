FROM ubuntu

# dependencies
RUN apt-get update && apt-get install -y \
    build-essential \
    bpftrace \
    linux-headers-$(uname -r) \
    openjdk-11-jdk \
    wget \
    unzip \
    libelf-dev \
    && rm -rf /var/lib/apt/lists/*

# kotlin
RUN wget https://github.com/JetBrains/kotlin/releases/download/v1.8.0/kotlin-compiler-1.8.0.zip \
    && unzip kotlin-compiler-1.8.0.zip -d /opt \
    && rm kotlin-compiler-1.8.0.zip
ENV PATH="/opt/kotlinc/bin:${PATH}"

COPY . /app
WORKDIR /app

# traced program
RUN gcc -O0 -o target ./src/main/c/target.c

# build tracer
RUN ./gradlew shadowJar --no-daemon

RUN chmod +x ./run.sh

CMD ["./run.sh"]
