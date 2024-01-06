package tech.dalapenko.premieres.model.repository

import tech.dalapenko.network.adapter.NetworkResponse
import tech.dalapenko.network.adapter.mapOnSuccess
import tech.dalapenko.network.api.KinoApi
import tech.dalapenko.premieres.model.entity.Premiere
import javax.inject.Inject

class PremiereRepositoryImpl @Inject constructor(
    private val kinoApi: KinoApi
) : PremiereRepository {

    override suspend fun getPremiereMovieList(
        month: String,
        year: Int
    ): NetworkResponse<List<Premiere>> {
        val response = kinoApi.getPremieres(
            month = month,
            year = year
        )

        return response.mapOnSuccess {
            it.items?.map(Premiere::mapFromDto) ?: emptyList()
        }
    }
}