from bcc import BPF
import time

program = """
int trace_target_function(struct pt_regs *ctx) {
    bpf_trace_printk("Целевая функция вызвана\\n");
    return 0;
}
"""

b = BPF(text=program)
b.attach_uprobe(name="./traced_program", sym="target_function", fn_name="trace_target_function")

print("Начинаем отслеживание... Нажмите Ctrl+C для выхода.")
b.trace_print()
