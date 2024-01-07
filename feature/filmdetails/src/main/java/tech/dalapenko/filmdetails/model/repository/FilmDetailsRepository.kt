package tech.dalapenko.filmdetails.model.repository

import tech.dalapenko.filmdetails.model.entity.Film
import tech.dalapenko.network.adapter.NetworkResponse

interface FilmDetailsRepository {

    suspend fun getFilmDetails(id: Int): NetworkResponse<Film>
}