package tech.dalapenko.data.premieres.datasource.local

import tech.dalapenko.data.premieres.model.Premiere

interface LocalDataSource {

    suspend fun getPremiereList(month: String, year: Int): List<Premiere>

    suspend fun insertPremiereList(premiereList: List<Premiere>)
}