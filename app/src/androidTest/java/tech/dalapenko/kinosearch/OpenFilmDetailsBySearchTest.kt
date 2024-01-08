package tech.dalapenko.kinosearch

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import tech.dalapenko.kinosearch.screen.FilmDetailsScreen
import tech.dalapenko.kinosearch.screen.HomeScreen
import tech.dalapenko.kinosearch.screen.SearchScreen

class OpenFilmDetailsBySearchTest : TestCase() {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun openFilmDetailsBySearchE2ETest() = run(
        testName = "[E2E] Поиск фильма. Точно совпадение имени. Переход в карточку фильма"
    ) {
        step("Нажать на поисковую строку главного экрана") {
            HomeScreen.searchBar.click()
        }

        step("Ввести в поисковую строку название фильма: $FILM_NAME") {
            SearchScreen.searchField.replaceText(FILM_NAME)
        }

        step("Проверить именя в первой карточке поисковой выдачи") {
            SearchScreen.searchResult.firstChild<SearchScreen.SearchResultItem> {
                ruTitle.hasText(FILM_NAME)
            }
        }

        step("Провалиться в карточку фильма") {
            SearchScreen.searchResult.firstChild(SearchScreen.SearchResultItem::click)
        }

        step("Проверить имя фильма в карточке фильма") {
            FilmDetailsScreen.title.hasText(FILM_NAME)
        }
    }
}

private const val FILM_NAME = "Ла-Ла Ленд"