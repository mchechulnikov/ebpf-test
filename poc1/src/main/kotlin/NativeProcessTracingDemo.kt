package org.mchechulnikov

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mchechulnikov.tracee.NativeTracee
import org.mchechulnikov.tracer.TraceTarget
import org.mchechulnikov.tracer.Tracer
import org.mchechulnikov.tracer.linux.BpfTraceTracer

class NativeProcessTracingDemo : KoinComponent {
    private val tracer: Tracer by inject<BpfTraceTracer>()

    fun run(programPath: String, pid: Int, functionName: String) {
        val tracee = NativeTracee(pid, programPath)
        val target = TraceTarget(functionName)
        tracer.trace(tracee, target)
    }
}