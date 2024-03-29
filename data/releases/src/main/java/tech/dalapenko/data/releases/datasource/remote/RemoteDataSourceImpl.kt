package tech.dalapenko.data.releases.datasource.remote

import android.content.res.Resources.NotFoundException
import kotlinx.coroutines.Dispatchers
import tech.dalapenko.data.releases.model.Release
import tech.dalapenko.core.network.adapter.NetworkResponse
import tech.dalapenko.core.network.adapter.mapOnSuccess
import tech.dalapenko.core.network.adapter.onSuccess
import tech.dalapenko.core.network.api.KinoApi
import tech.dalapenko.core.network.dto.DigitalReleaseItemDto
import tech.dalapenko.core.network.mapper.DtoMapper
import javax.inject.Inject

internal class RemoteDataSourceImpl @Inject constructor(
    private val kinoApi: KinoApi,
    private val dtoMapper: DtoMapper<DigitalReleaseItemDto, Release>
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
                responseItem.map(dtoMapper::mapToModel)
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