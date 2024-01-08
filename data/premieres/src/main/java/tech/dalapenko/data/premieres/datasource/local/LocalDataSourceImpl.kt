package tech.dalapenko.data.premieres.datasource.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tech.dalapenko.data.core.mapper.DboMapper
import tech.dalapenko.data.premieres.model.Premiere
import tech.dalapenko.database.dao.KinoDao
import tech.dalapenko.database.dbo.FullPremiereDataDbo
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

internal class LocalDataSourceImpl @Inject constructor(
    private val kinoDao: KinoDao,
    private val dboMapper: DboMapper<FullPremiereDataDbo, Premiere>
) : LocalDataSource {

    override suspend fun getPremiereList(month: String, year: Int): List<Premiere> {
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
            kinoDao.getPremieres(startOfMonth, endOfMonth).map(dboMapper::mapToModel)
        }
    }

    override suspend fun insertPremiereList(premiereList: List<Premiere>) {
        withContext(Dispatchers.IO) {
            val fullReleaseDbo = premiereList.map(dboMapper::mapToDbo)
            kinoDao.insertFullPremiereData(fullReleaseDbo)
        }
    }
}