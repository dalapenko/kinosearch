package tech.dalapenko.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReleaseDto(
    @SerialName("page") val page: Int?,
    @SerialName("total") val total: Int?,
    @SerialName("releases") val items: List<DigitalReleaseItemDto>?
)