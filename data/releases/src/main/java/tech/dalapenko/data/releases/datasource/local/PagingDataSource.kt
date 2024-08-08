package tech.dalapenko.data.releases.datasource.local

import tech.dalapenko.core.database.KinoDatabase
import tech.dalapenko.core.database.Table
import tech.dalapenko.core.database.dao.KinoDao
import tech.dalapenko.core.database.dbo.FullReleaseDataDbo
import tech.dalapenko.core.database.mapper.DboMapper
import tech.dalapenko.core.database.paging.DatabasePagingSource
import tech.dalapenko.data.releases.model.Release

class PagingDataSource(
    database: KinoDatabase,
    private val kinoDao: KinoDao,
    private val dboMapper: DboMapper<FullReleaseDataDbo, Release>
) : DatabasePagingSource<Release>(database, arrayOf(Table.RELEASES)) {

    override suspend fun fetchPageFromDatabase(limit: Int, offset: Int): List<Release> {
        return kinoDao.getReleasesPagedList(limit, offset).map(dboMapper::mapToModel)
    }

    override suspend fun getDatabaseItemCount(): Int {
        return kinoDao.getReleasesItemCount()
    }

    interface Factory {
        fun create(): PagingDataSource
    }
}
