package tech.dalapenko.data.filmdetails.repository

import kotlinx.coroutines.flow.Flow
import tech.dalapenko.data.filmdetails.model.Film

interface FilmDetailsRepository {

    suspend fun getFilmDetails(id: Int): Flow<DataState<Film>>
}