package tech.dalapenko.data.releases.repository

import androidx.paging.Pager
import tech.dalapenko.data.releases.model.Release

interface ReleaseRepository {

    fun getReleasePager(
        year: Int,
        month: String
    ): Pager<Int, Release>
}