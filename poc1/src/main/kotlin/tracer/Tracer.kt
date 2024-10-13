package org.mchechulnikov.tracer

import org.mchechulnikov.infra.SystemRequirement
import org.mchechulnikov.tracee.Tracee

interface Tracer {
    val compatibleWith: List<SystemRequirement>

    fun trace(tracee: Tracee, target: TraceTarget)
}

