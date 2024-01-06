package tech.dalapenko.releases.model.repository

import tech.dalapenko.network.adapter.NetworkResponse
import tech.dalapenko.releases.model.entity.Release

interface ReleaseRepository {

    suspend fun getReleaseMovieList(month: String, year: Int): NetworkResponse<List<Release>>
}