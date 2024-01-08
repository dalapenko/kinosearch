package tech.dalapenko.data.premieres.model

data class Premiere(
    val id: Int,
    val ruName: String?,
    val originName: String?,
    val year: Int?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val countryList: List<String>,
    val genreList: List<String>,
    val duration: Int?,
    val premiereDate: String?
)