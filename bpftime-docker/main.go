package main

import (
    "log"
    "time"

    "github.com/aquasecurity/libbpfgo"
)

func main() {
    // Загружаем eBPF-программу
    module, err := libbpfgo.NewModuleFromFile("bpftime_program.o")
    if err != nil {
        log.Fatalf("Не удалось создать модуль: %v", err)
    }
    defer module.Close()

    // Инициализируем программу
    prog, err := module.GetProgram("trace_write")
    if err != nil {
        log.Fatalf("Не удалось получить программу: %v", err)
    }

    // Устанавливаем uprobe на функцию write
    uprobe, err := prog.AttachUprobe("", "write")
    if err != nil {
        log.Fatalf("Не удалось установить uprobe: %v", err)
    }
    defer uprobe.Close()

    // Читаем вывод из кольцевого буфера
    ringBuffer, err := module.InitRingBuf("events", handleEvent)
    if err != nil {
        log.Fatalf("Не удалось инициализировать кольцевой буфер: %v", err)
    }
    defer ringBuffer.Stop()

    ringBuffer.Start()
    // Блокируем основной поток
    for {
        time.Sleep(1 * time.Second)
    }
}

func handleEvent(data []byte) {
    log.Printf("%s", string(data))
}
