#!/bin/bash

chmod +x ./target
./target > /dev/null 2>&1 &

TARGET_PID=$!
TARGET_PATH=$(realpath ./target)

echo Target $TARGET_PATH started with PID $TARGET_PID

java -jar ./build/libs/tracer.jar $TARGET_PATH $TARGET_PID my_function