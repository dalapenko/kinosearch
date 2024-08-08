package tech.dalapenko.feature.releases.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tech.dalapenko.core.basepresentation.network.NetworkConnectivityObserver
import javax.inject.Inject

class CheckNetworkAvailableUseCase @Inject constructor(
    private val connectivityObserver: NetworkConnectivityObserver
) {

    private val availableNetworkStatus = listOf(
        NetworkConnectivityObserver.Status.AVAILABILITY,
        NetworkConnectivityObserver.Status.LOSING
    )

    operator fun invoke(): Flow<Boolean> {
        return connectivityObserver.observe()
            .map { status ->
                status in availableNetworkStatus
            }
    }
}