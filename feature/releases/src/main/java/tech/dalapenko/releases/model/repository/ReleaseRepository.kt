package tech.dalapenko.releases.model.repository

import kotlinx.coroutines.flow.Flow
import tech.dalapenko.releases.model.DataState
import tech.dalapenko.releases.model.entity.Release

interface ReleaseRepository {

    suspend fun getReleasesList(month: String, year: Int): Flow<DataState<List<Release>>>
}