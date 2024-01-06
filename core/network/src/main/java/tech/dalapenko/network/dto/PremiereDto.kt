package tech.dalapenko.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PremiereDto(
    @SerialName("total") val total: Int?,
    @SerialName("items") val items: List<PremiereItemDto>?
)