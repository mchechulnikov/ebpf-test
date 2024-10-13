package org.mchechulnikov.tracer

data class TraceEvent(
    val timestamp: Long,
    val content: String,
)