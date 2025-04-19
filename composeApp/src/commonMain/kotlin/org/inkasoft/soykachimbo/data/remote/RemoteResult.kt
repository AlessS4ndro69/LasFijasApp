package org.inkasoft.soykachimbo.data.remote

import coil3.decode.ImageSource
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteResult(
    val results: List<RemoteQuestion>
)

@Serializable
data class RemoteQuestion(
    val url: String,
    val metadata: MetadataQuestion
)

@Serializable
data class MetadataQuestion(
    val answer: String,
    val topic: String,
    @SerialName("topiccode") val topicCode: Int,
    val course: String,
    @SerialName("correctalternative") val correctAlternative: String,
    @SerialName("coursecode") val courseCode: Int,
    @SerialName("solution_url") val solutionUrl: String,
    @SerialName("expiration_time") val expirationTime: Long
)