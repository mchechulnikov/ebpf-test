package org.mchechulnikov.infra

// Operating system
enum class OS {
    WINDOWS,
    LINUX,
    MACOS,
    OTHER;

    fun detect(): OS =
        System.getProperty("os.name").lowercase()
            .let {
                when {
                    it.contains("win") -> WINDOWS
                    it.contains("nux") || it.contains("nix") -> LINUX
                    it.contains("mac") -> MACOS
                    else -> OTHER
                }
            }
}

