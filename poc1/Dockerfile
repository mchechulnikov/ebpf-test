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

RUN mkdir /app
WORKDIR /app

COPY build.gradle.kts /app
COPY settings.gradle.kts app
COPY gradle /app/gradle
COPY gradlew /app
RUN ./gradlew build --no-daemon

COPY . /app


# build tracer
RUN ./gradlew shadowJar --no-daemon

# build tracee
RUN gcc -O0 -o target ./src/main/c/target.c

RUN chmod +x ./run.sh

CMD ["./run.sh"]
