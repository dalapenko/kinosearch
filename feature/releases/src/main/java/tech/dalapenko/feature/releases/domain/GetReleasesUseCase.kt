package tech.dalapenko.feature.releases.domain

import androidx.paging.PagingData
import androidx.paging.insertSeparators
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tech.dalapenko.data.releases.repository.ReleaseRepository
import tech.dalapenko.feature.releases.model.ReleasePagingItem.DateSeparatorItem
import tech.dalapenko.feature.releases.model.ReleasePagingItem.ReleaseItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetReleasesUseCase @Inject constructor(
    private val releasesPagingRepository: ReleaseRepository
) {

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    operator fun invoke(
        month: String, year: Int
    ): Flow<PagingData<tech.dalapenko.feature.releases.model.ReleasePagingItem>> {
        return releasesPagingRepository.getReleasePager(year, month)
            .flow.map { pagingData ->
                pagingData
                    .map(::ReleaseItem)
                    .insertSeparators { before: ReleaseItem?, after: ReleaseItem? ->
                        when {
                            before == null && after != null -> {
                                after.release.releaseDate
                                    ?.let(::parsePremierDate)
                                    ?.format(dateTimeFormatter)
                                    ?.let(::DateSeparatorItem)
                            }

                            before != null && after != null
                                    && before.release.releaseDate != after.release.releaseDate -> {
                                after.release.releaseDate
                                    ?.let(::parsePremierDate)
                                    ?.format(dateTimeFormatter)
                                    ?.let(::DateSeparatorItem)
                            }

                            else -> null
                        }
                    }
            }
    }

    private fun parsePremierDate(date: String): LocalDate {
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
    }
}
