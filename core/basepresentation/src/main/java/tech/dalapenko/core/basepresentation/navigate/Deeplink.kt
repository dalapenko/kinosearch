package tech.dalapenko.core.basepresentation.navigate

import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest

object Deeplink {

    fun openFilmDetails(id: Int): NavDeepLinkRequest {
        return NavDeepLinkRequest.Builder
            .fromUri("$FILM_DETAILS_DEEPLINK/$id".toUri())
            .build()
    }
}

private const val FILM_DETAILS_DEEPLINK = "kinosearch://filmdetails"