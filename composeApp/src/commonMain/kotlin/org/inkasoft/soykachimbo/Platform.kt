package org.inkasoft.soykachimbo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform