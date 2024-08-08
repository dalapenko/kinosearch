package tech.dalapenko.data.releases.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import tech.dalapenko.core.database.dao.KinoDao
import tech.dalapenko.core.database.dbo.FullReleaseDataDbo
import tech.dalapenko.core.database.mapper.DboMapper
import tech.dalapenko.core.network.adapter.NetworkResponse
import tech.dalapenko.core.network.api.KinoApi
import tech.dalapenko.core.network.dto.DigitalReleaseItemDto
import tech.dalapenko.core.network.mapper.DtoMapper
import tech.dalapenko.data.releases.model.Release
import java.util.concurrent.atomic.AtomicInteger

@ExperimentalPagingApi
class ReleasesRemoteMediator @AssistedInject constructor(
    private val kinoDao: KinoDao,
    private val kinoApi: KinoApi,
    private val dboMapper: DboMapper<FullReleaseDataDbo, Release>,
    private val dtoMapper: DtoMapper<DigitalReleaseItemDto, Release>,
    @Assisted private val year: Int,
    @Assisted private val month: String
) : RemoteMediator<Int, Release>() {

    private val lastPage = AtomicInteger(INITIAL_PAGE)

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Release>
    ): MediatorResult {
        val loadKey = when (loadType) {
            LoadType.PREPEND -> null
            LoadType.REFRESH -> 1.also(lastPage::set)
            LoadType.APPEND -> lastPage.incrementAndGet()
        } ?: return MediatorResult.Success(endOfPaginationReached = true)

        val mediatorResult = when (val releases = kinoApi.getReleases(year, month, loadKey)) {
            is NetworkResponse.Exception -> {
                MediatorResult.Error(releases.throwable)
            }

            is NetworkResponse.Error -> {
                MediatorResult.Error(releases.throwable)
            }

            is NetworkResponse.Success -> {
                val releaseDboList = releases.data.items
                    ?.map(dtoMapper::mapToModel)
                    ?.map(dboMapper::mapToDbo)
                    ?: emptyList()

                if (loadType == LoadType.REFRESH) {
                    kinoDao.clearAndInsertFullReleaseData(releaseDboList)
                } else {
                    kinoDao.insertFullReleaseData(releaseDboList)
                }

                val responseItemSize = (releases.data.items ?: emptyList()).size
                MediatorResult.Success(
                    endOfPaginationReached = responseItemSize < state.config.pageSize
                )
            }
        }

        return mediatorResult
    }

    @AssistedFactory
    interface Factory {
        fun create(year: Int, month: String): ReleasesRemoteMediator
    }
}

private const val INITIAL_PAGE = 0
