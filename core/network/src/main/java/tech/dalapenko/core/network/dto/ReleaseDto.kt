package tech.dalapenko.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReleaseDto(
    @SerialName("page") val page: Int? = null,
    @SerialName("total") val total: Int? = null,
    @SerialName("releases") val items: List<DigitalReleaseItemDto>? = null
)