package org.inkasoft.soykachimbo.data.local

import kotlinx.serialization.Serializable

@Serializable
data class Course(
    val name: String,
    val display: String,
    val topics: List<Topic>
)

@Serializable
data class Topic(
    val name: String,
    val display: String
)