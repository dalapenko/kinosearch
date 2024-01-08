package tech.dalapenko.data.filmdetails.datasource.remote

import tech.dalapenko.data.core.mapper.DtoMapper
import tech.dalapenko.data.filmdetails.model.Film
import tech.dalapenko.network.adapter.NetworkResponse
import tech.dalapenko.network.adapter.mapOnSuccess
import tech.dalapenko.network.api.KinoApi
import tech.dalapenko.network.dto.FilmDto
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