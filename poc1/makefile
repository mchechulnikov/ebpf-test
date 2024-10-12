IMAGE_NAME = ebpf-test
CONTAINER_NAME = ebpf-test

.PHONY: all build run

all: build run

build:
	docker build -t $(IMAGE_NAME) .

run:
	docker run --rm -it --privileged --name $(CONTAINER_NAME) $(IMAGE_NAME)
