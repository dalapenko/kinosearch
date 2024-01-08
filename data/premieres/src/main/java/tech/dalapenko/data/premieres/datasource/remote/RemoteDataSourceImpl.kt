package tech.dalapenko.data.premieres.datasource.remote

import kotlinx.coroutines.Dispatchers
import tech.dalapenko.data.core.mapper.DtoMapper
import tech.dalapenko.data.premieres.model.Premiere
import tech.dalapenko.core.network.adapter.NetworkResponse
import tech.dalapenko.core.network.adapter.mapOnSuccess
import tech.dalapenko.core.network.api.KinoApi
import tech.dalapenko.core.network.dto.PremiereItemDto
import javax.inject.Inject

internal class RemoteDataSourceImpl @Inject constructor(
    private val kinoApi: KinoApi,
    private val dtoMapper: DtoMapper<PremiereItemDto, Premiere>
) : RemoteDataSource {

    override suspend fun getPremiereList(
        month: String,
        year: Int
    ): NetworkResponse<List<Premiere>> = with(Dispatchers.IO) {
        val response = kinoApi.getPremieres(
            month = month,
            year = year
        )

        return response.mapOnSuccess {
            it.items?.map(dtoMapper::mapToModel) ?: emptyList()
        }
    }

}