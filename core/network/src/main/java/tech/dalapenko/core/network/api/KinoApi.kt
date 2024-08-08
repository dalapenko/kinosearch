package tech.dalapenko.core.network.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import tech.dalapenko.core.network.dto.PremiereDto
import tech.dalapenko.core.network.dto.ReleaseDto
import tech.dalapenko.core.network.adapter.NetworkResponse
import tech.dalapenko.core.network.dto.FilmDto
import tech.dalapenko.core.network.dto.FilmSearchDto

interface KinoApi {

    @GET("v2.2/films/premieres")
    suspend fun getPremieres(
        @Query("year") year: Int,
        @Query("month") month: String
    ): NetworkResponse<PremiereDto>

    @GET("v2.1/films/releases")
    suspend fun getReleases(
        @Query("year") year: Int,
        @Query("month") month: String,
        @Query("page") page: Int
    ): NetworkResponse<ReleaseDto>

    @GET("v2.1/films/search-by-keyword")
    suspend fun getSearchResult(
        @Query("keyword") keyword: String,
        @Query("page") page: Int
    ): NetworkResponse<FilmSearchDto>

    @GET("v2.2/films/{id}")
    suspend fun getFilmById(
        @Path("id") id: Int
    ): NetworkResponse<FilmDto>

    companion object {
        const val MAX_PAGE_SIZE = 20
    }
}
