package org.mchechulnikov.infra

data class SystemRequirement(
    val os: OS,
    val arch: ISA
)