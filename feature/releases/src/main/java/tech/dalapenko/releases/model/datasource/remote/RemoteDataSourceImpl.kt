package tech.dalapenko.releases.model.datasource.remote

import android.content.res.Resources.NotFoundException
import kotlinx.coroutines.Dispatchers
import tech.dalapenko.network.adapter.NetworkResponse
import tech.dalapenko.network.adapter.mapOnSuccess
import tech.dalapenko.network.adapter.onSuccess
import tech.dalapenko.network.api.KinoApi
import tech.dalapenko.releases.model.entity.Release
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val kinoApi: KinoApi,
    private val dtoMapper: ReleaseDtoMapper
) : RemoteDataSource {

    override suspend fun getReleaseList(
        month: String,
        year: Int
    ): NetworkResponse<List<Release>> = with(Dispatchers.IO) {
        var page = 1
        val releaseList = mutableListOf<Release>()

        while (true) {
            val response = kinoApi.getReleases(
                month = month, year = year, page = page
            )

            var isLast = response is NetworkResponse.Success
            val responseItems = response.mapOnSuccess {
                val responseItem = it.items ?: emptyList()
                responseItem.map(dtoMapper::mapToRelease)
            }

            if (responseItems is NetworkResponse.Success) {
                releaseList.addAll(responseItems.data)
                response.onSuccess {
                    val totalItem = it.total
                    isLast = totalItem == null || totalItem < page * 10
                }
            } else {
                return if (releaseList.isEmpty()) {
                    responseItems
                } else {
                    NetworkResponse.Exception(NotFoundException("Particular data fetch error"))
                }
            }

            if (isLast) break
            page++
        }

        return NetworkResponse.Success(releaseList)
    }

}