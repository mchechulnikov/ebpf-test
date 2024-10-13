IMAGE_NAME = ebpf-test
CONTAINER_NAME = ebpf-test

.PHONY: all build run

all: build run

build:
	docker build -t $(IMAGE_NAME) .

run:
	docker run --rm -it --privileged -v /sys/kernel/debug/:/sys/kernel/debug:rw --pid=host --name $(CONTAINER_NAME) $(IMAGE_NAME)
