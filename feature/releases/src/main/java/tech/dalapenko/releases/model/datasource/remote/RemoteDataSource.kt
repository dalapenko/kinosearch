package tech.dalapenko.releases.model.datasource.remote

import tech.dalapenko.network.adapter.NetworkResponse
import tech.dalapenko.releases.model.entity.Release

interface RemoteDataSource {

    suspend fun getReleaseList(month: String, year: Int): NetworkResponse<List<Release>>
}