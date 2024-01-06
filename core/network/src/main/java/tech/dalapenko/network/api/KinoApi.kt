package tech.dalapenko.network.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import tech.dalapenko.network.dto.PremiereDto
import tech.dalapenko.network.dto.ReleaseDto
import tech.dalapenko.network.adapter.NetworkResponse
import tech.dalapenko.network.dto.FilmDto
import tech.dalapenko.network.dto.FilmSearchDto

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

    @GET("/api/v2.2/films/{id}")
    suspend fun getFilmById(
        @Path("id") id: Int
    ): NetworkResponse<FilmDto>
}
