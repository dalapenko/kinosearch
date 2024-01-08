package tech.dalapenko.data.filmdetails.datasource.remote

import tech.dalapenko.data.filmdetails.model.Film
import tech.dalapenko.core.network.adapter.NetworkResponse

interface RemoteDataSource {

    suspend fun getFilmDetails(id: Int): NetworkResponse<Film>
}