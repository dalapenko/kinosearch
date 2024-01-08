package tech.dalapenko.core.database.dao

import androidx.room.*
import tech.dalapenko.core.database.dbo.CountryDbo
import tech.dalapenko.core.database.dbo.FullPremiereDataDbo
import tech.dalapenko.core.database.dbo.FullReleaseDataDbo
import tech.dalapenko.core.database.dbo.GenreDbo
import tech.dalapenko.core.database.dbo.PremiereGenreDbo
import tech.dalapenko.core.database.dbo.PremiereCountryDbo
import tech.dalapenko.core.database.dbo.PremiereDbo
import tech.dalapenko.core.database.dbo.ReleaseCountryDbo
import tech.dalapenko.core.database.dbo.ReleaseDbo
import tech.dalapenko.core.database.dbo.ReleaseGenreDbo

@Dao
abstract class KinoDao {

    @Transaction
    @Query("SELECT * FROM releases WHERE releaseDate BETWEEN :fromDate AND :toData ORDER BY releaseDate DESC")
    abstract suspend fun getReleases(fromDate: Long, toData: Long): List<FullReleaseDataDbo>

    @Transaction
    @Query("SELECT * FROM premieres WHERE premiereDate BETWEEN :fromDate AND :toData ORDER BY premiereDate DESC")
    abstract suspend fun getPremieres(fromDate: Long, toData: Long): List<FullPremiereDataDbo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertReleases(releaseList: List<ReleaseDbo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertReleaseGenres(releaseGenres: List<ReleaseGenreDbo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertReleaseCountries(releaseCountries: List<ReleaseCountryDbo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPremiere(premiereList: List<PremiereDbo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPremiereGenres(premiereGenres: List<PremiereGenreDbo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPremiereCountries(premiereCountries: List<PremiereCountryDbo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCountries(countriesList: List<CountryDbo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertGenre(genresList: List<GenreDbo>)

    @Transaction
    open suspend fun insertFullReleaseData(
        releaseList: List<FullReleaseDataDbo>
    ) {
        val releaseGenresAndCountries = releaseList.map { data ->
            val releaseCountries = data.countriesList.map {
                val (releaseId, country) = data.release.id to it.name
                ReleaseCountryDbo(releaseId, country)
            }
            val releaseGenres = data.genresList.map {
                val (releaseId, genre) = data.release.id to it.name
                ReleaseGenreDbo(releaseId, genre)
            }

            releaseCountries to releaseGenres
        }

        insertReleases(releaseList.map(FullReleaseDataDbo::release))
        insertGenre(releaseList.flatMap(FullReleaseDataDbo::genresList))
        insertCountries(releaseList.flatMap(FullReleaseDataDbo::countriesList))
        insertReleaseCountries(releaseGenresAndCountries.flatMap { it.first })
        insertReleaseGenres(releaseGenresAndCountries.flatMap { it.second })
    }

    @Transaction
    open suspend fun insertFullPremiereData(
        premiereList: List<FullPremiereDataDbo>
    ) {
        val premiereGenresAndCountries = premiereList.map { data ->
            val premiereCountries = data.countriesList.map {
                val (premierId, country) = data.premiere.id to it.name
                PremiereCountryDbo(premierId, country)
            }
            val premierGenres = data.genresList.map {
                val (premierId, genre) = data.premiere.id to it.name
                PremiereGenreDbo(premierId, genre)
            }

            premiereCountries to premierGenres
        }

        insertPremiere(premiereList.map(FullPremiereDataDbo::premiere))
        insertGenre(premiereList.flatMap(FullPremiereDataDbo::genresList))
        insertCountries(premiereList.flatMap(FullPremiereDataDbo::countriesList))
        insertPremiereCountries(premiereGenresAndCountries.flatMap { it.first })
        insertPremiereGenres(premiereGenresAndCountries.flatMap { it.second })
    }
}
