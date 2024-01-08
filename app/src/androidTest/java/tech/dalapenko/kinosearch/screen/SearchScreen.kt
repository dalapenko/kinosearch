package tech.dalapenko.kinosearch.screen

import android.view.View
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher
import tech.dalapenko.feature.search.R
import tech.dalapenko.feature.search.view.SearchFragment

object SearchScreen : KScreen<SearchScreen>() {
    override val layoutId: Int = R.layout.search
    override val viewClass: Class<*> = SearchFragment::class.java

    val searchField = KEditText {
        withId(R.id.query)
    }

    val searchResult = KRecyclerView(
        builder = {
            withId(R.id.content)
        },
        itemTypeBuilder = {
            itemType(::SearchResultItem)
        }
    )

    class SearchResultItem(parent: Matcher<View>) : KRecyclerItem<SearchResultItem>(parent) {

        val ruTitle = KTextView(parent) {
            withId(R.id.ru_title)
        }
    }

}