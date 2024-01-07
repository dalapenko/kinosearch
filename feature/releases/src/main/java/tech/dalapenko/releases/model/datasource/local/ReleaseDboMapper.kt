package tech.dalapenko.releases.model.datasource.local

import tech.dalapenko.database.dbo.CountryDbo
import tech.dalapenko.database.dbo.FullReleaseDataDbo
import tech.dalapenko.database.dbo.GenreDbo
import tech.dalapenko.database.dbo.ReleaseDbo
import tech.dalapenko.releases.model.entity.Release
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class ReleaseDboMapper @Inject constructor() {

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    fun mapToRelease(dbo: FullReleaseDataDbo): Release {
        return Release(
            id = dbo.release.id,
            ruName = dbo.release.nameRu,
            originName = dbo.release.nameEn,
            year = dbo.release.year,
            posterUrl = dbo.release.posterUrl,
            posterUrlPreview = dbo.release.posterUrlPreview,
            countryList = dbo.countriesList.map(CountryDbo::name),
            genreList = dbo.genresList.map(GenreDbo::name),
            rating = dbo.release.rating ?: 0f,
            ratingVoteCount = dbo.release.ratingVoteCount ?: 0,
            expectationsRating = dbo.release.expectationsRating ?: 0f,
            expectationsRatingVoteCount = dbo.release.expectationsRatingVoteCount ?: 0,
            duration = dbo.release.duration,
            releaseDate = dbo.release.releaseDate?.let(dateFormatter::format),
        )
    }

    fun mapToDbo(release: Release): FullReleaseDataDbo {
        return FullReleaseDataDbo(
            release = ReleaseDbo(
                id = release.id,
                nameRu = release.ruName,
                nameEn = release.originName,
                year = release.year,
                posterUrl = release.posterUrl,
                posterUrlPreview = release.posterUrlPreview,
                rating = release.rating,
                ratingVoteCount = release.ratingVoteCount,
                expectationsRating = release.expectationsRating,
                expectationsRatingVoteCount = release.expectationsRatingVoteCount,
                duration = release.duration,
                releaseDate = release.releaseDate?.let(dateFormatter::parse)
            ),
            genresList = release.genreList.map { GenreDbo(name = it) },
            countriesList = release.countryList.map { CountryDbo(name = it) }
        )
    }
}