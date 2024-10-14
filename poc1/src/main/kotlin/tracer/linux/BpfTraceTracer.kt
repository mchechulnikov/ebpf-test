package org.mchechulnikov.tracer.linux

import org.mchechulnikov.infra.ISA
import org.mchechulnikov.infra.OS
import org.mchechulnikov.infra.SystemRequirement
import org.mchechulnikov.tool.tools.BpfTrace
import org.mchechulnikov.tracee.Tracee
import org.mchechulnikov.tracer.TraceTarget
import org.mchechulnikov.tracer.Tracer
import java.io.File

internal class BpfTraceTracer(
    private val bpftrace: BpfTrace,
) : Tracer {
    override val compatibleWith = listOf(
        SystemRequirement(OS.LINUX, ISA.ARM64)
    )

    override fun trace(tracee: Tracee, target: TraceTarget) {
        println("Start tracing with bpftrace...")

        val scriptFile = "trace-script.bt"
        File(scriptFile).writeText(getScriptForJavaTracee(tracee, target))

        val command = listOf(
            bpftrace.path.toString(),
            "-p", tracee.pid.toString(),
            scriptFile
        )
        println("Command: " + command.joinToString(" "))
        runCatching {
            val process = ProcessBuilder(command).start()

            process.inputStream.bufferedReader().use { reader ->
                while (process.isAlive || reader.ready()) {
                    reader.readLine()?.let { line ->
                        println("[bpftrace]: $line")
                    }
                }
            }

            process.errorStream.bufferedReader().use { errorReader ->
                while (process.isAlive || errorReader.ready()) {
                    errorReader.readLine()?.let { errorLine ->
                        println("[bpftrace err]: $errorLine")
                    }
                }
            }

            process.waitFor()
        }.onFailure { e ->
            println("Error: ${e.message}")
        }
    }

    private fun getScriptForNativeTracee(tracee: Tracee, target: TraceTarget) = """
        uprobe:"${tracee.path}":${target.functionName}
        /pid == ${tracee.pid}/
        {
            printf("%lld %s, param: %d\n", nsecs, probe, arg0);
            @args[tid] = arg0;
        }

        uretprobe:"${tracee.path}":"${target.functionName}"
        /pid == ${tracee.pid}/
        {
            printf("%lld %s, param: %d, result: %d\n", nsecs, probe, @args[tid], retval);
            delete(@args[tid]);
        }
        
        tracepoint:syscalls:sys_enter_write
        /args->fd == 1/
        {
            if (pid == ${tracee.pid}) {
                printf("Write to stdout detected: %s\n", str(args->buf));
            }
        }
    """.trimIndent()

    private fun getScriptForJavaTracee(tracee: Tracee, target: TraceTarget) = """
        uprobe:/proc/${tracee.pid}/root/usr/lib/jvm/java-11-openjdk-arm64/lib/server/libjvm.so:JVM_*
        {
            printf("Function called: %s\n", probe);
            print(ustack);
        }
    """.trimIndent()
}


