package tech.dalapenko.kinosearch.screen

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.text.KButton
import tech.dalapenko.kinosearch.R
import tech.dalapenko.kinosearch.view.DiscoveryFragment

object HomeScreen : KScreen<HomeScreen>() {
    override val layoutId: Int = R.layout.discovery_fragment
    override val viewClass: Class<*> = DiscoveryFragment::class.java

    val searchBar = KButton {
        withId(R.id.search_bar)
    }
}