package tech.dalapenko.data.releases.datasource.remote

import tech.dalapenko.data.releases.model.Release
import tech.dalapenko.network.adapter.NetworkResponse

interface RemoteDataSource {

    suspend fun getReleaseList(month: String, year: Int): NetworkResponse<List<Release>>
}