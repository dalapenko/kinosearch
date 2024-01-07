package tech.dalapenko.releases.model.datasource.local

import tech.dalapenko.releases.model.entity.Release

interface LocalDataSource {

    suspend fun getReleaseList(month: String, year: Int): List<Release>

    suspend fun insertReleaseList(releaseList: List<Release>)
}