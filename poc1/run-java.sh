#!/bin/bash

echo Current PID $BASHPID

java -XX:+PreserveFramePointer -cp $(pwd)/victims Main &

TARGET_PID=$!
TARGET_PATH=$(which java)

echo Target $TARGET_PATH started with PID $TARGET_PID

java -jar ./build/libs/tracer.jar $TARGET_PATH $TARGET_PID myFunction