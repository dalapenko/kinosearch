package tech.dalapenko.releases.model.repository

import android.content.res.Resources.NotFoundException
import tech.dalapenko.network.adapter.NetworkResponse
import tech.dalapenko.network.adapter.mapOnSuccess
import tech.dalapenko.network.adapter.onSuccess
import tech.dalapenko.network.api.KinoApi
import tech.dalapenko.releases.model.entity.Release
import javax.inject.Inject

class ReleaseRepositoryImpl @Inject constructor(
    private val kinoApi: KinoApi
) : ReleaseRepository {

    override suspend fun getReleaseMovieList(
        month: String,
        year: Int
    ): NetworkResponse<List<Release>> {
        var page = 1
        val releaseList = mutableListOf<Release>()

        while (true) {
            val response = kinoApi.getReleases(
                month = month, year = year, page = page
            )

            var isLast = response is NetworkResponse.Success
            val responseItems = response.mapOnSuccess {
                val responseItem = it.items ?: emptyList()
                responseItem.map(Release::mapFromDto)
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