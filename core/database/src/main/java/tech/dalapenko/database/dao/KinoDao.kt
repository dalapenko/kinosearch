package tech.dalapenko.database.dao

import androidx.room.*
import tech.dalapenko.database.dbo.CountryDbo
import tech.dalapenko.database.dbo.FullReleaseDataDbo
import tech.dalapenko.database.dbo.GenreDbo
import tech.dalapenko.database.dbo.ReleaseCountryDbo
import tech.dalapenko.database.dbo.ReleaseDbo
import tech.dalapenko.database.dbo.ReleaseGenreDbo

@Dao
abstract class KinoDao {

    @Transaction
    @Query("SELECT * FROM releases WHERE releaseDate BETWEEN :fromDate AND :toData ORDER BY releaseDate DESC")
    abstract suspend fun getReleases(fromDate: Long, toData: Long): List<FullReleaseDataDbo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertReleases(releaseList: List<ReleaseDbo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertReleaseGenres(releaseGenres: List<ReleaseGenreDbo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertReleaseCountries(releaseCountries: List<ReleaseCountryDbo>)

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
}