package org.mchechulnikov.tool

import org.mchechulnikov.infra.SystemRequirement
import java.nio.file.Path

interface Tool {
    val name: String
    val path: Path?
    val requirements: List<SystemRequirement>
}
