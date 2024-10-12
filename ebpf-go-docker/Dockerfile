FROM ubuntu:latest

RUN apt update

RUN apt install llvm -y
RUN apt install clang -y
RUN apt install golang -y
RUN apt install libbpf-dev -y
RUN ln -sf /usr/include/asm-generic/ /usr/include/asm

ENV GOPATH /go
ENV PATH $GOPATH/bin:/usr/local/go/bin:$PATH

COPY . /app

WORKDIR /app

RUN go mod init ebpf-test
RUN go mod tidy
RUN go get github.com/cilium/ebpf/cmd/bpf2go

RUN go generate

RUN go build

CMD ["./ebpf-test"]
