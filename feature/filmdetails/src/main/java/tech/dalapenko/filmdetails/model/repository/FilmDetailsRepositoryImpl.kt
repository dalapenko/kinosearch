package tech.dalapenko.filmdetails.model.repository

import tech.dalapenko.filmdetails.model.entity.Film
import tech.dalapenko.network.adapter.NetworkResponse
import tech.dalapenko.network.adapter.mapOnSuccess
import tech.dalapenko.network.api.KinoApi
import javax.inject.Inject

class FilmDetailsRepositoryImpl @Inject constructor(
    private val kinoApi: KinoApi
) : FilmDetailsRepository {

    override suspend fun getFilmDetails(id: Int): NetworkResponse<Film> {
        val response = kinoApi.getFilmById(id)
        return response.mapOnSuccess(Film::mapFromDto)
    }
}