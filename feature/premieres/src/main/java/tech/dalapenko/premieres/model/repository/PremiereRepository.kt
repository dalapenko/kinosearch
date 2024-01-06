package tech.dalapenko.premieres.model.repository

import tech.dalapenko.network.adapter.NetworkResponse
import tech.dalapenko.premieres.model.entity.Premiere

interface PremiereRepository {

    suspend fun getPremiereMovieList(month: String, year: Int): NetworkResponse<List<Premiere>>
}