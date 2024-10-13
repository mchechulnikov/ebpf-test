package org.mchechulnikov

import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.mchechulnikov.tool.tools.BpfTrace
import org.mchechulnikov.tracer.linux.BpfTraceTracer

fun main(args: Array<String>) {
    startKoin { modules(tracerModule) }

    NativeProcessTracingDemo().run(
        programPath = args[0],
        pid = args[1].toInt(),
        functionName = args[2]
    )
}

val tracerModule = module {
    singleOf(::BpfTrace)
    singleOf(::BpfTraceTracer)
}

//fun _main() {
//    val cProgramPath = "/app/target"
//
//    val cProgramProcess = ProcessBuilder(cProgramPath).directory(File("/app")).start()
//
//    val pid = cProgramProcess.pid()
//    println("Started target with PID: $pid")
//
//    val functionName = "my_function"
//
//    val bpftraceScript =
//            """
//        uprobe:"$cProgramPath":$functionName
//        /pid == $pid/
//        {
//            // printf("%s, arg0: %d\n", probe, arg0);
//            @args[tid] = arg0;
//        }
//
//        tracepoint:syscalls:sys_enter_write
//        /args->fd == 1/
//        {
//            if (pid == $pid) {
//                printf("Write to stdout detected: %s\n", str(args->buf));
//            }
//        }
//
//        uretprobe:"$cProgramPath":"$functionName"
//        /pid == $pid/
//        {
//            printf("%s, param: %d, result: %d\n", probe, @args[tid], retval);
//            delete(@args[tid]);
//        }
//    """.trimIndent()

//    val scriptFile = "/app/trace_script.bt"
//    File(scriptFile).writeText(bpftraceScript)
//
//    val bpftraceProcess =
//            ProcessBuilder("bpftrace", "-p", "$pid", scriptFile).redirectErrorStream(true).start()
//
//    val reader = BufferedReader(InputStreamReader(bpftraceProcess.inputStream))
//
//    val thread = Thread {
//        try {
//            var line: String?
//            while (true) {
//                line = reader.readLine()
//                if (line == null) {
//                    // Конец потока, выходим из цикла
//                    break
//                }
//                println("Output bpftrace: $line")
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        } finally {
//            // Закрываем поток чтения
//            reader.close()
//        }
//    }
//
//    thread.start()
//
//    // Ждем некоторое время для сбора данных
//    println("bpftrace started, waiting for 10 seconds...")
//    Thread.sleep(10000)
//
//    // Завершаем процессы
//    println("Terminating bpftrace and target...")
//
//    // Попытка корректного завершения процессов
//    bpftraceProcess.destroy()
//    cProgramProcess.destroy()
//
//    // Ожидаем завершения процессов
//    if (bpftraceProcess.waitFor(5, java.util.concurrent.TimeUnit.SECONDS).not()) {
//        println("bpftrace did not terminate, forcing termination...")
//        bpftraceProcess.destroyForcibly()
//    }
//
//    if (cProgramProcess.waitFor(5, java.util.concurrent.TimeUnit.SECONDS).not()) {
//        println("target did not terminate, forcing termination...")
//        cProgramProcess.destroyForcibly()
//    }
//
//    // Закрываем поток чтения после завершения процесса bpftrace
//    reader.close()
//
//    // Ждем завершения потока чтения
//    thread.join()
//    println("Program finished.")
//}
