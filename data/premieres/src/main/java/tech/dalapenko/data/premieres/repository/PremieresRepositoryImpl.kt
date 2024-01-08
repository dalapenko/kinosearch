package tech.dalapenko.data.premieres.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tech.dalapenko.data.premieres.datasource.local.LocalDataSource
import tech.dalapenko.data.premieres.datasource.remote.RemoteDataSource
import tech.dalapenko.data.premieres.model.Premiere
import tech.dalapenko.core.network.adapter.NetworkResponse
import tech.dalapenko.core.network.adapter.onError
import tech.dalapenko.core.network.adapter.onException
import tech.dalapenko.core.network.adapter.onSuccess

internal class PremieresRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : PremieresRepository {

    override suspend fun getPremieresList(
        month: String,
        year: Int
    ): Flow<DataState<List<Premiere>>> = flow {
        emit(DataState.Loading)

        val localPremiereList = localDataSource.getPremiereList(month, year)

        if (localPremiereList.isNotEmpty()) {
            emit(DataState.Cached(localPremiereList))
        }

        val remotePremiereList = remoteDataSource.getPremiereList(month, year)

        if (remotePremiereList is NetworkResponse.Success) {
            emit(DataState.Current(remotePremiereList.data))
            localDataSource.insertPremiereList(remotePremiereList.data)
        } else {
            if (localPremiereList.isEmpty()) emit(DataState.FetchError)
        }
    }
}