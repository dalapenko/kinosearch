package tech.dalapenko.data.releases.repository

import kotlinx.coroutines.flow.Flow
import tech.dalapenko.data.releases.model.Release

interface ReleaseRepository {

    suspend fun getReleasesList(month: String, year: Int): Flow<DataState<List<Release>>>
}