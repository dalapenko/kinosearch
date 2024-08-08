package tech.dalapenko.data.releases.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import tech.dalapenko.data.releases.datasource.ReleasesRemoteMediator
import tech.dalapenko.data.releases.datasource.local.PagingDataSource
import tech.dalapenko.data.releases.model.Release

@ExperimentalPagingApi
class ReleaseRepositoryImpl(
    private val pagingDataSourceFactory: PagingDataSource.Factory,
    private val releaseRemoteMediatorFactory: ReleasesRemoteMediator.Factory
) : ReleaseRepository {

    override fun getReleasePager(
        year: Int,
        month: String
    ): Pager<Int, Release> {
        return Pager(
            config = PagingConfig(
                pageSize = RELEASE_PAGE_SIZE,
                initialLoadSize = INITIAL_LOAD_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = releaseRemoteMediatorFactory.create(year, month),
            pagingSourceFactory = { pagingDataSourceFactory.create() }
        )
    }
}

private const val RELEASE_PAGE_SIZE = 20
private const val INITIAL_LOAD_SIZE = 19