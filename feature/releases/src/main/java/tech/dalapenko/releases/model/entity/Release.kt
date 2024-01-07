package tech.dalapenko.releases.model.entity

data class Release(
    val id: Int,
    val ruName: String?,
    val originName: String?,
    val year: Int?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val countryList: List<String>,
    val genreList: List<String>,
    val rating: Float,
    val ratingVoteCount: Int,
    val expectationsRating: Float,
    val expectationsRatingVoteCount: Int,
    val duration: Int?,
    val releaseDate: String?
)