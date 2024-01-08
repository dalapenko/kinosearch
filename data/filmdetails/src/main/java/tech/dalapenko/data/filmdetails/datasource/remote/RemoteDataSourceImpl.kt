package tech.dalapenko.data.filmdetails.datasource.remote

import tech.dalapenko.data.filmdetails.model.Film
import tech.dalapenko.core.network.adapter.NetworkResponse
import tech.dalapenko.core.network.adapter.mapOnSuccess
import tech.dalapenko.core.network.api.KinoApi
import tech.dalapenko.core.network.dto.FilmDto
import tech.dalapenko.core.network.mapper.DtoMapper
import javax.inject.Inject

internal class RemoteDataSourceImpl @Inject constructor(
    private val kinoApi: KinoApi,
    private val dtoMapper: DtoMapper<FilmDto, Film>
) : RemoteDataSource {

    override suspend fun getFilmDetails(id: Int): NetworkResponse<Film> {
        val response = kinoApi.getFilmById(id)
        return response.mapOnSuccess(dtoMapper::mapToModel)
    }

}