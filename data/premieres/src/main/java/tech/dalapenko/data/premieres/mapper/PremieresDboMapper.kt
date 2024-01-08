package tech.dalapenko.data.premieres.mapper

import tech.dalapenko.data.core.mapper.DboMapper
import tech.dalapenko.data.premieres.model.Premiere
import tech.dalapenko.database.dbo.CountryDbo
import tech.dalapenko.database.dbo.FullPremiereDataDbo
import tech.dalapenko.database.dbo.GenreDbo
import tech.dalapenko.database.dbo.PremiereDbo
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

internal class PremieresDboMapper @Inject constructor() : DboMapper<FullPremiereDataDbo, Premiere> {

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    override fun mapToModel(dbo: FullPremiereDataDbo): Premiere {
        return Premiere(
            id = dbo.premiere.id,
            ruName = dbo.premiere.nameRu,
            originName = dbo.premiere.nameEn,
            year = dbo.premiere.year,
            posterUrl = dbo.premiere.posterUrl,
            posterUrlPreview = dbo.premiere.posterUrlPreview,
            countryList = dbo.countriesList.map(CountryDbo::name),
            genreList = dbo.genresList.map(GenreDbo::name),
            duration = dbo.premiere.duration,
            premiereDate = dbo.premiere.premiereDate?.let(dateFormatter::format),
        )
    }

    override fun mapToDbo(model: Premiere): FullPremiereDataDbo {
        return FullPremiereDataDbo(
            premiere = PremiereDbo(
                id = model.id,
                nameRu = model.ruName,
                nameEn = model.originName,
                year = model.year,
                posterUrl = model.posterUrl,
                posterUrlPreview = model.posterUrlPreview,
                duration = model.duration,
                premiereDate = model.premiereDate?.let(dateFormatter::parse)
            ),
            genresList = model.genreList.map { GenreDbo(name = it) },
            countriesList = model.countryList.map { CountryDbo(name = it) }
        )
    }
}