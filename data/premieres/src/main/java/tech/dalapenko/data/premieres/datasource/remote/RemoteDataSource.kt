package tech.dalapenko.data.premieres.datasource.remote

import tech.dalapenko.data.premieres.model.Premiere
import tech.dalapenko.network.adapter.NetworkResponse

interface RemoteDataSource {

    suspend fun getPremiereList(month: String, year: Int): NetworkResponse<List<Premiere>>
}