package tech.dalapenko.data.search.model

data class SearchResult(
    val id: Int,
    val ruName: String?,
    val originName: String?,
    val type: String?,
    val year: String?,
    val description: String?,
    val duration: String?,
    val countryList: List<String>,
    val genreList: List<String>,
    val rating: String?,
    val ratingVoteCount: Int?,
    val posterUrl: String?,
    val posterUrlPreview: String?
)