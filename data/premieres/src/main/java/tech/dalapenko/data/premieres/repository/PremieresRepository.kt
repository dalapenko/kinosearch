package tech.dalapenko.data.premieres.repository

import kotlinx.coroutines.flow.Flow
import tech.dalapenko.data.premieres.model.Premiere

interface PremieresRepository {

    suspend fun getPremieresList(month: String, year: Int): Flow<DataState<List<Premiere>>>
}