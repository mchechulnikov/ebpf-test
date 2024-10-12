// bpftime_program.c
#include <linux/ptrace.h>

int tracepoint__syscalls__sys_enter_write(struct pt_regs *ctx, int fd, const char *buf, size_t count) {
    char msg[256];
    bpf_probe_read_user(msg, sizeof(msg), buf);
    bpf_trace_printk("Запись в stdout: %s\n", msg);
    return 0;
}
