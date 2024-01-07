package tech.dalapenko.releases.model.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tech.dalapenko.network.adapter.NetworkResponse
import tech.dalapenko.releases.model.DataState
import tech.dalapenko.releases.model.datasource.local.LocalDataSource
import tech.dalapenko.releases.model.datasource.remote.RemoteDataSource
import tech.dalapenko.releases.model.entity.Release
import javax.inject.Inject

class ReleaseRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ReleaseRepository {

    override suspend fun getReleasesList(
        month: String,
        year: Int
    ): Flow<DataState<List<Release>>> = flow {
        val localReleaseList = localDataSource.getReleaseList(month, year)

        emit(DataState.Cached(localReleaseList))

        val remoteReleaseList =
            remoteDataSource.getReleaseList(month, year) as? NetworkResponse.Success ?: return@flow

        emit(DataState.Current(remoteReleaseList.data))

        localDataSource.insertReleaseList(remoteReleaseList.data)
    }
}