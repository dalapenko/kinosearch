package tech.dalapenko.data.releases.mapper

import tech.dalapenko.data.core.mapper.DboMapper
import tech.dalapenko.data.releases.model.Release
import tech.dalapenko.database.dbo.CountryDbo
import tech.dalapenko.database.dbo.FullReleaseDataDbo
import tech.dalapenko.database.dbo.GenreDbo
import tech.dalapenko.database.dbo.ReleaseDbo
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

internal class ReleaseDboMapper @Inject constructor() : DboMapper<FullReleaseDataDbo, Release> {

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    override fun mapToModel(dbo: FullReleaseDataDbo): Release {
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

    override fun mapToDbo(model: Release): FullReleaseDataDbo {
        return FullReleaseDataDbo(
            release = ReleaseDbo(
                id = model.id,
                nameRu = model.ruName,
                nameEn = model.originName,
                year = model.year,
                posterUrl = model.posterUrl,
                posterUrlPreview = model.posterUrlPreview,
                rating = model.rating,
                ratingVoteCount = model.ratingVoteCount,
                expectationsRating = model.expectationsRating,
                expectationsRatingVoteCount = model.expectationsRatingVoteCount,
                duration = model.duration,
                releaseDate = model.releaseDate?.let(dateFormatter::parse)
            ),
            genresList = model.genreList.map { GenreDbo(name = it) },
            countriesList = model.countryList.map { CountryDbo(name = it) }
        )
    }
}