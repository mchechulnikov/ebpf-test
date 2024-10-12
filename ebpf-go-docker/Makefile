IMAGE_NAME=ebpf-test
CONTAINER_NAME=ebpf-test
DOCKERFILE_PATH=.

build:
	docker build -t $(IMAGE_NAME) $(DOCKERFILE_PATH)

run:
	docker run --rm -it --name $(CONTAINER_NAME) --privileged $(IMAGE_NAME)

clear:
	docker rm -f $(CONTAINER_NAME)
