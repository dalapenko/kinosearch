package tech.dalapenko.data.releases.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tech.dalapenko.data.releases.datasource.local.LocalDataSource
import tech.dalapenko.data.releases.datasource.remote.RemoteDataSource
import tech.dalapenko.data.releases.model.Release
import tech.dalapenko.network.adapter.NetworkResponse
import javax.inject.Inject

internal class ReleaseRepositoryImpl(
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