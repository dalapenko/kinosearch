package tech.dalapenko.core.basepresentation.network

import kotlinx.coroutines.flow.Flow

interface NetworkConnectivityObserver {

    fun observe(): Flow<Status>

    enum class Status {
        AVAILABILITY, UNAVAILABILITY, LOSING, LOST
    }
}