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
COPY gradle.properties /app
RUN ./gradlew build --no-daemon

RUN mkdir /app/src
COPY src/ /app/src/

# build tracer
RUN ./gradlew shadowJar --no-daemon

RUN mkdir /app/victims
COPY victims/ /app/victims/

## build native tracee
#RUN gcc -O0 -o target ./victims/target.c
## run native tracee demo
#COPY ./run-native.sh /app
#RUN chmod +x ./run-native.sh
#CMD ["./run-native.sh"]

# build Java tracee
RUN javac ./victims/Main.java
# run Java tracee demo
COPY ./run-java.sh /app
RUN chmod +x ./run-java.sh
CMD ["./run-java.sh"]

