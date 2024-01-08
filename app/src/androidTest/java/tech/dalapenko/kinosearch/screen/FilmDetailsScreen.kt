package tech.dalapenko.kinosearch.screen

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.text.KTextView
import tech.dalapenko.feature.filmdetails.R
import tech.dalapenko.feature.filmdetails.view.FilmDetailsFragment

object FilmDetailsScreen : KScreen<FilmDetailsScreen>() {
    override val layoutId: Int = R.layout.film_details
    override val viewClass: Class<*> = FilmDetailsFragment::class.java

    val title = KTextView {
        withId(R.id.title)
    }
}