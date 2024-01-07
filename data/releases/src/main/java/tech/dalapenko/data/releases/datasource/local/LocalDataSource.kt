package tech.dalapenko.data.releases.datasource.local

import tech.dalapenko.data.releases.model.Release

interface LocalDataSource {

    suspend fun getReleaseList(month: String, year: Int): List<Release>

    suspend fun insertReleaseList(releaseList: List<Release>)
}