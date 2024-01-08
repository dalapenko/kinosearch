package tech.dalapenko.data.releases.datasource.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tech.dalapenko.data.core.mapper.DboMapper
import tech.dalapenko.data.releases.model.Release
import tech.dalapenko.core.database.dao.KinoDao
import tech.dalapenko.core.database.dbo.FullReleaseDataDbo
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

internal class LocalDataSourceImpl @Inject constructor(
    private val kinoDao: KinoDao,
    private val dboMapper: DboMapper<FullReleaseDataDbo, Release>
) : LocalDataSource {

    override suspend fun getReleaseList(month: String, year: Int): List<Release> {
        return withContext(Dispatchers.IO) {
            val monthValue = Month.entries.find { it.name.equals(month, ignoreCase = true) }?.value
                ?: return@withContext emptyList()
            val date = LocalDate.of(year, monthValue, 1)
            val startOfMonth = date
                .atStartOfDay()
                .let { ZonedDateTime.of(it, ZoneId.systemDefault()) }
                .toInstant().toEpochMilli()
            val endOfMonth = LocalDateTime
                .of(date.withDayOfMonth(date.lengthOfMonth()), LocalTime.MAX)
                .let { ZonedDateTime.of(it, ZoneId.systemDefault()) }
                .toInstant().toEpochMilli()
            kinoDao.getReleases(startOfMonth, endOfMonth).map(dboMapper::mapToModel)
        }
    }

    override suspend fun insertReleaseList(releaseList: List<Release>) {
        withContext(Dispatchers.IO) {
            val fullReleaseDbo = releaseList.map(dboMapper::mapToDbo)
            kinoDao.insertFullReleaseData(fullReleaseDbo)
        }
    }
}