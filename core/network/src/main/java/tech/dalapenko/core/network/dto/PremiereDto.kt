package tech.dalapenko.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PremiereDto(
    @SerialName("total") val total: Int? = null,
    @SerialName("items") val items: List<PremiereItemDto>? = null
)