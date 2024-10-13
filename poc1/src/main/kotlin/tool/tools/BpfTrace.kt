package org.mchechulnikov.tool.tools

import org.mchechulnikov.infra.ISA
import org.mchechulnikov.infra.OS
import org.mchechulnikov.infra.SystemRequirement
import org.mchechulnikov.tool.Tool
import java.nio.file.Path
import java.nio.file.Paths

internal class BpfTrace : Tool {
    override val name = "bpftrace"
    override val path: Path? = Paths.get("/usr/bin/bpftrace")
    override val requirements = listOf(
        SystemRequirement(OS.LINUX, ISA.ARM64)
    )
}

