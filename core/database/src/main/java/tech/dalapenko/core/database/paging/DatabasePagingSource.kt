package tech.dalapenko.core.database.paging

import android.annotation.SuppressLint
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.RoomDatabase
import androidx.room.getQueryDispatcher
import androidx.room.paging.util.ThreadSafeInvalidationObserver
import androidx.room.paging.util.getLimit
import androidx.room.paging.util.getOffset
import androidx.room.withTransaction
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger

/**
 * Based on androidx.room.paging.LimitOffsetPagingSource
 */
@SuppressLint("RestrictedApi")
abstract class DatabasePagingSource<R: Any>(
    private val database: RoomDatabase,
    trackingTables: Array<out String>
) : PagingSource<Int, R>() {

    private val observer by lazy {
        ThreadSafeInvalidationObserver(trackingTables, (::invalidate))
    }

    private val itemCount: AtomicInteger = AtomicInteger(INITIAL_ITEM_COUNT)

    abstract suspend fun fetchPageFromDatabase(limit: Int, offset: Int): List<R>
    abstract suspend fun getDatabaseItemCount(): Int

    final override fun getRefreshKey(state: PagingState<Int, R>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        return maxOf(0, anchorPosition - (state.config.initialLoadSize / 2))
    }

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, R> = withContext(database.getQueryDispatcher()) {
            observer.registerIfNecessary(database)
            val tempCount = itemCount.get()
            val loadResult = try {
                if (tempCount == INITIAL_ITEM_COUNT) {
                    initialLoad(params)
                } else {
                    nonInitialLoad(params, tempCount)
                }
            } catch (e: Exception) {
                LoadResult.Error(e)
            }

            return@withContext loadResult
    }

    private suspend fun initialLoad(params: LoadParams<Int>): LoadResult<Int, R> {
        return database.withTransaction {
            val tempCount = getDatabaseItemCount()
            itemCount.set(tempCount)
            loadData(params, tempCount)
        }
    }

    @SuppressLint("RestrictedApi")
    private suspend fun nonInitialLoad(params: LoadParams<Int>, itemCount: Int): LoadResult<Int, R> {
        val loadResult = loadData(params, itemCount)
        database.invalidationTracker.refreshVersionsSync()
        return if (invalid) LoadResult.Invalid() else loadResult
    }

    private suspend fun loadData(params: LoadParams<Int>, itemCount: Int): LoadResult<Int, R> {
        val key = params.key ?: 0
        val limit = getLimit(params, key)
        val offset = getOffset(params, key, itemCount)

        val data = fetchPageFromDatabase(limit, offset)

        val nextPosToLoad = offset + data.size
        val hasNotNextPage = data.isEmpty() || data.size < limit || nextPosToLoad >= itemCount
        val nextKey = if (hasNotNextPage) null else nextPosToLoad
        val prevKey = if (offset <= 0 || data.isEmpty()) null else offset

        return LoadResult.Page(
            data = data,
            prevKey = prevKey,
            nextKey = nextKey,
            itemsBefore = offset,
            itemsAfter = maxOf(0, itemCount - nextPosToLoad)
        )
    }
}

private const val INITIAL_ITEM_COUNT = -1
