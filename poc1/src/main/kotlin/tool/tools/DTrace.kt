package org.mchechulnikov.tool.tools

import org.mchechulnikov.infra.ISA
import org.mchechulnikov.infra.OS
import org.mchechulnikov.infra.SystemRequirement
import org.mchechulnikov.tool.Tool
import java.nio.file.Path
import java.nio.file.Paths

internal class DTrace : Tool {
    override val name = "dtrace"
    override val path: Path? = Paths.get("/usr/sbin/dtrace")
    override val requirements = listOf(
        SystemRequirement(OS.MACOS, ISA.ARM64)
    )
}