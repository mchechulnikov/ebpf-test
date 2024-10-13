package org.mchechulnikov.infra

// Instruction Set Architecture (ISA) of a processor
enum class ISA {
    X86_64,
    X86,
    ARM64,
    ARM,
    OTHER;

    fun detect(): ISA =
        System.getProperty("os.arch").lowercase()
            .let {
                when {
                    it.contains("x86_64") -> X86_64
                    it.contains("x86") -> X86
                    it.contains("aarch64") || it.contains("arm64") -> ARM64
                    it.contains("arm") -> ARM
                    else -> OTHER
                }
            }
}