package main

//go:generate go run -mod=mod github.com/cilium/ebpf/cmd/bpf2go -cc clang -cflags "-I/usr/include/x86_64-linux-gnu" counter counter.c
